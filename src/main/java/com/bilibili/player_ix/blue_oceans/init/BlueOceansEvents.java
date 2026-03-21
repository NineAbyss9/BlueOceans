
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.entities.ai.goal.AttackModVillagersGoal;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.AbstractRedPlumMob;
import com.bilibili.player_ix.blue_oceans.common.entities.red_plum.IPlumSpreader;
import com.bilibili.player_ix.blue_oceans.world.spawner.VillagerGroupSpawner;
import com.google.common.collect.Maps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.NineAbyss9.math.MathSupport;

import java.util.Map;

@Mod.EventBusSubscriber(modid = BlueOceans.MOD_ID)
public class BlueOceansEvents
{
    private BlueOceansEvents()
    {
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        Entity attacker = event.getSource().getEntity();
        //LivingEntity entity = event.getEntity();
        if (attacker != null) {
            Level level = attacker.level();
            if (attacker instanceof AbstractRedPlumMob) {
                var spreaders = level.getEntitiesOfClass(AbstractRedPlumMob.class, attacker.getBoundingBox()
                        .inflate(10), entity1 -> entity1 instanceof IPlumSpreader);
                if (!spreaders.isEmpty()) {
                    spreaders.stream().filter(spreader -> spreader.getInfectLevel() < 6)
                            .findAny().ifPresent(AbstractRedPlumMob::setInfectLevelPlus);
                }
            }
        }
    }

    public static final Map<ServerLevel, VillagerGroupSpawner> VILLAGER_GROUPS
            = Maps.newHashMap();

    //private static final Set<Government> GOVERNMENTS = new HashSet<>(Set.of(Government.EMPTY,
    //        Government.EVIL_FACTION));

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event)
    {
        if (!event.getLevel().isClientSide()) {
            VILLAGER_GROUPS.put((ServerLevel)event.getLevel(), new VillagerGroupSpawner());
        }
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event)
    {
        /*if (!GOVERNMENTS.isEmpty()) {
            GOVERNMENTS.forEach(Government::tick);
        }*/
        if (!event.level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel)event.level;
            VillagerGroupSpawner spawner = VILLAGER_GROUPS.get(serverLevel);
            if (spawner != null)
                spawner.tick(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event)
    {
        if (!event.getLevel().isClientSide()) {
            VILLAGER_GROUPS.remove((ServerLevel)event.getLevel());
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinLevelEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof AbstractIllager illager) {
            illager.targetSelector.addGoal(3, new AttackModVillagersGoal(illager, true));
        }
    }

    @SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Pre event)
    {
        if (event.getLevel().getBlockState(event.getPos().below()).is(BoTags.BARREN_FARMLANDS)
                && MathSupport.random.nextFloat() < 0.3F) {
            event.setResult(Event.Result.DENY);
        }
    }
}
