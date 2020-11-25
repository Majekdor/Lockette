package me.majekdor.chestlock.event;

import me.majekdor.chestlock.Lockette;
import me.majekdor.chestlock.data.LockedContainer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getBlockData() instanceof WallSign) {
            Block attachedTo = event.getBlock().getRelative(((WallSign)event.getBlock().getBlockData()).getFacing().getOppositeFace());
            if (Lockette.containerMap.containsKey(attachedTo.getLocation())) {
                LockedContainer container = Lockette.containerMap.get(attachedTo.getLocation());
                if (!container.isOwner(event.getPlayer())) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("You can't break dis.");
                } else {
                    event.getPlayer().sendMessage("Container no longer locked.");
                    if (container.isDoubleChest()) {
                        for (Location loc : container.getContainerLocations())
                            Lockette.containerMap.remove(loc);
                    } else
                        Lockette.containerMap.remove(container.getContainerLocation());
                }
            }
        }
        if (Lockette.containerMap.containsKey(event.getBlock().getLocation())) {
            LockedContainer container = Lockette.containerMap.get(event.getBlock().getLocation());
            if (!container.isAllowedAccess(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("You can't break dis.");
            } else {
                event.getPlayer().sendMessage("Container no longer locked.");
                if (container.isDoubleChest()) {
                    for (Location loc : container.getContainerLocations())
                        Lockette.containerMap.remove(loc);
                } else
                    Lockette.containerMap.remove(container.getContainerLocation());
            }
        }
    }

    @EventHandler
    public void onBlockBreakExplosion(BlockDamageEvent event) {

    }
}
