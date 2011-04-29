package de.htwk.openNoteKeeper.server.model

import javax.jdo.annotations._

@PersistenceCapable
@EmbeddedOnly
class Position(
  @Persistent var left: Int,
  @Persistent var top: Int) {
}