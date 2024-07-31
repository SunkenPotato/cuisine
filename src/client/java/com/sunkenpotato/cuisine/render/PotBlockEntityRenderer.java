package com.sunkenpotato.cuisine.render;

import com.sunkenpotato.cuisine.block.PotBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class PotBlockEntityRenderer implements BlockEntityRenderer<PotBlockEntity> {

    public PotBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    private static final Identifier WATER_TEXTURE = Identifier.of("minecraft:block/water_still");

    @Override
    public void render(PotBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int maxSize = entity.inventory.size();

        for (int i = 0; i < maxSize; i++) {

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            ItemStack stack = entity.inventory.get(i);

            renderItem(stack, itemRenderer, matrices, vertexConsumers, entity, i);
        }

    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }

    private void renderItem(ItemStack itemStack, ItemRenderer itemRenderer, MatrixStack stack, VertexConsumerProvider vertexConsumers, PotBlockEntity entity, int index) {

        if (index == 0) stack.translate(0, 0.2f, 0);

        stack.push();

        stack.translate(0.5f + entity.randomValues.get(index) * 0.1f, (float) index / 40, 0.5f - entity.randomValues.get(index) * 0.1f);
        stack.scale(0.5f, 0.4f, 0.5f);

        stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90 + entity.randomValues.get(index) * 45));
        stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.randomValues.get(index) * 360f));

        if (stack.isEmpty()) itemStack = Items.AIR.getDefaultStack();

        itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, stack, vertexConsumers, entity.getWorld(), 1);

        stack.pop();
    }

}
