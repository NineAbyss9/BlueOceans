
package com.bilibili.player_ix.blue_oceans.client.renderer.villager;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.villager.HuntingVillagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.BaseVillager;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class BaseVillagerRenderer<T extends BaseVillager>
extends AbstractHuntingVillagerRenderer<T> {
    public BaseVillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new HuntingVillagerModel<>(context.bakeLayer(HuntingVillagerModel.LOCATION)),
                0.5F);
        this.addItemInHandLayer(context);
    }

    protected void addItemInHandLayer(EntityRendererProvider.Context context) {
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
            public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity,
                               float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw,
                               float pHeadPitch) {
                if (pLivingEntity.getArmPose() != ApiPose.CROSSED) {
                    super.render(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount,
                            pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                }
            }
        });
    }

    protected void scale(T pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 0.9375F;
        if (pLivingEntity.isBaby()) {
            f *= 0.5F;
            this.shadowRadius = 0.25F;
        } else {
            this.shadowRadius = 0.5F;
        }
        pPoseStack.scale(f, f, f);
    }

    private static ResourceLocation LOC = BlueOceans.villager("base_villager");

    public ResourceLocation getTextureLocation(T pEntity) {
        return LOC;
    }
}
