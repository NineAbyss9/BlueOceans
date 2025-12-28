
package com.github.player_ix.ix_api.api.item;

import com.github.player_ix.ix_api.util.ItemUtil;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ApiSword extends SwordItem {
    public ApiSword(int uses, float miningSpeed, float damage, int level, int EV, Ingredient craft, int attackDamage,
                    float attackSpeed, Properties p_43272_) {
        this(ItemUtil.getTier(uses, miningSpeed, damage, level, EV, craft), attackDamage, attackSpeed, p_43272_);
    }

    public ApiSword(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }
}
