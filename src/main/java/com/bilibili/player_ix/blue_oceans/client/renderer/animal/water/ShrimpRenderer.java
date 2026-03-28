
package com.bilibili.player_ix.blue_oceans.client.renderer.animal.water;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.animal.water.ShrimpModel;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean.Shrimp;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShrimpRenderer<E extends Shrimp>
extends MobRenderer<E, ShrimpModel<E>>{
    public ShrimpRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShrimpModel<>(pContext.bakeLayer(ShrimpModel.LAYER_LOCATION)), 0.5f);
    }

    protected void scale(E pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
    }

    private static ResourceLocation LOC = BlueOceans.water("shrimp");

    public ResourceLocation getTextureLocation(E pEntity) {
        return LOC;
    }
}
