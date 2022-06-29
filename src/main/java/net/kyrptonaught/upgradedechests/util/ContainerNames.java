package net.kyrptonaught.upgradedechests.util;

import net.minecraft.text.Text;

public class ContainerNames {
    public static Text SPATIAL_CHEST = Text.translatable("upgradedechests.containername.spatialchest");

    public static Text getRiftChestName(Text playerName) {
        return Text.translatable("upgradedechests.containername.riftchest").append(playerName);
    }
}
