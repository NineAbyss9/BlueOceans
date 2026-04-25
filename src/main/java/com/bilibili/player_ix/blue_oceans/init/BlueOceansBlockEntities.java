
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.CorpseEntity;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.cooking.WoodenSupportBlockEntity;
import com.bilibili.player_ix.blue_oceans.common.blocks.be.farming.SprinklerEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.NineAbyss9.code.Instance;

import java.util.function.Supplier;

public class BlueOceansBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BlueOceans.MOD_ID);
    public static final RegistryObject<BlockEntityType<CorpseEntity>> CORPSE = BLOCK_ENTITIES.register("corpse",
            () -> BlockEntityType.Builder.of(CorpseEntity::new, BlueOceansBlocks.CORPSE.get()).build(Instance.nullOf()));
    public static final RegistryObject<BlockEntityType<SprinklerEntity>> SPRINKLER
            = register("sprinkler", SprinklerEntity::new, BlueOceansBlocks.SPRINKLER);
    public static final RegistryObject<BlockEntityType<WoodenSupportBlockEntity>> WOODEN_SUPPORT
            = register("wooden_support_entity", WoodenSupportBlockEntity::new, BlueOceansBlocks
            .WOODEN_SUPPORT);

    private static <T extends BlockEntity>
    RegistryObject<BlockEntityType<T>> register(String name,
                                                BlockEntityType.BlockEntitySupplier<T> pE, Supplier<Block> pB) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(pE, pB.get())
                .build(Instance.nullOf()));
    }
}
