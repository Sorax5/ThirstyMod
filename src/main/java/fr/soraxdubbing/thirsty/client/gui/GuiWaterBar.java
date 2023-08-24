package fr.soraxdubbing.thirsty.client.gui;

import fr.soraxdubbing.thirsty.IMixinEntityPlayerWaterBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiWaterBar extends Gui {
    private Minecraft mc;

    public GuiWaterBar(Minecraft mc) {
        this.mc = mc;
    }

    public void renderWaterBar(EntityPlayer player, ScaledResolution resolution) {
        IMixinEntityPlayerWaterBar playerWaterBar = (IMixinEntityPlayerWaterBar) player;

        int waterLevel = playerWaterBar.getWaterLevel(); // Récupère le niveau d'eau du joueur
        int waterBarWidth = 182;
        int waterBarHeight = 5;

        int screenWidth = resolution.getScaledWidth();
        int screenHeight = resolution.getScaledHeight();

        int xPos = (screenWidth - waterBarWidth) / 2; // Centré horizontalement
        int yPos = screenHeight - 49; // Position Y de la barre d'eau

        // Dessine la barre d'eau
        mc.getTextureManager().bindTexture(Gui.ICONS);

        GlStateManager.color(0.0F, 0.0F, 1.0F, 1.0F); // Change la couleur en bleu

        // Dessine la partie vide de la barre d'eau (background)
        drawTexturedModalRect(xPos, yPos, 0, 74, waterBarWidth, waterBarHeight);

        // Réinitialise la couleur
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // Remplit la barre d'eau en fonction du niveau d'eau du joueur
        int waterBarFilledWidth = (int) ((float) waterLevel / 20 * waterBarWidth);

        // Dessine la partie remplie de la barre d'eau en bleu
        GlStateManager.color(0.0F, 0.0F, 1.0F, 1.0F); // Change la couleur en bleu
        drawTexturedModalRect(xPos, yPos, 0, 79, waterBarFilledWidth, waterBarHeight);

        // Réinitialise la couleur
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
