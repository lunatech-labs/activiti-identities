package com.lunatech.activiti.identity

import org.activiti.engine.identity.Group

/**
 * Trait abstracting group searching. Only the `findAll` method needs to be implemented.
 * The other methods can be overridden for better performance.
 */
trait GroupService {
  def findAll(criteria: GroupQuery, offset: Option[Int] = None, limit: Option[Int] = None): Iterable[Group]
  def findFirst(criteria: GroupQuery): Option[Group] = findAll(criteria).headOption
  def count(criteria: GroupQuery) = findAll(criteria).size
}