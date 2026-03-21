
package com.bilibili.player_ix.blue_oceans.client.model.plum;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumMonster;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.world.entity.HumanoidArm;

public class RedPlumIllagerModel<T extends RedPlumMonster> extends HierarchicalModel<T>
        implements HeadedModel, ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("blue_oceans", "redplumillagermodel"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public RedPlumIllagerModel(ModelPart p_170688_) {
        super();
        this.root = p_170688_;
        this.head = p_170688_.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hat.visible = false;
        this.arms = p_170688_.getChild("arms");
        this.leftLeg = p_170688_.getChild("left_leg");
        this.rightLeg = p_170688_.getChild("right_leg");
        this.leftArm = p_170688_.getChild("left_arm");
        this.rightArm = p_170688_.getChild("right_arm");
    }

    public ModelPart getRoot() {
        return this.root;
    }

    public ModelPart getArms() {
        return this.arms;
    }

    public ModelPart getHat() {
        return this.hat;
    }

    public ModelPart getHead() {
        return this.head;
    }

    private ModelPart getArm(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
    }

    public ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        $$2.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
        $$2.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        $$1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38)
                .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition $$3 = $$1.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        $$3.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), PartPose.ZERO);
        $$1.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        $$1.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        $$1.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        $$1.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create($$0, 64, 64);
    }

    public void setupAnim(T p_102928_, float p_102929_, float p_102930_, float p_102931_, float p_102932_, float p_102933_) {
        this.head.yRot = p_102932_ * 0.017f;
        this.head.xRot = p_102933_ * 0.017f;
        if (this.riding) {
            this.rightArm.xRot = -0.62831855F;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = -0.62831855F;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = 0.31415927F;
            this.rightLeg.zRot = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = -0.31415927F;
            this.leftLeg.zRot = -0.07853982F;
        } else {
            this.rightArm.xRot = Mth.cos(p_102929_ * 0.6662F + 3.1415927F) * 2.0F * p_102930_ * 0.5F;
            this.rightArm.yRot = 0.0F;
            this.rightArm.zRot = 0.0F;
            this.leftArm.xRot = Mth.cos(p_102929_ * 0.6662F) * 2.0F * p_102930_ * 0.5F;
            this.leftArm.yRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(p_102929_ * 0.6662F) * 1.4F * p_102930_ * 0.5F;
            this.rightLeg.yRot = 0.0F;
            this.rightLeg.zRot = 0.0F;
            this.leftLeg.xRot = Mth.cos(p_102929_ * 0.6662F + 3.1415927F) * 1.4F * p_102930_ * 0.5F;
            this.leftLeg.yRot = 0.0F;
            this.leftLeg.zRot = 0.0F;
        }
        ApiPose pose = p_102928_.getPoses();
        switch (pose) {
            case ATTACKING: {
                if (p_102928_.getMainHandItem().isEmpty()) {
                    AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, p_102931_);
                } else {
                    AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, p_102928_, this.attackTime, p_102931_);
                }
                break;
            }
            case BOW_AND_ARROW: {
                this.rightArm.yRot = -0.1F + this.head.yRot;
                this.rightArm.xRot = -1.5707964F + this.head.xRot;
                this.leftArm.xRot = -0.9424779F + this.head.xRot;
                this.leftArm.yRot = this.head.yRot - 0.4F;
                this.leftArm.zRot = 1.5707964F;
                break;
            }
            case SPELL_CASTING: {
                this.rightArm.xRot = Mth.cos(p_102931_ * 0.6662F) * 0.25F;
                this.leftArm.xRot = Mth.cos(p_102931_ * 0.6662F) * 0.25F;
                this.rightArm.zRot = 2.3561945F;
                this.leftArm.zRot = -2.3561945F;
                this.rightArm.yRot = Mth.PI;
                this.leftArm.yRot = Mth.PI;
                break;
            }
            case ZOMBIE_ATTACKING:{
                AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, p_102931_);
                break;
            }
            default:
                break;
        }
        boolean o = p_102928_.getPoses() == ApiPose.CROSSED;
        this.arms.visible = o;
        this.leftArm.visible = !o;
        this.rightArm.visible = !o;
    }
}
