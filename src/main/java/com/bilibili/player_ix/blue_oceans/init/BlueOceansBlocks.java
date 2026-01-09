
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.*;
import com.bilibili.player_ix.blue_oceans.common.blocks.cave.MiningLamp;
import com.bilibili.player_ix.blue_oceans.common.blocks.cave.Rope;
import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.AlcoholLamp;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.Leek;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.RiceBlock;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.RiceEars;
import com.bilibili.player_ix.blue_oceans.common.item.FlagItem;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public class BlueOceansBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            BlueOceans.MOD_ID);

    public static ToIntFunction<BlockState> light(BooleanProperty pFlag, int pLightLevel) {
        return state -> state.getValue(pFlag) ? pLightLevel : 0;
    }

    public static ToIntFunction<BlockState> light(int pLightLevel) {
        return light(BlockStateProperties.LIT, pLightLevel);
    }

    public static boolean isLit(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getValue(BlockStateProperties.LIT);
    }

    public static boolean isLit(BooleanProperty property, BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getValue(property);
    }

    public static final RegistryObject<Block> ALCOHOL_LAMP = BLOCKS.register("alcohol_lamp",
            () -> new AlcoholLamp(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(
                    4F, 10F).lightLevel(value ->
                    value.getValue(AlcoholLamp.BURNING) ? 10 : 0).noOcclusion().emissiveRendering((pState,
                                                                                            pLevel, pPos) -> isLit(AlcoholLamp.BURNING,
                            pState, pLevel, pPos))));
    public static final RegistryObject<Block> MINING_LAMP = BLOCKS.register("mining_lamp",
        () -> new MiningLamp(
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.0F,
                    6F).sound(SoundType.LANTERN).lightLevel(light(15))
                    .emissiveRendering(BlueOceansBlocks::isLit)));
    public static final RegistryObject<FlagBlock> FLAG_BLOCK = BLOCKS.register("flag",
            ()-> new FlagBlock(FlagItem.Type.EVIL_FACTION));
    public static final RegistryObject<Block> GANODERMA_LUCIDUM = BLOCKS.register("ganoderma_lucidum",
            GanodermaLucidum::new);
    public static final RegistryObject<Block> LEEK = BLOCKS.register("leek", Leek::new);
    public static final RegistryObject<Block> RED_PLUM_BLOCK = BLOCKS.register("red_plum_block",
            RedPlumBlock::new);
    public static final RegistryObject<Block> RED_PLUM_CATALYST = BLOCKS.register("red_plum_catalyst",
            RedPlumCatalyst::new);
    public static final RegistryObject<Block> RED_PLUM_GRASS = BLOCKS.register("red_plum_grass",
            RedPlumGrass::new);
    public static final RegistryObject<Block> RED_PLUM_TRAP = BLOCKS.register("red_plum_trap",
            RedPlumTrap::new);
    public static final RegistryObject<Block> RICE = BLOCKS.register("rice",
            () -> new RiceBlock(Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY).strength(0.2F)));
    public static final RegistryObject<Block> RICE_EARS = BLOCKS.register("rice_ears",
            () -> new RiceEars(Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ROPE = BLOCKS.register("rope", () -> new Rope(
            BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).sound(SoundType.WOOL).strength(
                    0.5F, 1F).forceSolidOff().noCollission().pushReaction(
                            PushReaction.DESTROY).ignitedByLava()));
    public static final RegistryObject<Block> SALT_ORE = BLOCKS.register("salt_ore",
            () -> new OreBlock(Block.Properties.of().requiresCorrectToolForDrops()
                    .strength(2.0F, 20F), UniformInt.of(1, 2)));
    public static final RegistryObject<Block> WOODEN_SUPPORT = BLOCKS.register("wooden_support",
            () -> new WoodenSupport(BlockBehaviour.Properties.of().strength(1.5F)
                    .instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD)
                    .noOcclusion().lightLevel(state->state.getValue(WoodenSupport.BURNING) ? 10 : 0)));
}
