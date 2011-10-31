package de.htwk.openNoteKeeper.server
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import de.htwk.openNoteKeeper.shared._
import com.google.appengine.api.users.UserServiceFactory
import de.htwk.openNoteKeeper.client.main.service.UserService
import scala.collection.JavaConversions._
import de.htwk.openNoteKeeper.server.model.User

class UserServiceImpl extends RemoteServiceServlet with UserService with Persistence {

  val userService = UserServiceFactory.getUserService()

  def isLoggedIn = userService.isUserLoggedIn

  def getLoginUrl(openIdProvider: String) = userService.createLoginURL("/", null, openIdProvider, new java.util.HashSet[String]())

  def getLoginUrlsForOpenIdProviders = {
    var providers = new java.util.HashMap[OpenIdProvider, java.lang.String]()
    for (provider <- (OpenIdProvider.values() map ((provider) => provider -> getLoginUrl(provider.getUrl))).toMap[OpenIdProvider, String]) { providers += provider }
    providers
  }

  def getLogoutUrl = userService.createLogoutURL("/")

  def getUser = {
    val currentUser = userService.getCurrentUser
    val id = currentUser.getUserId
    val nick = currentUser.getNickname
    val email = currentUser.getEmail

    findObjectByCriteria(classOf[User], new Criteria("userId", id)) match {
      case None => {
        val user = new User(id)
        persist(user)
        new UserDTO(user.key, nick, email)
      }
      case Some(user) => new UserDTO(user.key, nick, email)
    }
  }
}