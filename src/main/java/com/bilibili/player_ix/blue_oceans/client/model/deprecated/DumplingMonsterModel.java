
package com.bilibili.player_ix.blue_oceans.client.model.deprecated;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.AnimationUtil;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.world.entity.Mob;

public class DumplingMonsterModel<T extends Mob>
extends HierarchicalModel<T>
implements HeadedModel, ArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BlueOceans.MOD_ID, "dumpling"), "main");
    protected final ModelPart head;
    protected final ModelPart body;
    protected final ModelPart leftArm;
    protected final ModelPart rightArm;
    protected final ModelPart root;

    public DumplingMonsterModel(ModelPart part) {
        this.root = part;
        this.head = part.getChild("head");
        this.body = part.getChild("body");
        this.leftArm = part.getChild("left_arm");
        this.rightArm = part.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        $$1.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        $$1.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 4.0F), PartPose.offset(0, 0 , 0));
        $$1.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        $$1.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create($$0, 64, 64);
    }

    private ModelPart getArm(HumanoidArm p_102923_) {
        return p_102923_ == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
    }

    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
    }

    public ModelPart getHead() {
        return this.head;
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
        this.head.yRot = v3 * 0.017f;
        this.head.xRot = v4 * 0.017f;
        this.rightArm.xRot = Mth.cos(v * 0.6662F + Mth.PI) * 2.0F * v1 * 0.5F;
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.xRot = Mth.cos(v * 0.6662F) * 2.0F * v1 * 0.5F;
        this.leftArm.yRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        if (t.isAggressive()) {
            AnimationUtil.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, v4);
        }
    }
}
