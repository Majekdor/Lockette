package me.majekdor.chestlock;

import me.majekdor.chestlock.data.LockedContainer;
import me.majekdor.chestlock.event.BlockBreak;
import me.majekdor.chestlock.event.BlockInteract;
import me.majekdor.chestlock.event.InventoryMove;
import me.majekdor.chestlock.event.SignUpdate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public final class Lockette extends JavaPlugin {

    public static Map<Location, LockedContainer> containerMap = new HashMap<>();

    public static Lockette instance;
    public Lockette() {
        instance = this;
    }

    public static final List<Material> SHULKERS = new ArrayList<>(materialsEndingWith("SHULKER_BOX", Collections.emptyList()));

    public static final List<Material> ACCEPTABLE_CONTAINERS = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        ACCEPTABLE_CONTAINERS.add(Material.CHEST);
        ACCEPTABLE_CONTAINERS.add(Material.BARREL);
        ACCEPTABLE_CONTAINERS.add(Material.FURNACE);
        ACCEPTABLE_CONTAINERS.addAll(materialsEndingWith("SHULKER_BOX", Collections.emptyList()));

        Bukkit.getPluginManager().registerEvents(new InventoryMove(), this);
        Bukkit.getPluginManager().registerEvents(new SignUpdate(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new BlockInteract(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Collect a group of Materials that have similar endings ignoring certain Materials
     *
     * @param with   end of Material names that should be collected.
     * @param except a List of Materials that should be ignored when collecting similar Materials
     * @return a List of Materials which all end with the value passed in minus exception Materials.
     */
    @SuppressWarnings("deprecation")
    public static List<Material> materialsEndingWith(String with, List<Material> except) {
        return Arrays.stream(Material.values()).filter(material -> !material.isLegacy() &&
                material.name().endsWith(with) && !except.contains(material)).collect(Collectors.toList());
    }
}
