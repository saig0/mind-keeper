package de.htwk.openNoteKeeper.server

import scala.collection.mutable.ListBuffer
import de.htwk.openNoteKeeper.server.model._
import de.htwk.openNoteKeeper.shared.NoteDTO
import de.htwk.openNoteKeeper.client.note.service.NoteService
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import com.google.appengine.api.datastore.Text
import scala.collection.JavaConversions._
import de.htwk.openNoteKeeper.shared.CoordinateDTO
import com.google.appengine.api.datastore.Key

class NoteServiceImpl extends RemoteServiceServlet with NoteService with Persistence {

  implicit def textToString(t: Text) = t.getValue

  implicit def stringToText(s: String) = new Text(s)

  implicit def note2Dto(note: Note) =
    new NoteDTO(note.key, note.title, note.content,
      new CoordinateDTO(note.position.x, note.position.y),
      new CoordinateDTO(note.size.x, note.size.x))

  def getAllNotesForUser(userId: String) =
    (ListBuffer[NoteDTO]() /: findByQuery(classOf[Note]).isEqual("ownerId", userId).run)(_ += _).toList

  def createNoteForUser(userId: String, title: String, position: CoordinateDTO, size: CoordinateDTO) = {
    val note = new Note(userId, title, "", new Coordinate(position.getX, position.getY), new Coordinate(size.getX, size.getY))
    store(note)
    note
  }

  private def findNoteById(noteDto: NoteDTO) = findByKey[Note](noteDto.getKey())

  def updateNoteOfUser(userId: String, noteDto: NoteDTO) = {
    //TODO update
  }

  def removeNoteOfUser(userId: String, noteDto: NoteDTO) = {
    val note = findNoteById(noteDto)
    delete(note)
  }
}