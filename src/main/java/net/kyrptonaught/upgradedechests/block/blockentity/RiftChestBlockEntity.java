package net.kyrptonaught.upgradedechests.block.blockentity;


import net.kyrptonaught.upgradedechests.block.RiftEChest;
import net.kyrptonaught.upgradedechests.inv.RiftEChestInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class RiftChestBlockEntity extends OpenableBlockEntity {
    public RiftChestBlockEntity(BlockPos pos, BlockState state) {
        super(RiftEChest.blockEntity, pos, state);
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
    public void readNbt(NbtCompound tag) {
        if (tag.contains("storedplayer"))
            storedPlayer = tag.getUuid("storedplayer");
        super.readNbt(tag);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        if (storedPlayer != null)
            tag.putUuid("storedplayer", storedPlayer);
         super.writeNbt(tag);
    }
}
