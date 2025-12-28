
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.client.model.RedPlumGirlModel;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl.AbstractGirl;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;

public abstract class AbstractGirlRenderer<T extends AbstractGirl>
        extends MobRenderer<T, RedPlumGirlModel<T>> {
    protected AbstractGirlRenderer(EntityRendererProvider.Context pContext, RedPlumGirlModel<T> m, float a) {
        super(pContext, m, a);
        this.addLayer(new CustomHeadLayer<>(this, pContext.getModelSet(),
                pContext.getItemInHandRenderer()));
    }

    protected void scale(T pEntity, PoseStack pStack, float pPartialTickTime) {
        float s = 0.9375f;
        pStack.scale(s, s, s);
    }
}
