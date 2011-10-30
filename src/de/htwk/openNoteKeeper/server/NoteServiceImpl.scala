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

class NoteServiceImpl extends RemoteServiceServlet with NoteService with Persistence {

  implicit def note2Dto(note: Note) =
    new NoteDTO(note.key, note.title, note.content,
      new CoordinateDTO(note.position.x, note.position.y),
      new CoordinateDTO(note.size.x, note.size.x))

  // private def findNoteById(noteDto: NoteDTO) = findByKey[Note](noteDto.getKey())

  def getAllGroupsForUser(userId: String) = {
    //    val user: User = findByKey(userId)
    //
    //    if (user.authorities.isEmpty) {
    //      val rootGroup = new Group("my notes")
    //      store(rootGroup)
    //
    //      val owner = new Authority(userId, rootGroup, AccessRole.Owner)
    //      store(owner)
    //
    //      user.authorities.add(owner)
    //      update(user)
    //    }

    //    (ListBuffer[GroupDTO]() /: user.authorities) { (groups, authority) =>
    //      val group = authority.group
    //      groups += new GroupDTO(group.key, group.title, authority.accessRole)
    //    }.toList

    ListBuffer[GroupDTO]().toList
  }

  def createGroupForUser(userId: String, parentGroup: GroupDTO, title: String) = {
    //    val user: User = findByKey(userId)
    //
    //    val group = new Group(title)
    //    val key = store(group)
    //
    //    val owner = new Authority(userId, group, AccessRole.Owner)
    //    store(owner)
    //
    //    user.authorities.add(owner)
    //    update(user)

    new GroupDTO("key", title, AccessRole.Owner)
  }

  def removeGroup(group: GroupDTO) {

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