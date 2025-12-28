
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.client.model.HeartOfHorrorModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.HeartOfHorror;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HeartOfHorrorRenderer<T extends HeartOfHorror> extends MobRenderer<T, HeartOfHorrorModel<T>> {
    private static final ResourceLocation HeartOfHorror = new ResourceLocation("blue_oceans:textures/entities/red_plum_mobs/heart_of_horror.png");

    public HeartOfHorrorRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new HeartOfHorrorModel<>(p_174304_.bakeLayer(HeartOfHorrorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    protected void scale(@NotNull T p_115314_, @NotNull PoseStack p_115315_, float p_115316_) {
        p_115315_.scale(2f, 2f, 2f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T t) {
        return HeartOfHorror;
    }

    @Override
    protected boolean isShaking(@NotNull T p_115304_) {
        return p_115304_.isHalfHealth();
    }
}
