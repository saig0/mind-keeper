package de.htwk.openNoteKeeper.server
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import de.htwk.openNoteKeeper.shared._
import com.google.appengine.api.users.UserServiceFactory
import de.htwk.openNoteKeeper.client.main.service.UserService
import scala.collection.JavaConversions._

class UserServiceImpl extends RemoteServiceServlet with UserService {

  val userService = UserServiceFactory.getUserService()

  def isLoggedIn = userService.isUserLoggedIn

  def getLoginUrl(openIdProvider: String) = userService.createLoginURL("/", null, openIdProvider, Set[String]())

  def getLoginUrlsForOpenIdProviders = {
    var providers = new java.util.HashMap[OpenIdProvider, String]()
    for (provider <- (OpenIdProvider.values() map ((provider) => provider -> getLoginUrl(provider.getUrl))).toMap[OpenIdProvider, String]) { providers += provider }
    providers
  }

  def getLogoutUrl = userService.createLogoutURL("/")

  def getUser = {
    val user = userService.getCurrentUser
    val id = user.getUserId
    val nick = user.getNickname
    val email = user.getEmail
    new UserDTO(id, nick, email)
  }
}