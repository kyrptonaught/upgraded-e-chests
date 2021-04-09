package net.kyrptonaught.upgradedechests.mixin;

import net.kyrptonaught.upgradedechests.util.SpatialInvStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements SpatialInvStorage {

    @Unique
    SimpleInventory spatialInv = new SimpleInventory(27);

    @Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
    public void writeSpatialEChest(CompoundTag tag, CallbackInfo ci) {
        tag.put("spatialItems", getTags(spatialInv));
    }

    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
    public void readSpatialEChest(CompoundTag tag, CallbackInfo ci) {
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
    public void readTags(ListTag tags, SimpleInventory simpleInventory) {
        int j;
        for (j = 0; j < simpleInventory.size(); ++j) {
            simpleInventory.setStack(j, ItemStack.EMPTY);
        }

        for (j = 0; j < tags.size(); ++j) {
            CompoundTag compoundTag = tags.getCompound(j);
            int k = compoundTag.getByte("Slot") & 255;
            if (k >= 0 && k < simpleInventory.size()) {
                simpleInventory.setStack(k, ItemStack.fromTag(compoundTag));
            }
        }

    }

    @Unique
    public ListTag getTags(SimpleInventory simpleInventory) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < simpleInventory.size(); ++i) {
            ItemStack itemStack = simpleInventory.getStack(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte) i);
                itemStack.toTag(compoundTag);
                listTag.add(compoundTag);
            }
        }

        return listTag;
    }
}
