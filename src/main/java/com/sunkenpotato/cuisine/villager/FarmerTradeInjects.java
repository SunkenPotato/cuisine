package com.sunkenpotato.cuisine.villager;

import com.sunkenpotato.cuisine.crop.CropRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;

public class FarmerTradeInjects {

    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add(new RosemaryOffer());
        });
    }

    public static class RosemaryOffer implements TradeOffers.Factory {

        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {
            ItemStack rosemaryStack = new ItemStack(CropRegistry.ROSEMARY, 5);
            TradedItem emeraldStack = new TradedItem(Items.EMERALD, 1);


            return new TradeOffer(emeraldStack, rosemaryStack, 20, 1, 1);
        }
    }

}
