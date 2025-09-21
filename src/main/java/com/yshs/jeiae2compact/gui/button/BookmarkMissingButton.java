package com.yshs.jeiae2compact.gui.button;

import appeng.client.gui.Icon;
import appeng.client.gui.widgets.IconButton;
import com.yshs.jeiae2compact.localization.TooltipText;

public class BookmarkMissingButton extends IconButton {
    public BookmarkMissingButton(OnPress onPress) {
        super(onPress);
        this.setMessage(TooltipText.BookmarkMissing.text());
    }

    @Override
    protected Icon getIcon() {
        return Icon.PATTERN_ACCESS_SHOW;
    }
}
