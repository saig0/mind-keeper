package de.htwk.openNoteKeeper.server
import scala.collection.mutable.ListBuffer

import de.htwk.openNoteKeeper.server.model.Note

import de.htwk.openNoteKeeper.shared.NoteDTO

import de.htwk.openNoteKeeper.client.note.service.NoteService
import com.google.gwt.user.server.rpc.RemoteServiceServlet

class NoteServiceImpl extends RemoteServiceServlet with NoteService with Persistence {

  def getAllNotesForUser(userId: String) = {
    val noteDtos = ListBuffer[NoteDTO]()
    findObjectsByCriteria(classOf[Note], new Criteria("ownerId", userId)) match {
      case Some(notes) => notes.map { note => noteDtos += new NoteDTO(note.id, note.title, note.content) }
      case None =>
    }
    noteDtos.toList.asInstanceOf[java.util.List[NoteDTO]]
  }

  def createNoteForUser(userId: String, title: String) = {
    val note = new Note(userId, title, "")
    persist(note)
    new NoteDTO(note.id, note.title, note.content)
  }

  def updateNoteOfUser(userId: String, noteDto: NoteDTO) = {
    val id = noteDto.getId.asInstanceOf[Long]
    findObjectById(id, classOf[Note]) match {
      case Some(note) => update[Note](id, classOf[Note], { note =>
        note.title = noteDto.getTitle
        note.content = noteDto.getContent
      })
      case None =>
    }
  }

  def removeNoteOfUser(userId: String, noteId: java.lang.Long) = {
    val id = noteId.asInstanceOf[Long]
    findObjectById(id, classOf[Note]) match {
      case Some(note) => delete(note)
      case None =>
    }
  }
}