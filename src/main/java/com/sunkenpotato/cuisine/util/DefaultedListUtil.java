package com.sunkenpotato.cuisine.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class DefaultedListUtil {

    public static DefaultedList<ItemStack> listWithoutEmpty(DefaultedList<ItemStack> list) {
        DefaultedList<ItemStack> tmp = DefaultedList.of();

        for (var e : list) {
            if (e.isEmpty()) continue;
            tmp.add(e);
        }

        return tmp;
    }
}
