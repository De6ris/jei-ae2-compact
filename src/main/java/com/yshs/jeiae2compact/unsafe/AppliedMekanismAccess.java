package com.yshs.jeiae2compact.unsafe;

import appeng.api.stacks.AEKey;
import me.ramidzkh.mekae2.ae2.MekanismKey;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mezz.jei.api.ingredients.ITypedIngredient;
import org.jetbrains.annotations.Nullable;

public class AppliedMekanismAccess {
    @Nullable
    public static AEKey makeAEKey(ITypedIngredient<?> ingredient) {
        return ingredient.getIngredient(MekanismJEI.TYPE_CHEMICAL).map(MekanismKey::of).orElse(null);
    }
}
