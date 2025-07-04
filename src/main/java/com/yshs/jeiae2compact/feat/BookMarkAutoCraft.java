package com.yshs.jeiae2compact.feat;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.client.gui.me.common.Repo;
import appeng.helpers.InventoryAction;
import appeng.menu.me.common.GridInventoryEntry;
import appeng.menu.me.common.MEStorageMenu;
import com.yshs.jeiae2compact.compat.ModReference;
import com.yshs.jeiae2compact.jei.JEIAE2CompactPlugin;
import com.yshs.jeiae2compact.unsafe.AppliedMekanismAccess;
import com.yshs.jeiae2compact.unsafe.MekanismAccess;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.runtime.IJeiRuntime;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BookMarkAutoCraft {
    public static void onPickItemClicked(MEStorageMenu menu, Repo repo) {
        // 获取JEI的运行时
        IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();
        // 得到书签覆盖层下面的材料
        Optional<ITypedIngredient<?>> ingredient = jeiRuntime.getBookmarkOverlay().getIngredientUnderMouse();
        if (ingredient.isEmpty()) return;

        AEKey key = makeAEKey(ingredient.get());
        if (key == null) {
            return;
        }

        // 遍历AE终端中的所有条目
        repo.getAllEntries().stream()
                // 过滤掉无法自动合成的条目
                .filter(GridInventoryEntry::isCraftable)
                // 过滤掉空值
                .filter(entry -> {
                    AEKey what = entry.getWhat();
                    if (what == null) return false;
                    return what.equals(key);
                })
                .findFirst()
                // 打开自动合成菜单
                .ifPresent(entry -> {
                    long serial = entry.getSerial();
                    menu.handleInteraction(serial, InventoryAction.AUTO_CRAFT);
                });
    }

    @Nullable
    private static AEKey makeAEKey(ITypedIngredient<?> ingredient) {
        IIngredientType<?> type = ingredient.getType();
        if (type == VanillaTypes.ITEM_STACK) {
            return ingredient.getIngredient(VanillaTypes.ITEM_STACK).map(AEItemKey::of).orElse(null);
        }
        if (type == NeoForgeTypes.FLUID_STACK) {
            return ingredient.getIngredient(NeoForgeTypes.FLUID_STACK).map(AEFluidKey::of).orElse(null);
        }
        if (ModReference.hasMod(ModReference.AppliedMekanism) && MekanismAccess.isChemical(type)) {
            return AppliedMekanismAccess.makeAEKey(ingredient);
        }
        return null;
    }

}
