package com.github.wohaopa.GTNHModify.tweakers.gt;

import com.github.wohaopa.GTNHModify.tweakers.ITweaker;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

public abstract class GT_RecipeTweaker extends ITweaker {

    /**
     * 遍历所有recipeMap, 调用modifyGT_Recipe和modifyGT_Recipe_AssemblyLine进行修改
     */
    @Override
    public void apply() {
        RecipeMap.ALL_RECIPE_MAPS.forEach((s, recipeMap) -> {  // 遍历所有recipeMap
            if (!recipeMap.unlocalizedName.equals("gg.recipe.naquadah_reactor"))
                recipeMap.getAllRecipes().forEach(this::modifyGT_Recipe); // lambda表达式 将forEach到的GTRecipe传入modifyGT_Recipe
        });
        GTRecipe.RecipeAssemblyLine.sAssemblylineRecipes.forEach(this::modifyGT_Recipe_AssemblyLine);
    }

    protected abstract void modifyGT_Recipe(GTRecipe aRecipe);

    protected abstract void modifyGT_Recipe_AssemblyLine(GTRecipe.RecipeAssemblyLine aRecipe);
}
