package de.htwk.openNoteKeeper.server

import de.htwk.openNoteKeeper.server._
import de.htwk.openNoteKeeper.server.model._
import de.htwk.openNoteKeeper.server.model.Note._
import org.junit._
import Assert._
import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import com.google.appengine.api.datastore.Query.FilterOperator
import de.htwk.openNoteKeeper.shared._
import com.google.appengine.api.datastore.KeyFactory

class CipherTest extends CipherUtil {

  @Test
  def encryptText {
    val key = "key"
    val plaintext = "test"
    val ciphertext = encrypt(key, plaintext)

    assertFalse(plaintext == ciphertext)
  }

  @Test
  def decryptText {
    val key = "key"
    val plaintext = "test"
    val ciphertext = encrypt(key, plaintext)
    val decryptedText = decrypt(key, ciphertext)

    assertTrue(plaintext == decryptedText)
  }

}