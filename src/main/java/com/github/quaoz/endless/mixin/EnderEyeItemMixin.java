package com.github.quaoz.endless.mixin;

import com.github.quaoz.endless.Endless;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
public abstract class EnderEyeItemMixin {

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		if (!Endless.get().config.enderEyeActivation()) {
			if (Endless.get().config.isVerbose()) {
				Endless.get().log("Player " + context.getPlayer().getName().getString() + " (" + context.getPlayer().getUuidAsString() + ") attempted to activate portal at [" + context.getBlockPos().toShortString() + "]");
			}

			cir.setReturnValue(ActionResult.PASS);
		}
	}
}
