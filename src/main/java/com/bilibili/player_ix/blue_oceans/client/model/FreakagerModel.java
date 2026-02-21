
package com.bilibili.player_ix.blue_oceans.client.model;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.AnimationUtil;
import com.bilibili.player_ix.blue_oceans.common.entities.illagers.red_plum_illager.Freak;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;

public class FreakagerModel<T extends Entity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BlueOceans.MOD_ID, "freak"), "main");
    public final ModelPart root;
    public final ModelPart head;
    public final ModelPart hat;
    public final ModelPart arms;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightHindLeg;
    public final ModelPart leftHindLeg;
    public final ModelPart rightMiddleHindLeg;
    public final ModelPart leftMiddleHindLeg;
    public final ModelPart rightMiddleFrontLeg;
    public final ModelPart leftMiddleFrontLeg;
    public final ModelPart rightFrontLeg;
    public final ModelPart leftFrontLeg;

    public FreakagerModel(ModelPart p_170688_) {
        super();
        this.root = p_170688_;
        this.head = p_170688_.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hat.visible = true;
        this.arms = p_170688_.getChild("arms");
        this.leftArm = p_170688_.getChild("left_arm");
        this.rightArm = p_170688_.getChild("right_arm");
        this.rightHindLeg = p_170688_.getChild("right_hind_leg");
        this.leftHindLeg = p_170688_.getChild("left_hind_leg");
        this.rightMiddleHindLeg = p_170688_.getChild("right_middle_hind_leg");
        this.leftMiddleHindLeg = p_170688_.getChild("left_middle_hind_leg");
        this.rightMiddleFrontLeg = p_170688_.getChild("right_middle_front_leg");
        this.leftMiddleFrontLeg = p_170688_.getChild("left_middle_front_leg");
        this.rightFrontLeg = p_170688_.getChild("right_front_leg");
        this.leftFrontLeg = p_170688_.getChild("left_front_leg");
    }

    public ModelPart getHead() {
        return this.head;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        $$2.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
        $$2.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        $$1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition $$3 = $$1.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        $$3.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
        $$1.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        $$1.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        CubeListBuilder $$4 = CubeListBuilder.create().texOffs(0, 68).mirror().addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        CubeListBuilder $$5 = CubeListBuilder.create().texOffs(0, 68).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F);
        $$1.addOrReplaceChild("right_hind_leg", $$5, PartPose.offset(-4.0F, 15.0F, 2.0F));
        $$1.addOrReplaceChild("left_hind_leg", $$4, PartPose.offset(4.0F, 15.0F, 2.0F));
        $$1.addOrReplaceChild("right_middle_hind_leg", $$5, PartPose.offset(-4.0F, 15.0F, 1.0F));
        $$1.addOrReplaceChild("left_middle_hind_leg", $$4, PartPose.offset(4.0F, 15.0F, 1.0F));
        $$1.addOrReplaceChild("right_middle_front_leg", $$5, PartPose.offset(-4.0F, 15.0F, 0.0F));
        $$1.addOrReplaceChild("left_middle_front_leg", $$4, PartPose.offset(4.0F, 15.0F, 0.0F));
        $$1.addOrReplaceChild("right_front_leg", $$5, PartPose.offset(-4.0F, 15.0F, -1.0F));
        $$1.addOrReplaceChild("left_front_leg", $$4, PartPose.offset(4.0F, 15.0F, -1.0F));
        return LayerDefinition.create($$0, 64, 72);
    }

    public ModelPart root() {
        return this.root;
    }

    private ModelPart getArm(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
    }

    protected void animateWalk(AnimationDefinition p_268159_, float p_268057_, float p_268347_, float p_268138_, float p_268165_) {
        super.animateWalk(p_268159_, p_268057_, p_268347_, p_268138_, p_268165_);
    }

    public void setupAnim(T pEntity, float p_102929_, float p_102930_, float p_102931_, float p_102932_, float p_102933_) {
        this.head.yRot = p_102932_ * 0.017f;
        this.head.xRot = p_102933_ * 0.017f;
        this.rightArm.xRot = Mth.cos(p_102929_ * 0.6662F + 3.1415927F) * 2.0F * p_102930_ * 0.5F;
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.xRot = Mth.cos(p_102929_ * 0.6662F) * 2.0F * p_102930_ * 0.5F;
        this.leftArm.yRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        float $float = 0.7853982F;
        this.rightHindLeg.zRot = -$float;
        this.leftHindLeg.zRot = $float;
        this.rightMiddleHindLeg.zRot = -0.58119464F;
        this.leftMiddleHindLeg.zRot = 0.58119464F;
        this.rightMiddleFrontLeg.zRot = -0.58119464F;
        this.leftMiddleFrontLeg.zRot = 0.58119464F;
        this.rightFrontLeg.zRot = -0.7853982F;
        this.leftFrontLeg.zRot = 0.7853982F;
        this.rightHindLeg.yRot = 0.7853982F;
        this.leftHindLeg.yRot = -0.7853982F;
        this.rightMiddleHindLeg.yRot = 0.3926991F;
        this.leftMiddleHindLeg.yRot = -0.3926991F;
        this.rightMiddleFrontLeg.yRot = -0.3926991F;
        this.leftMiddleFrontLeg.yRot = 0.3926991F;
        this.rightFrontLeg.yRot = -0.7853982F;
        this.leftFrontLeg.yRot = 0.7853982F;
        float $$9 = -(Mth.cos(p_102929_ * 0.6662F * 2.0F + 0.0F) * 0.4F) * p_102930_;
        float $$10 = -(Mth.cos(p_102929_ * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * p_102930_;
        float $$11 = -(Mth.cos(p_102929_ * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * p_102930_;
        float $$12 = -(Mth.cos(p_102929_ * 0.6662F * 2.0F + 4.712389F) * 0.4F) * p_102930_;
        float $$13 = Math.abs(Mth.sin(p_102929_ * 0.6662F + 0.0F) * 0.4F) * p_102930_;
        float $$14 = Math.abs(Mth.sin(p_102929_ * 0.6662F + 3.1415927F) * 0.4F) * p_102930_;
        float $$15 = Math.abs(Mth.sin(p_102929_ * 0.6662F + 1.5707964F) * 0.4F) * p_102930_;
        float $$16 = Math.abs(Mth.sin(p_102929_ * 0.6662F + 4.712389F) * 0.4F) * p_102930_;
        ModelPart var10000 = this.rightHindLeg;
        var10000.yRot += $$9;
        var10000 = this.leftHindLeg;
        var10000.yRot += -$$9;
        var10000 = this.rightMiddleHindLeg;
        var10000.yRot += $$10;
        var10000 = this.leftMiddleHindLeg;
        var10000.yRot += -$$10;
        var10000 = this.rightMiddleFrontLeg;
        var10000.yRot += $$11;
        var10000 = this.leftMiddleFrontLeg;
        var10000.yRot += -$$11;
        var10000 = this.rightFrontLeg;
        var10000.yRot += $$12;
        var10000 = this.leftFrontLeg;
        var10000.yRot += -$$12;
        var10000 = this.rightHindLeg;
        var10000.zRot += $$13;
        var10000 = this.leftHindLeg;
        var10000.zRot += -$$13;
        var10000 = this.rightMiddleHindLeg;
        var10000.zRot += $$14;
        var10000 = this.leftMiddleHindLeg;
        var10000.zRot += -$$14;
        var10000 = this.rightMiddleFrontLeg;
        var10000.zRot += $$15;
        var10000 = this.leftMiddleFrontLeg;
        var10000.zRot += -$$15;
        var10000 = this.rightFrontLeg;
        var10000.zRot += $$16;
        var10000 = this.leftFrontLeg;
        var10000.zRot += -$$16;
        if (pEntity instanceof Freak freak) {
            ApiPose pose = freak.getPoses();
            switch (pose) {
                case ATTACKING: {
                    if (freak.getMainHandItem().isEmpty()) {
                        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, p_102931_);
                    } else {
                        AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, freak, this.attackTime, p_102931_);
                    }
                    break;
                }
                case BOW_AND_ARROW: {
                    AnimationUtil.bowAndArrow(this.leftArm, this.rightArm, this.head);
                    break;
                }
                case SPELL_CASTING: {
                    AnimationUtil.castingSpell(this.leftArm, this.rightArm, p_102931_);
                    break;
                }
            }
            boolean o = freak.getPoses() == ApiPose.CROSSED;
            this.arms.visible = o;
            this.leftArm.visible = !o;
            this.rightArm.visible = !o;
            if (freak.isCrazy()) {
                float flot = 0.5f;
                head.x += (-flot + freak.getRandom().nextFloat() * 1.2f);
                if (freak.tickCount %2 == 0) head.x = 0;
                head.y += (-flot + freak.getRandom().nextFloat() * 1.2f);
                if (freak.tickCount %2 == 0) head.y = 0;
                head.z += (-flot + freak.getRandom().nextFloat() * 1.2f);
                if (freak.tickCount %2 == 0) head.z = 0;
            }
        }
    }
}
