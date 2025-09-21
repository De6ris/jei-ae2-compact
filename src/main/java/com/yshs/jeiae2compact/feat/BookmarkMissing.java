package com.yshs.jeiae2compact.feat;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.menu.me.crafting.CraftConfirmMenu;
import appeng.menu.me.crafting.CraftingPlanSummary;
import appeng.menu.me.crafting.CraftingPlanSummaryEntry;
import com.yshs.jeiae2compact.JEIAE2Compact;
import com.yshs.jeiae2compact.compat.ModReference;
import com.yshs.jeiae2compact.jei.JEIAE2CompactPlugin;
import com.yshs.jeiae2compact.unsafe.AppliedMekanismAccess;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IBookmarkOverlay;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.gui.bookmarks.BookmarkList;
import mezz.jei.gui.bookmarks.IngredientBookmark;
import mezz.jei.gui.overlay.bookmarks.BookmarkOverlay;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.List;

public class BookmarkMissing {
    private static boolean setup = false;
    @Nullable
    private static BookmarkList bookmarkList = null;

    public static void run(CraftConfirmMenu menu) {
        CraftingPlanSummary plan = menu.getPlan();
        if (plan == null) return;
        setupReflection();
        if (bookmarkList == null) return;
        List<CraftingPlanSummaryEntry> entries = plan.getEntries();
        IIngredientManager ingredientManager = JEIAE2CompactPlugin.getJeiRuntime().getIngredientManager();
        for (CraftingPlanSummaryEntry entry : entries) {
            if (entry.getMissingAmount() > 0) {
                bookmark(bookmarkList, ingredientManager, entry.getWhat());
            }
        }
    }

    private static void setupReflection() {
        if (setup) return;
        setup = true;
        IJeiRuntime jeiRuntime = JEIAE2CompactPlugin.getJeiRuntime();
        IBookmarkOverlay bookmarkOverlay = jeiRuntime.getBookmarkOverlay();
        Class<BookmarkOverlay> clazz = BookmarkOverlay.class;
        try {
            Field field = clazz.getDeclaredField("bookmarkList");
            field.trySetAccessible();
            Object o = field.get(bookmarkOverlay);
            bookmarkList = (BookmarkList) o;
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            JEIAE2Compact.LOGGER.warn("Bookmark reflection failure");
        }
    }

    private static void bookmark(BookmarkList bookmarkList, IIngredientManager ingredientManager, AEKey what) {
        ITypedIngredient<?> typedIngredient = makeIngredient(ingredientManager, what);
        if (typedIngredient == null) return;
        bookmarkList.add(IngredientBookmark.create(typedIngredient, ingredientManager));
    }

    @Nullable
    private static ITypedIngredient<?> makeIngredient(IIngredientManager ingredientManager, AEKey what) {
        AEKeyType type = what.getType();
        Object o = null;
        if (type == AEKeyType.items()) {
            o = ((AEItemKey) what).getReadOnlyStack();
        } else if (type == AEKeyType.fluids()) {
            o = ((AEFluidKey) what).toStack(1000);// FluidStack yes, Fluid no
        } else if (ModReference.hasMod(ModReference.AppliedMekanism) && AppliedMekanismAccess.isChemicalKey(type)) {
            o = AppliedMekanismAccess.getChemical(what);
        }
        if (o == null) return null;
        return ingredientManager.createTypedIngredient(o).orElse(null);
    }
}
