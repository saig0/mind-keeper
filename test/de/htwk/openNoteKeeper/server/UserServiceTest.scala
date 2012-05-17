package de.htwk.openNoteKeeper.server
import org.junit._
import Assert._
import de.htwk.openNoteKeeper.server.model.User

class UserServiceTest extends LocalTestService with Persistence {

  val service = new UserServiceImpl

  val user = new User("test")

  @Before
  def setup {
    persist(user)
  }

  @Ignore //@Test
  def createUserIfNotExist {
    val user = service.getUser()

    findObjectByKey(user.getId(), classOf[User]) match {
      case None => fail("no user for given key found")
      case Some(u) => {
        assertEquals(u.key, user.getId())
      }
    }
  }

  @Test
  def createSettingsIfNotExist {
    val settings = service.getSettings(user.key)

    assertNotNull(settings)
    assert(settings.shouldAskBeforeDelete() == false)
  }

  @Test
  def updateSettings {
    var settings = service.getSettings(user.key)
    assert(settings.shouldAskBeforeDelete() == false)

    settings.setShouldAskBeforeDelete(true)
    service.updateSettings(settings)

    settings = service.getSettings(user.key)
    assert(settings.shouldAskBeforeDelete() == true)
  }

}