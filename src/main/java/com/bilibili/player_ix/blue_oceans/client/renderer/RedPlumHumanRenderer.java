
package com.bilibili.player_ix.blue_oceans.client.renderer;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumHuman;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RedPlumHumanRenderer<R extends RedPlumHuman>
extends MobRenderer<R, HumanoidModel<R>> {
    public RedPlumHumanRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HumanoidModel<>(pContext.bakeLayer(ModelLayers.ZOMBIE)) {
            public void setupAnim(R pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks,
                                  float pNetHeadYaw, float pHeadPitch) {
                super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                AnimationUtils.animateZombieArms(this.leftArm,
                        this.rightArm, pEntity.isAggressive(), this.attackTime, pAgeInTicks);
            }
        }, 0.5f);
    }

    public ResourceLocation getTextureLocation(R r) {
        return BlueOceans.location("textures/entities/red_plum_mobs/human.png");
    }
}
