package de.htwk.openNoteKeeper.server.model

import javax.jdo.annotations._
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
class Note(
  @Persistent var ownerId: String,
  @Persistent var title: String,
  @Persistent var content: Text,
  @Persistent @Embedded var position: Position,
  @Persistent @Embedded var size: Size) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  var id: java.lang.Long = null
}