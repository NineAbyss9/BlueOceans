
package com.bilibili.player_ix.blue_oceans.client.renderer.villager;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.model.HattedVillagerModel;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.Farmer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class FarmerRenderer<E extends Farmer>
extends MobRenderer<E, HattedVillagerModel<E>> {
    public FarmerRenderer(EntityRendererProvider.Context context) {
        super(context, new HattedVillagerModel<>(context.bakeLayer(HattedVillagerModel.LOCATION)),
                0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
            public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, E pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
                if (pLivingEntity.isWorking() || pLivingEntity.isAggressive())
                    super.render(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            }
        });
    }

    public ResourceLocation getTextureLocation(E pEntity) {
        return BlueOceans.villager("farmer");
    }
}
