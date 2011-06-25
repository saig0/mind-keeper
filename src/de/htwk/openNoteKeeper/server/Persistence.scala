package de.htwk.openNoteKeeper.server
import scala.collection.mutable.ArrayBuffer

import java.io.Serializable

import scala.collection.mutable.ListBuffer

import javax.jdo._

import com.google.gwt.user.client.rpc.SerializationException

trait Persistence {

  private def request[T](f: (PersistenceManager) => T): T = {
    val entityManager = EntityManagerFactory.persistenceManager
    try {
      f(entityManager)
    } catch {
      case e => throw new SerializationException(e)
    }
    finally {
      entityManager.close
    }
  }

  def persist[T](objectToPersist: T) =
    request(pm => pm.makePersistent(objectToPersist))

  def update[T](id: Long, objectType: Class[T], modify: (T) => Unit) =
    request(pm => {
      val objectToModify = pm.getObjectById(objectType, id)
      modify(objectToModify)
    })

  def delete[T](id: Long, objectType: Class[T]) =
    request { pm =>
      val objectToPersist = pm.getObjectById(objectType, id)
      pm.deletePersistent(objectToPersist)
    }

  def query[T](queryString: String): Option[T] =
    request(pm => {
      val query = pm.newQuery(queryString)
      Some(query.execute().asInstanceOf[T])
    })

  def findObjectById[T](id: Long, objectType: Class[T]): Option[T] =
    request { pm =>
      try {
        Some(pm.getObjectById(objectType, id))
      } catch {
        case e => None
      }
    }

  def findAllObjects[T](objectType: Class[T]) =
    request(pm => {
      val extent = pm.getExtent[T](objectType);
      val result = createResultList(extent)
      extent.closeAll()
      result
    })

  private def createResultList[T](result: java.lang.Iterable[T]): Option[List[T]] = {
    val list = ListBuffer[T]()
    val i = result.iterator
    while (i.hasNext) {
      list += i.next
    }
    if (!list.isEmpty) Some(list.toList)
    else None
  }

  class Criteria[T <: Serializable](val name: String, val value: T) {
    val objectType = value.getClass.getSimpleName
  }

  //TODO generischer Type aber trotzdem eindeutig, damit nicht Object ermittelt wird
  private def findObjectsByGenericCriteria[T](objectType: Class[T], criterias: List[Criteria[_]]) = {
    request(pm => {
      val query = pm.newQuery(objectType)
      for (criteria <- criterias) {
        query.setFilter(criteria.name + " == " + criteria.name + "Param");
        query.declareParameters(criteria.objectType + " " + criteria.name + "Param");
      }
      val result = query.execute((criterias map (_.value)).toArray).asInstanceOf[java.util.List[T]]
      createResultList(result)
    })
  }

  def findObjectsByCriteria[T](objectType: Class[T], criteria: Criteria[_]) =
    request(pm => {
      val query = pm.newQuery(objectType)
      query.setFilter(criteria.name + " == " + criteria.name + "Param");
      query.declareParameters(criteria.objectType + " " + criteria.name + "Param");
      val result = query.execute(criteria.value).asInstanceOf[java.util.List[T]]
      createResultList(result)
    })

  def findObjectByCriteria[T](objectType: Class[T], criteria: Criteria[_]) =
    findObjectsByCriteria(objectType, criteria) match {
      case Some(results) if (results.size == 1) => Some(results(0))
      case _ => None
    }
}