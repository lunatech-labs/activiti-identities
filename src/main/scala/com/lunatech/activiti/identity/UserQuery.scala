package com.lunatech.activiti.identity

import org.activiti.engine.impl.UserQueryImpl
import org.activiti.engine.identity.User
import org.activiti.engine.ActivitiException
import scala.collection.JavaConverters._

/**
 * Scala implementation of an activiti 'UserQuery'. The Activiti interface requires that a query
 * can execute itself, so it requires a reference to a UserService.
 */
case class UserQuery (
  service: UserService,
  id: Option[String] = None,
  groupId: Option[String] = None,
  email: Option[String] = None,
  emailLike: Option[String] = None,
  firstName: Option[String] = None,
  firstNameLike: Option[String] = None,
  lastName: Option[String] = None,
  lastNameLike: Option[String] = None,
  _nextOrderField: Option[UserQuery.OrderField] = None,
  orders: Seq[(UserQuery.OrderField, OrderDirection)] = Nil,
  procDefId: Option[String] = None) extends org.activiti.engine.identity.UserQuery {

  override def userId(userId: String) = copy(id = Option(userId))
  override def memberOfGroup(groupId: String) = copy(groupId = Option(groupId))
  override def userEmail(email: String) = copy(email = Option(email))
  override def userEmailLike(emailLike: String) = copy(emailLike = Option(emailLike))
  override def userFirstName(firstName: String) = copy(firstName = Option(firstName))
  override def userFirstNameLike(firstNameLike: String) = copy(firstNameLike = Option(firstNameLike))
  override def userLastName(lastName: String) = copy(lastName = Option(lastName))
  override def userLastNameLike(lastNameLike: String) = copy(lastNameLike = Option(lastNameLike))
  override def potentialStarter(procDefId: String) = copy(procDefId = Option(procDefId))

  override def orderByUserEmail() = copy(_nextOrderField = Some(UserQuery.Email))
  override def orderByUserFirstName() = copy(_nextOrderField = Some(UserQuery.FirstName))
  override def orderByUserId() = copy(_nextOrderField = Some(UserQuery.Id))
  override def orderByUserLastName() = copy(_nextOrderField = Some(UserQuery.LastName))

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

object UserQuery {
  sealed trait OrderField
  case object Id extends OrderField
  case object FirstName extends OrderField
  case object LastName extends OrderField
  case object Email extends OrderField
}