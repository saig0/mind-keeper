package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._
import org.junit._
import Assert._
import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import de.htwk.openNoteKeeper.shared._
import de.htwk.openNoteKeeper.shared.GroupDTO._

class NoteServiceTest extends LocalTestService with Persistence {

  val service = new NoteServiceImpl

  var userId: String = _
  val user = new User("test")
  val group = new Group("root")
  val whiteboard = new WhiteBoard("test", group)
  val note = new Note(whiteboard, "title", "content", new Coordinate(0, 0), new Coordinate(0, 0))

  @Before
  def setup {
    userId = store(user)
  }

  @Test
  def findByKey {
    val key: String = store(note)
    assertEquals(key, note.key)

    val result: Note = findByKey(key)
    assertEquals(note, result)
  }

  @Test
  def getRootGroupForUserIfNoExist {
    val groups = service.getAllGroupsForUser(userId)

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
    val rootGroup = new Group("my own notes")
    store(rootGroup)
    val owner = new Authority(userId, rootGroup, AccessRole.Owner)
    store(owner)

    user.authorities.add(owner)
    update(user)

    val groups = service.getAllGroupsForUser(userId)
    assertEquals(1, groups.size)

    val group = groups.get(0)
    assertNotNull(group.getKey())
    assertEquals(AccessRole.Owner, group.getAccessRole())
    assertEquals("my own notes", group.getTitle())
    assertTrue(group.getSubGroups().isEmpty())
    assertTrue(group.getWhiteBoards().isEmpty())
  }

  @Test
  def createNewGroupForUser {
    val groups = service.getAllGroupsForUser(userId)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    service.createGroupForUser(userId, rootGroup, "new group")

    val result = service.getAllGroupsForUser(userId)
    assertEquals(2, groups.size)

    val group = result.get(0)
    assertNotNull(group.getKey())
    assertEquals(AccessRole.Owner, group.getAccessRole())
    assertEquals("new group", group.getTitle())
    assertTrue(group.getSubGroups().isEmpty())
    assertTrue(group.getWhiteBoards().isEmpty())
  }

}