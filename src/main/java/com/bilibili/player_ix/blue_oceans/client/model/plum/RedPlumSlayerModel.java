
package com.bilibili.player_ix.blue_oceans.client.model.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.animations.RedPlumSlayerAnimations;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.RedPlumSlayer;
import com.github.NineAbyss9.ix_api.util.Maths;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

public class RedPlumSlayerModel<T extends RedPlumSlayer>
extends HierarchicalModel<T>
implements HeadedModel, ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BlueOceans.location("slayers"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rope;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart scythe;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public RedPlumSlayerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		this.rope = this.body.getChild("rope");
		this.left_arm = this.root.getChild("left_arm");
		this.right_arm = this.root.getChild("right_arm");
		this.scythe = this.right_arm.getChild("scythe");
		this.left_leg = this.root.getChild("left_leg");
		this.right_leg = this.root.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));

		root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 1.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 0).addBox(-3.0F, -2.0F, -1.0F, 6.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

		PartDefinition rope = body.addOrReplaceChild("rope", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 3.0F));

		rope.addOrReplaceChild("rope_r1", CubeListBuilder.create().texOffs(24, 13).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 25).addBox(0.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -16.0F, 1.0F));

		PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -16.0F, 1.0F));

		PartDefinition scythe = right_arm.addOrReplaceChild("scythe", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 0.0F));

		scythe.addOrReplaceChild("scythe_r1", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -2.0F, -14.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 2.1817F, 0.0F, 0.0F));

		root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(8, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -7.0F, 1.0F));

		root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -7.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * Maths.PI_DIVIDING_180;
        this.head.xRot = headPitch * Maths.PI_DIVIDING_180;
        this.animateWalk(RedPlumSlayerAnimations.walk, limbSwing, limbSwingAmount,
                2.0F, 2.5F);
        this.animate(entity.attack, RedPlumSlayerAnimations.attack, ageInTicks);
        this.animate(entity.circle, RedPlumSlayerAnimations.circle, ageInTicks);
        this.animate(entity.summon, RedPlumSlayerAnimations.summon, ageInTicks);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    public ModelPart root() {
        return this.root;
    }

    public ModelPart getHead() {
        return head;
    }

    private ModelPart getArm(HumanoidArm pArm) {
        return pArm.equals(HumanoidArm.LEFT) ? left_arm : right_arm;
    }

    public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
        this.getArm(pSide).translateAndRotate(pPoseStack);
    }
}