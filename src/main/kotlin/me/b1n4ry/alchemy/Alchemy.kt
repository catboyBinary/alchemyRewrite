package me.b1n4ry.alchemy

import me.b1n4ry.alchemy.items.CraftingAltar
import me.b1n4ry.alchemy.listeners.HandRecipeListener
import me.b1n4ry.alchemy.listeners.ItemDropListener
import me.b1n4ry.alchemy.util.Items
import me.b1n4ry.alchemy.util.Util.id
import org.bukkit.entity.Item
import org.bukkit.plugin.java.JavaPlugin

lateinit var instance : JavaPlugin
lateinit var items: Items

class Alchemy : JavaPlugin() {
    override fun onEnable() {
        instance = this
        items = Items()
        val board = server.scoreboardManager.mainScoreboard
        if(board.getObjective("alchemylvl") == null) {
            board.registerNewObjective("alchemylvl", "dummy")
            board.registerNewObjective("alchemyxp", "dummy")
        }

        server.pluginManager.registerEvents(ItemDropListener(), this)
        server.pluginManager.registerEvents(HandRecipeListener(), this)

        for(w in server.worlds) {
            for(i in w.getEntitiesByClass(Item::class.java)) {
                if(i.itemStack.id == "altar") {
                    i.location.chunk.load()
                    CraftingAltar.drop(i)
                }
            }
        }
    }

    override fun onDisable() {
        for(w in server.worlds) {
            for(i in w.getEntitiesByClass(Item::class.java)) {
                if(i.scoreboardTags.contains("inCraft")) {
                    i.removeScoreboardTag("inCraft")
                    i.setGravity(true)
                    i.setCanPlayerPickup(true)
                }
            }
        }
    }
}