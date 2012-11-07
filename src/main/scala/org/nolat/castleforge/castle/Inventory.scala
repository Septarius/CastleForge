package org.nolat.castleforge.castle

import scala.collection.mutable.ArrayBuffer
import org.nolat.castleforge.castle.items.Item
import org.nolat.castleforge.castle.items.Key
import org.nolat.castleforge.castle.items.attributes.Quantity
import org.nolat.castleforge.castle.items.CrystalBall
import org.nolat.castleforge.castle.items.Match
import org.nolat.castleforge.castle.items.attributes.Collectable

class Inventory extends ArrayBuffer[Option[Collectable]] {
  def this(items: Seq[Option[Collectable]]) {
    this()
    this.appendAll(items)
  }

  //This should be comparing the items properly using Collectable type
  //all in one method because of http://stackoverflow.com/questions/2510108/why-avoid-method-overloading and
  //http://stackoverflow.com/questions/1094173/how-do-i-get-around-type-erasure-on-scala-or-why-cant-i-get-the-type-paramete
  def addItem(addedItem: Collectable) {
    addedItem match {
      case addItmQ: Item with Quantity =>
        {
          val itemsType = this.flatten.map { item =>
            item match {
              case itm: Collectable with Quantity =>
                if (itm.isSimilar(addedItem)) {
                  Some(itm)
                } else {
                  None
                }
              case _ => None
            }
          }.flatten
          if (itemsType.size == 1) {
            itemsType(0).quantity += addItmQ.quantity
          } else if (itemsType.size == 0) {
            this += Some(addItmQ)
          } else if (itemsType.size > 1) {
            assert(itemsType.size == 1)
          }
        }
      case addItm: Item =>
        {
          val itemsType = this.flatten.map { item =>
            item match {
              case itm: Collectable =>
                if (itm.isSimilar(addedItem)) {
                  Some(itm)
                } else {
                  None
                }
              case _ => None
            }
          }.flatten
          if (itemsType.size == 0) {
            this += Some(addItm)
          } else if (itemsType.size > 1) {
            assert(itemsType.size == 1)
          }
        }
    }

  }

  def addItems(addedItem: Collectable*) {
    addedItem.foreach { itm =>
      itm match {
        case collect: Collectable => addItem(collect)
      }
    }
  }

  def containsItem() {
    //true or false if the item is in this inventory
  }

  def decrementItem(item: Collectable) {
    //remove that item's quantity by 1 unless it goes <= 0 then remove it completely 
  }

  def getKeys = {
    this.flatten.map { item =>
      item match {
        case itm: Key => Some(itm)
        case _ => None
      }
    }.flatten.toList
  }

  def getMatch = {
    val matches = this.flatten.map { item =>
      item match {
        case itm: Match => Some(itm)
        case _ => None
      }
    }.flatten.toList
    if (matches.size > 0) Some(matches(0)) else None
  }

  def getCrystalBall = {
    val cballs = this.flatten.map { item =>
      item match {
        case itm: CrystalBall => Some(itm)
        case _ => None
      }
    }.flatten.toList
    if (cballs.size > 0) Some(cballs(0)) else None
  }

  def hasCrystalBall = getCrystalBall.size > 0

  def getMiscItems = {
    val mList = getMatch match {
      case Some(m) => List(m)
      case None => Nil
    }

    val cList = getCrystalBall match {
      case Some(c) => List(c)
      case None => Nil
    }

    mList ::: cList ::: Nil
  }

}