package de.htwk.openNoteKeeper.server
import org.junit._
import Assert._
import de.htwk.openNoteKeeper.server.model.User

class UserServiceTest extends LocalTestService with Persistence {

  val service = new UserServiceImpl

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

}