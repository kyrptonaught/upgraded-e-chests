package net.kyrptonaught.upgradedechests.compat;

import net.kyrptonaught.quickshulker.QuickShulkerMod;
import net.kyrptonaught.quickshulker.api.QuickOpenableRegistry;
import net.kyrptonaught.quickshulker.api.QuickShulkerData;
import net.kyrptonaught.quickshulker.api.RegisterQuickShulker;
import net.kyrptonaught.upgradedechests.block.RiftEChest;
import net.kyrptonaught.upgradedechests.block.SpatialEChest;
import net.kyrptonaught.upgradedechests.inv.SpatialEChestInventory;
import net.kyrptonaught.upgradedechests.util.ContainerNames;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;

public class Quickshulker implements RegisterQuickShulker {
    @Override
    public void registerProviders() {
        if (QuickShulkerMod.getConfig().quickEChest) {
            SpatialEChestQuickData spatData = new SpatialEChestQuickData((player, stack) -> {
                SpatialEChestInventory inv = new SpatialEChestInventory(player);
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                    return GenericContainerScreenHandler.createGeneric9x6(i, playerInventory, inv);
                }, ContainerNames.SPATIAL_CHEST));
            }, true);

            QuickOpenableRegistry.register(SpatialEChest.class, spatData);
            QuickShulkerData.QuickEnderData riftData = new QuickShulkerData.QuickEnderData((player, stack) -> {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                    return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, player.getEnderChestInventory());
                }, ContainerNames.getRiftChestName(player.getName())));
            }, true);

            QuickOpenableRegistry.register(RiftEChest.class, riftData);
        }
    }
}
