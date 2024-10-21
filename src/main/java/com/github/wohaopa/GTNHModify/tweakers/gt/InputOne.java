package com.github.wohaopa.GTNHModify.tweakers.gt;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GTRecipe;

public class InputOne extends GT_RecipeTweaker {

    int integer = 1;

    @Override
    protected void modifyGT_Recipe(GTRecipe aRecipe) {
        if (aRecipe.mInputs != null) {
            for (ItemStack itemStack : aRecipe.mInputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > integer) itemStack.stackSize = integer;
                }
            }
        }
        if (aRecipe.mFluidInputs != null) {
            for (FluidStack fluidStack : aRecipe.mFluidInputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > integer) fluidStack.amount = integer;
                }
            }
        }
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GTRecipe.RecipeAssemblyLine aRecipe) {
        if (aRecipe.mInputs != null) {
            for (ItemStack itemStack : aRecipe.mInputs) {
                if (itemStack != null) {
                    if (itemStack.stackSize > integer) itemStack.stackSize = integer;
                }
            }
        }
        if (aRecipe.mFluidInputs != null) {
            for (FluidStack fluidStack : aRecipe.mFluidInputs) {
                if (fluidStack != null) {
                    if (fluidStack.amount > integer) fluidStack.amount = integer;
                }
            }
        }
    }

    @Override
    public Object getSettings() {
        return integer;
    }

    @Override
    public void setSetting(Object s) {
        integer = (int) s;
    }
}
