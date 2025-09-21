package com.yshs.jeiae2compact.mixin;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.crafting.CraftConfirmScreen;
import appeng.client.gui.style.ScreenStyle;
import appeng.menu.me.crafting.CraftConfirmMenu;
import com.yshs.jeiae2compact.feat.BookmarkMissing;
import com.yshs.jeiae2compact.gui.button.BookmarkMissingButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftConfirmScreen.class)
public abstract class CraftConfirmScreenMixin extends AEBaseScreen<CraftConfirmMenu> {
    public CraftConfirmScreenMixin(CraftConfirmMenu menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addButton(CraftConfirmMenu menu, Inventory playerInventory, Component title, ScreenStyle style, CallbackInfo ci) {
        this.addToLeftToolbar(new BookmarkMissingButton(button -> BookmarkMissing.run(this.menu)));
    }
}
