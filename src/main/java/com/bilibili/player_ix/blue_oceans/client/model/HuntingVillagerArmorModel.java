
package com.bilibili.player_ix.blue_oceans.client.model;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.villagers.AbstractHuntingVillager;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HuntingVillagerArmorModel
extends HumanoidModel<AbstractHuntingVillager> {
    public static final ModelLayerLocation LOCATION = new ModelLayerLocation(new ResourceLocation(BlueOceans.MOD_ID, "arm"), "main");
    public static final ModelLayerLocation LOC = new ModelLayerLocation(new ResourceLocation(BlueOceans.MOD_ID, "no"), "main");

    public HuntingVillagerArmorModel(ModelPart part) {
        super(part);
    }

    public static @NotNull LayerDefinition createOuterArmorLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(1.0f), 0.0f);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(1.0f)), PartPose.offset(0.0f, 1.0f, 0.0f));
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -10.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(1.5f)), PartPose.offset(0.0f, 1.0f, 0.0f));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static @NotNull LayerDefinition createInnerArmorLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.5f), 0.0f);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
