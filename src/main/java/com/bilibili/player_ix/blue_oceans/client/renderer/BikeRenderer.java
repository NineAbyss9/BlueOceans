
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.BikeModel;
import com.bilibili.player_ix.blue_oceans.common.entities.traffic.Bike;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BikeRenderer<T extends Bike>
extends MobRenderer<T, BikeModel<T>> {
    public BikeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BikeModel<>(pContext.bakeLayer(BikeModel.LAYER_LOCATION)), 0.9F);
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(2.0F, 2.0F, 2.0F);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return BlueOceans.entity("/traffic/bike");
    }
}
