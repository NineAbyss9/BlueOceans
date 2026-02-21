
package com.bilibili.player_ix.blue_oceans.client.renderer.villager;

import com.bilibili.player_ix.blue_oceans.client.model.HuntingVillagerModel;
import com.bilibili.player_ix.blue_oceans.client.renderer.layer.item.OffHandItemLayer;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.VillagerBiologist;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class VillagerBiologistRenderer<V extends VillagerBiologist>
extends MobRenderer<V, HuntingVillagerModel<V>> {
    public VillagerBiologistRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new HuntingVillagerModel<>(p_174304_.bakeLayer(HuntingVillagerModel.LOCATION)),
                0.5f);
        this.addLayer(new ItemInHandLayer<>(this, p_174304_.getItemInHandRenderer()) {
            public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, V pLivingEntity, float pLimbSwing,
                               float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
                if (pLivingEntity.canAttackTarget())
                    super.render(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks,
                            pAgeInTicks, pNetHeadYaw, pHeadPitch);
            }
        });
        this.addLayer(new OffHandItemLayer<>(this, p_174304_.getItemInHandRenderer()) {
            public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, V pLivingEntity, float pLimbSwing,
                               float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
                if (!pLivingEntity.canAttackTarget())
                    super.render(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks,
                            pAgeInTicks, pNetHeadYaw, pHeadPitch);
            }
        });
    }

    public ResourceLocation getTextureLocation(V v) {
        return new ResourceLocation("blue_oceans:textures/entities/villagers/biologist.png");
    }
}
