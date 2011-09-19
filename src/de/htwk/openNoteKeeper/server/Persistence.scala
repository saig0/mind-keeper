package de.htwk.openNoteKeeper.server
import scala.collection.mutable.ArrayBuffer
import java.io.Serializable
import scala.collection.mutable.ListBuffer
import javax.jdo._
import com.google.gwt.user.client.rpc.SerializationException
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore
import com.google.appengine.api.datastore.Key
import com.vercer.engine.persist.FindCommand.RootFindCommand
import com.google.appengine.api.datastore.Query.FilterOperator
import com.google.appengine.api.datastore.Query.SortDirection
import com.google.appengine.api.datastore.KeyFactory

trait HasKey {
  var key: String = _
}

trait Persistence {

  val dataStore = new AnnotationObjectDatastore()

  implicit def key2String(key: Key) = KeyFactory.keyToString(key)

  implicit def string2Key(key: String) = KeyFactory.stringToKey(key)

  def store[T <: HasKey](objectToPersist: Any): Key = {
    val key = dataStore.store(objectToPersist)
    objectToPersist match {
      case o: T => {
        o.key = key
        update(o)
      }
      case _ =>
    }
    key
  }

  def update(objectToUpdate: Any) = dataStore.update(objectToUpdate)

  def delete(objectToDelete: Any) = dataStore.delete(objectToDelete)

  def findByKey[T](key: Key) = dataStore.load(key)

  def findByType[T](classOfObject: Class[T]) = dataStore.find(classOfObject)

  def findByQuery[T](classOfObject: Class[T]) =
    dataStore.find().`type`(classOfObject)

  implicit def command2Query[T](command: RootFindCommand[T]) = new {

    def isEqual(column: String, value: Any): RootFindCommand[T] = command.addFilter(column, FilterOperator.EQUAL, value)

    def sort(column: String, direction: SortDirection = SortDirection.ASCENDING): RootFindCommand[T] = command.addSort(column, direction)

    def run = command.returnResultsNow()
  }

}