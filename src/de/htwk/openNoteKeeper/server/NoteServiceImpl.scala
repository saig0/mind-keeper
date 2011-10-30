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

  //  implicit def note2Dto(note: Note) =
  //    new NoteDTO(note.key, note.title, note.content,
  //      new CoordinateDTO(note.position.x, note.position.y),
  //      new CoordinateDTO(note.size.x, note.size.x))

  // private def findNoteById(noteDto: NoteDTO) = findByKey[Note](noteDto.getKey())

  def getAllGroupsForUser(userKey: String) = {
    findObjectByKey(userKey, classOf[User]) match {
      case None => throw new SerializableException("no user with given key found")
      case Some(user) => {
        if (user.authorities.isEmpty) {
          createRootGroupForUser(user)
        }

        (ListBuffer[GroupDTO]() /: user.authorities) { (groups, authorityKey) =>
          findObjectByKey(authorityKey, classOf[Authority]) match {
            case None            => throw new SerializableException("no authority with given key found")
            case Some(authority) => groups += createGroupDtoForKey(authority.group)
          }
        }.toList
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
    findObjectByKey(key, classOf[Group]) match {
      case None => throw new SerializableException("no group with given key found")
      case Some(group) => {
        val dto = new GroupDTO(group.key, group.title, AccessRole.Owner)
        group.subGroups foreach (subGroup => dto.addSubGroup(createGroupDtoForKey(subGroup)))
        dto
      }
    }
  }

  def createGroupForUser(userKey: String, parentGroupKey: String, title: String) = {
    findObjectByKey(parentGroupKey, classOf[Group]) match {
      case None => throw new SerializableException("no parent group with given key found")
      case Some(parentGroup) => {
        val group = new Group(title)
        group.parentGroup = parentGroup.key
        persist(group)

        val owner = new Authority(userKey, group.key, AccessRole.Owner)
        persist(owner)

        update[Group](group.key, classOf[Group], group => group.authorities.add(owner.key))
        update[Group](parentGroupKey, classOf[Group], parentGroup => parentGroup.subGroups.add(group.key))

        new GroupDTO(group.key, title, owner.accessRole)
      }
    }
  }

  def removeGroup(userKey: String, groupKey: String) {
    findObjectByKey(groupKey, classOf[Group]) match {
      case None => throw new SerializableException("no group with given key found")
      case Some(group) => {
        if (group.parentGroup == null) throw new SerializableException("deleting the root group is not allowed")
        update[Group](group.parentGroup, classOf[Group], { parentGroup =>
          val groupKey: Key = group.key
          parentGroup.subGroups.remove(groupKey)
        })
        group.authorities foreach (authorityKey => delete(authorityKey, classOf[Authority]))
        delete(group.key, classOf[Group])
      }
    }
  }

  def createWhiteBoard(group: GroupDTO, title: String) = {
    new WhiteBoardDTO("key", title)
  }

  def removeWhiteBoard(whiteBoard: WhiteBoardDTO) {

  }

  def createNote(whiteBoard: WhiteBoardDTO, title: String,
                 position: CoordinateDTO, size: CoordinateDTO) = {
    new NoteDTO("key", title, "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
  }

  def updateNote(note: NoteDTO) {

  }

  def removeNote(note: NoteDTO) {

  }

}