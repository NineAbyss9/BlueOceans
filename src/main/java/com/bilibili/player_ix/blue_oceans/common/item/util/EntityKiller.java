
package com.bilibili.player_ix.blue_oceans.common.item.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EntityKiller extends Item {
    public EntityKiller() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    public int getUseDuration(ItemStack pStack) {
        return 1;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand hand) {
        if (!pLevel.isClientSide) {
            List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, pPlayer.getBoundingBox()
                    .inflate(99), entity -> !(entity instanceof Player));
            entities.forEach(entity -> entity.remove(Entity.RemovalReason.KILLED));
        }
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, hand);
    }

    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!(entity instanceof Player)) {
            entity.remove(Entity.RemovalReason.KILLED);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("info.blue_oceans.entity_killer"));
    }
}
