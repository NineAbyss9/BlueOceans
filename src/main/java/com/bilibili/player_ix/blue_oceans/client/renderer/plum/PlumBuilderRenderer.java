
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.PlumBuilderModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PlumBuilderRenderer<E extends PlumBuilder>
extends MobRenderer<E, PlumBuilderModel<E>> {
    public PlumBuilderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new PlumBuilderModel<>(pContext.bakeLayer(PlumBuilderModel.LAYER_LOCATION)),
                0.5F);
    }

    protected void scale(E pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        if (!pLivingEntity.isBuilding())
            pPoseStack.scale(0.5F, 0.5F, 0.5F);
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.redPlum("plum_factory");
    }
}
