
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.client.model.RedPlumGirlModel;
import com.bilibili.player_ix.blue_oceans.client.renderer.AbstractGirlRenderer;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl.RedPlumGirl;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumGirlRenderer<T extends RedPlumGirl> extends AbstractGirlRenderer<T> {
    private static final ResourceLocation GIRL = new ResourceLocation("blue_oceans:textures/entities/red_plum_mobs/red_plum_girl/red.png");

    public RedPlumGirlRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new RedPlumGirlModel<>(p_174304_.bakeLayer(RedPlumGirlModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, p_174304_.getItemInHandRenderer()) {

            public void render( PoseStack p_117204_,  MultiBufferSource p_117205_, int p_117206_,  T p_117207_, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
                if (p_117207_.isCastingSpell() || p_117207_.isAggressive()) {
                    super.render(p_117204_, p_117205_, p_117206_, p_117207_, p_117208_, p_117209_, p_117210_, p_117211_, p_117212_, p_117213_);
                }
            }
        });
    }

    @Override
    public  ResourceLocation getTextureLocation( T t) {
        return GIRL;
    }
}
