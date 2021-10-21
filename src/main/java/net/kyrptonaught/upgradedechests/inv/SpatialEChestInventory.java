package net.kyrptonaught.upgradedechests.inv;

import net.kyrptonaught.upgradedechests.block.blockentity.SpatialEChestBlockEntity;
import net.kyrptonaught.upgradedechests.util.SpatialInvStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SpatialEChestInventory implements Inventory {
    private SpatialEChestBlockEntity activeBlockEntity;
    private final PlayerEntity playerEntity;


    public SpatialEChestInventory(PlayerEntity playerEntity) {
        super();
        this.playerEntity = playerEntity;
    }

    public void setActiveBlockEntity(SpatialEChestBlockEntity blockEntity) {
        this.activeBlockEntity = blockEntity;
    }

    @Override
    public int size() {
        return 54;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot < 27)
            return playerEntity.getEnderChestInventory().getStack(slot);
        return ((SpatialInvStorage) playerEntity).getSpatialInv().getStack(slot - 27);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (slot < 27)
            return playerEntity.getEnderChestInventory().removeStack(slot, amount);
        return ((SpatialInvStorage) playerEntity).getSpatialInv().removeStack(slot - 27, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if (slot < 27)
            return playerEntity.getEnderChestInventory().removeStack(slot);
        return ((SpatialInvStorage) playerEntity).getSpatialInv().removeStack(slot - 27);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot < 27)
            playerEntity.getEnderChestInventory().setStack(slot, stack);
        else
            ((SpatialInvStorage) playerEntity).getSpatialInv().setStack(slot - 27, stack);
    }

    @Override
    public void markDirty() {
        playerEntity.getEnderChestInventory().markDirty();
        ((SpatialInvStorage) playerEntity).getSpatialInv().markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return playerEntity.getEnderChestInventory().isEmpty() && ((SpatialInvStorage) playerEntity).getSpatialInv().isEmpty();
    }

    @Override
    public void clear() {
        ((SpatialInvStorage) playerEntity).getSpatialInv().clear();
        this.markDirty();
    }

    public void onOpen(PlayerEntity player) {
        if (this.activeBlockEntity != null) {
            this.activeBlockEntity.onOpen();
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (this.activeBlockEntity != null) {
            this.activeBlockEntity.onClose();
        }
        this.activeBlockEntity = null;
        playerEntity.getEnderChestInventory().onClose(player);
        ((SpatialInvStorage) player).getSpatialInv().onClose(player);
    }
}
