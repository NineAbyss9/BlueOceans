
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.RedPlumCatalystEntity;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.WoodenSupportBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.NineAbyss9.code.Instance;

public class BlueOceansBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BlueOceans.MOD_ID);
    public static final RegistryObject<BlockEntityType<RedPlumCatalystEntity>> RED_PLUM_CATALYST
            = BLOCK_ENTITIES.register("red_plum_catalyst_entity",
            () -> BlockEntityType.Builder.of(RedPlumCatalystEntity::new, BlueOceansBlocks
                    .RED_PLUM_CATALYST.get()).build(Instance.nullOf()));
    public static final RegistryObject<BlockEntityType<WoodenSupportBlockEntity>> WOODEN_SUPPORT
            = BLOCK_ENTITIES.register("wooden_support_entity",
            () -> BlockEntityType.Builder.of(WoodenSupportBlockEntity::new, BlueOceansBlocks
                    .WOODEN_SUPPORT.get()).build(Instance.nullOf()));
}
