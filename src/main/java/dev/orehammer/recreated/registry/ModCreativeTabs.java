package dev.orehammer.recreated.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

public final class ModCreativeTabs {
    private ModCreativeTabs() {}

    public static void register() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> output.accept(ModItems.CRUSHING_HAMMER));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> {
            output.accept(ModItems.RAW_IRON_DUST);
            output.accept(ModItems.RAW_GOLD_DUST);
            output.accept(ModItems.RAW_COPPER_DUST);
            output.accept(ModItems.RAW_SILVER_DUST);
            output.accept(ModItems.RAW_ZINC_DUST);
            output.accept(ModItems.RAW_TIN_DUST);
        });
    }
}
