package me.b1n4ry.alchemy.listeners

import me.b1n4ry.alchemy.items.CraftingAltar.craft
import me.b1n4ry.alchemy.recipes.ItemRecipes
import me.b1n4ry.alchemy.util.Util.itemPair
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class HandRecipeListener : Listener {
    @EventHandler
    fun rightClick(e: PlayerInteractEvent) {
        val p = e.player
        if (p.itemPair == ItemRecipes.altar) p.craft()
    }
}