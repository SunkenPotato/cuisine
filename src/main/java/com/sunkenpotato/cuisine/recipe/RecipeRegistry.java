package com.sunkenpotato.cuisine.recipe;

import com.sunkenpotato.cuisine.Cuisine;
import com.sunkenpotato.cuisine.util.RegistryClass;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@RegistryClass
public class RecipeRegistry {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Cuisine.MOD_ID, PotRecipe.Serializer.ID),
                PotRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_TYPE, Identifier.of(Cuisine.MOD_ID, PotRecipe.PotRecipeType.ID),
                PotRecipe.PotRecipeType.INSTANCE);

        var x = Registries.RECIPE_TYPE.get(Identifier.of(Cuisine.MOD_ID, PotRecipe.PotRecipeType.ID));
        Cuisine.LOGGER.info("{}", x);

    }
}
