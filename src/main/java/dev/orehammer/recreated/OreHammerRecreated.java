package dev.orehammer.recreated;

import dev.orehammer.recreated.config.ModConfig;
import dev.orehammer.recreated.registry.ModCreativeTabs;
import dev.orehammer.recreated.registry.ModItems;
import dev.orehammer.recreated.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OreHammerRecreated implements ModInitializer {
    public static final String MOD_ID = "orehammer_recreated";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfig.load();
        ModItems.register();
        ModRecipes.register();
        ModCreativeTabs.register();
        LOGGER.info("Ore Hammer Recreated loaded (hammer damage per craft: {})", ModConfig.hammerDamagePerCraft());
    }
}
