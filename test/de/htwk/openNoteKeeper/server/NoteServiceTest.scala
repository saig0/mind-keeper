package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._
import org.junit._
import Assert._
import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import de.htwk.openNoteKeeper.shared.CoordinateDTO

class NoteServiceTest extends LocalTestService with Persistence {

  val service = new NoteServiceImpl

  val userId = "test"
  val note = new Note(userId, "title", "content", new Coordinate(0, 0), new Coordinate(0, 0))

  @Test
  def findByKey {
    val key: String = store(note)
    assertEquals(key, note.key)

    val result: Note = findByKey(key)
    assertEquals(note, result)
  }

  @Test
  def createNewNote {
    val dto = service.createNoteForUser(userId, "title", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    assertNotNull(dto.getKey())

    val result: Note = findByKey(dto.getKey())
    assertEquals(userId, result.ownerId)
    assertEquals("title", result.title)
  }

  @Test
  def findByOwnerId {
    val key = store(note)

    val result = service.getAllNotesForUser(userId)
    assertEquals(1, result)
  }

  @Test
  def delete {
    val dto = service.createNoteForUser(userId, "title", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    service.removeNoteOfUser(userId, dto)

    val result: Note = findByKey(dto.getKey())
    assertNull(result)
  }

}