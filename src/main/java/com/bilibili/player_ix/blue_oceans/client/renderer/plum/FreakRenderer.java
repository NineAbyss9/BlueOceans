
package com.bilibili.player_ix.blue_oceans.client.renderer.plum;

import com.bilibili.player_ix.blue_oceans.client.model.plum.FreakModel;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.Freak;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class FreakRenderer<T extends Freak> extends MobRenderer<T, FreakModel<T>> {
    private static final ResourceLocation FREAKER = new ResourceLocation("blue_oceans:textures/entities/red_plum_mobs/freak.png");

    public FreakRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new FreakModel<>(p_174304_.bakeLayer(FreakModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, p_174304_.getModelSet(), p_174304_.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, p_174304_.getItemInHandRenderer()) {
            public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pEntity, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
                if (pEntity.isCastingSpell() || pEntity.isAggressive()) {
                    super.render(pPoseStack, pBuffer, pPackedLight, pEntity, p_117208_, p_117209_, p_117210_, p_117211_, p_117212_, p_117213_);
                }
            }
        });
    }

    public ResourceLocation getTextureLocation(T t) {
        return FREAKER;
    }

    protected boolean isShaking(T pEntity) {
        return pEntity.isCrazy();
    }
}
