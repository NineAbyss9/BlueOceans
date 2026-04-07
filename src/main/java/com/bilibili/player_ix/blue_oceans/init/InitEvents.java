
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.capability.BoCapabilities;
import com.bilibili.player_ix.blue_oceans.common.capability.LivingHealth;
import com.bilibili.player_ix.blue_oceans.common.command.BoCommand;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = BlueOceans.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InitEvents
{
    private InitEvents()
    {
    }

    //Commands
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event)
    {
        BoCommand.register(event.getDispatcher(), event.getBuildContext());
    }

    @SubscribeEvent
    public static void addFuels(FurnaceFuelBurnTimeEvent event)
    {
        if (event.getItemStack().is(BlueOceansItems.Lignite.get()))
            event.setBurnTime(300);
    }

    //Capabilities start

    @SubscribeEvent
    public static void registerEntityCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof LivingEntity living)
        {
            event.addCapability(LivingHealth.RESOURCE, createProvider(LazyOptional.of(() -> new LivingHealth(living)),
                    BoCapabilities.LIVING_HEALTH));
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event)
    {
        if (event.getEntity().level().isClientSide)
        {
            return;
        }
        event.getEntity().getCapability(BoCapabilities.LIVING_HEALTH).ifPresent(LivingHealth::tick);
    }

    public static <S extends Tag, T extends INBTSerializable<S>> ICapabilitySerializable<S>
    createProvider(LazyOptional<T> pInstance, Capability<T> pC)
    {
        return new ICapabilitySerializable<>()
        {
            public <C> LazyOptional<C> getCapability(Capability<C> cap, @Nullable Direction side)
            {
                return pC.orEmpty(cap, pInstance.cast());
            }

            public S serializeNBT()
            {
                return pInstance.orElseThrow(NullPointerException::new).serializeNBT();
            }

            public void deserializeNBT(S nbt)
            {
                pInstance.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
            }
        };
    }
}
