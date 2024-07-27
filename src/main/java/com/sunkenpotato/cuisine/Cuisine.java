package com.sunkenpotato.cuisine;

import com.sunkenpotato.cuisine.block.BlockRegistry;
import com.sunkenpotato.cuisine.crop.CropRegistry;
import com.sunkenpotato.cuisine.gen.OreGeneration;
import com.sunkenpotato.cuisine.item.ItemRegistry;
import com.sunkenpotato.cuisine.villager.ChefTradeInjects;
import com.sunkenpotato.cuisine.villager.FarmerTradeInjects;
import com.sunkenpotato.cuisine.villager.Villagers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cuisine implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("cuisine");
	public static final String MOD_ID = "cuisine";

	@Override
	public void onInitialize() {
		CropRegistry.initialize();
		BlockRegistry.initialize();
		ItemRegistry.initialize();
		OreGeneration.initialize();
		Villagers.registerVillagers();
		ChefTradeInjects.registerTrades();
		FarmerTradeInjects.registerTrades();

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

	}
}