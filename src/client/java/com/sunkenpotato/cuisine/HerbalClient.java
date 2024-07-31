package com.sunkenpotato.cuisine;

import com.sunkenpotato.cuisine.block.BlockRegistry;
import com.sunkenpotato.cuisine.crop.CropRegistry;
import com.sunkenpotato.cuisine.render.PotBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class HerbalClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		addCutouts();

		BlockEntityRendererFactories.register(BlockRegistry.POT_BLOCK_ENTITY_T, PotBlockEntityRenderer::new);

		BlockColorProvider provider = (state, world, pos, tintIndex) -> {
			switch (tintIndex) {
				case 1:
					return 0xfffceeac;
				case 0:
					return 0xff3477eb;
				default: return 0x000000;
			}
		};

		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.POT_BLOCK, RenderLayer.getTranslucent());

		ColorProviderRegistry.BLOCK.register(provider, BlockRegistry.POT_BLOCK);

    }

	public void addCutouts() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CropRegistry.ROSEMARY_BUSH, CropRegistry.BASIL_BUSH);
	}


}