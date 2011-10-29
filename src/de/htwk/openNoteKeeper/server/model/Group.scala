package de.htwk.openNoteKeeper.server.model

import de.htwk.openNoteKeeper.server.HasKey
import com.vercer.engine.persist.annotation.Child
import com.vercer.engine.persist.annotation.Parent

class Group(var title: String) extends HasKey {

  def this() = this("title")

  @Child var whiteBoards: java.util.List[WhiteBoard] = new java.util.LinkedList[WhiteBoard]()

  @Child var subGroups: java.util.List[Group] = new java.util.LinkedList[Group]()

  var parentGroup: Group = _

}