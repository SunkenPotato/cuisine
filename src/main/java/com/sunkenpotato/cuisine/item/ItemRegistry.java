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

@RegistryClass
public class ItemRegistry {

    public static final Item SALT_STONE = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "salt_stone"), new Item(new Item.Settings()));
    public static final Item GROUND_SALT = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "ground_salt"), new Item(new Item.Settings()));
    public static final BlockItem SALT_ORE_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "salt_ore"), new BlockItem(BlockRegistry.SALT_ORE, new Item.Settings()));
    public static final BlockItem SALT_BLOCK_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "salt_block"), new BlockItem(BlockRegistry.SALT_BLOCK, new Item.Settings()));
    public static final BlockItem POT_BLOCK_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "pot_block"), new BlockItem(BlockRegistry.POT_BLOCK, new Item.Settings()));
    public static final BlockItem OLIVE_LEAVES_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "olive_leaves"), new BlockItem(BlockRegistry.OLIVE_LEAVES, new Item.Settings()));
    public static final BlockItem OLIVE_LOG_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "olive_log"), new BlockItem(BlockRegistry.OLIVE_LOG, new Item.Settings()));


    public static void initialize() {

    }

}
