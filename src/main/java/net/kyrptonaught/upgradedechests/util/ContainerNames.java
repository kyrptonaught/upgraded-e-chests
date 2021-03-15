package net.kyrptonaught.upgradedechests.util;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ContainerNames {
    public static Text SPATIAL_CHEST = new TranslatableText("upgradedechests.containername.spatialchest");

    public static Text getRiftChestName(Text playerName) {
        return new TranslatableText("upgradedechests.containername.riftchest").append(playerName);
    }
}
