package me.b1n4ry.alchemy.listeners

import me.b1n4ry.alchemy.items
import me.b1n4ry.alchemy.items.CraftingAltar
import me.b1n4ry.alchemy.util.Util.id
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class ItemDropListener : Listener {
    @EventHandler
    fun itemDrop(e: PlayerDropItemEvent) {
        val p = e.player
        val item = e.itemDrop
        when(item.itemStack.id) {
            "altar" -> CraftingAltar.drop(item)
        }

        if (item.itemStack.type == Material.DIAMOND) {
            item.itemStack = items.altar
        }
    }
}