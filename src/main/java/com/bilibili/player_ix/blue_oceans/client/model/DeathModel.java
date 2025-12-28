
package com.bilibili.player_ix.blue_oceans.client.model;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.client.AnimationUtil;
import com.bilibili.player_ix.blue_oceans.common.entities.undeads.Death;
import com.bilibili.player_ix.blue_oceans.common.entities.undeads.UndeadBoss;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

public class DeathModel<T extends Death>
extends HierarchicalModel<T>
implements ArmedModel,
HeadedModel {
    public static final ModelLayerLocation DEATH = new ModelLayerLocation(BlueOceans.location("death"), "main");
    protected final ModelPart root;
    protected final ModelPart body;
    protected final ModelPart leftArm;
    protected final ModelPart rightArm;
    protected final ModelPart head;

    public DeathModel(ModelPart part) {
        this.root = part;
        this.head = part.getChild("head");
        this.body = part.getChild("body");
        this.leftArm = part.getChild("left_arm");
        this.rightArm = part.getChild("right_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 33).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, new CubeDeformation(0.45f)), PartPose.ZERO);
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        UndeadBoss.BossPose pose = t.getBossPose();
        switch (pose) {
            case BOW_AND_ARROW:{
                AnimationUtil.bowAndArrow(this.rightArm, this.leftArm, this.head);
                break;
            }
            case CASTING_SPELL:{
                AnimationUtil.cultistCastingSpell(this.leftArm, this.rightArm, this.head);
                break;
            }
            case STAFF_ATTACKING:{

                break;
            }
        }
    }

    public ModelPart root() {
        return this.root;
    }

    public ModelPart getHead() {
        return this.head;
    }

    protected ModelPart getArm(HumanoidArm $$0) {
        if ($$0 == HumanoidArm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

    public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
        this.getArm(humanoidArm).translateAndRotate(poseStack);
    }
}
