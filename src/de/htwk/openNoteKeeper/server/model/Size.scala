package de.htwk.openNoteKeeper.server.model

import javax.jdo.annotations._

@PersistenceCapable
@EmbeddedOnly
class Size(
  @Persistent var width: Int,
  @Persistent var height: Int) {
}