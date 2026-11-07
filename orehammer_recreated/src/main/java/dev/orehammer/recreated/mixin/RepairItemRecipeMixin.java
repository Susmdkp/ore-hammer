package dev.orehammer.recreated.mixin;

import dev.orehammer.recreated.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** El martillo solo se repara en el yunque con lingotes de cobre, no fusionando dos martillos. */
@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private void orehammer_recreated$blockHammer(CraftingInput input, Level level, CallbackInfoReturnable<Boolean> cir) {
        for (int i = 0; i < input.size(); i++) {
            ItemStack s = input.getItem(i);
            if (s.is(ModItems.CRUSHING_HAMMER)) { cir.setReturnValue(false); return; }
        }
    }
}
