package de.htwk.openNoteKeeper.server.model
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey
import javax.jdo.annotations.IdGeneratorStrategy
import javax.jdo.annotations.Extension
import javax.jdo.annotations.PersistenceCapable
import com.google.appengine.api.datastore.Key

@PersistenceCapable
class User(
    @PrimaryKey @Persistent var userId: String) {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  var key: String = _

  @Persistent var authorities: java.util.List[Key] = new java.util.LinkedList[Key]()

}