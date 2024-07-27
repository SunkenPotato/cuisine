package com.sunkenpotato.cuisine.gen;

import com.sunkenpotato.cuisine.Cuisine;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class OreGeneration {
    public static final RegistryKey<PlacedFeature> SALT_ORE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Cuisine.MOD_ID, "salt_vein"));

    public static void initialize() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SALT_ORE_PLACED);
    }
}
