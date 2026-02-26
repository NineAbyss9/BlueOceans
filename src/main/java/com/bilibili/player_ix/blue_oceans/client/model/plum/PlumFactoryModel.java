
package com.bilibili.player_ix.blue_oceans.client.model.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.animations.PlumFactoryAnimations;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumFactory;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class PlumFactoryModel<T extends PlumFactory> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BlueOceans.location("factory"), "main");
	public final ModelPart root;
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart heart;
	public final ModelPart hemal;

	public PlumFactoryModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		this.heart = this.body.getChild("heart");
		this.hemal = this.body.getChild("hemal");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -4.0F, -1.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 47).addBox(-5.5F, -10.5F, -4.5F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.1868F, 0.5746F, 0.1309F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 27).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-14.0F, 0.0F, -13.0F, 27.0F, 0.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition heart = body.addOrReplaceChild("heart", CubeListBuilder.create().texOffs(40, 47).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -6.0F));

		PartDefinition hemal = body.addOrReplaceChild("hemal", CubeListBuilder.create().texOffs(48, 49).addBox(1.75F, -2.5F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.35F))
		.texOffs(48, 47).addBox(0.75F, -2.75F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, -4.0F, -6.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idle, PlumFactoryAnimations.idle, ageInTicks);
	}

    public ModelPart root() {
        return root;
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}