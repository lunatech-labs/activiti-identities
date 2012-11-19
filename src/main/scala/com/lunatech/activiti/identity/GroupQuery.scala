package com.lunatech.activiti.identity

import org.activiti.engine.impl.UserQueryImpl
import org.activiti.engine.identity.User
import org.activiti.engine.ActivitiException
import scala.collection.JavaConverters._

/**
 * Scala implementation of an activiti 'GroupQuery'. The query can execute itself, so it requires a reference to a GroupService.
 */
case class GroupQuery (
  service: GroupService,
  id: Option[String] = None,
  member: Option[String] = None,
  name: Option[String] = None,
  nameLike: Option[String] = None,
  typ: Option[String] = None,
  _nextOrderField: Option[GroupQuery.OrderField] = None,
  orders: Seq[(GroupQuery.OrderField, OrderDirection)] = Nil,
  procDefId: Option[String] = None) extends org.activiti.engine.identity.GroupQuery {

  override def groupId(groupId: String) = copy(id = Option(groupId))
  override def groupMember(userId: String) = copy(member = Option(userId))
  override def groupName(groupName: String) = copy(name = Option(groupName))
  override def groupNameLike(groupNameLike: String) = copy(nameLike = Option(groupNameLike))
  override def groupType(groupType: String) = copy(typ = Option(groupType))
  override def potentialStarter(procDefId: String) = copy(procDefId = Option(procDefId))

  override def orderByGroupId() = copy(_nextOrderField = Some(GroupQuery.Id))
  override def orderByGroupName() = copy(_nextOrderField = Some(GroupQuery.Name))
  override def orderByGroupType() = copy(_nextOrderField = Some(GroupQuery.Type))

  override def asc() = addOrder(ASC)
  override def desc() = addOrder(DESC)

  override def list() = service.findAll(this).toList.asJava
  override def listPage(firstResult: Int, maxResult: Int) = service.findAll(this, Option(firstResult), Option(maxResult)).toList.asJava
  override def singleResult = service.findFirst(this).orNull
  override def count() = service.count(this)

  protected def addOrder(orderDirection: OrderDirection) = _nextOrderField match {
    case Some(field) => copy(orders = orders :+ (field, orderDirection), _nextOrderField = None)
    case None => throw new ActivitiException("You should call any of the orderBy methods first before specifying a direction");
  }

}

object GroupQuery {
  sealed trait OrderField
  case object Id extends OrderField
  case object Name extends OrderField
  case object Type extends OrderField
}