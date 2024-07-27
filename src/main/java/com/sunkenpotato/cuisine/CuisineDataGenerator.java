package com.sunkenpotato.cuisine;

import com.sunkenpotato.cuisine.block.BlockRegistry;
import com.sunkenpotato.cuisine.crop.CropRegistry;
import com.sunkenpotato.cuisine.item.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.client.ModelProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.PointOfInterestTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.concurrent.CompletableFuture;

public class CuisineDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(LootTableDataGenerator::new);
		pack.addProvider(CraftingRecipeDataGenerator::new);
		pack.addProvider(ModPOITagProvider::new);
	}

	private static class LootTableDataGenerator extends FabricBlockLootTableProvider {
		protected LootTableDataGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}

		@Override
		public void generate() {

			UniformLootNumberProvider numberProvider = UniformLootNumberProvider.create(2.0f, 3.0f);
			ConstantLootNumberProvider constantLootNumberProvider = ConstantLootNumberProvider.create(8.0f);

			addDrop(CropRegistry.ROSEMARY_BUSH, drops(CropRegistry.ROSEMARY));
			addDrop(BlockRegistry.SALT_ORE, drops(ItemRegistry.SALT_STONE, numberProvider));
			addDrop(BlockRegistry.SALT_BLOCK, drops(ItemRegistry.GROUND_SALT, constantLootNumberProvider));
		}
	}

	private static final class CraftingRecipeDataGenerator extends FabricRecipeProvider {

		public CraftingRecipeDataGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		public void generate(RecipeExporter exporter) {
			ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.SALT_BLOCK).pattern("###").pattern("###").pattern("###")
					.input('#', ItemRegistry.SALT_STONE)
					.criterion(hasItem(ItemRegistry.SALT_STONE), conditionsFromItem(ItemRegistry.SALT_STONE))
					.offerTo(exporter);
		}
	}

	private static final class ModPOITagProvider extends TagProvider<PointOfInterestType> {

		public ModPOITagProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookupFuture) {
			super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registryLookupFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup lookup) {
			getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
					.addOptional(Identifier.of(Cuisine.MOD_ID, "chefpoi"));
		}
	}
}
