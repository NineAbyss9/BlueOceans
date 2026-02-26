
package com.bilibili.player_ix.blue_oceans.client.model.plum;

import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.red_plum_girl.AbstractGirl;
import com.github.NineAbyss9.ix_api.api.ApiPose;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedPlumGirlModel<T extends AbstractGirl> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("blue_oceans", "girlmodel"), "main");
    public final ModelPart clothes;
    private final List<ModelPart> parts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    private final ModelPart ear;

    public RedPlumGirlModel(ModelPart p_170821_) {
        super(p_170821_);
        this.hat.visible = true;
        this.ear = p_170821_.getChild("ear");
        this.leftSleeve = p_170821_.getChild("left_sleeve");
        this.rightSleeve = p_170821_.getChild("right_sleeve");
        this.leftPants = p_170821_.getChild("left_pants");
        this.rightPants = p_170821_.getChild("right_pants");
        this.parts = p_170821_.getAllParts().filter((p_170824_) -> !p_170824_.isEmpty()
        ).collect(ImmutableList.toImmutableList());
        this.clothes = p_170821_.getChild("clothes");
    }

    public static MeshDefinition createMesh() {
        CubeDeformation p_170826_ = new CubeDeformation(0, 0, 0);
        MeshDefinition $$2 = HumanoidModel.createMesh(p_170826_, 0.0F);
        PartDefinition $$3 = $$2.getRoot();
        $$3.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 6.0F, 1.0F, p_170826_), PartPose.ZERO);
        $$3.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, p_170826_), PartPose.offset(5.0F, 2.5F, 0.0F));
        $$3.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, p_170826_), PartPose.offset(-5.0F, 2.5F, 0.0F));
        $$3.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, p_170826_.extend(0.25F)), PartPose.offset(5.0F, 2.5F, 0.0F));
        $$3.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, p_170826_.extend(0.25F)), PartPose.offset(-5.0F, 2.5F, 0.0F));
        $$3.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170826_), PartPose.offset(1.9F, 12.0F, 0.0F));
        $$3.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170826_.extend(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));
        $$3.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, p_170826_.extend(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        $$3.addOrReplaceChild("clothes", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_170826_.extend(0.25F)), PartPose.ZERO);
        return $$2;
    }

     @Contract(" -> new")
     public static @NotNull LayerDefinition createBodyLayer() {
        return LayerDefinition.create(RedPlumGirlModel.createMesh(), 64, 64);
     }

    protected Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(this.leftPants, this.rightPants, this.leftSleeve, this.rightSleeve, this.clothes));
    }

    public void setupAnim(T entity, float var, float var1, float var2, float var3, float var4) {
        ApiPose pose = entity.getArmPose();
        this.head.yRot = var3 * ((float) Math.PI / 180);
        this.head.xRot = var4 * ((float) Math.PI / 180);
        this.rightArm.xRot = Mth.cos(var * 0.6662f + (float) Math.PI) * 2.0f * var1 * 0.5f;
        this.rightArm.yRot = 0.0f;
        this.rightArm.zRot = 0.0f;
        this.leftArm.xRot = Mth.cos(var * 0.6662f) * 2.0f * var1 * 0.5f;
        this.leftArm.yRot = 0.0f;
        this.leftArm.zRot = 0.0f;
        this.rightLeg.xRot = Mth.cos(var * 0.6662f) * 1.4f * var1 * 0.5f;
        this.rightLeg.yRot = 0.0f;
        this.rightLeg.zRot = 0.0f;
        this.leftLeg.xRot = Mth.cos(var * 0.6662f + (float) Math.PI) * 1.4f * var1 * 0.5f;
        this.leftLeg.yRot = 0.0f;
        this.leftLeg.zRot = 0.0f;
        this.clothes.copyFrom(this.body);
        this.ear.copyFrom(this.head);
        this.hat.copyFrom(this.head);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        switch (pose) {
            case BOW_AND_ARROW: {
                this.rightArm.yRot = -0.1f + this.head.yRot;
                this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
                this.rightArm.xRot = -1.5707964f + this.head.xRot;
                this.leftArm.xRot = -1.5707964f + this.head.xRot;
                this.leftSleeve.copyFrom(this.leftArm);
                this.rightSleeve.copyFrom(this.rightArm);
                this.leftPants.copyFrom(this.leftLeg);
                this.rightPants.copyFrom(this.rightLeg);
                break;
            }
            case SPELL_AND_WEAPON: {
                if (!entity.getMainHandItem().isEmpty()) {
                    AnimationUtils.swingWeaponDown(this.rightArm, this.rightArm, entity, this.attackTime, var2);
                }
                if (entity.getMainArm() == HumanoidArm.RIGHT) {
                    this.leftArm.z = 0.0f;
                    this.leftArm.x = 5.0f;
                    this.leftArm.xRot = Mth.cos((var2 * 0.6662f)) * 0.25f;
                    this.leftArm.zRot = -2.3561945f;
                    this.leftArm.yRot = 0.0f;
                } else {
                    this.rightArm.z = 0.0f;
                    this.rightArm.x = -5.0f;
                    this.rightArm.xRot = Mth.cos((var2 * 0.6662f)) * 0.25f;
                    this.rightArm.zRot = 2.3561945f;
                    this.rightArm.yRot = 0.0f;
                }
                this.leftSleeve.copyFrom(this.leftArm);
                this.rightSleeve.copyFrom(this.rightArm);
                this.leftPants.copyFrom(this.leftLeg);
                this.rightPants.copyFrom(this.rightLeg);
                break;
            }
            case SPELL_CASTING: {
                this.rightArm.xRot = Mth.cos(var2 * 0.6662f) * 0.25f;
                this.leftArm.xRot = Mth.cos(var2 * 0.6662f) * 0.25f;
                this.rightArm.zRot = 2.3561945f;
                this.leftArm.zRot = -2.3561945f;
                this.rightArm.yRot = Mth.PI;
                this.leftArm.yRot = Mth.PI;
                this.leftSleeve.copyFrom(this.leftArm);
                this.rightSleeve.copyFrom(this.rightArm);
                this.leftPants.copyFrom(this.leftLeg);
                this.rightPants.copyFrom(this.rightLeg);
                break;
            }
            case CHASING: {
                AnimationUtils.animateZombieArms(this.rightArm, this.leftArm, true, this.attackTime, var4);
                this.leftSleeve.copyFrom(this.leftArm);
                this.rightSleeve.copyFrom(this.rightArm);
                this.leftPants.copyFrom(this.leftLeg);
                this.rightPants.copyFrom(this.rightLeg);
                break;
            }
            case NATURAL: {
                this.head.yRot = var3 * ((float) Math.PI / 180);
                this.head.xRot = var4 * ((float) Math.PI / 180);
                break;
            }
        }
    }

    @Override
    public @NotNull ModelPart getHead() {
        return this.head;
    }

    @Override
    protected @NotNull ModelPart getArm(@NotNull HumanoidArm p_102852_) {
        if (p_102852_ == HumanoidArm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

    @Override
    public void translateToHand(@NotNull HumanoidArm p_102854_, @NotNull PoseStack p_102855_) {
        this.getArm(p_102854_).translateAndRotate(p_102855_);
    }
}
