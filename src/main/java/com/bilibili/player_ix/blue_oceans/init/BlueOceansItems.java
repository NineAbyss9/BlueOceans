
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.api.item.BORarity;
import com.bilibili.player_ix.blue_oceans.api.item.BoTier;
import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.bilibili.player_ix.blue_oceans.common.item.FlagItem;
import com.bilibili.player_ix.blue_oceans.common.item.WoodenStick;
import com.bilibili.player_ix.blue_oceans.common.item.biology.GravyBottle;
import com.bilibili.player_ix.blue_oceans.common.item.biology.TestTube;
import com.bilibili.player_ix.blue_oceans.common.item.block.RiceItem;
import com.bilibili.player_ix.blue_oceans.common.item.chemistry.ElementItem;
import com.bilibili.player_ix.blue_oceans.common.item.equipment.ElementArmor;
import com.bilibili.player_ix.blue_oceans.common.item.food.FoodItem;
import com.bilibili.player_ix.blue_oceans.common.item.food.MushroomItem;
import com.bilibili.player_ix.blue_oceans.common.item.food.PeaPod;
import com.bilibili.player_ix.blue_oceans.common.item.food.Peach;
import com.bilibili.player_ix.blue_oceans.common.item.gun.AbstractGun;
import com.bilibili.player_ix.blue_oceans.common.item.gun.SniperRifle;
import com.bilibili.player_ix.blue_oceans.common.item.plum.RedPlum;
import com.bilibili.player_ix.blue_oceans.common.item.plum.RedPlumFlesh;
import com.bilibili.player_ix.blue_oceans.common.item.ts.IceAxe;
import com.bilibili.player_ix.blue_oceans.common.item.ts.IcePickaxe;
import com.bilibili.player_ix.blue_oceans.common.item.ts.IceSword;
import com.bilibili.player_ix.blue_oceans.common.item.util.Bandage;
import com.bilibili.player_ix.blue_oceans.common.item.util.EntityKiller;
import com.bilibili.player_ix.blue_oceans.common.item.util.HammerItem;
import com.bilibili.player_ix.blue_oceans.common.item.util.ScytheItem;
import com.bilibili.player_ix.blue_oceans.common.item.util.axe.ElementAxe;
import com.bilibili.player_ix.blue_oceans.common.item.util.pickaxe.ElementPickaxe;
import com.bilibili.player_ix.blue_oceans.common.item.weapon.FreakyAxe;
import com.bilibili.player_ix.blue_oceans.common.item.weapon.red_plum.RedPlumSword;
import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.nine_abyss.annotation.PAMAreNonnullByDefault;
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
    public static Set<RegistryObject<? extends Item>> ELEMENT_CREATIVE_ITEMS = Sets.newLinkedHashSet();
    private BlueOceansItems() {
    }

    public static RegistryObject<Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> object, int b, int g, Rarity rarity) {
        return ITEMS.register(name+"_spawn_egg", ()-> new ForgeSpawnEggItem(object, b, g, properties().rarity(rarity)));
    }

    public static RegistryObject<Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> object, int b, int g) {
        return ITEMS.register(name+"_spawn_egg", ()-> new ForgeSpawnEggItem(object, b, g, properties()));
    }

    @SuppressWarnings("all")
    public static RegistryObject<Item> block(RegistryObject<Block> block) {
        return block(block.getId().getPath(), block);
    }

    public static RegistryObject<Item> block(String name, RegistryObject<Block> block) {
        return block(name, block, properties());
    }

    public static RegistryObject<Item> block(String name, RegistryObject<Block> block, Item.Properties properties) {
        return ITEMS.register(name, ()-> new BlockItem(block.get(), properties));
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
    public static final RegistryObject<Item> RED_PLUM = ITEMS.register("red_plum",
            RedPlum::new);
    //PEnd

    //Spawn Egg
    public static final RegistryObject<Item> BASE_VILLAGER_SPAWN_EGG = spawnEgg("base_villager",
            BlueOceansEntities.BASE_VILLAGER, 5651507, 12422002);
    public static final RegistryObject<Item> DEATH_SPAWN_EGG = ITEMS.register("death_spawn_egg", ()-> new ForgeSpawnEggItem(BlueOceansEntities.DEATH, 0x000000, 0x001000, new Item.Properties()));
    public static final RegistryObject<Item> DICTATOR_SPAWN_EGG = ITEMS.register("dictator_spawn_egg", () -> new ForgeSpawnEggItem(BlueOceansEntities.DICTATOR,-10066330, -6710887, new Item.Properties()));
    public static final RegistryObject<Item> FARMER_SPAWN_EGG = spawnEgg("farmer", BlueOceansEntities.FARMER,
            5651507, 12422002);
    public static final RegistryObject<Item> FREAK_SPAWN_EGG = ITEMS.register("freak_spawn_egg", () -> new ForgeSpawnEggItem(BlueOceansEntities.FREAK, 0x272727, 0xDCDCDC, new Item.Properties()));
    public static final RegistryObject<Item> HUNTING_VILLAGER_SPAWN_EGG = ITEMS.register("hunting_villager_spawn_egg", ()-> new ForgeSpawnEggItem(BlueOceansEntities.HUNTING_VILLAGER, 5651507, 12422002, new Item.Properties()));
    public static final RegistryObject<Item> NEO_PLUM_SPAWN_EGG = spawnEgg("neo_plum", BlueOceansEntities.NEO_PLUM,
            0xf32fdc0, 0x4c0000);
    public static final RegistryObject<Item> PARAMECIUM_SPAWN_EGG = spawnEgg("paramecium", BlueOceansEntities.PARAMECIUM,
            0x1aB654, 0x3CBB33);
    public static final RegistryObject<Item> PLUM_FACTORY_SPAWN_EGG = spawnEgg("plum_factory", BlueOceansEntities.PLUM_FACTORY,
            355000000, 325000000);
    public static final RegistryObject<Item> RED_PLUMS_COW_SPAWN_EGG = ITEMS.register("red_plums_cow_spawn_egg", () -> new ForgeSpawnEggItem(BlueOceansEntities.RED_PLUMS_COW, -3407872, -6750208, new Item.Properties()));
    public static final RegistryObject<Item> RED_PLUM_GIRL_SPAWN_EGG = spawnEgg("red_plum_girl",
            BlueOceansEntities.RED_PLUM_GIRL, -13434880, -10092544, BORarity.RED_PLUM);
    public static final RegistryObject<Item> RED_PLUM_HUMAN_SPAWN_EGG = spawnEgg("red_plum_human", BlueOceansEntities.RED_PLUM_HUMAN, 2558751, 2558750);
    public static final RegistryObject<Item> RED_PLUM_SKELETON_SPAWN_EGG = spawnEgg("red_plum_skeleton",
            BlueOceansEntities.RED_PLUM_SKELETON, -13434000, -10092544);
    public static final RegistryObject<Item> RED_PLUM_SLAYER = spawnEgg("red_plum_slayer",
            BlueOceansEntities.RED_PLUM_SLAYER, -13434880, 0xf32fdc0);
    public static final RegistryObject<Item> RED_PLUM_SPIDER_SPAWN_EGG = spawnEgg("red_plum_spider",
            BlueOceansEntities.RED_PLUM_SPIDER, 245000000, 100000000);
    public static final RegistryObject<Item> NATURAL_ENVOY_SPAWN_EGG = ITEMS.register("natural_envoy_spawn_egg", ()-> new ForgeSpawnEggItem(BlueOceansEntities.NATURAL_ENVOY, -13434880, -16777216, new Item.Properties()));
    public static final RegistryObject<Item> VILLAGER_BIOLOGIST_SPAWN_EGG = spawnEgg("villager_biologist", BlueOceansEntities.VILLAGER_BIOLOGIST,
            5651507, 12422002);
    public static final RegistryObject<Item> VILLAGER_CHIEF_SPAWN_EGG = spawnEgg("villager_chief", BlueOceansEntities.VILLAGER_CHIEF,
            5651507, 12422002);
    //SEnd

    //Block
    public static final RegistryObject<Item> FLAG = ITEMS.register("flag", FlagItem::new);
    public static final RegistryObject<Item> LEEK_SEEDS = ITEMS.register("leek_seeds", () ->
            new ItemNameBlockItem(BlueOceansBlocks.LEEK.get(), properties().stacksTo(64)));
    public static final RegistryObject<Item> MINING_LAMP = block(BlueOceansBlocks.MINING_LAMP);
    public static final RegistryObject<Item> RED_PLUM_BLOCK = block(BlueOceansBlocks.RED_PLUM_BLOCK);
    public static final RegistryObject<Item> RED_PLUM_CATALYST = block(BlueOceansBlocks.RED_PLUM_CATALYST);
    public static final RegistryObject<Item> RED_PLUM_GRASS = block(BlueOceansBlocks.RED_PLUM_GRASS);
    public static final RegistryObject<Item> RED_PLUM_TRAP = block(BlueOceansBlocks.RED_PLUM_TRAP);
    public static final RegistryObject<Item> RICE_SEEDS = ITEMS.register("rice_seeds", () ->
            new RiceItem(BlueOceansBlocks.RICE.get(), properties()));
    public static final RegistryObject<Item> ROPE = block(BlueOceansBlocks.ROPE);
    public static final RegistryObject<Item> SALT_ORE = block(BlueOceansBlocks.SALT_ORE);
    public static final RegistryObject<Item> WOODEN_SUPPORT = block(BlueOceansBlocks.WOODEN_SUPPORT);
    //BEnd

    //Food
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
    public static final RegistryObject<Item> MUSHROOM_SKEWER = ITEMS.register("mushroom_skewer",
            () -> new FoodItem(5, 1.0F));
    public static final RegistryObject<Item> GINKGO = ITEMS.register("ginkgo",
            () -> new FoodItem(1, 1.5F));
    public static final RegistryObject<Item> LEEK = ITEMS.register("leek", () -> new FoodItem(2, 1.0F));
    public static final RegistryObject<Item> GRAPE = ITEMS.register("grape", ()-> new FoodItem(2, 1.0F));
    public static final RegistryObject<Item> PEA_POD = ITEMS.register("pea_pod", PeaPod::new);
    public static final RegistryObject<Item> PEACH = ITEMS.register("peach", Peach::new);
    public static final RegistryObject<Item> PEAR = ITEMS.register("pear", ()-> new FoodItem(2, 1.0F));
    public static final RegistryObject<Item> RED_PLUM_FLESH = ITEMS.register("red_plum_flesh",
            RedPlumFlesh::new);
    public static final RegistryObject<Item> RICE = ITEMS.register("rice", () -> new FoodItem(
            2, 1.0F, Rarity.UNCOMMON));
    public static final RegistryObject<Item> RICE_BOWL = ITEMS.register("rice_bowl", () ->
            new Item(properties().stacksTo(1).rarity(Rarity.UNCOMMON).food(new FoodProperties.Builder()
                    .nutrition(5).saturationMod(1.2F).build())));
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
    //FEnd

    //Util
    //Axe
    public static final RegistryObject<Item> ICE_AXE = ITEMS.register("ice_axe", IceAxe::new);
    //Hammer
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () ->
            new HammerItem(11.0F, -3.2F, BoTier.IRON, properties()));
    //Pickaxe
    public static final RegistryObject<Item> FLINT_PICKAXE = ITEMS.register("flint_pickaxe",
            () -> new PickaxeItem(BoTier.FLINT, 2, -3.0F, properties()));
    public static final RegistryObject<Item> ICE_PICKAXE = ITEMS.register("ice_pickaxe", IcePickaxe::new);
    //Scythe
    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe", () ->
            new ScytheItem(4.0F, -1.6F, BoTier.IRON, properties()));
    //Shovel
    public static final RegistryObject<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel",
            () -> new ShovelItem(BoTier.FLINT, 2, -3.0F, properties()));
    //Traffic
    public static final RegistryObject<Item> BIKE_EGG = spawnEgg("bike", BlueOceansEntities.BIKE,
            1001033, 1001033);
    //UEnd

    //Misc
    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage", Bandage::new);
    public static final RegistryObject<Item> ENTITY_KILLER = ITEMS.register("entity_killer",
            EntityKiller::new);
    //MEnd

    //Weapon
    //Gun
    public static final RegistryObject<AbstractGun> SNIPER_RIFLE = ITEMS.register("sniper_rifle",
            SniperRifle::new);
    //Axe
    public static final RegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe",
            () -> new AxeItem(BoTier.FLINT, 4, -3.1F,
                    properties()));
    public static final RegistryObject<Item> FREAKY_AXE = ITEMS.register("freaky_axe", FreakyAxe::new);
    //Sword
    public static final RegistryObject<Item> FLINT_SWORD = ITEMS.register("flint_sword",
            () -> new SwordItem(BoTier.FLINT, 3, -2.2F, properties()));
    public static final RegistryObject<Item> ICE_SWORD = ITEMS.register("ice_sword", IceSword::new);
    public static final RegistryObject<Item> RED_PLUM_SWORD = ITEMS.register("red_plum_sword",
            RedPlumSword::new);
    //WEnd

    //Other
    public static final RegistryObject<Item> WOODEN_STICK = ITEMS.register("wooden_stick", WoodenStick::new);
    //OEnd

    //Biology
    public static final RegistryObject<Item> GRAVY_BOTTLE = ITEMS.register("gravy_bottle", GravyBottle::new);
    public static final RegistryObject<Item> TEST_TUBE = ITEMS.register("test_tube", TestTube::new);
    //BiEnd
}
