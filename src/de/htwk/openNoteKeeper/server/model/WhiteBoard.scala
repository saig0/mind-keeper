package de.htwk.openNoteKeeper.server.model
import com.vercer.engine.persist.annotation.Child
import de.htwk.openNoteKeeper.server.HasKey
import com.vercer.engine.persist.annotation.Parent

class WhiteBoard(
    var title: String,
    var group: Group) extends HasKey {

  def this() = this("title", new Group)

  @Child var notes: java.util.List[Note] = new java.util.LinkedList[Note]()
}