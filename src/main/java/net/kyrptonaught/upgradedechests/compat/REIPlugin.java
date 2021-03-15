package net.kyrptonaught.upgradedechests.compat;

import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import me.shedaniel.rei.plugin.information.DefaultInformationDisplay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyrptonaught.upgradedechests.UpgradedEchestMod;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class REIPlugin implements REIPluginV0 {

    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier(UpgradedEchestMod.MOD_ID, "upgradedechest");
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStack.create(UpgradedEchestMod.riftEChest), new TranslatableText("block.upgradedechests.riftchest"));
        String desc = I18n.translate("upgradedechests.rei.riftrecipe").replaceAll("SPACIALECHEST", I18n.translate("block.upgradedechests.spatialchest")).replaceAll("RIFTECHEST", I18n.translate("block.upgradedechests.riftchest"));
        display.line(new LiteralText(desc));
        recipeHelper.registerDisplay(display);
    }
}