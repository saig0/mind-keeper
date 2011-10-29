package de.htwk.openNoteKeeper.server.model

import com.google.appengine.api.datastore.Text
import com.vercer.engine.persist.annotation.Child
import com.vercer.engine.persist.annotation.Type
import com.vercer.engine.persist.annotation.Key
import de.htwk.openNoteKeeper.server.HasKey
import com.vercer.engine.persist.annotation.Parent

class Note(
    var whiteBoard: WhiteBoard,
    var title: String,
    @Type(classOf[Text]) var content: String,
    @Child var position: Coordinate,
    @Child var size: Coordinate) extends HasKey {

  def this() = this(new WhiteBoard, "title", "", new Coordinate, new Coordinate)

}