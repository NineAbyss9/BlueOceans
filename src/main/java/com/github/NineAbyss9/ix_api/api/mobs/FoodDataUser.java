
package com.github.NineAbyss9.ix_api.api.mobs;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public interface FoodDataUser {
    MobFoodData foodData();

    default MobFoodData createFoodData() {
        return new MobFoodData((Mob)this);
    }

    default void eat(LivingEntity pEntity) {
        this.foodData().eat(pEntity);
    }
}
