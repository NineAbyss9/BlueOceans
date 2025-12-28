
package com.github.player_ix.ix_api.api.item;

import com.github.player_ix.ix_api.util.ItemUtil;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ApiAxe
extends AxeItem {
    public ApiAxe(int uses, float miningSpeed, float damage, int level, int EV, Ingredient craft, int attackDamage,
                  float attackSpeed, Properties p_43272_) {
        this(ItemUtil.getTier(uses, miningSpeed, damage, level, EV, craft), attackDamage, attackSpeed, p_43272_);
    }

    public ApiAxe(Tier tier, float damage, float speed, Properties p_40524_) {
        super(tier, damage, speed, p_40524_);
    }
}
