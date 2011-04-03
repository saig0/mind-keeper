package de.htwk.openNoteKeeper.server.model

import javax.jdo.annotations._

@PersistenceCapable
class Note(
  @Persistent var ownerId: String,
  @Persistent var title: String,
  @Persistent var content: String) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  var id: java.lang.Long = null

}