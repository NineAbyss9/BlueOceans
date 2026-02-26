
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.plum.RedPlumSlayerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumSlayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumSlayerRenderer<T extends RedPlumSlayer>
extends MobRenderer<T, RedPlumSlayerModel<T>> {
    public RedPlumSlayerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RedPlumSlayerModel<>(pContext.bakeLayer(RedPlumSlayerModel
                .LAYER_LOCATION)), 0.5F);
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(1.5F, 1.5F, 1.5F);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return BlueOceans.redPlum("red_plum_slayer");
    }
}
