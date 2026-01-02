
package com.bilibili.player_ix.blue_oceans.client.renderer.block;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.WoodenSupportBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class WoodenSupportRenderer
implements BlockEntityRenderer<WoodenSupportBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    public WoodenSupportRenderer(BlockEntityRendererProvider.Context pContext) {
        this.context = pContext;
    }

    public void render(WoodenSupportBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemStack stack = pBlockEntity.getPuttedItem();
        if (stack != null) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.5, 0, 0.5);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90F));
            var a = this.context.getItemRenderer();
            a.render(stack, ItemDisplayContext.NONE, false, pPoseStack, pBuffer, 255,
                    1, a.getModel(stack, pBlockEntity.getLevel(), null, 0));
            pPoseStack.popPose();
        }
    }
}
