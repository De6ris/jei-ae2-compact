package com.yshs.jeiae2compact.compat;

import net.neoforged.fml.ModList;

public class ModReference {
    public static final String Mekanism = "mekanism";
    public static final String AppliedMekanism = "appmek";

    public static boolean hasMod(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
