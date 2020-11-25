package me.majekdor.chestlock.event;

import me.majekdor.chestlock.Lockette;
import me.majekdor.chestlock.data.LockedContainer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Collections;

public class SignUpdate implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSignUpdate(SignChangeEvent event) {
        if (event.getBlock().getBlockData() instanceof WallSign) {
            // Make sure the sign is actually attached to an acceptable container
            Block attachedTo = event.getBlock().getRelative(((WallSign)event.getBlock().getBlockData()).getFacing().getOppositeFace());
            if (!Lockette.ACCEPTABLE_CONTAINERS.contains(attachedTo.getType()))
                return;

            DoubleChest db = null;
            if (attachedTo.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) attachedTo.getState();
                Inventory inv = chest.getInventory();
                if (inv instanceof DoubleChestInventory) {
                    db = (DoubleChest) chest.getInventory().getHolder();
                }
            }

            // Do nothing if a locked container already exists here
            if (Lockette.containerMap.containsKey(attachedTo.getLocation())) {
                LockedContainer container = Lockette.containerMap.get(attachedTo.getLocation());
                if (container.getTrustedPlayers().size() > 2) {

                }
                Bukkit.getConsoleSender().sendMessage("Exists"); return;
            }

            String[] lines = event.getLines();
            for (String line : lines) {
                if (line.equalsIgnoreCase("[locked]") ||
                        line.equalsIgnoreCase("[lock]") ||
                        line.equalsIgnoreCase("[private]")) {
                    DoubleChest finalDb = db;
                    Bukkit.getScheduler().runTaskLater(Lockette.instance, () -> {
                        LockedContainer container;
                        if (finalDb != null) {
                            Location leftSide = finalDb.getLeftSide().getInventory().getLocation();
                            Location rightSide = finalDb.getRightSide().getInventory().getLocation();
                            if (String.valueOf(rightSide.getX()).contains(".5")) {
                                leftSide = leftSide.add(0.5, 0, 0);
                                rightSide  = rightSide.subtract(0.5, 0, 0);
                            } else if (String.valueOf(rightSide.getZ()).contains(".5")) {
                                leftSide = leftSide.add(0, 0, 0.5);
                                rightSide  = rightSide.subtract(0, 0, 0.5);
                            } else {
                                Bukkit.getConsoleSender().sendMessage("ERROR THROWN WHEE OOH WHEE OOH WHEE OOH");
                                Bukkit.getConsoleSender().sendMessage(rightSide.getX() + " and " + rightSide.getZ());
                            }
                            container = new LockedContainer(event.getBlock().getLocation(), Arrays.asList(leftSide,
                                    rightSide), event.getPlayer(), attachedTo.getType());
                            Lockette.containerMap.put(leftSide, container);
                            Lockette.containerMap.put(rightSide, container);
                            Bukkit.getConsoleSender().sendMessage(finalDb.getLeftSide().getInventory().getLocation().toString()
                                    + " and " + finalDb.getRightSide().getInventory().getLocation().toString());
                        } else {
                            container = new LockedContainer(event.getBlock().getLocation(),
                                    Collections.singletonList(attachedTo.getLocation()), event.getPlayer(),
                                    attachedTo.getType());
                            Lockette.containerMap.put(attachedTo.getLocation(), container);
                        }
                        updateSign((Sign) event.getBlock().getState(), event.getPlayer(), lines);
                        event.getPlayer().sendMessage(ChatColor.GREEN + container.getContainerName() + " now locked!");
                    }, 1); // Let sign update
                    break;
                }
            }
        }
    }

    public void updateSign(Sign sign, Player player, String[] lines) {
        sign.setLine(0, ChatColor.GREEN + "" + ChatColor.BOLD + "[Locked]");
        sign.setLine(1, ChatColor.AQUA + player.getName());
        sign.update();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }
}
