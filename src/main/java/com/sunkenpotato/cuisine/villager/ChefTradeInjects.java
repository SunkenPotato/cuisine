package com.sunkenpotato.cuisine.villager;

import com.sunkenpotato.cuisine.crop.CropRegistry;
import com.sunkenpotato.cuisine.item.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;

public class ChefTradeInjects {

    public static void registerTrades() {
        levelOneTrades();
    }

    private static void levelOneTrades() {
        TradeOfferHelper.registerVillagerOffers(Villagers.CHEF, 1, factories -> {
                factories.add((entity, random) -> new TradeOffer(
                                    new TradedItem(Items.EMERALD, 2),
                                    new ItemStack(ItemRegistry.GROUND_SALT, 5),
                                    10,
                                    random.nextInt(2),
                                    1.0f)

                );
                factories.add(((entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 1),
                            new ItemStack(CropRegistry.BASIL),
                            15,
                            random.nextInt(2),
                            1.0f)
                ));
                factories.add(((entity, random) -> new TradeOffer(
                            new TradedItem(Items.EMERALD, 2),
                            new ItemStack(CropRegistry.ROSEMARY),
                            15,
                            random.nextInt(2),
                            1.0f
                )));
        });
    }

}
