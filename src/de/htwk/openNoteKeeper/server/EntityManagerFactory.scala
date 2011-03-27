package de.le.mfw.server

import javax.jdo.JDOHelper

object EntityManagerFactory {
  private val factory = JDOHelper.getPersistenceManagerFactory("transactions-optional")

  val persistenceManager = factory.getPersistenceManager
}
