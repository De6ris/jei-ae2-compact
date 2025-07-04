package com.yshs.jeiae2compact.unsafe;

import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mezz.jei.api.ingredients.IIngredientType;

public class MekanismAccess {
    public static boolean isChemical(IIngredientType<?> type) {
        return type == MekanismJEI.TYPE_CHEMICAL;
    }
}
