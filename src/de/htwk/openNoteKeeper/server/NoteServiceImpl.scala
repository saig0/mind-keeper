package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.shared.Coordinate

import scala.collection.mutable.ListBuffer

import de.htwk.openNoteKeeper.server.model.Note

import de.htwk.openNoteKeeper.shared.NoteDTO

import de.htwk.openNoteKeeper.client.note.service.NoteService
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import com.google.appengine.api.datastore.Text;

class NoteServiceImpl extends RemoteServiceServlet with NoteService with Persistence {

  implicit def javaToScalaInt(d: java.lang.Integer) = d.intValue
  
  implicit def textToString(t: Text) = t.getValue
  
  implicit def stringToText(s: String) = new Text(s)
 
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
      new Coordinate(note.left, note.top),
      new Coordinate(note.width, note.height))

  def createNoteForUser(userId: String, title: String, position: Coordinate, size: Coordinate) = {
    val note = new Note(userId, title, "", position.getX, position.getY, size.getX, size.getY)
    persist(note)
    createDtoOfNote(note)
  }

  def updateNoteOfUser(userId: String, noteDto: NoteDTO) = {
    val id = noteDto.getId.asInstanceOf[Long]
    findObjectById(id, classOf[Note]) match {
      case Some(note) => update[Note](id, classOf[Note], { note =>
        note.title = noteDto.getTitle
        note.content = noteDto.getContent
        note.left = noteDto.getPosition.getX
        note.top = noteDto.getPosition.getY
        note.width = noteDto.getSize.getX
        note.height = noteDto.getSize.getY
      })
      case None =>
    }
  }

  def removeNoteOfUser(userId: String, noteDto: NoteDTO) = {
    val noteId = noteDto.getId.asInstanceOf[Long]
    delete(noteId, classOf[Note])
  }
}