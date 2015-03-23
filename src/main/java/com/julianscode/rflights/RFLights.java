package com.julianscode.rflights;

import com.julianscode.rflights.block.BlockLight;
import com.julianscode.rflights.items.ItemBlockLight;
import com.julianscode.rflights.tileentities.TileEntityLight;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.ItemBlock;

@Mod(modid = RFLights.MODID, version = RFLights.VERSION)
public class RFLights
{
    public static final String MODID = "rflights";
    public static final String VERSION = "1.0";

    private BlockLight blockLight;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        registerBlocks();
        registerOres();
        registerTileEntities();
    }

    private void registerBlocks() {
        blockLight = new BlockLight();
        GameRegistry.registerBlock(blockLight, ItemBlockLight.class, "RFLightBlock");
    }

    private void registerOres() {

    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityLight.class, "RFLight");
    }
}
