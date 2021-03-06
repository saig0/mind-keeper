package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._
import org.junit._
import Assert._
import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import com.google.appengine.api.datastore.Query.FilterOperator
import de.htwk.openNoteKeeper.shared._
import com.google.appengine.api.datastore.KeyFactory

class DataStoreTest extends LocalTestService with Persistence {

  val userId = "test"

  val group = new Group("root")
  val whiteboard = new WhiteBoard("test", group)
  val note = new Note(whiteboard, "title", "content", new Coordinate(0, 0), new Coordinate(0, 0))

  val datastore = new AnnotationObjectDatastore()

  @Test
  def findByType {
    datastore.store(note)
    val result = datastore.find(classOf[Note])

    assertEquals(true, result.hasNext());
    result.next()
    assertEquals(false, result.hasNext());
  }

  @Test
  def findQuery {
    datastore.store(note)
    val result = datastore.find().`type`(classOf[Note]).
      addFilter("title", FilterOperator.EQUAL, "title").returnResultsNow()

    assertEquals(true, result.hasNext());
    assertEquals(note, result.next())
    assertEquals(false, result.hasNext());
  }

  @Test
  def loadByKey {
    val key = datastore.store(note)
    val result: Note = datastore.load(key)

    assertEquals(note, result);
  }

  @Test
  def keyFactory {
    val key = datastore.store(note)
    note.key = key
    datastore.update(note)
    val keyAsString = KeyFactory.keyToString(note.key)

    val restoreKey = KeyFactory.stringToKey(keyAsString)
    val result: Note = datastore.load(restoreKey)

    assertEquals(note, result);
  }

  @Test
  def loadEmbeddedChild {
    val key = datastore.store(note)
    val result: Note = datastore.load(key)

    assertEquals(note.position, result.position);
    assertEquals(note.size, result.size);
  }

  @Test
  def loadChild {
    val key = datastore.store(group)
    group.whiteBoards.add(whiteboard)
    val result: Group = datastore.load(key)

    assertEquals(group.title, result.title);
    assertEquals(group.whiteBoards, result.whiteBoards);
    assertEquals(group, result.whiteBoards.get(0).group);
  }

  @Test
  def loadChildAndParent {
    val key = datastore.store(group)
    val keyOfWhiteBoard = datastore.store(whiteboard)
    group.whiteBoards.add(whiteboard)
    val result = datastore.find(classOf[WhiteBoard])

    assertEquals(true, result.hasNext())
    val resultWhiteboard = result.next()

    assertEquals(whiteboard.title, resultWhiteboard.title)
    assertEquals(whiteboard.group, resultWhiteboard.group)

    assertEquals(false, result.hasNext());
  }

  @Test
  def update {
    val key = datastore.store(note)
    note.content = "new"
    datastore.update(note)
    val result: Note = datastore.load(key)

    assertEquals(note, result);
  }

  @Test
  def updateByKey {
    val key = datastore.store(note)
    note.content = "new"
    datastore.update(note, key)
    val result: Note = datastore.load(key)

    assertEquals(note, result);
  }

  @Test
  def delete {
    val key = datastore.store(note)
    datastore.delete(note)
    val result = datastore.find(classOf[Note])

    assertEquals(false, result.hasNext());
  }

}