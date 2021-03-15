package net.kyrptonaught.upgradedechests.block.blockentity;


import net.kyrptonaught.upgradedechests.block.RiftEChest;
import net.kyrptonaught.upgradedechests.inv.RiftEChestInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.UUID;

public class RiftChestBlockEntity extends OpenableBlockEntity {
    public RiftChestBlockEntity() {
        super(RiftEChest.blockEntity);
    }

    public UUID storedPlayer;


    public void setStoredPlayer(PlayerEntity player) {
        this.storedPlayer = player.getUuid();
        sync();
    }

    public boolean hasStoredPlayer() {
        return storedPlayer != null;
    }

    public RiftEChestInventory getEChestInv(ServerWorld world) {
        if (!hasStoredPlayer()) return null;
        PlayerEntity player = world.getServer().getPlayerManager().getPlayer(storedPlayer);
        if (player == null) return null;
        return new RiftEChestInventory(player.getEnderChestInventory(), this);
    }

    public Text getPlayerName(ServerWorld world) {
        PlayerEntity player = world.getServer().getPlayerManager().getPlayer(storedPlayer);
        if (player == null) return null;
        return player.getName();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        if (tag.contains("storedplayer"))
            storedPlayer = tag.getUuid("storedplayer");
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (storedPlayer != null)
            tag.putUuid("storedplayer", storedPlayer);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        if (tag.contains("storedplayer"))
            storedPlayer = tag.getUuid("storedplayer");
        super.fromClientTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        if (storedPlayer != null)
            tag.putUuid("storedplayer", storedPlayer);
        return super.toClientTag(tag);
    }
}
