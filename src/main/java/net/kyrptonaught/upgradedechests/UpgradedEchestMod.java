package net.kyrptonaught.upgradedechests;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.kyrptonaught.upgradedechests.block.RiftEChest;
import net.kyrptonaught.upgradedechests.block.SpatialEChest;
import net.kyrptonaught.upgradedechests.inv.RiftEChestInventory;
import net.kyrptonaught.upgradedechests.util.SpatialInvStorage;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UpgradedEchestMod implements ModInitializer {
    public static final String MOD_ID = "upgradedechests";

    public static SpatialEChest spatialEChest;
    public static RiftEChest riftEChest;

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "echests"), () -> new ItemStack(riftEChest));

    @Override
    public void onInitialize() {
        spatialEChest = new SpatialEChest(AbstractBlock.Settings.copy(Blocks.ENDER_CHEST));
        riftEChest = new RiftEChest(AbstractBlock.Settings.copy(Blocks.ENDER_CHEST));

        ServerPlayConnectionEvents.DISCONNECT.register((serverPlayNetworkHandler, minecraftServer) -> {
            minecraftServer.getPlayerManager().getPlayerList().forEach(playerEntity -> {
                if (playerEntity.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    Inventory inv = ((GenericContainerScreenHandler) playerEntity.currentScreenHandler).getInventory();
                    if (inv instanceof RiftEChestInventory)
                        if (((RiftEChestInventory) inv).activeBlockEntity.storedPlayer.equals(serverPlayNetworkHandler.player.getUuid())) {
                            playerEntity.closeHandledScreen();
                            playerEntity.sendMessage(Text.translatable("upgradedechests.message.boundoffline"), true);
                        }
                }
            });
        });
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (oldPlayer instanceof SpatialInvStorage)
                ((SpatialInvStorage) newPlayer).setSpatialInventory(((SpatialInvStorage) oldPlayer).getSpatialInv());
        });
    }
}