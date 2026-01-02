
package com.bilibili.player_ix.blue_oceans.common.entities.animal;

import com.github.player_ix.ix_api.api.mobs.FoodDataUser;
import com.github.player_ix.ix_api.api.mobs.MobFoodData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;

public class BoAnimal
extends Animal
implements FoodDataUser {
    protected final MobFoodData foodData;
    protected BoAnimal(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.foodData = this.createFoodData();
    }

    public MobFoodData foodData() {
        return this.foodData;
    }

    public ItemStack eat(Level pLevel, ItemStack pFood) {
        this.foodData.eat(pFood.getItem(), pFood, this);
        return super.eat(pLevel, pFood);
    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return (AgeableMob)this.getType().create(pLevel);
    }
}
