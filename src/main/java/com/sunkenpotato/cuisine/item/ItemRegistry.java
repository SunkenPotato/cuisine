package com.sunkenpotato.cuisine.item;

import com.sunkenpotato.cuisine.Cuisine;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry {

    public static final Item SALT_STONE = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "salt_stone"), new Item(new Item.Settings()));
    public static final Item GROUND_SALT = Registry.register(Registries.ITEM, Identifier.of(Cuisine.MOD_ID, "ground_salt"), new Item(new Item.Settings()));

    public static void initialize() {

    }

}
