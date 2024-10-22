package com.github.wohaopa.GTNHModify.mixins.late.gregtech;

import com.github.wohaopa.GTNHModify.tweakers.handler.TreeFarmHandler;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.MTETreeFarm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = MTETreeFarm.class, remap = false)
public class GTETreeFarmMixin {
    @ModifyVariable(
        method = "useToolForMode",
        at = @At(
            value = "HEAD"
        ),
        argsOnly = true,
        remap = false
    )
    private boolean modify(boolean a) {
        int bool;
        if (a==true) { // 当原值为消耗时
            bool = TreeFarmHandler.instance.handle(this, 0);
        } else {
            bool = TreeFarmHandler.instance.handle(this, 1);
        }
        return bool == 0; // 开启时返回原值
    }
}
