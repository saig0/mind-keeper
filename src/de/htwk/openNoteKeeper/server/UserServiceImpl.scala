package de.htwk.openNoteKeeper.server
import com.google.gwt.user.server.rpc.RemoteServiceServlet

import de.htwk.openNoteKeeper.shared.UserDTO

import com.google.appengine.api.users.UserServiceFactory

import de.htwk.openNoteKeeper.client.main.service.UserService

class UserServiceImpl extends RemoteServiceServlet with UserService {

  val userService = UserServiceFactory.getUserService()

  def isLoggedIn = userService.isUserLoggedIn

  def getLoginUrl = userService.createLoginURL("/")

  def getLogoutUrl = userService.createLogoutURL("/")

  def getUser = {
    val user = userService.getCurrentUser
    val id = user.getUserId
    val nick = user.getNickname
    val email = user.getEmail
    new UserDTO(id, nick, email)
  }
}