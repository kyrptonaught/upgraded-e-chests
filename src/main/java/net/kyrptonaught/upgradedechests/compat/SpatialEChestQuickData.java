package net.kyrptonaught.upgradedechests.compat;

import net.kyrptonaught.quickshulker.QuickShulkerMod;
import net.kyrptonaught.quickshulker.api.QuickShulkerData;
import net.kyrptonaught.upgradedechests.inv.SpatialEChestInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.function.BiConsumer;

public class SpatialEChestQuickData extends QuickShulkerData.QuickEnderData {
    public SpatialEChestQuickData(BiConsumer<PlayerEntity, ItemStack> openConsumer, Boolean supportsBundleing) {
        super(openConsumer, supportsBundleing);
    }

    public Inventory getInventory(PlayerEntity player, ItemStack stack) {
        if (!QuickShulkerMod.getConfig().quickEChest)
            return null;
        return new SpatialEChestInventory(player);
    }
}
