package com.sunkenpotato.herbal;

import com.sunkenpotato.herbal.crop.CropRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class HerbalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		addCutouts();
	}

	public void addCutouts() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), CropRegistry.ROSEMARY_BUSH, CropRegistry.BASIL_BUSH);
	}
}