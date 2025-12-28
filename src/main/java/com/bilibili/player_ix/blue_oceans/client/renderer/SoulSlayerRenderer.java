
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.common.entities.undeads.SoulSlayer;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SoulSlayerRenderer<T extends SoulSlayer>
extends MobRenderer<T, SkeletonModel<T>> {
    public SoulSlayerRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new SkeletonModel<>(p_174304_.bakeLayer(ModelLayers.SKELETON)), 0.5f);
    }

    public ResourceLocation getTextureLocation(T t) {
        return new ResourceLocation("blue_oceans:textures/entities/undeads/death.png");
    }
}
