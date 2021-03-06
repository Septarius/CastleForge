package org.nolat.castleforge.castle.items

import org.nolat.castleforge.tools.Preamble._
import org.nolat.castleforge.graphics.Sprites
import org.nolat.castleforge.graphics.Sprite
import org.nolat.castleforge.castle.items.attributes.IDColor
import org.nolat.castleforge.castle.items.attributes.Luminosity
import org.nolat.castleforge.castle.items.attributes.TorchState
import org.nolat.castleforge.castle.Player
import org.nolat.castleforge.castle.Floor

class Torch(var lit: Boolean, lumen: String, _color: String) extends Item with IDColor with Luminosity with TorchState {
  torchstate = TorchState.fromBoolean(lit)
  luminosity = Luminosity.fromString(lumen)
  idcolor = IDColor.fromString(_color)

  def this(paramList: List[String]) = {
    this(paramList(0).booleanOption.getOrElse(true), paramList(1), paramList(2))
  }

  sprite = new Sprite(getItemType)
  if (lit) {
    println("torch is lit. lumens: " + lumen)
    sprite.setAnimation(lumen)
  } else {
    println("torch is off. lumens: " + lumen)
    sprite.setAnimation("off")
  }

  override def getItemType = Sprites.torch

  override def getParamList = {
    
    Seq(torchstate.toString, Luminosity.toString(luminosity), IDColor.toString(idcolor))
  }

  override def onPlayerEnter(player: Player, srcFloor: Floor) {
    player.inventory.getMatch match {
      case Some(m) => {
        lit = true
        sprite.setAnimation(lumen)
        player.inventory.decrementItem(m)
      }
      case None =>
    }
  }

  def brightnessDecrement = {
    luminosity match {
      case Luminosity.LOW => .1f
      case Luminosity.MEDIUM => .15f
      case Luminosity.HIGH => .2f

    }
  }
}