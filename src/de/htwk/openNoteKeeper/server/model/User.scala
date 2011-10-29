package de.htwk.openNoteKeeper.server.model
import com.vercer.engine.persist.annotation.Child
import com.vercer.engine.persist.annotation.Key
import de.htwk.openNoteKeeper.server.HasKey
import com.vercer.engine.persist.annotation.Activate

class User(
    @Key var userId: String) {

  def this() = this("userId")

  @Activate @Child var authorities: java.util.List[Authority] = new java.util.LinkedList[Authority]()

}