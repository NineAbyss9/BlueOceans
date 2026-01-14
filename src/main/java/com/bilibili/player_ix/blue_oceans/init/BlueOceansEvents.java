
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.AlcoholLamp;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.AttackModVillagersGoal;
import com.bilibili.player_ix.blue_oceans.common.mob_effect.ConfigurableDamageBoost;
import com.bilibili.player_ix.blue_oceans.config.BlueOceansConfig;
import com.bilibili.player_ix.blue_oceans.events.HandleConfigValueEvent;
import com.bilibili.player_ix.blue_oceans.government.Government;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = BlueOceans.MOD_ID)
public class BlueOceansEvents {
    private BlueOceansEvents() {}

    public static void onLivingDeath(LivingDeathEvent event) {
        Entity pAttacker = event.getSource().getEntity();
        LivingEntity entity = event.getEntity();
    }

    private static final Set<Government> GOVERNMENTS = Sets.newLinkedHashSet(
            Sets.newHashSet(Government.EMPTY, Government.EVIL_FACTION));

    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (!GOVERNMENTS.isEmpty()) {
            GOVERNMENTS.forEach(Government::tick);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractIllager illager) {
            illager.targetSelector.addGoal(3, new AttackModVillagersGoal(illager, true));
        }
    }

    @SubscribeEvent
    public static void onHandleConfigValues(HandleConfigValueEvent event) {
        AlcoholLamp.setFireDamage(BlueOceansConfig.Common.ALCOHOL_LAMP_DAMAGE.get().floatValue());
        ConfigurableDamageBoost.value = BlueOceansConfig.Common.DAMAGE_BOOST_PLUS_VALUE.get()
                .doubleValue();
    }
}
