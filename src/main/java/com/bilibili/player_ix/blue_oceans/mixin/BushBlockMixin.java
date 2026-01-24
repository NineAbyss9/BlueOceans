
package com.bilibili.player_ix.blue_oceans.mixin;

import com.bilibili.player_ix.blue_oceans.init.BoTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BushBlock.class)
public abstract class BushBlockMixin extends Block {
    private BushBlockMixin(Properties pP) {
        super(pP);
    }

    @Inject(method = "mayPlaceOn", at = @At("HEAD"), cancellable = true)
    private void mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos,
                            CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(pState.is(BoTags.BUSHES_MAY_PLACE_ON));
    }
}
