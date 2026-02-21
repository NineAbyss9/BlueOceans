
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumSpider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumSpiderRenderer<T extends RedPlumSpider>
extends MobRenderer<T, SpiderModel<T>> {
    public RedPlumSpiderRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SpiderModel<>(pContext.bakeLayer(ModelLayers.SPIDER)), 1F);
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(1.25F, 1.25F, 1.25F);
    }

    public ResourceLocation getTextureLocation(T pEntity) {
        return BlueOceans.redPlum("red_plum_spider");
    }
}
