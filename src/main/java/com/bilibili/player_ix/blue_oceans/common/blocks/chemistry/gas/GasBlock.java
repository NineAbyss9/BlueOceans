
package com.bilibili.player_ix.blue_oceans.common.blocks.chemistry.gas;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Element;
import com.bilibili.player_ix.blue_oceans.common.chemistry.IElement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.NineAbyss9.util.pair.Pair;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class GasBlock
extends Block
implements IElement
{
    protected final Element element;
    protected final float density;
    protected final float dissipationChance;
    protected final List<MobEffectInstance> effectInstances;
    protected final boolean suffocating;
    protected final int color;
    protected final Pair<Level.ExplosionInteraction, Float> flammability;
    public GasBlock(Properties pProperties, Element elementIn, float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn,
                    boolean suffocatingIn, Pair<Level.ExplosionInteraction,Float> flammabilityIn, int colorIn)
    {
        super(pProperties);
        this.element = elementIn;
        this.density = densityIn;
        this.dissipationChance = dissipationChanceIn;
        this.effectInstances = effectInstancesIn;
        this.suffocating = suffocatingIn;
        this.flammability = flammabilityIn;
        this.color = colorIn;
    }

    protected void addEffectToEntity(Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntity)
    {

    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentState, Direction pDirection)
    {
        if (pAdjacentState.getBlock() == this)
            return true;
        return super.skipRendering(pState, pAdjacentState, pDirection);
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext)
    {
        return true;
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity)
    {
        if (pEntity instanceof LivingEntity)
            this.addEffectToEntity(pLevel, pState, pPos, (LivingEntity)pEntity);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return Shapes.empty();
    }

    public Element getElement()
    {
        return element;
    }

    public static Supplier<GasBlock> simpleSupplier(GasEnum gasEnum) {
        return () -> new GasBlock(Properties.of(), gasEnum.getElement(), gasEnum.getDensity(), gasEnum.getDissipationRate(),
                gasEnum.getEffects(), gasEnum.isSuffocating(), gasEnum.getFlammability(), gasEnum.getColor());
    }

    public static Supplier<GasBlock> simpleSupplier(Element elementIn, float densityIn, float dissipationChanceIn,
                                                    List<MobEffectInstance> effectInstancesIn,
                                                    boolean suffocatingIn, Pair<Level.ExplosionInteraction,Float> flammabilityIn, int colorIn) {
        return () -> new GasBlock(Properties.of().air().noCollission().noLootTable(), elementIn, densityIn, dissipationChanceIn,
                effectInstancesIn, suffocatingIn, flammabilityIn, colorIn);
    }
}
