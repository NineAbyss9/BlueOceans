
package com.bilibili.player_ix.blue_oceans.common.blocks.be;

import com.bilibili.player_ix.blue_oceans.init.BlueOceansBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class CorpseEntity
extends BlockEntity
{
    private Entity entity = null;
    public CorpseEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(BlueOceansBlockEntities.CORPSE.get(), pPos, pBlockState);
    }

    public Entity getEntity()
    {
        return entity;
    }

    public void setEntity(EntityType<?> pEntity)
    {
        this.entity = pEntity.create(this.level);
    }

    public void drop()
    {
        if (entity instanceof LivingEntity livingEntity) {
            ResourceLocation resourcelocation = livingEntity.getLootTable();
            LootTable loottable = this.level.getServer().getLootData().getLootTable(resourcelocation);
            LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)this.level)).withParameter(
                            LootContextParams.THIS_ENTITY, livingEntity)
                    .withParameter(LootContextParams.ORIGIN, livingEntity.position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, level.damageSources().cactus())
                    .withOptionalParameter(LootContextParams.KILLER_ENTITY, null)
                    .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, null);
            LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
            loottable.getRandomItems(lootparams, livingEntity.getLootTableSeed(), this::spawnAtLocation);
        }
    }

    public void spawnAtLocation(ItemStack pStack)
    {
        ItemEntity itementity = new ItemEntity(this.level, worldPosition.getX() + 0.5d, worldPosition.getY(),
                worldPosition.getZ() + 0.5d, pStack);
        this.level.addFreshEntity(itementity);
    }
}
