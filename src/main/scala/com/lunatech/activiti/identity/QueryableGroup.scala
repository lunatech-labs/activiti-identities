package com.lunatech.activiti.identity

import org.activiti.engine.identity.Group
import org.apache.commons.lang.NotImplementedException

/**
 * Groups that are queryable after creation. This allows for an easy implementation of
 * `GroupService`, since you can just fetch all groups from your database, create an instance
 * of this trait for each of them, and then use `matches` to filter based on the criteria.
 *
 * This won't perform well for a large number of groups.
 */
trait QueryableGroup extends Group {

  def hasMember(userId: String): Boolean

  def matches(criteria: GroupQuery): Boolean = {
    if(criteria.id.isDefined && criteria.id.get != getId) return false
    if(criteria.typ.isDefined && criteria.typ.get != getType) return false
    if(criteria.name.isDefined && criteria.name.get != getName) return false
    // TODO, nameLike

    return true
  }

  override def setName(name: String){ throw new NotImplementedException }
  override def setId(id: String){ throw new NotImplementedException }
  override def setType(typ: String){ throw new NotImplementedException }
}