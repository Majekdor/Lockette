package me.majekdor.chestlock.event;

import me.majekdor.chestlock.data.LockedContainer;
import me.majekdor.chestlock.Lockette;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteract implements Listener {

    @EventHandler
    public void onContainerOpen(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null && !player.isSneaking()) {
            LockedContainer container = Lockette.containerMap.get(event.getClickedBlock().getLocation());
            if (container == null)
                return;
            if (Lockette.containerMap.containsKey(event.getClickedBlock().getLocation())
                    && !container.isAllowedAccess(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "This " + container.getContainerName().toLowerCase()
                        + " is locked.");
            }
            if (event.getClickedBlock().getLocation().equals(container.getMainSignLocation())){
                if (container.isAllowedAccess(player.getUniqueId())) {
                    player.sendMessage(ChatColor.GREEN + container.getContainerName() + " owned by: " +
                            ChatColor.GRAY + Bukkit.getOfflinePlayer(container.getContainerOwner()).getName());
                    if (!container.getTrustedPlayers().isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (OfflinePlayer offlinePlayer : container.getTrustedPlayers()) {
                            sb.append(offlinePlayer.getName()).append(", ");
                        }
                        // Clean up string
                        String cleanMembers = sb.toString().trim();
                        cleanMembers = cleanMembers.substring(0, cleanMembers.length()-1);
                        player.sendMessage(ChatColor.GREEN + "Trusted players: " + cleanMembers);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to view "
                            + container.getContainerName() + " info.");
                }
            }
        }
    }
}
