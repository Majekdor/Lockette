package me.majekdor.chestlock.data;

import me.majekdor.chestlock.Lockette;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class LockedContainer {

    private final List<Location> containerLocations;
    private final List<Location> signLocations;
    private final UUID containerOwner;
    private final List<UUID> trustedPlayers;
    private final ContainerType containerType;
    private final boolean isDoubleChest;

    // Called on creation
    public LockedContainer(Location signLocation, List<Location> containerLocations, Player player, Material containerMaterial) {
        this.signLocations = new ArrayList<>();
        this.signLocations.add(signLocation);
        this.containerLocations = new ArrayList<>();
        this.containerLocations.addAll(containerLocations);
        this.containerOwner = player.getUniqueId();
        this.trustedPlayers = new ArrayList<>();
        this.containerType = getContainerType(containerMaterial);
        this.isDoubleChest = containerLocations.size() > 1;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public String getContainerName() {
        return containerType.getDisplayName(containerType);
    }

    public boolean isOwner(Player player) {
        return containerOwner.equals(player.getUniqueId());
    }

    public UUID getContainerOwner() {
        return containerOwner;
    }

    public boolean isAllowedAccess(UUID uuid) {
        return uuid == containerOwner || trustedPlayers.contains(uuid);
    }

    public void addTrustedPlayer(UUID uuid) {
        trustedPlayers.add(uuid);
    }

    public List<OfflinePlayer> getTrustedPlayers() {
        return trustedPlayers.stream().map(Bukkit::getOfflinePlayer).collect(Collectors.toList());
    }

    public Location getContainerLocation() {
        return containerLocations.get(0);
    }

    public List<Location> getContainerLocations() {
        return containerLocations;
    }

    public Location getMainSignLocation() {
        return signLocations.get(0);
    }

    public List<Location> getSignLocations() {return signLocations; }

    public void addSignLocation(Location location) {
        signLocations.add(location);
    }

    public boolean isDoubleChest() {
        return isDoubleChest;
    }

    public ContainerType getContainerType(Material containerMaterial) {
        if (Lockette.SHULKERS.contains(containerMaterial))
            return ContainerType.SHULKER_BOX;
        switch (containerMaterial) {
            case CHEST:
                return ContainerType.CHEST;
            case BARREL:
                return ContainerType.BARREL;
            case FURNACE:
                return ContainerType.FURNACE;
            default:
                return null;
        }
    }
}
