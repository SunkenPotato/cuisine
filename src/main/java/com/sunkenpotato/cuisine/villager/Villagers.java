package com.sunkenpotato.cuisine.villager;

import com.google.common.collect.ImmutableSet;
import com.sunkenpotato.cuisine.Cuisine;
import com.sunkenpotato.cuisine.block.BlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class Villagers {

    public static final RegistryKey<PointOfInterestType> CHEF_POI_KEY = poiKey("chefpoi");
    public static final PointOfInterestType CHEF_POI = registerPOI("chefpoi", BlockRegistry.POT_BLOCK);


    public static final VillagerProfession CHEF = registerProfession("chef", CHEF_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(Cuisine.MOD_ID, name), new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(Cuisine.MOD_ID, name), 1, 15, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(Cuisine.MOD_ID, name));
    }

    public static void registerVillagers() {
        Cuisine.LOGGER.info("Registering Villagers");
    }
}
