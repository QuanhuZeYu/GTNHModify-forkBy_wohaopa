package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GTRecipe;

public class TenthsTweaker extends GT_RecipeTweaker {

    int integer = 10;

    @Override
    protected void modifyGT_Recipe(GTRecipe aRecipe) {
        aRecipe.mDuration /= integer;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GTRecipe.RecipeAssemblyLine aRecipe) {
        aRecipe.mDuration /= integer;
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
