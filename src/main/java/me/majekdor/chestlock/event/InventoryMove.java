package me.majekdor.chestlock.event;

import me.majekdor.chestlock.Lockette;
import me.majekdor.chestlock.data.LockedContainer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryMove implements Listener {

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        for (LockedContainer container : Lockette.containerMap.values()) {
            Inventory inv = event.getSource(); InventoryHolder invHolder = inv.getHolder();
            if (inv instanceof DoubleChestInventory) {
                DoubleChest db = (DoubleChest) inv.getHolder();
                if (container.getContainerLocations().contains(db.getLocation().add(0, 0, 0.5)) ||
                        container.getContainerLocations().contains(db.getLocation().add(0.5, 0, 0)))
                    event.setCancelled(true);
            }
            if (invHolder instanceof BlockState) {
                Block block = ((BlockState) invHolder).getBlock();
                if (container.getContainerLocations().contains(block.getLocation()))
                    event.setCancelled(true);
                if (block.getLocation().equals(container.getContainerLocation()))  {
                    event.setCancelled(true);
                }
            }
        }
    }
}
