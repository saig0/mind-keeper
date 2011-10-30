package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._
import de.htwk.openNoteKeeper.server.model.Note._
import org.junit._
import Assert._
import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import com.google.appengine.api.datastore.Query.FilterOperator
import de.htwk.openNoteKeeper.shared._
import com.google.appengine.api.datastore.KeyFactory

class DataStoreTest extends LocalTestService with Persistence {

  @Test
  def findByType {
    val group = new Group("root")
    persist(group)
    val result = findAllObjects(classOf[Group])

    assertFalse(result.isEmpty)
    assertEquals(1, result.size)
  }

  @Test
  def loadByKey {
    val group = new Group("root")
    persist(group)

    findObjectByKey(group.key, classOf[Group]) match {
      case None => fail("no object found with key")
      case Some(g) => {
        assertEquals(group.title, g.title)
      }
    }
  }

  @Test
  def loadChild {
    val group = new Group("root")
    persist(group)

    val whiteboard = new WhiteBoard("test", group.key)
    persist(whiteboard)

    update[Group](group.key, classOf[Group], { g =>
      g.whiteBoards.add(whiteboard.key)
    })

    findObjectByKey(group.key, classOf[Group]) match {
      case None => fail("no object found with key")
      case Some(g) => {
        assertEquals(group.title, g.title)
        assertEquals(1, g.whiteBoards.size)
        val keyOfWhiteboard: String = g.whiteBoards.get(0)
        assertEquals(whiteboard.key, keyOfWhiteboard)

        findObjectByKey(keyOfWhiteboard, classOf[WhiteBoard]) match {
          case None => fail("no object found with key")
          case Some(w) => {
            val keyOfGroup: String = w.group
            assertEquals(group.key, keyOfGroup)
          }
        }

      }
    }
  }

  @Test
  def keyFactory {
    val group = new Group("root")
    persist(group)

    val key: String = group.key
    findObjectByKey(key, classOf[Group]) match {
      case None    => fail("no object found with key")
      case Some(g) => assertEquals(group.title, g.title)
    }
  }

  @Test
  def update {
    val group = new Group("root")
    persist(group)

    group.title = "my group"
    persist(group)

    findObjectByKey(group.key, classOf[Group]) match {
      case None    => fail("no object found with key")
      case Some(g) => assertEquals(group.title, g.title)
    }
  }

  @Test
  def updateByKey {
    val group = new Group("root")
    persist(group)

    update[Group](group.key, classOf[Group], g => g.title = "my group")

    findObjectByKey(group.key, classOf[Group]) match {
      case None    => fail("no object found with key")
      case Some(g) => assertEquals("my group", g.title)
    }
  }

  @Test
  def delete {
    val group = new Group("root")
    persist(group)

    delete(group.key, classOf[Group])

    findObjectByKey(group.key, classOf[Group]) match {
      case None    =>
      case Some(g) => fail("object found with key after delete")
    }
  }

  @Test
  def findQueryWithSingleObject {
    val group = new Group("root")
    persist(group)

    findObjectByCriteria(classOf[Group], new Criteria("title", "root")) match {
      case None    => fail("no object found with key")
      case Some(g) => assertEquals(group.title, g.title)
    }
  }

}