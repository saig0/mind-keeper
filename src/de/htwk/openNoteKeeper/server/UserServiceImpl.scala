package de.htwk.openNoteKeeper.server
import com.google.gwt.user.server.rpc.RemoteServiceServlet
import de.htwk.openNoteKeeper.shared._
import com.google.appengine.api.users.UserServiceFactory
import de.htwk.openNoteKeeper.client.main.service.UserService
import scala.collection.JavaConversions._
import de.htwk.openNoteKeeper.server.model.User
import de.htwk.openNoteKeeper.server.model.Settings
import de.htwk.openNoteKeeper.shared.SettingsDTO
import com.google.gwt.user.client.rpc.SerializableException
import com.google.appengine.api.datastore.Key

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

  def getSettings(userKey: String) = {
    findObjectByKey(userKey, classOf[User]) match {
      case None => throw new SerializableException("no user with given key found")
      case Some(user) => {
        val settingsKey = user.settings
        if (settingsKey == null) {
          createSettings(userKey)
        } else {
          findSettings(settingsKey)
        }
      }
    }
  }

  private def createSettings(userKey: String): SettingsDTO = {
    val settings = new Settings()
    persist(settings)
    update[User](userKey, classOf[User], user => user.settings = settings.key)
    new SettingsDTO(settings.key, settings.shouldAskBeforeDelete, settings.defaultNoteColor)
  }

  private def findSettings(settingsKey: Key): SettingsDTO = {
    findObjectByKey(settingsKey, classOf[Settings]) match {
      case Some(settings) => {
        new SettingsDTO(settings.key, settings.shouldAskBeforeDelete, settings.defaultNoteColor)
      }
      case None => throw new SerializableException("no settings with given key found")
    }
  }

  def updateSettings(settingsDto: SettingsDTO) {
    update[Settings](settingsDto.getKey(), classOf[Settings], { settings =>
      settings.shouldAskBeforeDelete = settingsDto.shouldAskBeforeDelete()
      settings.defaultNoteColor = settingsDto.getDefaultNoteColor()
    })
  }
}