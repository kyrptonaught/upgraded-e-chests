package net.kyrptonaught.upgradedechests.block.blockentity;

import net.kyrptonaught.upgradedechests.block.SpatialEChest;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;


public class SpatialEChestBlockEntity extends OpenableBlockEntity {
    public SpatialEChestBlockEntity(BlockPos pos, BlockState state) {
        super(SpatialEChest.blockEntity,pos,state);
    }

}
