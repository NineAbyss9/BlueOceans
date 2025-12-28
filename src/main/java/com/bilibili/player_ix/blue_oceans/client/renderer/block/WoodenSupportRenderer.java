
package com.bilibili.player_ix.blue_oceans.client.renderer.block;

import com.bilibili.player_ix.blue_oceans.common.blocks.be.WoodenSupportBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;

public class WoodenSupportRenderer<T extends WoodenSupportBlockEntity>
implements BlockEntityRenderer<T> {
    public WoodenSupportRenderer() {
    }

    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer,
                       int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.isBurning()) {
            ItemStack stack = pBlockEntity.getPuttedItem().copy();
            pPoseStack.pushPose();

            pPoseStack.popPose();
        }
    }
}
