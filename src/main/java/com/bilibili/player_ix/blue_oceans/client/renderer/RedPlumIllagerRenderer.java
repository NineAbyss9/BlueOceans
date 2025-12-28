
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.client.model.RedPlumIllagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.RedPlumIllager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;

public abstract class RedPlumIllagerRenderer<T extends RedPlumIllager>
        extends MobRenderer<T, RedPlumIllagerModel<T>> {

    public RedPlumIllagerRenderer(EntityRendererProvider.Context p_174304_, RedPlumIllagerModel<T>
            p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
        this.addLayer(new CustomHeadLayer<>(this, p_174304_.getModelSet(), p_174304_
                .getItemInHandRenderer()));
    }

    protected void scale( T p_115314_,  PoseStack p_115315_, float p_115316_) {
        float s = 0.9375f;
        p_115315_.scale(s, s, s);
    }
}
