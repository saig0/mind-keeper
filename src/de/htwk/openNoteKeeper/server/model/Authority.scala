package de.htwk.openNoteKeeper.server.model

import com.vercer.engine.persist.annotation.Parent
import com.vercer.engine.persist.annotation.Type
import de.htwk.openNoteKeeper.shared._
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey
import javax.jdo.annotations.IdGeneratorStrategy
import javax.jdo.annotations.Extension
import javax.jdo.annotations.PersistenceCapable
import com.google.appengine.api.datastore.Key

@PersistenceCapable
class Authority(
    @Persistent var user: Key,
    @Persistent var group: Key,
    @Persistent var accessRole: AccessRole) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  var key: String = _
}