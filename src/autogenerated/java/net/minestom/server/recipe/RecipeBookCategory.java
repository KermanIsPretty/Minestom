package net.minestom.server.recipe;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.registry.StaticProtocolObject;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.nbt.BinaryTagSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * AUTOGENERATED by GenericEnumGenerator
 */
public enum RecipeBookCategory implements StaticProtocolObject {
    CRAFTING_BUILDING_BLOCKS(NamespaceID.from("minecraft:crafting_building_blocks")),

    CRAFTING_REDSTONE(NamespaceID.from("minecraft:crafting_redstone")),

    CRAFTING_EQUIPMENT(NamespaceID.from("minecraft:crafting_equipment")),

    CRAFTING_MISC(NamespaceID.from("minecraft:crafting_misc")),

    FURNACE_FOOD(NamespaceID.from("minecraft:furnace_food")),

    FURNACE_BLOCKS(NamespaceID.from("minecraft:furnace_blocks")),

    FURNACE_MISC(NamespaceID.from("minecraft:furnace_misc")),

    BLAST_FURNACE_BLOCKS(NamespaceID.from("minecraft:blast_furnace_blocks")),

    BLAST_FURNACE_MISC(NamespaceID.from("minecraft:blast_furnace_misc")),

    SMOKER_FOOD(NamespaceID.from("minecraft:smoker_food")),

    STONECUTTER(NamespaceID.from("minecraft:stonecutter")),

    SMITHING(NamespaceID.from("minecraft:smithing")),

    CAMPFIRE(NamespaceID.from("minecraft:campfire"));

    public static final NetworkBuffer.Type<RecipeBookCategory> NETWORK_TYPE = NetworkBuffer.Enum(RecipeBookCategory.class);

    public static final BinaryTagSerializer<RecipeBookCategory> NBT_TYPE = BinaryTagSerializer.fromEnumKeyed(RecipeBookCategory.class);

    private final NamespaceID namespace;

    RecipeBookCategory(@NotNull NamespaceID namespace) {
        this.namespace = namespace;
    }

    @NotNull
    @Override
    public NamespaceID namespace() {
        return this.namespace;
    }

    @Override
    public int id() {
        return this.ordinal();
    }
}
