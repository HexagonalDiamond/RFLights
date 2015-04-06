package com.julianscode.rflights;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import com.julianscode.rflights.block.BlockLight;
import com.julianscode.rflights.block.BlockLightBeam;
import com.julianscode.rflights.items.ItemBlockLight;
import com.julianscode.rflights.tileentities.TileEntityLight;
import com.julianscode.rflights.tileentities.TileEntityLightBeam;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RFLights.MODID, version = RFLights.VERSION)
public class RFLights
{
    public static final String MODID = "rflights";
    public static final String VERSION = "1.0";
    public static final String TEXTURE_NAME_PREFIX = "rflights:";

    public BlockLight blockLight;
    public BlockLightBeam blockLightBeam;
    
    public int tier1rfpt;
    public int tier2rfpt;
    public int tier3rfpt;

    public int tier1light;
    public int tier2light;
    public int tier3light;
    
    private Configuration config;
    
    @Instance
    public static RFLights instance;
        
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	initConfig(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        registerLights();
        registerLightBeam();
        registerTileEntities();
    }

    private void initConfig(FMLPreInitializationEvent event) {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	
    	config.load();
    	tier1rfpt = config.getInt("Tier 1", "RF Per Tick", 50, 0, 1000000, "");
    	tier2rfpt = config.getInt("Tier 2", "RF Per Tick", 100, 0, 1000000, "");
    	tier3rfpt = config.getInt("Tier 3", "RF Per Tick", 200, 0, 1000000, "");
    	
    	tier1light = config.getInt("Tier 1", "Light Values", 20, 0, 20, "");
    	tier2light = config.getInt("Tier 2", "Light Values", 15, 0, 15, "");
    	tier3light = config.getInt("Tier 3", "Light Values", 15, 0, 15, "");
    	config.save();
    }
    
    private void registerLightBeam() {
    	if(blockLightBeam == null) {
    		blockLightBeam = new BlockLightBeam();
    		GameRegistry.registerBlock(blockLightBeam, "RFLightBeamBlock");
    	}
     }
     
    private void registerLights() {
    	if(blockLight == null) {
	        blockLight = (BlockLight) new BlockLight();
	        GameRegistry.registerBlock(blockLight, ItemBlockLight.class, "RFLightBlock");
	        
	        OreDictionary.registerOre("blockLightTier1", blockLight.getTier1Off());
	        OreDictionary.registerOre("blockLightTier2", blockLight.getTier2Off());
	        OreDictionary.registerOre("blockLightTier3", blockLight.getTier3Off());
    	}
    }

    private void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityLight.class, "RFLight");
        GameRegistry.registerTileEntity(TileEntityLightBeam.class, "RFLightBeam");
    }
}
