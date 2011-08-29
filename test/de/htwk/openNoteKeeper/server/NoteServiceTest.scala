package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.shared.Coordinate

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._

import org.junit._
import Assert._

class NoteServiceTest extends LocalTestService with Persistence {

  val service = new NoteServiceImpl

  val userId = "test"

  @Test
  def test {
    service.createNoteForUser(userId, "title", new Coordinate(0, 0), new Coordinate(0, 0))

    assertEquals(1, service.getAllNotesForUser(userId).size)
  }

}