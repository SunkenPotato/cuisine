package com.sunkenpotato.cuisine.block;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.sunkenpotato.cuisine.Cuisine;
import com.sunkenpotato.cuisine.util.RegistryClass;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


/**
 * A registry containing all registered Cuisine blocks
 * @see com.sunkenpotato.cuisine.crop.CropRegistry
 */
@RegistryClass
public class BlockRegistry {

    public static final Block SALT_ORE = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "salt_ore"), new Block(AbstractBlock.Settings.create().solid().requiresTool().hardness(3.0f)));
    public static final Block SALT_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "salt_block"), new Block(AbstractBlock.Settings.create().solid().requiresTool().hardness(3.0f)));

    public static final Block OLIVE_LEAVES = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "olive_leaves"), new LeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(Blocks::canSpawnOnLeaves).suffocates(Blocks::never).blockVision(Blocks::never).burnable().pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)));
    public static final Block OLIVE_LOG = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "olive_log"), new PillarBlock(AbstractBlock.Settings.create().solid().hardness(1).sounds(BlockSoundGroup.WOOD)));

    // public static final Block PLACEHOLDER_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "placeholder"), new Block(AbstractBlock.Settings.create().solid()));
    public static final Block POT_BLOCK = Registry.register(Registries.BLOCK, Identifier.of(Cuisine.MOD_ID, "pot_block"), new PotBlock(AbstractBlock.Settings.create().solid().nonOpaque()));

    public static final BlockEntityType<PotBlockEntity> POT_BLOCK_ENTITY_T = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(Cuisine.MOD_ID, "pot_block_entity"),
            BlockEntityType.Builder.create(PotBlockEntity::new, POT_BLOCK).build()
    );



    public static void initialize() {
        Cuisine.LOGGER.info("Registering Blocks");
    }

}
