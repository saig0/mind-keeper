package de.htwk.openNoteKeeper.server.model
import javax.jdo.annotations.PersistenceCapable
import javax.jdo.annotations.EmbeddedOnly
import javax.jdo.annotations.Persistent

@PersistenceCapable
@EmbeddedOnly
class Coordinate(
    @Persistent var x: Int,
    @Persistent var y: Int) {

}