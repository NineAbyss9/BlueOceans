
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.client.model.plum.RedPlumIllagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.Dictator;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class DictatorRenderer<T extends Dictator> extends RedPlumIllagerRenderer<T> {
    private static final ResourceLocation DICTATOR = new ResourceLocation("blue_oceans:textures/entities/red_plum_mobs/dictator.png");

    public DictatorRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new RedPlumIllagerModel<>(p_174304_.bakeLayer(RedPlumIllagerModel.LAYER_LOCATION) ), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, p_174304_.getItemInHandRenderer()) {
            public void render(PoseStack p_114989_, MultiBufferSource p_114990_, int p_114991_, T p_114992_, float p_114993_, float p_114994_, float p_114995_, float p_114996_, float p_114997_, float p_114998_) {
                if (p_114992_.isCastingSpell() || p_114992_.isAggressive()) {
                    super.render(p_114989_, p_114990_, p_114991_, p_114992_, p_114993_, p_114994_, p_114995_, p_114996_, p_114997_, p_114998_);
                }
            }
        });
    }

    public ResourceLocation getTextureLocation(T entity) {
        return DICTATOR;
    }
}
