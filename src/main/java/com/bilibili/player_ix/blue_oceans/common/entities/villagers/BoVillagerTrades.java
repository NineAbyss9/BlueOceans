
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;

public class BoVillagerTrades {
    public static final VillagerTrades.ItemListing[] BIOLOGIST_TRADES;
    public static final VillagerTrades.ItemListing[] FARMER_TRADES;
    public static final VillagerTrades.ItemListing[] FISHMAN_TRADES;
    public static final VillagerTrades.ItemListing[] HUNTER_TRADES;
    private BoVillagerTrades() {
    }

    static {
        BIOLOGIST_TRADES = new VillagerTrades.ItemListing[] {
                new BasicItemListing(5, new ItemStack(BlueOceansItems.GRAVY_BOTTLE.get()), 10, 1),
                new BasicItemListing(5, new ItemStack(Items.AXOLOTL_BUCKET), 1, 1),
                new BasicItemListing(new ItemStack(Items.KELP, 12), new ItemStack(Items.EMERALD),
                        20, 1, 0.05f)
        };
        FARMER_TRADES = new VillagerTrades.ItemListing[] {
                new BasicItemListing(1, new ItemStack(Items.WHEAT_SEEDS, 2), 15, 1),
                new BasicItemListing(1, new ItemStack(Items.BEETROOT_SEEDS, 2), 15, 1),
                new BasicItemListing(1, new ItemStack(Items.MELON_SEEDS), 15, 2),
                new BasicItemListing(1, new ItemStack(BlueOceansItems.LEEK_SEEDS.get(), 2),
                        15, 1),
                new BasicItemListing(1, new ItemStack(BlueOceansItems.RICE_SEEDS.get(), 2),
                        15, 1),
                new BasicItemListing(1, new ItemStack(BlueOceansItems.LEEK.get()), 15, 2),
                new BasicItemListing(1, new ItemStack(BlueOceansItems.GINKGO.get(), 3), 15,
                        2)
        };
        FISHMAN_TRADES = new VillagerTrades.ItemListing[] {
                new BasicItemListing(1, new ItemStack(Items.SALMON, 2), 15, 1),
                new BasicItemListing(1, new ItemStack(Items.TROPICAL_FISH, 2), 15, 1),
                new BasicItemListing(1, new ItemStack(Items.KELP, 6), 60, 1),
                new BasicItemListing(2, new ItemStack(Items.PUFFERFISH), 10, 2),
                new BasicItemListing(3, new ItemStack(Items.TROPICAL_FISH_BUCKET), 5, 3,
                        0.01F),
                new BasicItemListing(3, new ItemStack(Items.SALMON_BUCKET), 5, 3, .01f),
                new BasicItemListing(6, new ItemStack(Items.PUFFERFISH_BUCKET), 5, 3, .02f)
        };
        HUNTER_TRADES = new VillagerTrades.ItemListing[] {
                new BasicItemListing(3, new ItemStack(BlueOceansItems.RED_PLUM_FLESH.get()),
                        5, 1),
                new BasicItemListing(new ItemStack(Items.COOKED_BEEF, 2), new ItemStack(Items.EMERALD),
                        8, 1, 0.01f)
        };
    }
}
