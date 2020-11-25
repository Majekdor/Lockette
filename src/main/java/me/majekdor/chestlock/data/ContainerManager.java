package me.majekdor.chestlock.data;

import me.majekdor.chestlock.Lockette;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ContainerManager {

    /*
    public void addLockedContainer(Location signLocation, Player player, Material containerMaterial) {
        LockedContainer container = new LockedContainer(signLocation, player, containerMaterial);
        Lockette.containerMap.put(container.getContainerLocation(), container);
    }

    public void addLockedContainer(List<Location> locations, Location signLocation, Player player, Material containerMaterial) {
        LockedContainer container = new LockedContainer(locations, signLocation, player, containerMaterial);
        for (Location loc : container.getContainerLocations())
            Lockette.containerMap.put(loc, container);
    }

    public void removeLockedContainer(Location location) {
        removeLockedContainer(Collections.singletonList(location));
    }

    public void removeLockedContainer(List<Location> locations) {
        for (Location location : locations)
            Lockette.containerMap.remove(location);
    }

     */

}
