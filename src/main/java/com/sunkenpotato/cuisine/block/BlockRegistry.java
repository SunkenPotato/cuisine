package com.sunkenpotato.cuisine.block;

import com.sunkenpotato.cuisine.Cuisine;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockRegistry {

    public static final Block SALT_ORE = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "salt_ore"), new Block(AbstractBlock.Settings.create().solid().requiresTool().hardness(3.0f)));
    public static final Block SALT_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "salt_block"), new Block(AbstractBlock.Settings.create().solid().requiresTool().hardness(3.0f)));
    public static final BlockItem SALT_ORE_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "salt_ore"), new BlockItem(SALT_ORE, new Item.Settings()));
    public static final BlockItem SALT_BLOCK_ITEM = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "salt_block"), new BlockItem(SALT_BLOCK, new Item.Settings()));

    public static final Block PLACEHOLDER_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "placeholder"), new Block(AbstractBlock.Settings.create().solid()));

    public static void initialize() {

    }

}
