package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.wohaopa.GTNHModify.tweakers.handler.GregTechHandler;

import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.common.tileentities.machines.multi.MTEDrillerBase;

@Mixin(value = MTEDrillerBase.class, remap = false)
public abstract class GT_MetaTileEntity_DrillerBaseMixin {

    @Inject(
        method = "checkProcessing",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/common/tileentities/machines/multi/MTEDrillerBase;setElectricityStats()V", // "Lgregtech/common/tileentities/machines/multi/MTEDrillerBase;setElectricityStats()V"
            shift = At.Shift.AFTER))
    private void injected(CallbackInfoReturnable<CheckRecipeResult> cir) {

        ((MTEDrillerBase) ((Object) this)).mMaxProgresstime = GregTechHandler.instance
            .handle(this, ((MTEDrillerBase) ((Object) this)).mMaxProgresstime);
    }
}
