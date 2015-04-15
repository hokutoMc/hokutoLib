package com.github.hokutomc.lib.scala.entity

import com.github.hokutomc.lib.scala.HT_Predef._
import com.github.hokutomc.lib.scala.HT_ScalaConversion._
import com.github.hokutomc.lib.scala.nbt.HT_NBTEvidence

object HT_EntityVariable {

  sealed trait T_NBT[A] extends HT_EntityVariable[A] {
    val key: String

    def read(nbt: TagComp)(implicit ev: HT_NBTEvidence[A]): Option[A] = nbt[A](key)(ev)

    /**
     * write the value to nbtTag.
     * removes the tag if the value is None.
     * @param nbt
     * @return
     */
    def write(nbt: TagComp)(implicit ev: HT_NBTEvidence[A]): this.type = {
      this.getOption match {
        case Some(v) => nbt.update[A](key, v)(ev)
        case _ => nbt.removeTag(key)
      }
      this
    }
  }

  class NBT[A](override val key: String, override val initial: Option[A]) extends T_NBT[A]

  class NBTWatchable[A](val entity: Entity, override val key: String, val id: Int, override val initial: Option[A])(implicit evD: HT_DataWatchEvidence[A])
    extends T_NBT[A] {
    def initialize(): Unit = initial foreach { a => evD.init(entity.getDataWatcher, id, a) }

    override def :=(a: A): Unit = entity.getDataWatcher.update(id, a)(evD)

    override def getOption: Option[A] = Some(entity.getDataWatcher.apply(id)(evD))
  }

  def nbt[A](key: String, initial: Option[A]) = new NBT[A](key, initial)

  def nbtWatchable[A](entity: Entity, key: String, id: Int, initial: Option[A])(implicit evD: HT_DataWatchEvidence[A]) =
    new NBTWatchable[A](entity, key, id, initial)(evD)
}

/**
 * Created by user on 2015/04/14.
 */
abstract class HT_EntityVariable[A] {
  self =>

  val initial: Option[A]

  protected var value = initial

  def getOption: Option[A] = value

  def :=(a: A): Unit = value = Option(a)


  def safe[U](f: A => U): Unit = foreach(f)

  def isEmpty: Boolean = getOption isEmpty

  def isDefined: Boolean = !isEmpty

  def get: A = getOption get

  @inline def getOrElse[B >: A](default: => B): B = getOption getOrElse default

  @inline def orNull[A1 >: A](implicit ev: Null <:< A1): A1 = getOption orNull


  @inline def map[B](f: A => B): Option[B] = getOption map f

  @inline def fold[B](ifEmpty: => B)(f: A => B): B = getOption.fold(ifEmpty)(f)

  @inline def flatMap[B](f: A => Option[B]): Option[B] = getOption flatMap f

  def flatten[B](implicit ev: A <:< Option[B]): Option[B] = getOption flatten ev

  @inline def filter(p: A => Boolean): Option[A] = getOption filter p


  @inline def filterNot(p: A => Boolean): Option[A] = getOption filterNot p

  def nonEmpty = isDefined

  @inline def withFilter(p: A => Boolean) = new WithFilter(p)

  class WithFilter(p: A => Boolean) {
    def map[B](f: A => B): Option[B] = self filter p map f

    def flatMap[B](f: A => Option[B]): Option[B] = self filter p flatMap f

    def foreach[U](f: A => U): Unit = self filter p foreach f

    def withFilter(q: A => Boolean): WithFilter = new WithFilter(x => p(x) && q(x))
  }

  def contains[A1 >: A](elem: A1): Boolean = getOption contains elem

  @inline def exists(p: A => Boolean): Boolean = getOption exists p

  @inline def forall(p: A => Boolean): Boolean = getOption forall p

  @inline def foreach[U](f: A => U): Unit = getOption foreach f

  @inline def collect[B](pf: PartialFunction[A, B]): Option[B] = getOption collect pf

  @inline def orElse[B >: A](alternative: => Option[B]): Option[B] = getOption orElse alternative

  def iterator: Iterator[A] = getOption iterator

  def toList: List[A] = getOption toList

  @inline def toRight[X](left: => X) = getOption toRight left

  @inline def toLeft[X](right: => X) = getOption toLeft right
}
