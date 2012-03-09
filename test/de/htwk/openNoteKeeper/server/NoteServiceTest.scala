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
  def removeGroupWithWhiteBoardAndNotes {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group_ = service.createGroupForUser(user.key, rootGroup_.getKey(), "new group")
    val whiteboard_ = service.createWhiteBoard(group_.getKey(), "new whiteboard")
    val noteDTO = new NoteDTO("", "new note", "", "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    val note_ = service.createNote(whiteboard_.getKey(), noteDTO)
    service.removeGroup(user.key, group_.getKey())

    findObjectByKey(group_.getKey, classOf[Group]) match {
      case Some(_) => fail("found group after remove it")
      case None    =>
    }

    findObjectByKey(whiteboard_.getKey, classOf[WhiteBoard]) match {
      case Some(_) => fail("found whiteboard after remove it")
      case None    =>
    }

    findObjectByKey(note_.getKey, classOf[Note]) match {
      case Some(_) => fail("found note after remove it")
      case None    =>
    }
  }

  @Test
  def removeGroupWithSubGroups {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group_ = service.createGroupForUser(user.key, rootGroup_.getKey(), "new group")
    val subGroup_ = service.createGroupForUser(user.key, group_.getKey(), "new group")
    val whiteboard_ = service.createWhiteBoard(subGroup_.getKey(), "new whiteboard")
    service.removeGroup(user.key, group_.getKey())

    findObjectByKey(group_.getKey, classOf[Group]) match {
      case Some(_) => fail("found group after remove it")
      case None    =>
    }

    findObjectByKey(subGroup_.getKey, classOf[Group]) match {
      case Some(_) => fail("found group after remove it")
      case None    =>
    }

    findObjectByKey(whiteboard_.getKey, classOf[WhiteBoard]) match {
      case Some(_) => fail("found whiteboard after remove it")
      case None    =>
    }
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

  @Test
  def removeWhiteBoard {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard_ = service.createWhiteBoard(rootGroup_.getKey(), "new whiteboard")
    service.removeWhiteBoard(whiteboard_.getKey())

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val group = groups.get(0)
    assertEquals(0, group.getWhiteBoards.size)
  }

  @Test
  def removeWhiteBoardWithNotes {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard_ = service.createWhiteBoard(rootGroup_.getKey(), "new whiteboard")
    val noteDTO = new NoteDTO("", "new note", "", "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    val note_ = service.createNote(whiteboard_.getKey(), noteDTO)
    service.removeWhiteBoard(whiteboard_.getKey())

    findObjectByKey(whiteboard_.getKey, classOf[WhiteBoard]) match {
      case Some(_) => fail("found whiteboard after remove it")
      case None    =>
    }

    findObjectByKey(note_.getKey, classOf[Note]) match {
      case Some(_) => fail("found note after remove it")
      case None    =>
    }
  }

  @Test
  def createNote {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard_ = service.createWhiteBoard(rootGroup_.getKey(), "new whiteboard")
    val noteDTO = new NoteDTO("", "new note", "", "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    val note_ = service.createNote(whiteboard_.getKey(), noteDTO)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val group = groups.get(0)
    assertEquals(1, group.getWhiteBoards.size)
    val whiteboard = group.getWhiteBoards.get(0)
    assertEquals(1, whiteboard.getNotes().size)
    val note = whiteboard.getNotes().get(0)
    assertEquals("new note", note.getTitle())
    assertTrue(note.getContent().isEmpty())
  }

  @Test
  def removeNote {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard_ = service.createWhiteBoard(rootGroup_.getKey(), "new whiteboard")
    val noteDTO = new NoteDTO("", "new note", "", "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    val note_ = service.createNote(whiteboard_.getKey(), noteDTO)
    service.removeNote(note_.getKey())

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val group = groups.get(0)
    assertEquals(1, group.getWhiteBoards.size)
    val whiteboard = group.getWhiteBoards.get(0)
    assertEquals(0, whiteboard.getNotes().size)
  }

  @Test
  def updateNote {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard_ = service.createWhiteBoard(rootGroup_.getKey(), "new whiteboard")
    val noteDTO = new NoteDTO("", "new note", "", "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    val note_ = service.createNote(whiteboard_.getKey(), noteDTO)
    note_.setContent("text")
    note_.setPosition(new CoordinateDTO(1, 2))
    note_.setSize(new CoordinateDTO(3, 4))
    service.updateNote(note_)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val group = groups.get(0)
    assertEquals(1, group.getWhiteBoards.size)
    val whiteboard = group.getWhiteBoards.get(0)
    assertEquals(1, whiteboard.getNotes().size)
    val note = whiteboard.getNotes().get(0)
    assertEquals("new note", note.getTitle())
    assertEquals("text", note.getContent())
    assertEquals(note_.getPosition(), note.getPosition())
    assertEquals(note_.getSize(), note.getSize())
  }

  @Test
  def moveGroupChangeOrder {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group1 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 1")
    val group2 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 2")

    service.moveGroup(group2.getKey(), rootGroup_.getKey(), 0)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val subGroups = rootGroup.getSubGroups()
    assertEquals(2, subGroups.size)

    val newGroup1 = subGroups.get(0)
    assertEquals("group 2", newGroup1.getTitle())

    val newGroup2 = subGroups.get(1)
    assertEquals("group 1", newGroup2.getTitle())
  }

  @Test
  def moveGroup {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group1 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 1")
    val group2 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 2")

    service.moveGroup(group2.getKey(), group1.getKey(), 0)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val subGroups = rootGroup.getSubGroups()
    assertEquals(1, subGroups.size)

    val newGroup1 = subGroups.get(0)
    assertEquals("group 1", newGroup1.getTitle())

    assertEquals(1, newGroup1.getSubGroups().size)
    val newGroup2 = newGroup1.getSubGroups().get(0)
    assertEquals("group 2", newGroup2.getTitle())
  }

  @Test
  def moveGroupWithSubGroups {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group1 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 1")
    val subGroup = service.createGroupForUser(user.key, group1.getKey(), "sub group")

    val group2 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 2")

    service.moveGroup(group2.getKey(), rootGroup_.getKey(), 0)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val subGroups = rootGroup.getSubGroups()
    assertEquals(2, subGroups.size)

    val newGroup1 = subGroups.get(0)
    assertEquals("group 2", newGroup1.getTitle())

    val newGroup2 = subGroups.get(1)
    assertEquals("group 1", newGroup2.getTitle())

    assertEquals(1, newGroup2.getSubGroups().size)
    val subGroup_ = newGroup2.getSubGroups().get(0)
    assertEquals("sub group", subGroup_.getTitle())
  }

  @Test
  def moveGroupWithIllegalIndex {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val group1 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 1")
    val group2 = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 2")

    service.moveGroup(group1.getKey(), rootGroup_.getKey(), 2)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val subGroups = rootGroup.getSubGroups()
    assertEquals(2, subGroups.size)

    val newGroup1 = subGroups.get(0)
    assertEquals("group 2", newGroup1.getTitle())

    val newGroup2 = subGroups.get(1)
    assertEquals("group 1", newGroup2.getTitle())
  }

  @Test
  def moveWhiteBoardChangeOrder {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard1 = service.createWhiteBoard(rootGroup_.getKey(), "whiteboard 1")
    val whiteboard2 = service.createWhiteBoard(rootGroup_.getKey(), "whiteboard 2")

    service.moveWhiteBoard(whiteboard1.getKey(), rootGroup_.getKey(), 1)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val whiteboards = rootGroup.getWhiteBoards()
    assertEquals(2, whiteboards.size)

    val newWhiteboard1 = whiteboards.get(0)
    assertEquals("whiteboard 2", newWhiteboard1.getTitle())

    val newWhiteBoard2 = whiteboards.get(1)
    assertEquals("whiteboard 1", newWhiteBoard2.getTitle())
  }

  @Test
  def moveWhiteBoard {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard1 = service.createWhiteBoard(rootGroup_.getKey(), "whiteboard 1")
    val whiteboard2 = service.createWhiteBoard(rootGroup_.getKey(), "whiteboard 2")

    val group = service.createGroupForUser(user.key, rootGroup_.getKey(), "group 1")

    service.moveWhiteBoard(whiteboard1.getKey(), group.getKey(), 0)

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val whiteboards = rootGroup.getWhiteBoards()
    assertEquals(1, whiteboards.size)

    val newWhiteboard1 = whiteboards.get(0)
    assertEquals("whiteboard 2", newWhiteboard1.getTitle())

    val subGroups = rootGroup.getSubGroups()
    assertEquals(1, subGroups.size)

    val subgroup = subGroups.get(0)
    assertEquals("group 1", subgroup.getTitle())

    val whiteboardsOfSubGroup = subgroup.getWhiteBoards()
    assertEquals(1, whiteboardsOfSubGroup.size)

    val newWhiteboard2 = whiteboardsOfSubGroup.get(0)
    assertEquals("whiteboard 1", newWhiteboard2.getTitle())
  }

  @Test
  def moveNote {
    val groups_ = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups_.size)
    val rootGroup_ = groups_.get(0)

    val whiteboard1_ = service.createWhiteBoard(rootGroup_.getKey(), "whiteboard 1")
    val whiteboard2_ = service.createWhiteBoard(rootGroup_.getKey(), "whiteboard 2")

    val noteDTO = new NoteDTO("", "new note", "", "", new CoordinateDTO(0, 0), new CoordinateDTO(0, 0))
    val note_ = service.createNote(whiteboard1_.getKey(), noteDTO)

    service.moveNote(note_.getKey(), whiteboard2_.getKey())

    val groups = service.getAllGroupsForUser(user.key)
    assertEquals(1, groups.size)
    val rootGroup = groups.get(0)

    val whiteboards = rootGroup.getWhiteBoards()
    assertEquals(2, whiteboards.size)

    val whiteboard1 = whiteboards.get(0)
    assertTrue(whiteboard1.getNotes().isEmpty)

    val whiteboard2 = whiteboards.get(1)
    assertEquals("whiteboard 2", whiteboard2.getTitle())

    assertEquals(1, whiteboard2.getNotes().size)
    val note = whiteboard2.getNotes().get(0)
    assertEquals(note_.getKey(), note.getKey())

  }
}