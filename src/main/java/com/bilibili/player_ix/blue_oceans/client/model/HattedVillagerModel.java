
package com.bilibili.player_ix.blue_oceans.client.model;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.mob.IBOMob;
import com.bilibili.player_ix.blue_oceans.client.AnimationUtil;
import com.bilibili.player_ix.blue_oceans.util.MathUtils;
import com.github.player_ix.ix_api.api.ApiPose;
import com.github.player_ix.ix_api.util.Maths;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;

public class HattedVillagerModel<T extends Mob & IBOMob>
extends HierarchicalModel<T>
implements HeadedModel, ArmedModel {
    public static final ModelLayerLocation LOCATION = BlueOceans.modelLocation("hattedvillager");
    public final ModelPart root;
    public final ModelPart head;
    public final ModelPart hat;
    public final ModelPart hatRim;
    public final ModelPart arms;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart rightArm;
    public final ModelPart leftArm;

    public HattedVillagerModel(ModelPart pRoot) {
        this.root = pRoot;
        this.head = pRoot.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hatRim = this.hat.getChild("hat_rim");
        this.hat.visible = true;
        this.arms = pRoot.getChild("arms");
        this.leftLeg = pRoot.getChild("left_leg");
        this.rightLeg = pRoot.getChild("right_leg");
        this.leftArm = pRoot.getChild("left_arm");
        this.rightArm = pRoot.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, new CubeDeformation(0.45f)), PartPose.ZERO);
        hat.addOrReplaceChild("hat_rim", CubeListBuilder.create().texOffs(0, 63).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F), PartPose.rotation((-(float)Math.PI / 2F), 0.0F, 0.0F));
        head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f), PartPose.offset(0.0f, -2.0f, 0.0f));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f).texOffs(0, 38).addBox(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition arms = root.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f).texOffs(40, 38).addBox(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f), PartPose.offsetAndRotation(0.0f, 3.0f, -1.0f, -0.75f, 0.0f, 0.0f));
        arms.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f), PartPose.ZERO);
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(-2.0f, 12.0f, 0.0f));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(2.0f, 12.0f, 0.0f));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(-5.0f, 2.0f, 0.0f));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f), PartPose.offset(5.0f, 2.0f, 0.0f));
        return LayerDefinition.create(mesh, 64, 80);
    }

    private ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? leftArm : rightArm;
    }

    public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
        this.getArm(pSide).translateAndRotate(pPoseStack);
    }

    public ModelPart getHead() {
        return head;
    }

    public ModelPart root() {
        return root;
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw,
                          float pHeadPitch) {
        boolean $$7;
        this.head.yRot = pNetHeadYaw * (Maths.CLOSER_PI / 180);
        this.head.xRot = pHeadPitch * (Maths.CLOSER_PI / 180);
        if (this.riding) {
            this.rightArm.xRot = -0.62831855f;
            this.rightArm.yRot = 0.0f;
            this.rightArm.zRot = 0.0f;
            this.leftArm.xRot = -0.62831855f;
            this.leftArm.yRot = 0.0f;
            this.leftArm.zRot = 0.0f;
            this.rightLeg.xRot = -1.4137167f;
            this.rightLeg.yRot = 0.31415927f;
            this.rightLeg.zRot = 0.07853982f;
            this.leftLeg.xRot = -1.4137167f;
            this.leftLeg.yRot = -0.31415927f;
            this.leftLeg.zRot = -0.07853982f;
        } else {
            this.rightArm.xRot = Mth.cos(pLimbSwing * 0.6662f + (float)Math.PI) * 2.0f * pLimbSwingAmount * 0.5f;
            this.rightArm.yRot = 0.0f;
            this.rightArm.zRot = 0.0f;
            this.leftArm.xRot = Mth.cos(pLimbSwing * 0.6662f) * 2.0f * pLimbSwingAmount * 0.5f;
            this.leftArm.yRot = 0.0f;
            this.leftArm.zRot = 0.0f;
            this.rightLeg.xRot = Mth.cos(pLimbSwing * 0.6662f) * 1.4f * pLimbSwingAmount * 0.5f;
            this.rightLeg.yRot = 0.0f;
            this.rightLeg.zRot = 0.0f;
            this.leftLeg.xRot = Mth.cos(pLimbSwing * 0.6662f + (float)Math.PI) * 1.4f * pLimbSwingAmount * 0.5f;
            this.leftLeg.yRot = 0.0f;
            this.leftLeg.zRot = 0.0f;
        }
        ApiPose pose = pEntity.getArmPose();
        switch (pose) {
            case ATTACKING: {
                if (pEntity.getMainHandItem().isEmpty()) {
                    AnimationUtil.animateZombieArms(this.rightArm, this.leftArm, true, this.attackTime, pAgeInTicks);
                } else {
                    AnimationUtil.swingWeaponDown(this.rightArm, this.leftArm, pEntity, this.attackTime, pAgeInTicks);
                }
                break;
            }
            case BOW_AND_ARROW: {
                this.rightArm.yRot = -0.1f + this.head.yRot;
                this.rightArm.xRot = -1.5707964f + this.head.xRot;
                this.leftArm.xRot = -0.9424779f + this.head.xRot;
                this.leftArm.yRot = this.head.yRot - 0.4f;
                this.leftArm.zRot = 1.5707964f;
                break;
            }
            case CROSSBOW_CHARGE: {
                AnimationUtil.animateCrossbowCharge(this.rightArm, this.leftArm, pEntity, true);
                break;
            }
            case CROSSBOW_HOLD: {
                AnimationUtil.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
                break;
            }
            case SPELL_CASTING: {
                this.rightArm.xRot = Mth.cos(pAgeInTicks * 0.6662f) * 0.25f;
                this.leftArm.xRot = Mth.cos(pAgeInTicks * 0.6662f) * 0.25f;
                this.rightArm.yRot = MathUtils.PI;
                this.leftArm.yRot = MathUtils.PI;
                this.rightArm.zRot = 2.3561945f;
                this.leftArm.zRot = -2.3561945f;
                break;
            }
        }
        this.arms.visible = $$7 = pose == ApiPose.CROSSED;
        this.leftArm.visible = !$$7;
        this.rightArm.visible = !$$7;
    }
}
