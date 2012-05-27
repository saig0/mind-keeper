package de.htwk.openNoteKeeper.server.model
import javax.jdo.annotations.PersistenceCapable
import com.google.appengine.api.datastore.Key
import javax.jdo.annotations.Persistent
import javax.jdo.annotations.PrimaryKey
import javax.jdo.annotations.IdGeneratorStrategy
import javax.jdo.annotations.Extension

@PersistenceCapable
class Settings() {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
  var key: String = _

  @Persistent
  var shouldAskBeforeDelete: Boolean = false

  @Persistent
  var defaultNoteColor: String = "#F3F781"

  @Persistent
  var shouldUseRichTextEditor: Boolean = false

  @Persistent
  var textEditorOptions: java.util.List[String] = new java.util.LinkedList[String]()
}