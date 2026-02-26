
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.client.model.plum.HeartOfHorrorModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.HeartOfHorror;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HeartOfHorrorRenderer<T extends HeartOfHorror>
extends MobRenderer<T, HeartOfHorrorModel<T>> {
    private static final ResourceLocation HeartOfHorror = new ResourceLocation(
            "blue_oceans:textures/entities/red_plum_mobs/heart_of_horror.png");

    public HeartOfHorrorRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new HeartOfHorrorModel<>(p_174304_.bakeLayer(HeartOfHorrorModel.LAYER_LOCATION)),
                0.5f);
    }

    protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
        p_115315_.scale(2f, 2f, 2f);
    }

    public ResourceLocation getTextureLocation(T t) {
        return HeartOfHorror;
    }

    protected boolean isShaking(T p_115304_) {
        return p_115304_.isHalfHealth();
    }
}
