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
    assert(settings.getDefaultNoteColor() == "#F3F781")
    assert(settings.shouldUseRichTextEditor() == false)
    assert(settings.getTextEditorOptions().isEmpty() == true)
  }

  @Test
  def updateSettings {
    var settings = service.getSettings(user.key)
    assert(settings.shouldAskBeforeDelete() == false)
    assert(settings.getDefaultNoteColor() == "#F3F781")
    assert(settings.shouldUseRichTextEditor() == false)
    assert(settings.getTextEditorOptions().isEmpty() == true)

    settings.setShouldAskBeforeDelete(true)
    settings.setDefaultNoteColor("#ffffff")
    settings.setShouldUseRichTextEditor(true)
    val textEditorOptions = new java.util.LinkedList[String]()
    textEditorOptions.add("Bold")
    settings.setTextEditorOptions(textEditorOptions)
    service.updateSettings(settings)

    settings = service.getSettings(user.key)
    assert(settings.shouldAskBeforeDelete() == true)
    assert(settings.getDefaultNoteColor() == "#ffffff")
    assert(settings.shouldUseRichTextEditor() == true)
    assert(settings.getTextEditorOptions().size() == 1)
    assert(settings.getTextEditorOptions().contains("Bold") == true)
  }

}