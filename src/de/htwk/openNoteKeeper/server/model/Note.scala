package de.htwk.openNoteKeeper.server.model

import com.google.appengine.api.datastore.Text
import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey
import javax.jdo.annotations.IdGeneratorStrategy
import javax.jdo.annotations.Embedded
import javax.jdo.annotations.Extension
import javax.jdo.annotations.Column
import com.google.appengine.api.datastore.Key

object Note {
  implicit def textToString(t: Text) = t.getValue

  implicit def stringToText(s: String) = new Text(s)
}

@PersistenceCapable
class Note(
    @Persistent var title: String,
    @Persistent var content: Text,
    @Persistent var color: String,
    @Persistent var whiteboard: Key,
    @Persistent var width: Int,
    @Persistent var height: Int,
    @Persistent var left: Int,
    @Persistent var top: Int) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  var key: String = _

}