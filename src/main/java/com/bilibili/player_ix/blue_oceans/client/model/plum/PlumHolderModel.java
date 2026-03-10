
package com.bilibili.player_ix.blue_oceans.client.model.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.animations.PlumHolderAnims;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@SuppressWarnings("unused")
public class PlumHolderModel<T extends PlumHolder> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BlueOceans.location(
            "plumholder"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart bone2;
	private final ModelPart bone;
	private final ModelPart bone3;
	private final ModelPart bone6;
	private final ModelPart bone4;
	private final ModelPart bone5;

	public PlumHolderModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.bone2 = this.root.getChild("bone2");
		this.bone = this.root.getChild("bone");
		this.bone3 = this.root.getChild("bone3");
		this.bone6 = this.bone3.getChild("bone6");
		this.bone4 = this.root.getChild("bone4");
		this.bone5 = this.bone4.getChild("bone5");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, 0.0F, -13.0F, 27.0F, 0.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 2.0F, -0.6109F, 0.7854F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.0F, -8.0F, -0.8727F, -0.7418F, 0.0F));

		PartDefinition bone2 = root.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 67).addBox(-1.0F, -20.0F, -7.0F, 2.0F, 20.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 0.0F));

		PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 67).addBox(-1.0F, -20.0F, -7.0F, 2.0F, 20.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 0.0F, 0.0F));

		PartDefinition bone3 = root.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition cube_r3 = bone3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 67).addBox(-1.0F, -20.0F, -7.0F, 2.0F, 20.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone6 = bone3.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, -2.0F));

		PartDefinition cube_r4 = bone6.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 1.0F, 1.0F, 2.2689F, 0.5672F, 3.1416F));

		PartDefinition cube_r5 = bone6.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(90, 115).addBox(-6.0F, -1.0F, -6.0F, 7.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone4 = root.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition cube_r6 = bone4.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 67).addBox(-1.0F, -20.0F, -7.0F, 2.0F, 20.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 0.0F));

		PartDefinition cube_r7 = bone5.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(90, 115).addBox(0.0F, -1.0F, -6.0F, 7.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    public ModelPart root() {
        return root;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idle, PlumHolderAnims.idle, ageInTicks);
        this.animate(entity.despawn, PlumHolderAnims.despawn, ageInTicks);
        this.animate(entity.spawn, PlumHolderAnims.spawn, ageInTicks);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}