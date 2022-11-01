package me.b1n4ry.alchemy.util

import me.b1n4ry.alchemy.items.CraftingAltar
import me.b1n4ry.alchemy.util.Util.id
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

class Items {
    val waterBottle = ItemStack(Material.POTION)
    val altar = ItemStack(Material.POTION)

    init {
        CraftingAltar.createItem(altar)
        val waterMeta = waterBottle.itemMeta as PotionMeta
        waterMeta.basePotionData = PotionData(PotionType.WATER)
        waterBottle.itemMeta = waterMeta
    }
}