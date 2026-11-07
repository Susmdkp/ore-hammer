package dev.orehammer.recreated.registry;

import dev.orehammer.recreated.OreHammerRecreated;
import dev.orehammer.recreated.recipe.CrushingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class ModRecipes {
    public static final RecipeSerializer<CrushingRecipe> CRUSHING_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            Identifier.fromNamespaceAndPath(OreHammerRecreated.MOD_ID, "crushing"),
            new RecipeSerializer<>(CrushingRecipe.CODEC, CrushingRecipe.STREAM_CODEC));

    private ModRecipes() {}

    public static void register() {}
}
