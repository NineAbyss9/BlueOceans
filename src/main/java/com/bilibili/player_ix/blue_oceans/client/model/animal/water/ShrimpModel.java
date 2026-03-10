
package com.bilibili.player_ix.blue_oceans.client.model.animal.water;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.animations.ShrimpAnims;
import com.bilibili.player_ix.blue_oceans.common.entities.animal.ocean.Shrimp;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@SuppressWarnings("unused")
public class ShrimpModel<T extends Shrimp> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BlueOceans.location("shrimp"),
            "main");
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone9;
	private final ModelPart bone10;
	private final ModelPart bone11;
	private final ModelPart bone12;
	private final ModelPart bone13;
	private final ModelPart bone14;
	private final ModelPart bone15;
	private final ModelPart bone16;
	private final ModelPart bone17;
	private final ModelPart bone18;
	private final ModelPart bone40;
	private final ModelPart bone41;
	private final ModelPart bone56;
	private final ModelPart bone57;
	private final ModelPart bone58;
	private final ModelPart bone59;
	private final ModelPart bone48;
	private final ModelPart bone49;
	private final ModelPart bone46;
	private final ModelPart bone47;
	private final ModelPart bone54;
	private final ModelPart bone55;
	private final ModelPart bone42;
	private final ModelPart bone43;
	private final ModelPart bone52;
	private final ModelPart bone53;
	private final ModelPart bone44;
	private final ModelPart bone45;
	private final ModelPart bone50;
	private final ModelPart bone51;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;
	private final ModelPart bone7;
	private final ModelPart bone8;
	private final ModelPart bone19;
	private final ModelPart bone20;
	private final ModelPart bone22;
	private final ModelPart bone21;
	private final ModelPart bone23;
	private final ModelPart bone30;
	private final ModelPart bone31;
	private final ModelPart bone38;
	private final ModelPart bone39;
	private final ModelPart bone28;
	private final ModelPart bone29;
	private final ModelPart bone36;
	private final ModelPart bone37;
	private final ModelPart bone24;
	private final ModelPart bone25;
	private final ModelPart bone34;
	private final ModelPart bone35;
	private final ModelPart bone26;
	private final ModelPart bone27;
	private final ModelPart bone32;
	private final ModelPart bone33;

	public ShrimpModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = this.bone.getChild("bone2");
		this.bone9 = this.bone2.getChild("bone9");
		this.bone10 = this.bone9.getChild("bone10");
		this.bone11 = this.bone2.getChild("bone11");
		this.bone12 = this.bone11.getChild("bone12");
		this.bone13 = this.bone2.getChild("bone13");
		this.bone14 = this.bone13.getChild("bone14");
		this.bone15 = this.bone14.getChild("bone15");
		this.bone16 = this.bone2.getChild("bone16");
		this.bone17 = this.bone16.getChild("bone17");
		this.bone18 = this.bone17.getChild("bone18");
		this.bone40 = this.bone2.getChild("bone40");
		this.bone41 = this.bone40.getChild("bone41");
		this.bone56 = this.bone2.getChild("bone56");
		this.bone57 = this.bone56.getChild("bone57");
		this.bone58 = this.bone2.getChild("bone58");
		this.bone59 = this.bone58.getChild("bone59");
		this.bone48 = this.bone2.getChild("bone48");
		this.bone49 = this.bone48.getChild("bone49");
		this.bone46 = this.bone2.getChild("bone46");
		this.bone47 = this.bone46.getChild("bone47");
		this.bone54 = this.bone2.getChild("bone54");
		this.bone55 = this.bone54.getChild("bone55");
		this.bone42 = this.bone2.getChild("bone42");
		this.bone43 = this.bone42.getChild("bone43");
		this.bone52 = this.bone2.getChild("bone52");
		this.bone53 = this.bone52.getChild("bone53");
		this.bone44 = this.bone2.getChild("bone44");
		this.bone45 = this.bone44.getChild("bone45");
		this.bone50 = this.bone2.getChild("bone50");
		this.bone51 = this.bone50.getChild("bone51");
		this.bone3 = this.bone.getChild("bone3");
		this.bone4 = this.bone3.getChild("bone4");
		this.bone5 = this.bone4.getChild("bone5");
		this.bone6 = this.bone5.getChild("bone6");
		this.bone7 = this.bone6.getChild("bone7");
		this.bone8 = this.bone7.getChild("bone8");
		this.bone19 = this.bone8.getChild("bone19");
		this.bone20 = this.bone19.getChild("bone20");
		this.bone22 = this.bone19.getChild("bone22");
		this.bone21 = this.bone19.getChild("bone21");
		this.bone23 = this.bone19.getChild("bone23");
		this.bone30 = this.bone7.getChild("bone30");
		this.bone31 = this.bone30.getChild("bone31");
		this.bone38 = this.bone7.getChild("bone38");
		this.bone39 = this.bone38.getChild("bone39");
		this.bone28 = this.bone6.getChild("bone28");
		this.bone29 = this.bone28.getChild("bone29");
		this.bone36 = this.bone6.getChild("bone36");
		this.bone37 = this.bone36.getChild("bone37");
		this.bone24 = this.bone5.getChild("bone24");
		this.bone25 = this.bone24.getChild("bone25");
		this.bone34 = this.bone5.getChild("bone34");
		this.bone35 = this.bone34.getChild("bone35");
		this.bone26 = this.bone4.getChild("bone26");
		this.bone27 = this.bone26.getChild("bone27");
		this.bone32 = this.bone4.getChild("bone32");
		this.bone33 = this.bone32.getChild("bone33");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 13.0F));

		PartDefinition bone2 = bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(80, 10).addBox(-3.15F, -4.2F, -10.0F, 6.3F, 8.2F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(80, 36).addBox(0.0F, -6.2F, -10.0F, 0.0F, 2.2F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(80, 23).addBox(-2.75F, -4.0F, -5.0F, 5.5F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(26, 78).addBox(-2.05F, -3.5F, -20.0F, 1.675F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(48, 78).addBox(0.375F, -3.5F, -20.0F, 1.675F, 4.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(80, 48).addBox(-3.15F, -3.3F, -17.5F, 2.275F, 3.6F, 7.5F, new CubeDeformation(0.0F))
		.texOffs(0, 82).addBox(0.875F, -3.3F, -17.5F, 2.275F, 3.6F, 7.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition bone9 = bone2.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(100, 44).addBox(-0.45F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.7F, -2.5F, -9.0F));

		PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(18, 89).addBox(-0.75F, -0.75F, -1.45F, 1.5F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, -0.05F, -3.05F));

		PartDefinition bone11 = bone2.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(102, 17).addBox(-0.55F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.7F, -2.5F, -9.0F));

		PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(22, 90).addBox(-0.75F, -0.75F, -1.45F, 1.5F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, -0.05F, -3.05F));

		PartDefinition bone13 = bone2.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, -20.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, -10.0F));

		PartDefinition bone14 = bone13.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -1.0F, -20.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -20.0F));

		PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(40, 0).addBox(0.0F, -1.0F, -20.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -20.0F));

		PartDefinition bone16 = bone2.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(40, 22).addBox(0.0F, -1.0F, -20.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, -10.0F));

		PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(40, 44).addBox(0.0F, -1.0F, -20.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -20.0F));

		PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(0, 44).addBox(0.0F, -1.0F, -20.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -20.0F));

		PartDefinition bone40 = bone2.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(68, 92).addBox(-0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.35F, 3.8F, -9.4F));

		PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(24, 82).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, 6.4F, -0.1F));

		PartDefinition bone56 = bone2.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(22, 103).addBox(-0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.35F, 3.8F, -9.4F));

		PartDefinition bone57 = bone56.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(102, 55).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, 6.4F, -0.1F));

		PartDefinition bone58 = bone2.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(24, 103).addBox(0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.35F, 3.8F, -9.4F));

		PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(102, 77).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, 6.4F, -0.1F));

		PartDefinition bone48 = bone2.addOrReplaceChild("bone48", CubeListBuilder.create().texOffs(20, 103).addBox(0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.35F, 3.8F, -9.4F));

		PartDefinition bone49 = bone48.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(18, 103).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, 6.4F, -0.1F));

		PartDefinition bone46 = bone2.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(6, 103).addBox(-0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.35F, 3.8F, -7.4F));

		PartDefinition bone47 = bone46.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(64, 92).addBox(0.0F, 0.0F, -0.5F, 0.0F, 11.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, 6.4F, -0.1F));

		PartDefinition bone54 = bone2.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(16, 103).addBox(0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.35F, 3.8F, -7.4F));

		PartDefinition bone55 = bone54.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(66, 92).addBox(0.0F, 0.0F, -0.5F, 0.0F, 11.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, 6.4F, -0.1F));

		PartDefinition bone42 = bone2.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(0, 103).addBox(-0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.35F, 3.8F, -6.4F));

		PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(102, 86).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, 6.4F, -0.1F));

		PartDefinition bone52 = bone2.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(14, 103).addBox(0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.35F, 3.8F, -6.4F));

		PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(12, 103).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, 6.4F, -0.1F));

		PartDefinition bone44 = bone2.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(4, 103).addBox(-0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.35F, 3.8F, -3.4F));

		PartDefinition bone45 = bone44.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(2, 103).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.05F, 6.4F, -0.1F));

		PartDefinition bone50 = bone2.addOrReplaceChild("bone50", CubeListBuilder.create().texOffs(10, 103).addBox(0.05F, -0.8F, -0.6F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.35F, 3.8F, -3.4F));

		PartDefinition bone51 = bone50.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(8, 103).addBox(0.0F, 0.0F, -0.5F, 0.0F, 7.2F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.05F, 6.4F, -0.1F));

		PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(86, 88).addBox(-2.425F, -3.8F, 0.0F, 4.85F, 7.8F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 92).addBox(-2.35F, -3.7F, 0.0F, 4.7F, 7.7F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(16, 92).addBox(-2.3F, -3.6F, 0.0F, 4.6F, 7.6F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(32, 92).addBox(-2.2F, -3.5F, 0.0F, 4.4F, 7.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(48, 92).addBox(-2.15F, -3.4F, 0.0F, 4.3F, 7.4F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 66).addBox(-2.075F, -3.2F, 0.0F, 4.15F, 7.2F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(80, 58).addBox(-1.2918F, -2.5F, 13.9487F, 2.5837F, 6.4F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition cube_r1 = bone8.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(70, 88).addBox(-0.575F, -3.4F, -3.0F, 2.15F, 6.4F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1133F, 0.9F, 11.8993F, 0.0F, 0.1309F, 0.0F));

		PartDefinition cube_r2 = bone8.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(86, 58).addBox(-1.575F, -3.4F, -3.0F, 2.15F, 6.4F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1133F, 0.9F, 11.8993F, 0.0F, -0.1309F, 0.0F));

		PartDefinition bone19 = bone8.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone20 = bone19.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(26, 66).addBox(-1.325F, -0.2F, -1.0F, 4.15F, 1.2F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 9.0F));

		PartDefinition bone22 = bone19.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(56, 66).addBox(-2.825F, -0.2F, -1.0F, 4.15F, 1.2F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 9.0F));

		PartDefinition bone21 = bone19.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(70, 78).addBox(-1.575F, 0.0F, 0.0F, 4.15F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 1.0F, 10.0F));

		PartDefinition bone23 = bone19.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(80, 0).addBox(-2.575F, 0.0F, 0.0F, 4.15F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 1.0F, 10.0F));

		PartDefinition bone30 = bone7.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(86, 70).addBox(0.0F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.1F, 4.0F, 1.0F));

		PartDefinition bone31 = bone30.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(18, 82).addBox(0.0F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone38 = bone7.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(102, 10).addBox(0.0F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, 4.0F, 1.0F));

		PartDefinition bone39 = bone38.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(80, 100).addBox(0.0F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone28 = bone6.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(92, 99).addBox(0.0F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.1F, 4.0F, 1.0F));

		PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(86, 99).addBox(0.0F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone36 = bone6.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(74, 100).addBox(0.0F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.1F, 4.0F, 1.0F));

		PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(68, 100).addBox(0.0F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone24 = bone5.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(96, 77).addBox(-0.1F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.3F, 4.0F, 1.0F));

		PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(92, 70).addBox(-0.1F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone34 = bone5.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(100, 37).addBox(0.1F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.3F, 4.0F, 1.0F));

		PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(100, 30).addBox(0.1F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone26 = bone4.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(98, 70).addBox(-0.1F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4F, 4.0F, 1.0F));

		PartDefinition bone27 = bone26.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(98, 48).addBox(-0.1F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		PartDefinition bone32 = bone4.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(100, 23).addBox(0.1F, -1.0F, -1.0F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.4F, 4.0F, 1.0F));

		PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(98, 99).addBox(0.1F, 0.2F, -1.5F, 0.0F, 4.2F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

    public ModelPart root() {
        return bone;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.pose, ShrimpAnims.pose, ageInTicks);
        this.animate(entity.idle, ShrimpAnims.idle, ageInTicks);
        this.animateWalk(ShrimpAnims.swim, limbSwing, limbSwingAmount,
                2.0f, 2.5f);
        this.animate(entity.dig, ShrimpAnims.dig, ageInTicks);
	}

	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}