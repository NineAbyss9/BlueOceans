
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.AttackModVillagersGoal;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.IPlumSpreader;
import com.bilibili.player_ix.blue_oceans.government.Government;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = BlueOceans.MOD_ID)
public class BlueOceansEvents {
    private BlueOceansEvents() {}

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity attacker = event.getSource().getEntity();
        //LivingEntity entity = event.getEntity();
        if (attacker != null) {
            Level level = attacker.level();
            if (attacker instanceof AbstractRedPlumMob) {
                var spreaders = level.getEntitiesOfClass(AbstractRedPlumMob.class, attacker.getBoundingBox()
                        .inflate(10), entity1 -> entity1 instanceof IPlumSpreader);
                if (!spreaders.isEmpty()) {
                    spreaders.forEach(AbstractRedPlumMob::setInfectLevelPlus);
                }
            }
        }
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

    /*@SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Pre event) {
        if (event.getLevel().getBlockState(event.getPos().below()).is(BoTags.BARREN_FARMLANDS)
                && MathSupport.random.nextFloat() < 0.3F) {
            event.setResult(Event.Result.DENY);
        }
    }*/
}
