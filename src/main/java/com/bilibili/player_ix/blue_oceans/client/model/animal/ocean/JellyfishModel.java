
package com.bilibili.player_ix.blue_oceans.client.model.animal.ocean;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.animations.JellyfishAnims;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean.Jellyfish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@SuppressWarnings("unused")
public class JellyfishModel<T extends Jellyfish> extends HierarchicalModel<T>
implements HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BlueOceans.location("jellyfish"),
            "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart tentacle0;
	private final ModelPart tentacle1;
	private final ModelPart tentacle2;
	private final ModelPart tentacle3;
	private final ModelPart tentacle4;
	private final ModelPart tentacle5;
	private final ModelPart tentacle6;
	private final ModelPart tentacle7;

	public JellyfishModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.tentacle0 = this.root.getChild("tentacle0");
		this.tentacle1 = this.root.getChild("tentacle1");
		this.tentacle2 = this.root.getChild("tentacle2");
		this.tentacle3 = this.root.getChild("tentacle3");
		this.tentacle4 = this.root.getChild("tentacle4");
		this.tentacle5 = this.root.getChild("tentacle5");
		this.tentacle6 = this.root.getChild("tentacle6");
		this.tentacle7 = this.root.getChild("tentacle7");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacle0 = root.addOrReplaceChild("tentacle0", CubeListBuilder.create().texOffs(4, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tentacle1 = root.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(4, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tentacle2 = root.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 0.0F));

		PartDefinition tentacle3 = root.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(4, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.0F, 2.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition tentacle4 = root.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

		PartDefinition tentacle5 = root.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(4, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 2.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition tentacle6 = root.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 17).addBox(1.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.0F, 2.0F));

		PartDefinition tentacle7 = root.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idle, JellyfishAnims.idle, ageInTicks);
	}

    public ModelPart root() {
        return root;
    }

    public ModelPart getHead() {
        return head;
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}