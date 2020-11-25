package me.majekdor.chestlock.data;

public enum ContainerType {

    CHEST("Chest"),
    TRAPPED_CHEST("Trapped Chest"),
    BARREL("Barrel"),
    SHULKER_BOX("Shulker Box"),
    FURNACE("Furnace"),
    BLAST_FURNACE("Blast Furnace"),
    SMOKER("Smoker"),
    CRAFTING_TABLE("Crafting Table"),
    ENDER_CHEST("Ender Chest"),
    BREWING_STAND("Brewing Stand"),
    DISPENSER("Dispenser"),
    DROPPER("Dropper"),
    HOPPER("Hopper"),
    ANVIL("Anvil");

    private final String displayName;

    ContainerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName(ContainerType type) {
        return type.displayName;
    }
}
