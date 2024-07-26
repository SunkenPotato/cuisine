package com.sunkenpotato.cuisine.crop;

import com.sunkenpotato.cuisine.Cuisine;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class CropRegistry {

    public static final AbstractBlock.Settings BUSH_SETTINGS = AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .nonOpaque()
            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH);

    // Blocks
    public static final Item.Settings DEFAULT_SETTINGS = new Item.Settings();

    public static final Block ROSEMARY_BUSH = registerBlockWithoutItem("rosemary",
            new RosemaryBush(BUSH_SETTINGS));

    public static final Block BASIL_BUSH = registerBlockWithoutItem("basil",
            new BasilPlant(BUSH_SETTINGS));


    // Items
    public static final Item ROSEMARY = registerItem("rosemary", new BasicSeedItem(ROSEMARY_BUSH, DEFAULT_SETTINGS));
    public static final Item BASIL = registerItem("basil", new BasicSeedItem(BASIL_BUSH, DEFAULT_SETTINGS));

    @SuppressWarnings("unused")
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, name), block);
    }

    @SuppressWarnings({"UnusedReturnValue"})
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    private static Block registerBlockWithoutItem(String name, Block block) {

        return Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, name), block);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, name), item);
    }


    public static void initialize() {
        Cuisine.LOGGER.info("Registering Bushes/Crops for " + Cuisine.MOD_ID);
    }

}
