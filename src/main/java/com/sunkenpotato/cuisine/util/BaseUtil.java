package com.sunkenpotato.cuisine.util;

import com.sunkenpotato.cuisine.recipe.Base;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;

public class BaseUtil {

    public static final RegistryEntry<Potion> WATER_POTION_ENTRY = Potions.WATER;

    public static boolean isItemOfBase(ItemStack item) {
         if (item.isOf(Items.GLASS_BOTTLE) && item.get(DataComponentTypes.POTION_CONTENTS).matches(WATER_POTION_ENTRY)) {
            return true;
        } else return item.isOf(Items.LAVA_BUCKET);
    }

    public static Base getBaseFromItem(ItemStack item) {
        if (item.isOf(Items.GLASS_BOTTLE) && item.get(DataComponentTypes.POTION_CONTENTS).matches(WATER_POTION_ENTRY)) {
            return Base.WATER;
        }
        else if (item.isOf(Items.LAVA_BUCKET)) {
            return Base.OIL;
        } else return Base.NULL;
    }

    public static int baseToInt(Base base) {
        return switch (base) {
            case WATER -> 0;
            case OIL -> 1;
            case NULL -> 2;
        };
    }
}
