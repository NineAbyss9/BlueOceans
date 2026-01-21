
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.github.player_ix.ix_api.api.item.ItemStacks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlueOceansTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, BlueOceans.MOD_ID);
    public static final RegistryObject<CreativeModeTab> BLUE_OCEANS = TABS.register("blue_oceans",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(BlueOceansItems
                            .RED_PLUM_GIRL_SPAWN_EGG.get()))
                    .title(Component.translatable("item_group.blue_oceans.blue_oceans")
                            .withStyle(ChatFormatting.BLUE)).displayItems((parameters, output) -> {
                        output.accept(BlueOceansItems.BANDAGE.get());
                        output.accept(BlueOceansItems.ECHO_POTION.get());
                        output.accept(BlueOceansItems.ENTITY_KILLER.get());
                        output.accept(BlueOceansItems.FREAKY_AXE.get());
                        output.accept(BlueOceansItems.RED_PLUM_SWORD.get());
                        output.accept(BlueOceansItems.RED_PLUM.get());
                        output.accept(BlueOceansItems.STEEL_INGOT.get());
                        output.accept(BlueOceansItems.SNIPER_RIFLE.get());
                        output.accept(BlueOceansItems.FLINT_AXE.get());
                        output.accept(BlueOceansItems.FLINT_PICKAXE.get());
                        output.accept(BlueOceansItems.FLINT_SHOVEL.get());
                        output.accept(BlueOceansItems.FLINT_SWORD.get());
                        output.accept(BlueOceansItems.ICE_AXE.get());
                        output.accept(BlueOceansItems.ICE_PICKAXE.get());
                        output.accept(BlueOceansItems.ICE_SWORD.get());
                        output.accept(BlueOceansItems.IRON_HAMMER.get());
                        output.accept(BlueOceansItems.STEEL_AXE.get());
                        output.accept(BlueOceansItems.STEEL_PICKAXE.get());
                        output.accept(BlueOceansItems.STEEL_SHOVEL.get());
                        output.accept(BlueOceansItems.STEEL_SWORD.get());
                        output.accept(BlueOceansItems.WOODEN_STICK.get());
                        output.accept(BlueOceansItems.BIKE_EGG.get());
                        output.accept(BlueOceansItems.BASE_VILLAGER_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.DEATH_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.DICTATOR_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.FARMER_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.FREAK_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.HUNTING_VILLAGER_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.NEO_FIGHTER_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.NEO_PLUM_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.PARAMECIUM_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.PLUM_FACTORY_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.PLUM_SPREADER_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.RED_PLUM_GIRL_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.RED_PLUM_HUMAN_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.RED_PLUM_SKELETON_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.RED_PLUM_SLAYER.get());
                        output.accept(BlueOceansItems.RED_PLUM_SPIDER_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.RED_PLUMS_COW_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.NATURAL_ENVOY_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.VILLAGER_BIOLOGIST_SPAWN_EGG.get());
                        output.accept(BlueOceansItems.VILLAGER_CHIEF_SPAWN_EGG.get());
                    }).build());
    public static final RegistryObject<CreativeModeTab> BO_BLOCK = TABS
            .register("blue_oceans_block", ()-> CreativeModeTab.builder().icon(()->
                    ItemStacks.of(BlueOceansItems.RED_PLUM_BLOCK)).title(Component.translatable(
                            "item_group.blue_oceans.blue_oceans_block").withStyle(
                            ChatFormatting.GRAY)).displayItems((parameters, output) -> {
                        output.accept(BlueOceansItems.MINING_LAMP.get());
                        output.accept(BlueOceansItems.RED_PLUM_BLOCK.get());
                        output.accept(BlueOceansItems.RED_PLUM_CATALYST.get());
                        output.accept(BlueOceansItems.RED_PLUM_GRASS.get());
                        output.accept(BlueOceansItems.RED_PLUM_TRAP.get());
                        output.accept(BlueOceansItems.RED_PLUM_VEIN.get());
                        output.accept(BlueOceansItems.ROPE.get());
                        output.accept(BlueOceansItems.SALT_ORE.get());
                        output.accept(BlueOceansItems.WOODEN_SUPPORT.get());
            }).build());
    public static final RegistryObject<CreativeModeTab> BO_BIOLOGY = TABS
            .register("blue_oceans_biology", () -> CreativeModeTab.builder().icon(() ->
                    new ItemStack(BlueOceansItems.GRAVY_BOTTLE.get())).title(Component.translatable(
                    "item_group.blue_oceans.blue_oceans_biology"
            ).withStyle(
                    ChatFormatting.BLUE)).displayItems((parameter, output) -> {
                output.accept(BlueOceansItems.GRAVY_BOTTLE.get());
                output.accept(BlueOceansItems.TEST_TUBE.get());
                ItemStack stack = new ItemStack(Items.SPLASH_POTION);
                PotionUtils.setPotion(stack, BlueOceansMobEffects.Potions.PLUM_INVADE_POTION.get());
                output.accept(stack);
            }).build());
    public static final RegistryObject<CreativeModeTab> BO_CHEMISTRY = TABS
            .register("blue_oceans_chemistry", () -> CreativeModeTab.builder().icon(() ->
                    new ItemStack(BlueOceansItems.ALUMINUM_INGOT.get())).title(Component.translatable(
                    "item_group.blue_oceans.blue_oceans_chemistry"
            ).withStyle(
                    ChatFormatting.AQUA)).displayItems((parameter, output) -> {
                output.accept(BlueOceansItems.ALCOHOL_LAMP.get());
                output.accept(BlueOceansItems.ALUMINUM_INGOT.get());
                output.accept(BlueOceansItems.ALUMINUM_AXE.get());
                output.accept(BlueOceansItems.ALUMINUM_PICKAXE.get());
                for (RegistryObject<? extends Item> item : BlueOceansItems.ELEMENT_CREATIVE_ITEMS) {
                    output.accept(item.get());
                }
            }).build());
    public static final RegistryObject<CreativeModeTab> BO_FOOD = TABS
            .register("blue_oceans_farming", () -> CreativeModeTab.builder().icon(() ->
                    new ItemStack(BlueOceansItems.RICE_BOWL.get())).title(Component.translatable(
                    "item_group.blue_oceans.blue_oceans_farming"
            ).withStyle(
                    ChatFormatting.GOLD)).displayItems((parameter, output) -> {
                output.accept(BlueOceansItems.IRON_SCYTHE.get());
                output.accept(BlueOceansItems.LEEK_SEEDS.get());
                output.accept(BlueOceansItems.RICE_SEEDS.get());
                output.accept(BlueOceansItems.GANODERMA_LUCIDUM.get());
                output.accept(BlueOceansItems.CORN.get());
                output.accept(BlueOceansItems.GINKGO.get());
                output.accept(BlueOceansItems.GRAPE.get());
                output.accept(BlueOceansItems.LEEK.get());
                output.accept(BlueOceansItems.PEA_POD.get());
                output.accept(BlueOceansItems.PEACH.get());
                output.accept(BlueOceansItems.PEAR.get());
                output.accept(BlueOceansItems.RED_PLUM_FLESH.get());
                output.accept(BlueOceansItems.RICE.get());
                output.accept(BlueOceansItems.RICE_BOWL.get());
                output.accept(BlueOceansItems.STRAWBERRY.get());
                output.accept(BlueOceansItems.MILK_BOTTLE.get());
                output.accept(BlueOceansItems.MUSHROOM_SKEWER.get());
                output.accept(BlueOceansItems.SALT_PILE.get());
            }).build());

    private BlueOceansTabs() {
    }
}
