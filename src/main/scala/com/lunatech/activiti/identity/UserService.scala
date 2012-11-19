package com.lunatech.activiti.identity

import org.activiti.engine.identity.User

/**
 * Trait abstracting group searching. Only the `findAll` method needs to be implemented.
 * The other methods can be overridden for better performance.
 */
trait UserService {
  def findAll(criteria: UserQuery, offset: Option[Int] = None, limit: Option[Int] = None): Iterable[User]
  def findFirst(criteria: UserQuery): Option[User] = findAll(criteria).headOption
  def count(criteria: UserQuery) = findAll(criteria).size
}