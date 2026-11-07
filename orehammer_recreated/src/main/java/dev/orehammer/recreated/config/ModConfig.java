package dev.orehammer.recreated.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.orehammer.recreated.OreHammerRecreated;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Configuracion minima: config/orehammer_recreated.json */
public final class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static int hammerDamagePerCraft = 1;

    private ModConfig() {}

    public static int hammerDamagePerCraft() {
        return hammerDamagePerCraft;
    }

    public static void load() {
        Path file = FabricLoader.getInstance().getConfigDir().resolve(OreHammerRecreated.MOD_ID + ".json");
        try {
            if (Files.exists(file)) {
                JsonObject o = GSON.fromJson(Files.readString(file), JsonObject.class);
                if (o != null && o.has("hammer_damage_per_craft")) {
                    hammerDamagePerCraft = Math.max(1, o.get("hammer_damage_per_craft").getAsInt());
                }
            } else {
                JsonObject o = new JsonObject();
                o.addProperty("hammer_damage_per_craft", hammerDamagePerCraft);
                Files.writeString(file, GSON.toJson(o));
            }
        } catch (IOException | RuntimeException e) {
            OreHammerRecreated.LOGGER.warn("Could not read config, using defaults", e);
        }
    }
}
