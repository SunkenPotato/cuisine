package com.sunkenpotato.cuisine.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sunkenpotato.cuisine.Cuisine;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;


public class PotRecipe implements Recipe<PotRecipeInput> {

    public final ItemStack output;
    private final DefaultedList<Ingredient> ingredients;
    // TODO private final Ingredient base;

    public PotRecipe(List<Ingredient> ingredients, ItemStack is) {
        this.output = is;
        this.ingredients = (DefaultedList<Ingredient>) ingredients;
    }

    @Override
    public boolean matches(PotRecipeInput input, World world) {

        if (world.isClient()) return false;

        return true;
    }

    @Override
    public ItemStack craft(PotRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {

        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.ingredients.size());

        list.addAll(this.ingredients);

        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return PotRecipeType.INSTANCE;
    }

    public static final class PotRecipeType implements RecipeType<PotRecipe> {
        public static final PotRecipeType INSTANCE = new PotRecipeType();
        public static final String ID = "cooking";

        public String toString() {
            return ID;
        }
    }

    @SuppressWarnings({"unused", "SameParameterValue"})
    public static final class Serializer implements RecipeSerializer<PotRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "cooking";

        private static final MapCodec<PotRecipe> CODEC = RecordCodecBuilder.mapCodec( instance -> instance.group( (Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")).flatXmap(ingredients -> {
            Ingredient[] ingredients2 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
            if (ingredients2.length == 0) {
                return DataResult.error(() -> "No ingredients for shapeless recipe");
            }
            if (ingredients2.length > 9) {
                return DataResult.error(() -> "Too many ingredients for shapeless recipe");
            }
            return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));

        }, DataResult::success).forGetter(PotRecipe::getIngredients),(ItemStack.VALIDATED_CODEC.fieldOf("result")).forGetter(recipe -> recipe.output)).apply(instance, PotRecipe::new));

        private static final PacketCodec<RegistryByteBuf, PotRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::write, Serializer::read);

        private static PotRecipe read(RegistryByteBuf registryByteBuf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(registryByteBuf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.PACKET_CODEC.decode(registryByteBuf));
            }

            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(registryByteBuf);

            Cuisine.LOGGER.info("read recipe: " + itemStack);

            return new PotRecipe(inputs, itemStack);
        }

        private static void write(RegistryByteBuf registryByteBuf, PotRecipe potRecipe) {
            registryByteBuf.writeInt(potRecipe.getIngredients().size());

            for (Ingredient ingredient : potRecipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(registryByteBuf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(registryByteBuf, potRecipe.getResult(null));
            Cuisine.LOGGER.info("wrote recipe");
        }

        @Override
        public MapCodec<PotRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, PotRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}