package me.b1n4ry.alchemy.util

import me.b1n4ry.alchemy.instance
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object Util {
    val key = NamespacedKey(instance, "item")
    var ItemStack.id
        get() = this.itemMeta.persistentDataContainer[key, PersistentDataType.STRING]
        set(value) {
            val meta = this.itemMeta
            meta.persistentDataContainer.set(key, PersistentDataType.STRING, value.toString())
            this.itemMeta = meta
        }

    val Player.itemPair
        get() = Pair(this.inventory.itemInMainHand,this.inventory.itemInOffHand)
}