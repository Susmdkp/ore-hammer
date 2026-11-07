package dev.orehammer.recreated.registry;

import dev.orehammer.recreated.OreHammerRecreated;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;
import java.util.function.Function;

public final class ModItems {
    /** Durabilidad del martillo (especificacion: 74). */
    public static final int HAMMER_DURABILITY = 74;

    public static final Item CRUSHING_HAMMER = register("crushing_hammer", Item::new,
            new Item.Properties()
                    .durability(HAMMER_DURABILITY)
                    .repairable(Items.COPPER_INGOT)   // yunque: lingote de cobre. Sin componente "enchantable" -> no encantable.
                    .component(DataComponents.LORE, new ItemLore(List.of(
                            tip("item.orehammer_recreated.crushing_hammer.tooltip1"),
                            tip("item.orehammer_recreated.crushing_hammer.tooltip2"),
                            tip("item.orehammer_recreated.crushing_hammer.tooltip3")))));

    public static final Item RAW_IRON_DUST = register("raw_iron_dust", Item::new, new Item.Properties());
    public static final Item RAW_GOLD_DUST = register("raw_gold_dust", Item::new, new Item.Properties());
    public static final Item RAW_COPPER_DUST = register("raw_copper_dust", Item::new, new Item.Properties());
    public static final Item RAW_SILVER_DUST = register("raw_silver_dust", Item::new, new Item.Properties());
    public static final Item RAW_ZINC_DUST = register("raw_zinc_dust", Item::new, new Item.Properties());
    public static final Item RAW_TIN_DUST = register("raw_tin_dust", Item::new, new Item.Properties());

    private static Component tip(String key) {
        return Component.translatable(key).withStyle(s -> s.withItalic(false).withColor(ChatFormatting.GRAY));
    }

    private ModItems() {}

    private static Item register(String name, Function<Item.Properties, Item> factory, Item.Properties props) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(OreHammerRecreated.MOD_ID, name));
        Item item = factory.apply(props.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    /** Fuerza la carga de la clase (los campos estaticos registran los objetos). */
    public static void register() {}
}
