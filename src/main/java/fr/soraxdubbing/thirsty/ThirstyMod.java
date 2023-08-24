package fr.soraxdubbing.thirsty;

import fr.soraxdubbing.thirsty.client.gui.GuiWaterBar;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ThirstyMod.MODID, name = ThirstyMod.NAME, version = ThirstyMod.VERSION)
public class ThirstyMod
{
    public static final String MODID = "thirsty";
    public static final String NAME = "Thirsty Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @Mod.Instance
    public static ThirstyMod instance;

    private GuiWaterBar waterBarRenderer;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        waterBarRenderer = new GuiWaterBar(Minecraft.getMinecraft());
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            waterBarRenderer.renderWaterBar(Minecraft.getMinecraft().player, event.getResolution());
        }
    }

    // get logger
    public static Logger getLogger(){
        return logger;
    }
}
