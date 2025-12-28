
package com.bilibili.player_ix.blue_oceans.init;

import com.bilibili.player_ix.blue_oceans.BlueOceans;
import com.bilibili.player_ix.blue_oceans.common.blocks.fluid.NuclearContaminatedWater;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlueOceansFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS,
            BlueOceans.MOD_ID);
    //Fluids start
    public static final RegistryObject<FlowingFluid> NUCLEAR_CONTAMINATED_WATER
            = FLUIDS.register("nuclear_contaminated_water", NuclearContaminatedWater.Source::new);
    public static final RegistryObject<FlowingFluid> NCW_FLOWING
            = FLUIDS.register("flowing_ncw", NuclearContaminatedWater.Flowing::new);
    //Fluids end

    //FluidTypes start
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(
            ForgeRegistries.Keys.FLUID_TYPES, BlueOceans.MOD_ID);
    public static final RegistryObject<FluidType> NUCLEAR_CONTAMINATED_WATER_TYPE
            = FLUID_TYPES.register("nuclear_contaminated_water", () -> new
            FluidType(FluidType.Properties.create().pathType(BlockPathTypes.LAVA)
            .canSwim(true).temperature(1).canDrown(true)));
    //FluidTypes end

    private BlueOceansFluids() {
    }


}
