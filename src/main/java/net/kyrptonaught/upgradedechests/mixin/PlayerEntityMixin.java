package net.kyrptonaught.upgradedechests.mixin;

import net.kyrptonaught.upgradedechests.util.SpatialInvStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements SpatialInvStorage {

    @Unique
    SimpleInventory spatialInv = new SimpleInventory(27);

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeSpatialEChest(NbtCompound tag, CallbackInfo ci) {
        tag.put("spatialItems", getTags(spatialInv));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readSpatialEChest(NbtCompound tag, CallbackInfo ci) {
        if (tag.contains("spatialItems", 9)) {
            readTags(tag.getList("spatialItems", 10), spatialInv);
        }
    }

    @Override
    public SimpleInventory getSpatialInv() {
        return spatialInv;
    }

    @Override
    public void setSpatialInventory(SimpleInventory spatialInventory) {
        this.spatialInv = spatialInventory;
    }

    @Unique
    public void readTags(NbtList tags, SimpleInventory simpleInventory) {
        int j;
        for (j = 0; j < simpleInventory.size(); ++j) {
            simpleInventory.setStack(j, ItemStack.EMPTY);
        }

        for (j = 0; j < tags.size(); ++j) {
            NbtCompound compoundTag = tags.getCompound(j);
            int k = compoundTag.getByte("Slot") & 255;
            if (k >= 0 && k < simpleInventory.size()) {
                simpleInventory.setStack(k, ItemStack.fromNbt(compoundTag));
            }
        }

    }

    @Unique
    public NbtList getTags(SimpleInventory simpleInventory) {
        NbtList listTag = new NbtList();

        for (int i = 0; i < simpleInventory.size(); ++i) {
            ItemStack itemStack = simpleInventory.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound compoundTag = new NbtCompound();
                compoundTag.putByte("Slot", (byte) i);
                itemStack.writeNbt(compoundTag);
                listTag.add(compoundTag);
            }
        }

        return listTag;
    }
}
