package me.b1n4ry.alchemy.items

import me.b1n4ry.alchemy.instance
import me.b1n4ry.alchemy.items
import me.b1n4ry.alchemy.util.Styles
import me.b1n4ry.alchemy.util.Util.id
import me.b1n4ry.alchemy.util.Util.itemPair
import net.kyori.adventure.text.Component
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin


object CraftingAltar {
    fun drop(item: Item) {
        item.isUnlimitedLifetime = true
        object : BukkitRunnable() {
            override fun run() {
                if (item.isOnGround) {
                    item.setGravity(false)
                    item.pickupDelay = 40
                    item.chunk.isForceLoaded = true
                    val altarItems = ArrayList<Item>(6)
                    val altarItemStacks = ArrayList<ItemStack>(6)
                    val cent: Location = item.location.toCenterLocation()
                    item.setGravity(true)
                    item.world.playSound(item.location, Sound.BLOCK_BEACON_POWER_SELECT, 1f, 1f)
                    var iter = 0.0

                    // Запускает цикл пока алтарь работает
                    object : BukkitRunnable() {
                        override fun run() {
                            item.teleport(cent.set(cent.x, item.location.y, cent.z))
                            item.velocity = Vector(0, 0, 0)
                            val nearby = item.location.getNearbyEntitiesByType(
                                Item::class.java, 1.5
                            )
                            if (item.isValid) {
                                // Цилиндр
                                var x: Double
                                var y: Double
                                var z: Double
                                for (i in 0..44) {
                                    x = cos(i * 2 + iter) * 1.5
                                    y = i * 0.025
                                    z = sin(i * 2 + iter) * 1.5
                                    val loc = Location(
                                        item.world,
                                        x + item.location.x,
                                        y + item.location.y,
                                        z + item.location.z
                                    )
                                    item.world.spawnParticle(Particle.SUSPENDED, loc, 1, 0.0, 0.0, 0.0, 0.0)
                                }
                                // Вращение
                                iter += 0.04

                                // Определение предметов рядом
                                for (i in nearby) {
                                    if (!i.scoreboardTags.contains("inCraft") && i != item && altarItems.size < 6 && i.itemStack.amount == 1 && i.itemStack.id != "altar"
                                        && i.itemStack.id != "catalyst"
                                    ) {
                                        i.addScoreboardTag("inCraft")
                                        i.setGravity(false)
                                        i.setCanPlayerPickup(false)
                                        i.isUnlimitedLifetime = true
                                        i.velocity = Vector(0, 0, 0)
                                        altarItems.add(i)
                                        altarItemStacks.add(i.itemStack)
                                        // Bukkit.getLogger().info(altarItemStacks.toString());
                                        // Bukkit.getLogger().info(String.valueOf(altarItems.size()));
                                    }
                                    // if (i.itemStack.id == "catalyst") testRecipes(p,item,altarItemStacks,altarItems)
                                }

                                // Описанная окружность прикол
                                if (altarItems.size > 0) {
                                    var x2: Double
                                    var y2: Double
                                    var z2: Double
                                    for (i in 1..altarItems.size) {
                                        val alpha = 2 * Math.PI * (i.toDouble() / altarItems.size)
                                        x2 = cos(alpha) * 1.5
                                        y2 = 1.0
                                        z2 = sin(alpha) * 1.5
                                        val loc = Location(
                                            item.world,
                                            x2 + item.location.x,
                                            y2 + item.location.y,
                                            z2 + item.location.z
                                        )
                                        altarItems[i - 1].teleport(loc)
                                    }
                                }
                            } else {
                                for (i in altarItems) {
                                    if (i.scoreboardTags.contains("inCraft")) {
                                        i.removeScoreboardTag("inCraft")
                                        i.setGravity(true)
                                        i.setCanPlayerPickup(true)
                                    }
                                }
                                item.chunk.isForceLoaded = false
                                this.cancel()
                            }
                        }
                    }.runTaskTimer(instance, 0, 1)
                    cancel()
                }
            }

        }.runTaskTimer(instance,0,1)
    }
    fun createItem(altar: ItemStack) {
        altar.id = "altar"
        val altarMeta = altar.itemMeta as PotionMeta
        altarMeta.color = Color.FUCHSIA
        altarMeta.basePotionData = PotionData(PotionType.WATER)
        altarMeta.displayName(Component.text("Crafting Altar", Styles.epic))
        altarMeta.lore(listOf(Component.text("An item used for advanced alchemy recipes.", Styles.none)))
        altarMeta.addEnchant(Enchantment.DURABILITY,1,true)
        altarMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_POTION_EFFECTS)
        altar.itemMeta = altarMeta
    }
    fun Player.craft() {
        itemPair.first.subtract()
        itemPair.second.subtract()
        inventory.addItem(items.altar)
        world.playSound(location,Sound.BLOCK_END_PORTAL_FRAME_FILL,1f,1f)
    }
}