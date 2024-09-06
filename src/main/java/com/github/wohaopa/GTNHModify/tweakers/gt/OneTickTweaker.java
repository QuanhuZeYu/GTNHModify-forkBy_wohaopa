package com.github.wohaopa.GTNHModify.tweakers.gt;

import gregtech.api.util.GT_Recipe;

public class OneTickTweaker extends GT_RecipeTweaker {

    Integer integer = 0;

    @Override
    protected void modifyGT_Recipe(GT_Recipe aRecipe) {
        if (aRecipe.mDuration > integer) aRecipe.mDuration = integer;
    }

    @Override
    protected void modifyGT_Recipe_AssemblyLine(GT_Recipe.GT_Recipe_AssemblyLine aRecipe) {
        if (aRecipe.mDuration > integer) aRecipe.mDuration = integer;
    }

    @Override
    public Object getSettings() {
        return integer;
    }
}
