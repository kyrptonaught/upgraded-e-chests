package net.kyrptonaught.upgradedechests.util;

import net.minecraft.inventory.SimpleInventory;
import sun.java2d.pipe.SpanShapeRenderer;

public interface SpatialInvStorage {

    SimpleInventory getSpatialInv();
    void setSpatialInventory(SimpleInventory spatialInventory);
}
