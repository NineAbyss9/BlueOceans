
package com.bilibili.player_ix.blue_oceans.client.model.plum;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.animations.PlumBuilderAnims;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.PlumBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@SuppressWarnings("unused")
public class PlumBuilderModel<T extends PlumBuilder>
extends HierarchicalModel<T>
implements HeadedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            BlueOceans.location("builder"), "main");
	private final ModelPart root;
	private final ModelPart head;
    private final ModelPart head1;
    private final ModelPart head2;
	private final ModelPart body;
	private final ModelPart jet_engine;

	public PlumBuilderModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
        this.head1 = this.head.getChild("head1");
        this.head2 = this.head.getChild("head2");
		this.body = this.root.getChild("body");
		this.jet_engine = this.root.getChild("jet_engine");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -4.0F, -1.0F, -0.5672F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 47).addBox(-5.5F, -10.5F, -4.5F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.1868F, 0.5746F, 0.1309F, 0.0F, 0.0F));

        PartDefinition head1 = head.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 2.0F, 0.0F));

        PartDefinition head2 = head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(0, 8).addBox(10.0F, -5.0F, -6.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 2.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 27).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, 0.0F, -13.0F, 27.0F, 0.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jet_engine = root.addOrReplaceChild("jet_engine", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, -6.0F, -13.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public ModelPart root() {
        return root;
    }

    public ModelPart getHead() {
        return head;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idle, PlumBuilderAnims.idle, ageInTicks);
        this.animate(entity.spread, PlumBuilderAnims.spread, ageInTicks);
        this.jet_engine.visible = entity.isBuilding();
        this.head1.visible = entity.getAge() > 1;
        this.head2.visible = entity.getAge() > 2;
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}