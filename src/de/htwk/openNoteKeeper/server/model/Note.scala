package de.htwk.openNoteKeeper.server.model

import javax.jdo.annotations._
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
class Note(
  @Persistent var ownerId: String,
  @Persistent var title: String,
  @Persistent var content: Text,
  @Persistent var left: Int,
  @Persistent var top: Int,
  @Persistent var width: Int,
  @Persistent var height: Int) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  var id: java.lang.Long = null
}