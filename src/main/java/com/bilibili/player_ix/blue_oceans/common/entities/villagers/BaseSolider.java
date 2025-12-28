
package com.bilibili.player_ix.blue_oceans.common.entities.villagers;

import com.bilibili.player_ix.blue_oceans.api.mob.Profession;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.behavior.GunAttackBehavior;
import com.bilibili.player_ix.blue_oceans.init.BlueOceansItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.nine_abyss.util.function.FunctionCollector;

public class BaseSolider
extends BaseVillager {
    public BaseSolider(EntityType<? extends BaseSolider> pType, Level level) {
        super(pType, level);
        this.populateItems();
    }

    protected void populateItems() {
        this.setMainHandItem(BlueOceansItems.SNIPER_RIFLE.get());
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
    }

    public void registerBehaviors() {
        this.behaviorSelector.addBehavior(1, new GunAttackBehavior(this,
                1.0, 30F));
        super.registerBehaviors();
        this.behaviorSelector.addBehavior(4, new MoveThroughVillageBehavior(this, 0.8,
                true, 10, FunctionCollector.positiveSupplier()));
    }

    public Profession getProfession() {
        return Profession.SOLIDER;
    }

    public static AttributeSupplier createAttributes() {
        return createBaseVillagerAttributes().add(Attributes.ARMOR, 2).build();
    }
}
