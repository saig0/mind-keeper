package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.shared.Coordinate

import scala.collection.mutable.ListBuffer

import de.htwk.openNoteKeeper.server.model._

import de.htwk.openNoteKeeper.shared.NoteDTO

import de.htwk.openNoteKeeper.client.note.service.NoteService
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import com.google.appengine.api.datastore.Text;

class NoteServiceImpl extends RemoteServiceServlet with NoteService with Persistence {

  implicit def javaToScalaInt(d: java.lang.Integer) = d.intValue

  implicit def textToString(t: Text) = t.getValue

  implicit def stringToText(s: String) = new Text(s)

  implicit def coordinateToPosition(c: Coordinate) = new Position(c.getX, c.getY)

  implicit def coordinateToSize(c: Coordinate) = new Size(c.getX, c.getY)

  def getAllNotesForUser(userId: String) = {
    val noteDtos = ListBuffer[NoteDTO]()
    findObjectsByCriteria(classOf[Note], new Criteria("ownerId", userId)) match {
      case Some(notes) => notes.map { note =>
        noteDtos += createDtoOfNote(note)
      }
      case None =>
    }

    val result = new java.util.LinkedList[NoteDTO]()
    noteDtos foreach (note => result.add(note))
    result
  }

  private def createDtoOfNote(note: Note) =
    new NoteDTO(note.id, note.title, note.content,
      new Coordinate(note.position.left, note.position.top),
      new Coordinate(note.size.width, note.size.height))

  def createNoteForUser(userId: String, title: String, position: Coordinate, size: Coordinate) = {
    val note = new Note(userId, title, "", position, size)
    persist(note)
    createDtoOfNote(note)
  }

  def updateNoteOfUser(userId: String, noteDto: NoteDTO) = {
    val id = noteDto.getId.asInstanceOf[Long]
    findObjectById(id, classOf[Note]) match {
      case Some(note) => update[Note](id, classOf[Note], { note =>
        note.title = noteDto.getTitle
        note.content = noteDto.getContent
        note.position = noteDto.getPosition
        note.size = noteDto.getSize
      })
      case None =>
    }
  }

  def removeNoteOfUser(userId: String, noteDto: NoteDTO) = {
    val noteId = noteDto.getId.asInstanceOf[Long]
    delete(noteId, classOf[Note])
  }
}