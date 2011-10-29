package de.htwk.openNoteKeeper.server.model

import com.vercer.engine.persist.annotation.Parent
import com.vercer.engine.persist.annotation.Type
import de.htwk.openNoteKeeper.shared._

class Authority(
    var userId: String,
    var group: Group,
    var accessRole: AccessRole) {

  def this() = this("userId", new Group, AccessRole.Owner)
}