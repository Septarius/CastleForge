package org.nolat.castleforge.castle.items

import org.nolat.castleforge.graphics.Sprites
import org.nolat.castleforge.graphics.Sprite
import org.nolat.castleforge.castle.CastleUtil
import org.nolat.castleforge.castle.Player
import org.nolat.castleforge.castle.Floor
import org.nolat.castleforge.castle.items.attributes.Collectable

class CrystalBall extends Item with Collectable {
  sprite = new Sprite(getItemType)

  override def getItemType = Sprites.crystal_ball

  override def isSimilar(that: Collectable): Boolean = {
    that match {
      case cb: CrystalBall => true
      case _ => false
    }
  }
}