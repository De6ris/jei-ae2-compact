package com.yshs.jeiae2compact.localization;

import appeng.core.localization.LocalizationEnum;
import net.minecraft.network.chat.Component;

public enum TooltipText implements LocalizationEnum {
    BookmarkMissing("Bookmark Missing"),
    ;

    private final String englishText;
    private final Component text;

    TooltipText(String englishText) {
        this.englishText = englishText;
        this.text = Component.translatable(getTranslationKey());
    }

    public String getTranslationKey() {
        return "gui.jeiae2c." + this.name();
    }

    public String getEnglishText() {
        return this.englishText;
    }

    public String getLocal() {
        return this.text.getString();
    }
}
