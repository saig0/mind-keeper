package de.htwk.openNoteKeeper.server

import scala.collection.mutable.ListBuffer
import de.htwk.openNoteKeeper.server.model._
import de.htwk.openNoteKeeper.server.model.Note._
import de.htwk.openNoteKeeper.client.note.service.NoteService
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import com.google.appengine.api.datastore.Text
import scala.collection.JavaConversions._
import com.google.appengine.api.datastore.Key
import de.htwk.openNoteKeeper.shared._
import com.google.gwt.user.client.rpc.SerializableException

class NoteServiceImpl extends RemoteServiceServlet with NoteService with Persistence {

  def getAllGroupsForUser(userKey: String) = {
    findObjectByKey(userKey, classOf[User]) match {
      case None => throw new SerializableException("no user with given key found")
      case Some(user) => {
        if (user.authorities.isEmpty) {
          createRootGroupForUser(user)
        }

        val groups = new java.util.LinkedList[GroupDTO]()
        user.authorities foreach { authorityKey =>
          findObjectByKey(authorityKey, classOf[Authority]) match {
            case None            => throw new SerializableException("no authority with given key found")
            case Some(authority) => groups.add(createGroupDtoForKey(authority.group))
          }
        }
        groups
      }
    }
  }

  private def createRootGroupForUser(user: User) {
    val rootGroup = new Group("my notes")
    persist(rootGroup)
    val owner = new Authority(user.key, rootGroup.key, AccessRole.Owner)
    persist(owner)
    update[Group](rootGroup.key, classOf[Group], group => group.authorities.add(owner.key))
    update[User](user.key, classOf[User], user => user.authorities.add(owner.key))
    user.authorities.add(owner.key)
  }

  private def createGroupDtoForKey(key: String): GroupDTO = {
    val group = findGroupByKey(key)
    val dto = new GroupDTO(group.key, group.title, AccessRole.Owner)
    group.subGroups foreach (subGroup => dto.addSubGroup(createGroupDtoForKey(subGroup)))
    group.whiteBoards foreach (whiteBoard => dto.addWhiteBoard(createWhiteBoardDtoForKey(whiteBoard)))
    dto
  }

  def createGroupForUser(userKey: String, parentGroupKey: String, title: String) = {
    val parentGroup = findGroupByKey(parentGroupKey)
    val group = new Group(title)
    group.parentGroup = parentGroup.key
    persist(group)

    val owner = new Authority(userKey, group.key, AccessRole.Owner)
    persist(owner)

    update[Group](group.key, classOf[Group], group => group.authorities.add(owner.key))
    update[Group](parentGroupKey, classOf[Group], parentGroup => parentGroup.subGroups.add(group.key))

    new GroupDTO(group.key, title, owner.accessRole)
  }

  private def findGroupByKey(key: String) = findObjectByKey(key, classOf[Group]) match {
    case None        => throw new SerializableException("no group with given key found")
    case Some(group) => group
  }

  private def createWhiteBoardDtoForKey(whiteBoardKey: String) = {
    val whiteboard = findWhiteBoardByKey(whiteBoardKey)
    val dto = new WhiteBoardDTO(whiteboard.key, whiteboard.title)
    whiteboard.notes foreach (note => dto.addNote(createNoteDtoForKey(note)))
    dto
  }

  private def findWhiteBoardByKey(whiteBoardKey: String) = findObjectByKey(whiteBoardKey, classOf[WhiteBoard]) match {
    case None             => throw new SerializableException("no white board with given key found")
    case Some(whiteboard) => whiteboard
  }

  private def createNoteDtoForKey(noteKey: String) = {
    val note = findNoteByKey(noteKey)
    new NoteDTO(note.key, note.title, note.content, new CoordinateDTO(note.width, note.height), new CoordinateDTO(note.left, note.top))
  }

  private def findNoteByKey(noteKey: String) = findObjectByKey(noteKey, classOf[Note]) match {
    case None       => throw new SerializableException("no note with given key found")
    case Some(note) => note
  }

  def removeGroup(userKey: String, groupKey: String) {
    val group = findGroupByKey(groupKey)
    if (group.parentGroup == null) throw new SerializableException("deleting the root group is not allowed")
    update[Group](group.parentGroup, classOf[Group], { parentGroup =>
      val groupKey: Key = group.key
      parentGroup.subGroups.remove(groupKey)
    })
    group.authorities foreach (authorityKey => delete(authorityKey, classOf[Authority]))
    group.subGroups foreach (subGroup => removeGroup(userKey, subGroup))
    group.whiteBoards foreach (whiteboard => removeWhiteBoard(whiteboard))
    delete(group.key, classOf[Group])
  }

  def createWhiteBoard(groupKey: String, title: String) = {
    val group = findGroupByKey(groupKey)
    val whiteBoard = new WhiteBoard(title, group.key)
    persist(whiteBoard)
    update[Group](group.key, classOf[Group], group => group.whiteBoards.add(whiteBoard.key))
    new WhiteBoardDTO(whiteBoard.key, title)
  }

  def removeWhiteBoard(whiteBoardKey: String) {
    val whiteboard = findWhiteBoardByKey(whiteBoardKey)
    update[Group](whiteboard.group, classOf[Group], { group =>
      val whiteBoardKey: Key = whiteboard.key
      group.whiteBoards.remove(whiteBoardKey)
    })
    whiteboard.notes foreach (note => removeNote(note))
    delete(whiteboard.key, classOf[WhiteBoard])
  }

  def createNote(whiteBoardKey: String, title: String,
                 position: CoordinateDTO, size: CoordinateDTO) = {
    val whiteboard = findWhiteBoardByKey(whiteBoardKey)
    val note = new Note(title, "", whiteboard.key, size.getY, size.getY, position.getX, position.getY)
    persist(note)
    update[WhiteBoard](whiteboard.key, classOf[WhiteBoard], whiteBoard => whiteBoard.notes.add(note.key))

    new NoteDTO(note.key, title, "", position, size)
  }

  def updateNote(noteDto: NoteDTO) {
    update[Note](noteDto.getKey(), classOf[Note], { note =>
      note.title = noteDto.getTitle()
      note.content = noteDto.getContent()
      note.width = noteDto.getPosition().getX
      note.height = noteDto.getPosition().getY
      note.left = noteDto.getSize().getX
      note.top = noteDto.getSize().getY
    })
  }

  def removeNote(noteKey: String) {
    val note = findNoteByKey(noteKey)
    update[WhiteBoard](note.whiteboard, classOf[WhiteBoard], { whiteBoard =>
      val noteKey: Key = note.key
      whiteBoard.notes.remove(noteKey)
    })
    delete(note.key, classOf[Note])
  }

  def moveGroup(groupKey: String, targetGroupKey: String, index: Integer) {
    val group = findGroupByKey(groupKey)

    update[Group](group.parentGroup, classOf[Group], { parentGroup =>
      val groupKey: Key = group.key
      parentGroup.subGroups.remove(groupKey)
    })

    update[Group](targetGroupKey, classOf[Group], { parentGroup =>
      if (index > parentGroup.subGroups.size) {
        val endOfList = parentGroup.subGroups.size
        parentGroup.subGroups.add(endOfList, group.key)
      } else if (index < 0) {
        val beginOfList = 0
        parentGroup.subGroups.add(beginOfList, group.key)
      } else {
        parentGroup.subGroups.add(index, group.key)
      }
    })

    update[Group](group.key, classOf[Group], group => group.parentGroup = targetGroupKey)
  }

  def moveWhiteBoard(whiteBoardKey: String, targetGroupKey: String, index: Integer) {
    val whiteboard = findWhiteBoardByKey(whiteBoardKey)

    update[Group](whiteboard.group, classOf[Group], { group =>
      val whiteboardKey: Key = whiteboard.key
      group.whiteBoards.remove(whiteboardKey)
    })

    update[Group](targetGroupKey, classOf[Group], { group =>
      if (index > group.whiteBoards.size) {
        val endOfList = group.whiteBoards.size
        group.whiteBoards.add(endOfList, whiteboard.key)
      } else if (index < 0) {
        val beginOfList = 0
        group.whiteBoards.add(beginOfList, whiteboard.key)
      } else {
        group.whiteBoards.add(index, whiteboard.key)
      }
    })

    update[WhiteBoard](whiteboard.key, classOf[WhiteBoard], whiteboard => whiteboard.group = targetGroupKey)
  }

}