
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.*;
import com.bilibili.player_ix.blue_oceans.common.blocks.cave.MiningLamp;
import com.bilibili.player_ix.blue_oceans.common.blocks.cave.Rope;
import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.AlcoholLamp;
import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.gas.GasBlock;
import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.gas.GasEnum;
import com.bilibili.player_ix.blue_oceans.common.blocks.corpse.Corpse;
import com.bilibili.player_ix.blue_oceans.common.blocks.corpse.PlumCorpse;
import com.bilibili.player_ix.blue_oceans.common.blocks.farming.*;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.Leek;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.RiceBlock;
import com.bilibili.player_ix.blue_oceans.common.blocks.food.RiceEars;
import com.bilibili.player_ix.blue_oceans.common.blocks.nature.Bush;
import com.bilibili.player_ix.blue_oceans.common.blocks.nature.GanodermaLucidum;
import com.bilibili.player_ix.blue_oceans.common.blocks.nature.water.Reed;
import com.bilibili.player_ix.blue_oceans.common.blocks.plum.*;
import com.bilibili.player_ix.blue_oceans.common.blocks.util.IronLadder;
import com.bilibili.player_ix.blue_oceans.common.blocks.util.WoodenSupport;
import com.bilibili.player_ix.blue_oceans.common.item.FlagItem;
import com.bilibili.player_ix.blue_oceans.init.data.BoDataGenHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.SaplingBlock;
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

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings({"deprecation", "unused"})
public class BlueOceansBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            BlueOceans.MOD_ID);
    public static final Set<RegistryObject<GasBlock>> GASES = new HashSet<>();
    public static GasBlock getGas(GasEnum gasEnum) {
        AtomicReference<GasBlock> result = new AtomicReference<>();
        GASES.forEach(object -> {
            if (!object.getId().getPath().contains(gasEnum.name())) {
                return;
            }
            result.set((GasBlock)object.get());
        });
        if (result.get() == null) throw new NullPointerException();
        return result.get();
    }

    private static RegistryObject<GasBlock> gas(GasEnum gasEnum,
                                                Supplier<GasBlock> supplier) {
        return BLOCKS.register(gasEnum.name().toLowerCase(), supplier);
    }

    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

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

    private static <T extends Block> RegistryObject<T> block(String en, Supplier<T> pBlock) {
        var obj = BlueOceansBlocks.BLOCKS.register(en.replace(" ", "_").toLowerCase(), pBlock);
        BoDataGenHelper.BLOCK_REGISTRIES.add(obj);
        return obj;
    }

    public static final RegistryObject<Block> ALCOHOL_LAMP;
    public static final RegistryObject<Block> BARREN_SOIL;
    public static final RegistryObject<Block> BARREN_SOIL_FARMLAND = block("Barren Soil Farmland",
            () -> new BarrenSoilFarmland(BlockBehaviour.Properties.copy(Blocks.FARMLAND)
                    .strength(0.7F)));
    public static final RegistryObject<Block> BLACK_SOIL = block("Black Soil", () ->
            new BlackSoil(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> BLACK_SOIL_FARMLAND = block("black_soil_farmland",
            () -> new BlackSoilFarmland(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).randomTicks()
                    .strength(0.6F).sound(SoundType.GRAVEL).isViewBlocking(BlueOceansBlocks::always)
                    .isSuffocating(BlueOceansBlocks::always)));
    public static final RegistryObject<Block> BUDDING_NEO_PLUM = BLOCKS.register("budding_neo_plum",
            () -> new BuddingNeoPlum(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).forceSolidOn()
                    .noCollission().strength(0.3F).sound(SoundType.SCULK_VEIN)
                    .pushReaction(PushReaction.DESTROY).randomTicks().replaceable()));
    public static final RegistryObject<Block> BUSH = BLOCKS.register("bush", Bush::new);
    //public static final RegistryObject<Block> CELL = BLOCKS.register("cell", () ->
    //        new CellBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Corpse> CORPSE = BLOCKS.register("corpse", Corpse::new);
    public static final RegistryObject<Block> MINING_LAMP = BLOCKS.register("mining_lamp",
        () -> new MiningLamp(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(1.0F,
                    6F).sound(SoundType.LANTERN).lightLevel(light(15))
                    .emissiveRendering(BlueOceansBlocks::isLit)));
    public static final RegistryObject<FlagBlock> FLAG_BLOCK = BLOCKS.register("flag",
            ()-> new FlagBlock(FlagItem.Type.EVIL_FACTION));
    public static final RegistryObject<Block> GANODERMA_LUCIDUM = BLOCKS.register("ganoderma_lucidum",
            GanodermaLucidum::new);
    public static final RegistryObject<Block> IRON_LADDER = block("iron_ladder",
            IronLadder::new);
    public static final RegistryObject<Block> LEEK = BLOCKS.register("leek", Leek::new);
    /*public static final RegistryObject<Block> ORANGE_SAPLING = BLOCKS.register("orange_sapling",
            () -> new SaplingBlock(BoSaplings.orange(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission()
                    .randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));*/
    //public static final RegistryObject<Block> PEBBLE = BLOCKS.register("pebble", Pebble::new);
    public static final RegistryObject<Block> PLUM_CELL_CLUSTER = BLOCKS.register("plum_cell_cluster",
            PlumCellClusterBlock::new);
    public static final RegistryObject<PlumCorpse> PLUM_CORPSE = block("plum_corpse", PlumCorpse::new);
    public static final RegistryObject<Block> PLUM_TISSUE = BLOCKS.register("plum_tissue",
            PlumTissueBlock::new);
    public static final RegistryObject<Block> RED_PLUM_BLOCK = BLOCKS.register("red_plum_block",
            RedPlumBlock::new);
    public static final RegistryObject<Block> RED_PLUM_CATALYST = BLOCKS.register("red_plum_catalyst",
            RedPlumCatalyst::new);
    public static final RegistryObject<Block> RED_PLUM_GRASS = block("Red Plum Grass",
            RedPlumGrass::new);
    public static final RegistryObject<Block> RED_PLUM_TRAP = BLOCKS.register("red_plum_trap",
            RedPlumTrap::new);
    public static final RegistryObject<Block> RED_PLUM_VEIN = BLOCKS.register("red_plum_vein",
            () -> new RedPlumVein(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).forceSolidOn()
                    .noCollission().strength(0.3F).sound(SoundType.SCULK_VEIN)
                    .pushReaction(PushReaction.DESTROY).randomTicks()));
    public static final RegistryObject<Block> REED;
    public static final RegistryObject<Block> RICE = BLOCKS.register("rice",
            () -> new RiceBlock(Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY).strength(0.2F)));
    public static final RegistryObject<RiceEars> RICE_EARS = BLOCKS.register("rice_ears",
            () -> new RiceEars(Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ROPE = BLOCKS.register("rope", () -> new Rope(
            BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).sound(SoundType.WOOL).strength(
                    0.5F, 1F).forceSolidOff().noCollission().pushReaction(
                            PushReaction.DESTROY).ignitedByLava()));
    public static final RegistryObject<Block> SALT_ORE = BLOCKS.register("salt_ore",
            () -> new OreBlock(Block.Properties.of().requiresCorrectToolForDrops()
                    .strength(2.0F, 20F), UniformInt.of(1, 2)));
    public static final RegistryObject<Block> SCLEROTIC_RED_PLUM_BLOCK = BLOCKS.register(
            "sclerotic_red_plum_block", ScleroticPlumBlock::new);
    public static final RegistryObject<Block> SPRINKLER = BLOCKS.register("sprinkler",
            () -> new Sprinkler(BlockBehaviour.Properties.of().strength(2, 2)
                    .requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WEED;
    public static final RegistryObject<Block> WOODEN_SUPPORT = BLOCKS.register("wooden_support",
            () -> new WoodenSupport(BlockBehaviour.Properties.of().strength(1.5F)
                    .instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD)
                    .noOcclusion().lightLevel(state->state.getValue(WoodenSupport.BURNING) ? 10 : 0)));

    static {
        ALCOHOL_LAMP = BLOCKS.register("alcohol_lamp", () ->
                new AlcoholLamp(BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(
                                4F, 10F).lightLevel(light(AlcoholLamp.BURNING, 10))
                        .noOcclusion().emissiveRendering((pState, pLevel, pPos) ->
                                isLit(AlcoholLamp.BURNING, pState, pLevel, pPos))));
        BARREN_SOIL = block("Barren Soil", () ->
                new BarrenSoil(BlockBehaviour.Properties.copy(Blocks.DIRT)));
        REED = block("reed", () -> new Reed(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
        WEED = block("weed", () -> new Weed(BlockBehaviour.Properties.copy(Blocks.GRASS)));
    }


    public static class SimpleBlockProperties extends BlockBehaviour.Properties {
        public SimpleBlockProperties() {
            super();
        }


    }
}
