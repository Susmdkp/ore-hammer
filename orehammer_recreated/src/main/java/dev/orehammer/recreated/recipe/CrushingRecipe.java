package dev.orehammer.recreated.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.orehammer.recreated.config.ModConfig;
import dev.orehammer.recreated.registry.ModItems;
import dev.orehammer.recreated.registry.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Receta de trituracion: [ingredientes...] + Crushing Hammer.
 *  - ordered=false (defecto): sin forma.
 *  - ordered=true: una sola fila "ingredientes..., martillo" (o espejada), como la de Ancient Debris.
 * El martillo vuelve a la rejilla con {@link ModConfig#hammerDamagePerCraft()} de dano y se destruye al agotarse.
 *
 * JSON: {"type":"orehammer_recreated:crushing","ordered":false,"ingredients":["minecraft:iron_ore"],"result":"orehammer_recreated:raw_iron_dust","count":3}
 */
public class CrushingRecipe implements CraftingRecipe {
    public static final MapCodec<CrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(r -> r.ingredients),
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("result").forGetter(r -> r.result),
            ExtraCodecs.POSITIVE_INT.fieldOf("count").forGetter(r -> r.count),
            Codec.BOOL.optionalFieldOf("ordered", false).forGetter(r -> r.ordered)
    ).apply(i, CrushingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), r -> r.ingredients,
            ByteBufCodecs.registry(Registries.ITEM), r -> r.result,
            ByteBufCodecs.VAR_INT, r -> r.count,
            ByteBufCodecs.BOOL, r -> r.ordered,
            CrushingRecipe::new);

    private final List<Ingredient> ingredients;
    private final Item result;
    private final int count;
    private final boolean ordered;

    public CrushingRecipe(List<Ingredient> ingredients, Item result, int count, boolean ordered) {
        this.ingredients = List.copyOf(ingredients);
        this.result = result;
        this.count = count;
        this.ordered = ordered;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return ordered ? matchesOrdered(input) : matchesUnordered(input);
    }

    private boolean matchesUnordered(CraftingInput input) {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack s = input.getItem(i);
            if (!s.isEmpty()) stacks.add(s);
        }
        if (stacks.size() != ingredients.size() + 1) return false;

        boolean hammerFound = false;
        List<ItemStack> rest = new ArrayList<>();
        for (ItemStack s : stacks) {
            if (!hammerFound && s.is(ModItems.CRUSHING_HAMMER)) hammerFound = true;
            else rest.add(s);
        }
        if (!hammerFound) return false;

        for (Ingredient ing : ingredients) {
            boolean ok = false;
            for (int k = 0; k < rest.size(); k++) {
                if (ing.test(rest.get(k))) { rest.remove(k); ok = true; break; }
            }
            if (!ok) return false;
        }
        return rest.isEmpty();
    }

    private boolean matchesOrdered(CraftingInput input) {
        int w = input.width(), h = input.height();
        int minX = w, maxX = -1, minY = h, maxY = -1, n = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (!input.getItem(x + y * w).isEmpty()) {
                    n++;
                    minX = Math.min(minX, x); maxX = Math.max(maxX, x);
                    minY = Math.min(minY, y); maxY = Math.max(maxY, y);
                }
            }
        }
        int len = ingredients.size() + 1;
        if (n != len || minY != maxY || maxX - minX + 1 != len) return false;
        return rowMatches(input, minX, minY, false) || rowMatches(input, minX, minY, true);
    }

    /** Secuencia esperada: [ingredientes..., martillo]. */
    private boolean rowMatches(CraftingInput input, int x0, int y, boolean mirrored) {
        int len = ingredients.size() + 1;
        for (int i = 0; i < len; i++) {
            ItemStack s = input.getItem(x0 + i + y * input.width());
            int k = mirrored ? len - 1 - i : i;
            boolean ok = (k == len - 1) ? s.is(ModItems.CRUSHING_HAMMER) : ingredients.get(k).test(s);
            if (!ok) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        return new ItemStack(result, count);
    }

    /** El martillo vuelve con dano; el resto de ingredientes se consume. */
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> out = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack s = input.getItem(i);
            if (s.is(ModItems.CRUSHING_HAMMER)) {
                ItemStack hammer = s.copy();
                hammer.setCount(1);
                int damage = hammer.getDamageValue() + ModConfig.hammerDamagePerCraft();
                if (damage < hammer.getMaxDamage()) {
                    hammer.setDamageValue(damage);
                    out.set(i, hammer);
                } // si no: se rompe (queda vacio)
            }
        }
        return out;
    }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() { return ModRecipes.CRUSHING_SERIALIZER; }

    @Override
    public CraftingBookCategory category() { return CraftingBookCategory.MISC; }

    @Override
    public PlacementInfo placementInfo() { return PlacementInfo.NOT_PLACEABLE; }

    public boolean isSpecial() { return true; }
    public boolean showNotification() { return false; }
    public String group() { return ""; }
}
