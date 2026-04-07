
package com.bilibili.player_ix.blue_oceans.init.data;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.corpse.Corpse;
import com.bilibili.player_ix.blue_oceans.common.blocks.farming.AbstractFarmland;
import com.bilibili.player_ix.blue_oceans.common.blocks.farming.AbstractSoil;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.Crop;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.NineAbyss9.util.IXUtil;

public class ModBlockStateProvider
extends BlockStateProvider
{
    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    protected void registerStatesAndModels()
    {
        BoDataGenHelper.BLOCK_REGISTRIES.forEach(object ->
        {
            Block block = object.get();
            if (block instanceof MultifaceBlock)
                getMultipartBuilder(block);
            else if (block instanceof Crop crop)
                crop(IXUtil.c.convert(object), crop);
            else if (block instanceof Cross) {
                cross(object, block);
            } else if (block instanceof AbstractFarmland farmland)
                farmland(object, farmland);
            else if (block instanceof AbstractSoil)
                soil(object, block);
            else if (block instanceof Corpse) {
                corpse(object, block);
            } else if (block instanceof Util)
            {
                simpleBlockWithItem(block, new ModelFile.UncheckedModelFile(modLoc("block/util/"
                    + object.getId().getPath())));
            } else
                simpleBlockWithItem(block, new ModelFile.ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
        });
    }

    private void util(RegistryObject<?> block, Block instance)
    {

    }

    private void corpse(RegistryObject<?> block, Block instance) {
        //VariantBlockStateBuilder builder = getVariantBuilder(instance);
        BlockModelBuilder model = models().singleTexture(
                block.getId().getPath().replace("block/", ""),
                new ResourceLocation(BlueOceans.MOD_ID, "block/corpse_model"),
                "0",
                modLoc("block/corpse/" + block.getId().getPath()))
                .renderType("translucent");
        simpleBlockWithItem(instance, model);
    }

    private void farmland(RegistryObject<?> block, AbstractFarmland instance) {
        VariantBlockStateBuilder builder = getVariantBuilder(instance);
        BlockModelBuilder first = null;
        BlockModelBuilder moist = models().singleTexture(
                block.getId().getPath() + "_moist",
                mcLoc("block/template_farmland"),
                "top",
                modLoc("block/farming/" + block.getId().getPath() + "_moist")
        ).texture("dirt", modLoc("block/farming/" + block.getId().getPath()
                .replace("_farmland", "")));
        for (int stage = 0;stage < 8;stage++) {
            BlockModelBuilder model = models().singleTexture(
                    block.getId().getPath(),
                    mcLoc("block/template_farmland"),
                    "top",
                    modLoc("block/farming/" + block.getId().getPath())
            ).texture("dirt", modLoc("block/farming/" + block.getId().getPath()
                            .replace("_farmland", "")));
            builder.partialState().with(FarmBlock.MOISTURE, stage)
                    .modelForState()
                    .modelFile(stage == 7 ? moist : model)
                    .addModel();
            if (stage == 0) {
                first = model;
            }
        }
        simpleBlockItem(instance, first);
    }

    @SuppressWarnings("UnusedReturnValue")
    private ItemModelBuilder simpleBlockItemNoBlock(Block block, ModelFile model) {
        return itemModels().getBuilder("block/" + reg(block).getPath()).parent(model);
    }

    public void simpleBlockItem(Block block, ModelFile model) {
        itemModels().getBuilder("item/block/" + reg(block).getPath().replace("block.", "")).parent(model);
    }

    private void soil(RegistryObject<?> block, Block instance) {
        //VariantBlockStateBuilder builder = getVariantBuilder(instance);
        simpleBlockWithItem(instance, models().cubeAll(
                block.getId().getPath(),
                modLoc("block/farming/" + block.getId().getPath())));
    }

    private void cross(RegistryObject<?> block, Block instance) {
        //VariantBlockStateBuilder builder = getVariantBuilder(instance);
        simpleBlock(instance, models().cross(
                block.getId().getPath(),
                modLoc("block/cross/" + block.getId().getPath())
        ).renderType("cutout"));
        itemModels().getBuilder("item/block/" + block.getId().getPath().replace("block/", ""))
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(
                        BlueOceans.MOD_ID, "block/cross/" + block.getId().getPath()));
    }

    private void crop(RegistryObject<?> block, Crop crop) {
        // 获取方块的变量生成器
        VariantBlockStateBuilder builder = getVariantBuilder(crop);
        // 循环遍历 stage 的值
        for (int stage = 0;stage < crop.getMaxAge();stage++) {
            // 为每个阶段创建一个独立的模型文件
            // 模型文件的路径会自动生成在 models/block/ 下，例如 seasonal_block_stage0.json
            BlockModelBuilder model = models().cross(
                    block.getId().getPath() + "_stage_" + stage, // 模型文件名
                    modLoc("block/" + block.getId().getPath() + "_stage_" + stage) // 纹理路径
            );
            // 将模型与特定的 BlockState 关联起来
            // 当方块状态中的 STAGE 属性等于当前循环的 stage 值时，就使用上面创建的模型
            builder.partialState().with(crop.getAgeProperty(), stage)
                    .modelForState()
                    .modelFile(model)
                    .addModel();
        }
        // 为物品生成一个模型，通常物品模型可以使用第一个阶段或一个默认的图标
        simpleBlockWithItem(crop, models().cross(block.getId().getPath() + "_stage_0",
                modLoc("block/" + block.getId().getPath() + "_stage_0")));
    }

    public ResourceLocation reg(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private static ModelFile unchecked(String path) {
        return new ModelFile.UncheckedModelFile(path);
    }

    public interface Cross
    extends ITextureProvider {
    }

    public interface Util
    extends ITextureProvider {
    }
}
