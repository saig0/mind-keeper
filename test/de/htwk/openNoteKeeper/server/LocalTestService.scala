package de.htwk.openNoteKeeper.server
import com.google.appengine.api.channel.dev.LocalChannelService

import com.google.appengine.api.mail.dev.LocalMailService

import com.google.appengine.api.channel.ChannelServiceFactory

import com.google.appengine.api.datastore.DatastoreServiceFactory

import com.google.appengine.api.mail._

import com.google.appengine.api.datastore.dev._

import com.google.appengine.tools.development.testing._
import org.junit._
import org.junit._

trait LocalTestService {

  private val localDataStoreService =
    new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())

  def dataStoreService = LocalServiceTestHelper.getLocalService(LocalDatastoreService.PACKAGE).asInstanceOf[LocalDatastoreService]

  private val localMailService =
    new LocalServiceTestHelper(new LocalMailServiceTestConfig())

  def mailService = LocalServiceTestHelper.getLocalService(LocalMailService.PACKAGE).asInstanceOf[LocalMailService]

  private val localChannelService = new LocalServiceTestHelper(new LocalChannelServiceTestConfig())

  def channelService = LocalServiceTestHelper.getLocalService(LocalChannelService.PACKAGE).asInstanceOf[LocalChannelService]

  @Before
  def setUpTestService() {
    localDataStoreService.setUp()
    localMailService.setUp()
    localChannelService.setUp()
  }

  @After
  def tearDownTestService() {
    try {
      localDataStoreService.tearDown()
      localChannelService.tearDown()
      localMailService.tearDown()
    } catch {
      case e => e.printStackTrace()
    }
  }
}