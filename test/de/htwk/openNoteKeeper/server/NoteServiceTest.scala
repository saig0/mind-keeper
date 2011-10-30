package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._
import de.htwk.openNoteKeeper.server.model.Note._
import org.junit._
import Assert._
import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import de.htwk.openNoteKeeper.shared._
import de.htwk.openNoteKeeper.shared.GroupDTO._
import com.google.gwt.user.client.rpc.SerializableException

class NoteServiceTest extends LocalTestService with Persistence {

  val service = new NoteServiceImpl

  val user = new User("test")

  @Before
  def setup {
    persist(user)
  }

  @Test
  def getRootGroupForUserIfNoExist {
    val groups = service.getAllGroupsForUser(user.key)

    assertNotNull(groups)
    assertEquals(1, groups.size)

    val group = groups.get(0)
    assertNotNull(group.getKey())
    assertEquals(AccessRole.Owner, group.getAccessRole())
    assertEquals("my notes", group.getTitle())
    assertTrue(group.getSubGroups().isEmpty())
    assertTrue(group.getWhiteBoards().isEmpty())
  }

  @Test
  def getRootGroupForUserIfExist {
    service.getAllGroupsForUser(user.key)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)

    val group = groups.get(0)
    assertNotNull(group.getKey())
    assertEquals(AccessRole.Owner, group.getAccessRole())
    assertEquals("my notes", group.getTitle())
    assertTrue(group.getSubGroups().isEmpty())
    assertTrue(group.getWhiteBoards().isEmpty())
  }

  @Test
  def createNewGroupForUser {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    service.createGroupForUser(user.key, rootGroup_.getKey(), "new group")

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val subGroups = rootGroup.getSubGroups()
    assertEquals(1, subGroups.size)

    val group = subGroups.get(0)
    assertEquals(AccessRole.Owner, group.getAccessRole())
    assertEquals("new group", group.getTitle())
    assertTrue(group.getSubGroups().isEmpty())
    assertTrue(group.getWhiteBoards().isEmpty())
  }

  @Test
  def removeRootGroupThrowException {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    try {
      service.removeGroup(user.key, rootGroup_.getKey())
      fail("should not be allowed")
    } catch {
      case e: SerializableException =>
      case e                        => throw e
    }
  }

  @Test
  def removeGroupForUser {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group_ = service.createGroupForUser(user.key, rootGroup_.getKey(), "new group")
    service.removeGroup(user.key, group_.getKey())

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val group = groups.get(0)
    assertTrue(group.getSubGroups().isEmpty())
  }

  @Test
  def createWhiteBoard {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    service.createWhiteBoard(rootGroup_.getKey(), "new whiteboard")

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val group = groups.get(0)
    assertEquals(1, group.getWhiteBoards.size)
    val whiteboard = group.getWhiteBoards.get(0)
    assertEquals("new whiteboard", whiteboard.getTitle())
  }

}