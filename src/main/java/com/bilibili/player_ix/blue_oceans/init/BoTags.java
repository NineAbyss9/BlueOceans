
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import net.minecraft.world.item.Item;
import org.NineAbyss9.annotation.PFMAreNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
@PFMAreNonnullByDefault
public class BoTags {
    //EntityTypes
    public static TagKey<EntityType<?>> RED_PLUM_MOBS
            = entityTypeTag("red_plum_mobs");

    //Blocks
    public static TagKey<Block> BARREN_FARMLANDS
            = blockTagKey("barren_farmlands");
    public static TagKey<Block> BUSHES_MAY_PLACE_ON
            = blockTagKey("bushes_may_place_on");
    public static TagKey<Block> FUELS
            = blockTagKey("fuels");
    public static TagKey<Block> PLUMS_CAN_UPGRADE
            = blockTagKey("plums_can_upgrade");
    public static TagKey<Block> RED_PLUM_BLOCKS
            = blockTagKey("red_plum_blocks");

    //Items
    public static TagKey<Item> HAMMERS = itemTagKey("hammers");
    public static TagKey<Item> SCYTHES = itemTagKey("scythes");
    public static TagKey<Item> SHEARS = itemTagKey("shears");

    public static TagKey<EntityType<?>> entityTypeTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, BlueOceans.location(path));
    }

    public static TagKey<Block> blockTagKey(String path) {
        return TagKey.create(Registries.BLOCK, BlueOceans.location(path));
    }

    public static TagKey<Item> itemTagKey(String path) {
        return TagKey.create(Registries.ITEM, BlueOceans.location(path));
    }

    public static void register() {
    }
}
