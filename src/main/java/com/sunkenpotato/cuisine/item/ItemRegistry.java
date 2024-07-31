package com.sunkenpotato.cuisine.item;

import com.sunkenpotato.cuisine.Cuisine;
import com.sunkenpotato.cuisine.block.BlockRegistry;
import com.sunkenpotato.cuisine.util.RegistryClass;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
@RegistryClass
public class ItemRegistry {

    public static final Item SALT_STONE = register(new Item(new Item.Settings()), "salt_stone");
    public static final Item GROUND_SALT = register(new Item(new Item.Settings()), "ground_salt");
    public static final BlockItem SALT_ORE_ITEM = register(new BlockItem(BlockRegistry.SALT_ORE, new Item.Settings()), "salt_ore");
    public static final BlockItem SALT_BLOCK_ITEM = register(new BlockItem(BlockRegistry.SALT_BLOCK, new Item.Settings()), "salt_block");
    public static final BlockItem POT_BLOCK_ITEM = register(new BlockItem(BlockRegistry.POT_BLOCK, new Item.Settings()), "pot");
    public static final BlockItem OLIVE_LEAVES_ITEM = register(new BlockItem(BlockRegistry.OLIVE_LEAVES, new Item.Settings()), "olive_leaves");
    public static final BlockItem OLIVE_LOG_ITEM = register(new BlockItem(BlockRegistry.OLIVE_LOG, new Item.Settings()), "olive_log");


    public static Item register(Item item, String name) {
        return Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, name), item);
    }

    public static BlockItem register(BlockItem item, String name) {
        return Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, name), item);
    }

    public static void initialize() {

    }

}
