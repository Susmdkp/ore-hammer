package dev.orehammer.recreated.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public final class ModCreativeTabs {
    private ModCreativeTabs() {}

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> entries.accept(ModItems.CRUSHING_HAMMER));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.accept(ModItems.RAW_IRON_DUST);
            entries.accept(ModItems.RAW_GOLD_DUST);
            entries.accept(ModItems.RAW_COPPER_DUST);
            entries.accept(ModItems.RAW_SILVER_DUST);
            entries.accept(ModItems.RAW_ZINC_DUST);
            entries.accept(ModItems.RAW_TIN_DUST);
        });
    }
}
