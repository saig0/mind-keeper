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

  implicit def coordinateToPosition(c: Coordinate) = new Position(c.x, c.y)

  implicit def positionToCoordinate(p: Position) = new Coordinate(p.left, p.top)

  implicit def coordinateToSize(c: Coordinate) = new Size(c.x, c.y)

  implicit def sizeToCoordinate(s: Size) = new Coordinate(s.width, s.height)
}

@PersistenceCapable
class Note(
    @Persistent var title: String,
    @Persistent var content: Text,
    @Persistent var whiteboard: Key,
    @Persistent @Embedded var position: Position,
    @Persistent @Embedded var size: Size) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  var key: String = _

}