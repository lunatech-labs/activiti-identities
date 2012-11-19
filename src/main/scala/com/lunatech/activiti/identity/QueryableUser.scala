package com.lunatech.activiti.identity

import org.activiti.engine.identity.User
import org.apache.commons.lang.NotImplementedException

/**
 * User that are queryable after creation. This allows for an easy implementation of
 * `UserService`, since you can just fetch all users from your database, create an instance
 * of this trait for each of them, and then use `matches` to filter based on the criteria.
 *
 * This won't perform well for a large number of users. If you have many users, you should
 * implement a UserService that filters in the database.
 */
trait QueryableUser extends User {

  def isInGroup(groupId: String): Boolean

  def matches(criteria: UserQuery): Boolean = {

    if(criteria.id.isDefined && criteria.id.get != getId) return false
    if(criteria.firstName.isDefined && criteria.firstName.get != getFirstName) return false
    // TODO, firstNameLike
    if(criteria.lastName.isDefined && criteria.lastName.get != getLastName) return false
    // TODO, lastNameLike
    if(criteria.email.isDefined && criteria.email.get != getEmail) return false
    // TODO, emailLike
    // TODO, procDefId
    if(criteria.groupId.isDefined && !isInGroup(criteria.groupId.get)) return false

    return true
  }

  override def setEmail(email: String){ throw new NotImplementedException }
  override def setFirstName(firstName: String){ throw new NotImplementedException }
  override def setId(id: String){ throw new NotImplementedException }
  override def setLastName(lastName: String){ throw new NotImplementedException }
  override def setPassword(string: String){ throw new NotImplementedException }
}