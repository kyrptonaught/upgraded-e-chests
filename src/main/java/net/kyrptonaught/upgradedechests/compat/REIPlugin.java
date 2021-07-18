package net.kyrptonaught.upgradedechests.compat;


import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.upgradedechests.UpgradedEchestMod;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;


@Environment(EnvType.CLIENT)
public class REIPlugin implements REIClientPlugin {


    @Override
    public void registerDisplays(DisplayRegistry registry) {
        DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStacks.of(UpgradedEchestMod.riftEChest), new TranslatableText("block.upgradedechests.riftchest"));
        String desc = I18n.translate("upgradedechests.rei.riftrecipe").replaceAll("SPACIALECHEST", I18n.translate("block.upgradedechests.spatialchest")).replaceAll("RIFTECHEST", I18n.translate("block.upgradedechests.riftchest"));
        display.line(new LiteralText(desc));
        registry.add(display);
    }
}