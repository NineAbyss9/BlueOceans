
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.item.BORarity;
import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.bilibili.player_ix.blue_oceans.common.item.FlagItem;
import com.bilibili.player_ix.blue_oceans.common.item.ItemLocBlockItem;
import com.bilibili.player_ix.blue_oceans.common.item.WoodenStick;
import com.bilibili.player_ix.blue_oceans.common.item.biology.GravyBottle;
import com.bilibili.player_ix.blue_oceans.common.item.biology.Scalpel;
import com.bilibili.player_ix.blue_oceans.common.item.biology.TestTube;
import com.bilibili.player_ix.blue_oceans.common.item.biology.organ.*;
import com.bilibili.player_ix.blue_oceans.common.item.biology.tissue.Muscle;
import com.bilibili.player_ix.blue_oceans.common.item.biology.tissue.Nerve;
import com.bilibili.player_ix.blue_oceans.common.item.biology.tissue.PlumMuscle;
import com.bilibili.player_ix.blue_oceans.common.item.biology.tissue.PlumNerve;
import com.bilibili.player_ix.blue_oceans.common.item.block.RiceItem;
import com.bilibili.player_ix.blue_oceans.common.item.chemistry.ElementItem;
import com.bilibili.player_ix.blue_oceans.common.item.equipment.ElementArmor;
import com.bilibili.player_ix.blue_oceans.common.item.farming.ChemicalFertilizer;
import com.bilibili.player_ix.blue_oceans.common.item.food.*;
import com.bilibili.player_ix.blue_oceans.common.item.gun.AbstractGun;
import com.bilibili.player_ix.blue_oceans.common.item.gun.SniperRifle;
import com.bilibili.player_ix.blue_oceans.common.item.plum.*;
import com.bilibili.player_ix.blue_oceans.common.item.strange.GasMask;
import com.bilibili.player_ix.blue_oceans.common.item.ts.IceAxe;
import com.bilibili.player_ix.blue_oceans.common.item.ts.IcePickaxe;
import com.bilibili.player_ix.blue_oceans.common.item.ts.IceSword;
import com.bilibili.player_ix.blue_oceans.common.item.util.Bandage;
import com.bilibili.player_ix.blue_oceans.common.item.util.EntityKiller;
import com.bilibili.player_ix.blue_oceans.common.item.util.HammerItem;
import com.bilibili.player_ix.blue_oceans.common.item.farming.ScytheItem;
import com.bilibili.player_ix.blue_oceans.common.item.util.IntegratedTool;
import com.bilibili.player_ix.blue_oceans.common.item.util.axe.ElementAxe;
import com.bilibili.player_ix.blue_oceans.common.item.util.pickaxe.ElementPickaxe;
import com.bilibili.player_ix.blue_oceans.common.item.weapon.FreakyAxe;
import com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum.RedPlumScythe;
import com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum.RedPlumSword;
import com.bilibili.player_ix.blue_oceans.init.data.BoDataGenHelper;
import com.github.NineAbyss9.ix_api.api.item.ApiSpawnEgg;
import com.github.NineAbyss9.ix_api.api.item.BaseItem;
import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.NineAbyss9.annotation.PAMAreNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@PAMAreNonnullByDefault
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlueOceansItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS,  BlueOceans.MOD_ID);
    public static Set<RegistryObject<? extends Item>> MAIN_CREATIVE_ITEMS = Sets.newLinkedHashSet();
    public static Set<RegistryObject<? extends Item>> SPAWN_EGGS = Sets.newLinkedHashSet();
    public static Set<RegistryObject<? extends Item>> BLOCKS = Sets.newLinkedHashSet();
    public static Set<RegistryObject<? extends Item>> ELEMENT_CREATIVE_ITEMS = Sets.newLinkedHashSet();
    public static Set<RegistryObject<? extends Item>> BIOLOGY_ITEMS = Sets.newLinkedHashSet();
    public static Set<RegistryObject<? extends Item>> PLUM_ITEMS = Sets.newLinkedHashSet();
    public static Set<RegistryObject<? extends Item>> UTILS = Sets.newLinkedHashSet();
    private BlueOceansItems() {
    }

    private static ItemBuilder builder() {
        return new ItemBuilder();
    }

    private static RegistryObject<Item> item(String en, Supplier<Item> pItem) {
        var obj = BlueOceansItems.ITEMS.register(en.replace(" ", "_").toLowerCase(), pItem);
        BoDataGenHelper.ITEM_REGISTRIES.add(obj);
        return obj;
    }

    private static RegistryObject<Item> block(String en, Supplier<? extends Block> supplier, String address)
    {
        return builder().name("block/" + en).item(() -> new ItemLocBlockItem(supplier.get(), properties(), address)).blocks().build();
    }

    private static RegistryObject<Item> block(RegistryObject<? extends Block> supplier, String address)
    {
        return builder().name("block/" + supplier.getId().getPath()).item(() -> new ItemLocBlockItem(supplier.get(), properties(), address)).blocks().build();
    }

    public static RegistryObject<Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> object, int b,
                                                int g, Rarity rarity) {
        RegistryObject<Item> obj = item(name, ()-> new ForgeSpawnEggItem(object, b, g,
                properties().rarity(rarity)));
        SPAWN_EGGS.add(obj);
        return obj;
    }

    public static RegistryObject<Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> object, int b,
                                                int g) {
        RegistryObject<Item> obj = ITEMS.register("spawn_egg/"+name+"_spawn_egg", ()-> new ForgeSpawnEggItem(object, b, g,
                properties()));
        SPAWN_EGGS.add(obj);
        return obj;
    }

    public static RegistryObject<Item> noAddToCreativeSpawnEgg(String name,
                                                               Supplier<? extends EntityType<? extends Mob>> object, int b, int g) {
        return ITEMS.register("spawn_egg/"+name+"_spawn_egg", ()-> new ForgeSpawnEggItem(object, b, g,
                properties()));
    }

    public static RegistryObject<Item> apiSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> object,
                                                   int b, int g) {
        RegistryObject<Item> obj = ITEMS.register("spawn_egg/"+name+"_spawn_egg", ()-> new ApiSpawnEgg(object, b, g,
                properties()));
        SPAWN_EGGS.add(obj);
        return obj;
    }

    public static RegistryObject<Item> addBiology(String name, Supplier<Item> item) {
        RegistryObject<Item> obj = ITEMS.register(name, item);
        BIOLOGY_ITEMS.add(obj);
        return obj;
    }

    public static RegistryObject<Item> addPlum(String name, Supplier<Item> item) {
        RegistryObject<Item> obj = ITEMS.register(name, item);
        PLUM_ITEMS.add(obj);
        return obj;
    }

    @SuppressWarnings("all")
    public static RegistryObject<Item> block(RegistryObject<? extends Block> block) {
        return block(block.getId().getPath(), block);
    }

    public static RegistryObject<Item> block(String name, RegistryObject<? extends Block> block) {
        return block(name, block, properties());
    }

    public static RegistryObject<Item> block(String name, RegistryObject<? extends Block> block, Item.Properties properties) {
        RegistryObject<Item> obj = ITEMS.register("block/" + name, ()-> new BlockItem(block.get(), properties));
        BLOCKS.add(obj);
        return obj;
    }

    public static RegistryObject<Item> itemNameBlock(String name, Supplier<Block> block) {
        return itemNameBlock(name, block, properties());
    }

    public static RegistryObject<Item> itemNameBlock(String name, Supplier<Block> block, Item.Properties pP) {
        return ITEMS.register("block/" + name, () -> new ItemNameBlockItem(block.get(), pP));
    }

    public static RegistryObject<Item> mushroom(String name, Supplier<Block> supplier, Item.Properties properties) {
        return ITEMS.register(name, () -> new MushroomItem(supplier.get(), properties));
    }

    public static RegistryObject<Item> mushroom(String name, Supplier<Block> supplier, Item.Properties properties, int n, float s) {
        return ITEMS.register(name, () -> new MushroomItem(supplier.get(), properties, n, s));
    }

    public static RegistryObject<Item> registerMainCreativeTabItem(String st, Supplier<Item> supplier) {
        RegistryObject<Item> object = ITEMS.register(st, supplier);
        MAIN_CREATIVE_ITEMS.add(object);
        return object;
    }

    public static RegistryObject<ElementArmor> registerElementArmor(String st, Supplier<ElementArmor> supplier) {
        RegistryObject<ElementArmor> object = ITEMS.register(st, supplier);
        ELEMENT_CREATIVE_ITEMS.add(object);
        return object;
    }

    public static RegistryObject<Item> addUtils(String name, Supplier<Item> item) {
        RegistryObject<Item> obj = ITEMS.register(name, item);
        UTILS.add(obj);
        return obj;
    }

    public static Item.Properties properties() {
        return new Item.Properties();
    }

    //ChemistryBase
    public static final RegistryObject<Item> ALCOHOL_LAMP = block(BlueOceansBlocks.ALCOHOL_LAMP);
    //CBEnd

    //Chemistry Items
    //Ingots
    public static final RegistryObject<ElementItem> ALUMINUM_INGOT = ITEMS.register(
            "aluminum_ingot", ()-> new ElementItem(Element.Al));
    //Pickaxe
    public static final RegistryObject<ElementAxe> ALUMINUM_AXE = ITEMS.register(
            "aluminum_axe", ()-> new ElementAxe(Element.Al, 3, 14,
                    "aluminum_ingot", 3, -2.8F)
    );
    public static final RegistryObject<ElementPickaxe> ALUMINUM_PICKAXE = ITEMS.register(
            "aluminum_pickaxe", () -> new ElementPickaxe(Element.Al, 3, 14,
                    "aluminum_ingot", 1, -2.8F)
    );
    //Equipments
    public static final RegistryObject<ElementArmor> ALUMINUM_HELMET = registerElementArmor(
            "aluminum_helmet", () -> ElementArmor.helmet(Element.Al, SoundEvents.ARMOR_EQUIP_IRON,
                    Ingredient.of(ALUMINUM_INGOT.get()), "aluminum_helmet", properties()));
    public static final RegistryObject<ElementArmor> ALUMINUM_CHEST_PLATE = registerElementArmor(
            "aluminum_chestplate", () -> ElementArmor.chestplate(Element.Al, SoundEvents.ARMOR_EQUIP_IRON,
                    Ingredient.of(ALUMINUM_INGOT.get()), "aluminum_chestplate", properties()));
    public static final RegistryObject<ElementArmor> ALUMINUM_LEGGINGS = registerElementArmor(
            "aluminum_leggings", () -> ElementArmor.leggings(Element.Al, SoundEvents.ARMOR_EQUIP_IRON,
                    Ingredient.of(ALUMINUM_INGOT.get()), "aluminum_leggings", properties()));
    public static final RegistryObject<ElementArmor> ALUMINUM_BOOTS = registerElementArmor(
            "aluminum_boots", () -> ElementArmor.boots(Element.Al, SoundEvents.ARMOR_EQUIP_IRON,
                    Ingredient.of(ALUMINUM_INGOT.get()), "aluminum_boots", properties()));
    //CIEnd

    //Plum
    public static final RegistryObject<Item> ECHO_POTION = addPlum("echo_potion",
            () -> new EchoPotionItem(properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RED_PLUM = addPlum("red_plum", RedPlum::new);
    public static final RegistryObject<Item> PLUM_CELL_CLUSTER = addPlum("block/plum_cell_cluster",
            PlumCellCluster::new);
    public static final RegistryObject<Item> PLUM_TISSUE = addPlum("block/plum_tissue", PlumTissue::new);
    public static final RegistryObject<Item> PLUM_MUSCLE = addPlum("plum_muscle", PlumMuscle::new);
    public static final RegistryObject<Item> PLUM_NERVE = addPlum("plum_nerve", PlumNerve::new);
    public static final RegistryObject<Item> PLUM_BRAIN = addPlum("plum_brain", PlumBrain::new);
    public static final RegistryObject<Item> PLUM_HEART = addPlum("plum_heart", PlumHeart::new);
    public static final RegistryObject<Item> PLUM_LUNG = addPlum("plum_lung", PlumLung::new);
    //P End

    //Spawn Egg
    public static final RegistryObject<Item> BASE_VILLAGER_SPAWN_EGG = spawnEgg("base_villager",
            BlueOceansEntities.BASE_VILLAGER, 5651507, 12422002);
    public static final RegistryObject<Item> DEATH_SPAWN_EGG = spawnEgg("death",
            BlueOceansEntities.DEATH, 0x000000, 0x001000);
    public static final RegistryObject<Item> DICTATOR_SPAWN_EGG = spawnEgg("dictator",
            BlueOceansEntities.DICTATOR,-10066330, -6710887);
    public static final RegistryObject<Item> DUCK_SPAWN_EGG = apiSpawnEgg("duck", BlueOceansEntities.DUCK,
            Mth.hsvToRgb(0, 0, 100), Mth.hsvToRgb(120, 100, 50));
    public static final RegistryObject<Item> EARTHWORM = apiSpawnEgg("earthworm", BlueOceansEntities.EARTHWORM,
            Mth.hsvToRgb(60, 100, 50), Mth.hsvToRgb(0, 100, 50));
    public static final RegistryObject<Item> FARMER_SPAWN_EGG = spawnEgg("farmer", BlueOceansEntities.FARMER,
            5651507, 12422002);
    public static final RegistryObject<Item> FREAK_SPAWN_EGG = spawnEgg("freak",
           BlueOceansEntities.FREAK, 0x272727, 0xDCDCDC);
    public static final RegistryObject<Item> HUNTING_VILLAGER_SPAWN_EGG = spawnEgg("hunting_villager", BlueOceansEntities.HUNTING_VILLAGER, 5651507, 12422002);
    public static final RegistryObject<Item> JELLYFISH_SPAWN_EGG = apiSpawnEgg("jellyfish", BlueOceansEntities.JELLYFISH,
            0xf32fdc0, 0xf32fdc0);
    public static final RegistryObject<Item> NATURAL_ENVOY_SPAWN_EGG = spawnEgg("natural_envoy",
            BlueOceansEntities.NATURAL_ENVOY, -13434880, -16777216);
    public static final RegistryObject<Item> NEO_FIGHTER_SPAWN_EGG = spawnEgg("neo_fighter", BlueOceansEntities.NEO_FIGHTER,
            0xf32fdc0, 0x4c0000);
    public static final RegistryObject<Item> NEO_PLUM_SPAWN_EGG = spawnEgg("neo_plum", BlueOceansEntities.NEO_PLUM,
            0xf32fdc0, 0x4c0000);
    public static final RegistryObject<Item> PARAMECIUM_SPAWN_EGG = spawnEgg("paramecium", BlueOceansEntities.PARAMECIUM,
            0x1aB654, 0x3CBB33);
    public static final RegistryObject<Item> RED_PLUM_CREEPER_SPAWN_EGG = spawnEgg("red_plum_creeper", BlueOceansEntities.RED_PLUM_CREEPER,
            355000009, 324000000);
    public static final RegistryObject<Item> RED_PLUM_VILLAGER_SPAWN_EGG = apiSpawnEgg("red_plum_villager", BlueOceansEntities.RED_PLUM_VILLAGER,
            352000009, 329000000);
    public static final RegistryObject<Item> PLUM_BUILDER_SPAWN_EGG = spawnEgg("plum_builder", BlueOceansEntities.PLUM_BUILDER,
            355000000, 325000000);
    public static final RegistryObject<Item> PLUM_FACTORY_SPAWN_EGG = spawnEgg("plum_factory", BlueOceansEntities.PLUM_FACTORY,
            355000000, 325000000);
    public static final RegistryObject<Item> PLUM_HOLDER_SPAWN_EGG = apiSpawnEgg("plum_holder", BlueOceansEntities.PLUM_HOLDER,
            355000000, 325000000);
    public static final RegistryObject<Item> PLUM_SPREADER_SPAWN_EGG = spawnEgg("plum_spreader", BlueOceansEntities.PLUM_SPREADER,
            355000000, 325000000);
    public static final RegistryObject<Item> RED_DEMON_SPAWN_EGG = apiSpawnEgg("red_demon", BlueOceansEntities.RED_DEMON,
            -3407872, -6750208);
    public static final RegistryObject<Item> RED_PLUMS_COW_SPAWN_EGG = spawnEgg("red_plums_cow", BlueOceansEntities.RED_PLUMS_COW, -3407872, -6750208);
    public static final RegistryObject<Item> RED_PLUM_GIRL_SPAWN_EGG = spawnEgg("red_plum_girl_spawn_egg",
            BlueOceansEntities.RED_PLUM_GIRL, -13434880, -10092544, BORarity.RED_PLUM);
    public static final RegistryObject<Item> RED_PLUM_HUMAN_SPAWN_EGG = spawnEgg("red_plum_human", BlueOceansEntities.RED_PLUM_HUMAN, 2558751, 2558750);
    public static final RegistryObject<Item> RED_PLUM_SKELETON_SPAWN_EGG = spawnEgg("red_plum_skeleton",
            BlueOceansEntities.RED_PLUM_SKELETON, -13434000, -10092544);
    public static final RegistryObject<Item> RED_PLUM_SLAYER = spawnEgg("red_plum_slayer",
            BlueOceansEntities.RED_PLUM_SLAYER, -13434880, 0xf32fdc0);
    public static final RegistryObject<Item> RED_PLUM_SPIDER_SPAWN_EGG = spawnEgg("red_plum_spider",
            BlueOceansEntities.RED_PLUM_SPIDER, 245000000, 100000000);
    public static final RegistryObject<Item> RED_PLUM_WORM = apiSpawnEgg("red_plum_worm",
            BlueOceansEntities.RED_PLUM_WORM, 245000000, 100000000);
    public static final RegistryObject<Item> VILLAGER_BIOLOGIST_SPAWN_EGG = spawnEgg("villager_biologist", BlueOceansEntities.VILLAGER_BIOLOGIST,
            5651507, 12422002);
    public static final RegistryObject<Item> VILLAGER_CHIEF_SPAWN_EGG = apiSpawnEgg("villager_chief",
            BlueOceansEntities.VILLAGER_CHIEF, 5651507, 12422002);
    //S End

    //Material
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", BaseItem::new);

    public static final RegistryObject<Item> REED = block("reed", BlueOceansBlocks.REED, "");
    //Fuels
    public static final RegistryObject<Item> Lignite = item("Lignite", BaseItem::new);
    //F End
    //M End

    //Block
    public static final RegistryObject<Item> BARREN_SOIL = block(BlueOceansBlocks.BARREN_SOIL);
    public static final RegistryObject<Item> BARREN_SOIL_FARMLAND = block(BlueOceansBlocks.BARREN_SOIL_FARMLAND);
    public static final RegistryObject<Item> BLACK_SOIL = block(BlueOceansBlocks.BLACK_SOIL);
    public static final RegistryObject<Item> BLACK_SOIL_FARMLAND = block(BlueOceansBlocks
            .BLACK_SOIL_FARMLAND);
    public static final RegistryObject<Item> BUDDING_NEO_PLUM = block(BlueOceansBlocks.BUDDING_NEO_PLUM);
    public static final RegistryObject<Item> BUSH = block(BlueOceansBlocks.BUSH);
    public static final RegistryObject<Item> CORPSE = block(BlueOceansBlocks.CORPSE);
    public static final RegistryObject<Item> FLAG = ITEMS.register("flag", FlagItem::new);
    public static final RegistryObject<Item> LEEK_SEEDS = itemNameBlock("leek_seeds", BlueOceansBlocks.LEEK,
            properties().stacksTo(64));
    public static final RegistryObject<Item> MINING_LAMP = block(BlueOceansBlocks.MINING_LAMP);
    public static final RegistryObject<Item> PLUM_CORPSE = block(BlueOceansBlocks.PLUM_CORPSE, "");
    public static final RegistryObject<Item> RED_PLUM_BLOCK = block(BlueOceansBlocks.RED_PLUM_BLOCK);
    public static final RegistryObject<Item> RED_PLUM_CATALYST = block(BlueOceansBlocks.RED_PLUM_CATALYST);
    public static final RegistryObject<Item> RED_PLUM_GRASS = block(BlueOceansBlocks.RED_PLUM_GRASS);
    public static final RegistryObject<Item> RED_PLUM_TRAP = block(BlueOceansBlocks.RED_PLUM_TRAP);
    public static final RegistryObject<Item> RED_PLUM_VEIN = block(BlueOceansBlocks.RED_PLUM_VEIN);
    public static final RegistryObject<Item> RICE_SEEDS = ITEMS.register("block/rice_seeds", () ->
            new RiceItem(BlueOceansBlocks.RICE.get(), properties()));
    public static final RegistryObject<Item> ROPE = block(BlueOceansBlocks.ROPE);
    public static final RegistryObject<Item> SALT_ORE = block(BlueOceansBlocks.SALT_ORE);
    public static final RegistryObject<Item> SCLEROTIC_RED_PLUM_BLOCK =
            block(BlueOceansBlocks.SCLEROTIC_RED_PLUM_BLOCK);
    public static final RegistryObject<Item> SPRINKLER = block(BlueOceansBlocks.SPRINKLER);
    public static final RegistryObject<Item> WOODEN_SUPPORT = block(BlueOceansBlocks.WOODEN_SUPPORT);
    //B end

    //Food
    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", Coffee::new);
    //public static final RegistryObject<Item> CompressedBiscuit = null;
    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            ()-> new FoodItem(2, 1.5F));
    //public static final RegistryObject<Item> EdibleSalt = ITEMS.register("edible_salt", () -> new Item(properties()));
    public static final RegistryObject<Item> GANODERMA_LUCIDUM = mushroom("ganoderma_lucidum",
            BlueOceansBlocks.GANODERMA_LUCIDUM, properties().stacksTo(64), 2, 1.0F);
    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle",
            () -> new FoodItem(2, 1.5F, (itemStack, level, player) -> {
                if (!level.isClientSide)
                    player.curePotionEffects(itemStack);
                if (player instanceof ServerPlayer serverplayer) {
                    CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, itemStack);
                    serverplayer.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                }
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                return itemStack.isEmpty() ? new ItemStack(Items.GLASS_BOTTLE) : itemStack;
            }) {
                public UseAnim getUseAnimation(ItemStack pStack) {
                    return UseAnim.DRINK;
                }

                public SoundEvent getEatingSound() {
                    return SoundEvents.GENERIC_DRINK;
                }
            });
    public static final RegistryObject<Item> BROWN_MUSHROOM_SKEWER = item("brown_mushroom_skewer",
            () -> new FoodItem(2, 1.2f));
    public static final RegistryObject<Item> MUSHROOM_SKEWER = ITEMS.register("mushroom_skewer",
            () -> new FoodItem(2, 1.2F));
    public static final RegistryObject<Item> C_M_S = item("cooked_mushroom_skewer",
            () -> new FoodItem(4, 1.5f));
    public static final RegistryObject<Item> C_B_M_S = item("cooked_brown_mushroom_skewer",
            () -> new FoodItem(4, 1.5f));
    public static final RegistryObject<Item> GINKGO = ITEMS.register("ginkgo",
            () -> new FoodItem(1, 1.5F));
    public static final RegistryObject<Item> LEEK = ITEMS.register("leek", () -> new FoodItem(2, 1.0F));
    public static final RegistryObject<Item> GRAPE = ITEMS.register("grape", ()-> new FoodItem(2, 1.0F));
    public static final RegistryObject<Item> ORANGE_FLESH = ITEMS.register("orange_flesh", () ->
            new FoodItem(2, 0.5F));
    public static final RegistryObject<Item> ORANGE = ITEMS.register("orange", Orange::new);
    public static final RegistryObject<Item> PEA_POD = ITEMS.register("pea_pod", PeaPod::new);
    public static final RegistryObject<Item> PEACH = ITEMS.register("peach", Peach::new);
    public static final RegistryObject<Item> PEAR = ITEMS.register("pear", ()-> new FoodItem(2, 1.0F));
    public static final RegistryObject<Item> RED_PLUM_FLESH = ITEMS.register("red_plum_flesh",
            RedPlumFlesh::new);
    public static final RegistryObject<Item> RICE = ITEMS.register("rice", () -> new FoodItem(
            2, 1.0F, Rarity.UNCOMMON));
    public static final RegistryObject<Item> RICE_BOWL = ITEMS.register("rice_bowl", () ->
            new Item(properties().stacksTo(1).rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder()
                    .nutrition(5).saturationMod(1.2F).build())) {
                public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
                    if (pLivingEntity instanceof Player player) {
                        if (!player.getAbilities().instabuild) {
                            pStack.shrink(1);
                            player.getInventory().add(new ItemStack(Items.BOWL));
                        }
                    }
                    return super.finishUsingItem(pStack, pLevel, pLivingEntity);
                }
            });
    public static final RegistryObject<Item> RoastedNori = builder().item(() -> new FoodItem(2, 1.1f))
            .translate("Roasted Nori", "烤紫菜").build();
    public static final RegistryObject<Item> SALT_PILE = ITEMS.register("salt_pile",
            () -> new Item(properties().stacksTo(64)));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> FoodItem.fast(1, 1F));
    public static final RegistryObject<Item> GREATEST_MANTOU = ITEMS.register("greatest_mantou",
            () -> new FoodItem(100, 100F, 1,
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 5),
                    new MobEffectInstance(MobEffects.CONFUSION, 600, 9),
                    new MobEffectInstance(MobEffects.WEAKNESS, 600, 255)) {
                public Rarity getRarity(ItemStack pStack) {
                    return Rarity.EPIC;
                }
            });
    //F End

    //Util
    public static final RegistryObject<Item> CHEMICAL_FERTILIZER = builder().translate("Chemical Fertilizer",
            "化肥").item(ChemicalFertilizer::new).utils().build();
    //Axe
    public static final RegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(BoTier.FLINT, 4, -3.1F,
                    properties()));
    public static final RegistryObject<Item> FREAKY_AXE = ITEMS.register("freaky_axe", FreakyAxe::new);
    public static final RegistryObject<Item> ICE_AXE = addUtils("ice_axe", IceAxe::new);
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(BoTier.STEEL,
            5.0F, -2.8F, properties()));
    //Hammer
    public static final RegistryObject<Item> IRON_HAMMER = addUtils("iron_hammer", () ->
            new HammerItem(11.0F, -3.2F, BoTier.IRON, properties()));
    //Pickaxe
    public static final RegistryObject<Item> FLINT_PICKAXE = addUtils("flint_pickaxe",
            () -> new PickaxeItem(BoTier.FLINT, 2, -3.0F, properties()));
    public static final RegistryObject<Item> STEEL_PICKAXE = addUtils("steel_pickaxe", () ->
            new PickaxeItem(BoTier.STEEL, 1, -3.0F, properties()));
    public static final RegistryObject<Item> ICE_PICKAXE = addUtils("ice_pickaxe", IcePickaxe::new);
    //Scythe
    public static final RegistryObject<Item> IRON_SCYTHE = addUtils("iron_scythe", () ->
            new ScytheItem(4.0F, -1.6F, BoTier.IRON, properties()));
    //Shovel
    public static final RegistryObject<Item> FLINT_SHOVEL = addUtils("flint_shovel",
            () -> new ShovelItem(BoTier.FLINT, 2, -3.0F, properties()));
    public static final RegistryObject<Item> STEEL_SHOVEL = addUtils("steel_shovel",
            () -> new ShovelItem(BoTier.STEEL, 1, -3.0F, properties()));
    //Integrated
    public static final RegistryObject<Item> INTEGRATED_DIAMOND_TOOL = addUtils("integrated_diamond_tool",
            () -> new IntegratedTool(BoTier.DIAMOND, 6, -2.4F,
                    properties()));
    public static final RegistryObject<Item> INTEGRATED_IRON_TOOL = addUtils("integrated_iron_tool",
            () -> new IntegratedTool(BoTier.IRON, 4, -2.6F, properties()));
    //Traffic
    public static final RegistryObject<Item> BIKE_EGG = noAddToCreativeSpawnEgg("bike",
            BlueOceansEntities.BIKE, 1001033, 1001033);
    //UEnd

    //Misc
    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage", Bandage::new);
    public static final RegistryObject<Item> ENTITY_KILLER = ITEMS.register("entity_killer",
            EntityKiller::new);
    //M End

    //Weapon
    //Gun
    public static final RegistryObject<AbstractGun> SNIPER_RIFLE = ITEMS.register("sniper_rifle",
            SniperRifle::new);
    //Sword
    public static final RegistryObject<Item> FLINT_SWORD = ITEMS.register("flint_sword",
            () -> new SwordItem(BoTier.FLINT, 3, -2.2F, properties()));
    public static final RegistryObject<Item> ICE_SWORD = ITEMS.register("ice_sword", IceSword::new);
    public static final RegistryObject<Item> RED_PLUM_SWORD = addPlum("red_plum_sword",
            RedPlumSword::new);
    public static final RegistryObject<Item> RED_PLUM_SCYTHE = addPlum("red_plum_scythe",
            RedPlumScythe::new);
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new AxeItem(
            BoTier.STEEL, 3.5F, -2.0F, properties()));
    //W End

    //Other
    public static final RegistryObject<Item> WOODEN_STICK = ITEMS.register("wooden_stick", WoodenStick::new);
    //OEnd

    //Biology
    //Organ
    public static final RegistryObject<Item> BRAIN = addBiology("brain", Brain::new);
    public static final RegistryObject<Item> HEART = addBiology("heart", Heart::new);
    public static final RegistryObject<Item> LUNG = addBiology("lung", Lung::new);
    //OrEnd
    //Tissue
    public static final RegistryObject<Item> MUSCLE = addBiology("muscle", Muscle::new);
    public static final RegistryObject<Item> NERVE = addBiology("nerve", Nerve::new);
    //TiEnd
    //Cell

    //CeEnd
    //Eq
    public static final RegistryObject<Item> GAS_MASK = addBiology("gas_mask", GasMask::new);
    //Eq End
    //Hospital
    public static final RegistryObject<Item> SCALPEL = addBiology("scalpel", Scalpel::new);
    //HoEnd
    public static final RegistryObject<Item> GRAVY_BOTTLE = addBiology("gravy_bottle", GravyBottle::new);
    public static final RegistryObject<Item> TEST_TUBE = addBiology("test_tube", TestTube::new);
    //BiEnd

    public static class ItemBuilder {
        public Supplier<Item> item;
        public boolean mainCreative;
        public boolean spawnEggs;
        public boolean blocks;
        public boolean utils;
        public String en;
        public String zh;
        public ItemBuilder() {
        }

        public ItemBuilder item(Supplier<Item> blockSupplier) {
            this.item = blockSupplier;
            return this;
        }

        public ItemBuilder addToCreativeTab() {
            this.mainCreative = true;
            return this;
        }

        public ItemBuilder spawnEggs() {
            this.spawnEggs = true;
            return this;
        }

        public ItemBuilder blocks() {
            this.blocks = true;
            return this;
        }

        public ItemBuilder utils() {
            this.utils = true;
            return this;
        }

        public ItemBuilder name(String name)
        {
            this.en = name;
            return this;
        }

        public ItemBuilder translate(String en, String zh) {
            this.en = en;
            this.zh = zh;
            return this;
        }

        public RegistryObject<Item> build() {
            RegistryObject<Item> obj = BlueOceansItems.item(en, item);
            if (mainCreative) MAIN_CREATIVE_ITEMS.add(obj);
            if (spawnEggs) SPAWN_EGGS.add(obj);
            if (blocks) BLOCKS.add(obj);
            if (utils) UTILS.add(obj);
            return obj;
        }
    }
}
