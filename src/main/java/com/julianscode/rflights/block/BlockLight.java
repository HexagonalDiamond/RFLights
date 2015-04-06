package com.julianscode.rflights.block;

import com.julianscode.rflights.RFLights;
import com.julianscode.rflights.tileentities.TileEntityLight;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Julian on 3/22/2015.
 */
public class BlockLight extends Block implements ITileEntityProvider {

    public static int[] lightLevels = {0, RFLights.instance.tier1light, 0, RFLights.instance.tier2light, 0, RFLights.instance.tier3light};
    
    public static final int METADATA_TIER1_OFF = 0;
    public static final int METADATA_TIER1_ON = 1;
    public static final int METADATA_TIER2_OFF = 2;
    public static final int METADATA_TIER2_ON = 3;
    public static final int METADATA_TIER3_OFF = 4;
    public static final int METADATA_TIER3_ON = 5;
    
    private static String[] _subBlocks = new String[] {
    	"tier1Off",
    	"tier1On",
    	"tier2Off",
    	"tier2On",
    	"tier3Off",
    	"tier3On"
    };
    
    private IIcon[] _icons = new IIcon[_subBlocks.length];
    
    public static boolean isTier1Off(int metadata) { return metadata == METADATA_TIER1_OFF; }
	public static boolean isTier1On(int metadata) { return metadata == METADATA_TIER1_ON; }
	public static boolean isTier2Off(int metadata) { return metadata == METADATA_TIER2_OFF; }
	public static boolean isTier2On(int metadata) { return metadata == METADATA_TIER2_ON; }
	public static boolean isTier3Off(int metadata) { return metadata == METADATA_TIER3_OFF; }
	public static boolean isTier3On(int metadata) { return metadata == METADATA_TIER3_ON; }
    
    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
    	int metadata = blockAccess.getBlockMetadata(x, y, z);
    	return _icons[metadata];
    }
	
    @Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		String prefix = RFLights.TEXTURE_NAME_PREFIX + getUnlocalizedName() + ".";

		for(int metadata = 0; metadata < _subBlocks.length; ++metadata) {
			_icons[metadata] = par1IconRegister.registerIcon(prefix + metadata);
		}
		
		this.blockIcon = par1IconRegister.registerIcon(RFLights.TEXTURE_NAME_PREFIX + getUnlocalizedName());
	}
    
    public BlockLight() {
        super(Material.redstoneLight);
        setCreativeTab(CreativeTabs.tabBlock);
        setBlockTextureName(RFLights.TEXTURE_NAME_PREFIX + "blockReactorPart");
        setHardness(2.0f);
        setBlockName("blockLight");
        setStepSound(soundTypeGlass);
    }
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileEntityLight(1000, meta);
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
      Block block = world.getBlock(x, y, z);
      if(block != null && block != this) {
        return block.getLightValue(world, x, y, z);
      }
      
      int meta = world.getBlockMetadata(x, y, z);
      if(lightLevels[meta] >= 15) {
    	  return 15;
      } else {
    	  return lightLevels[meta];
      }
    }
    
    @Override
    public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
    	TileEntityLight tel = (TileEntityLight) w.getTileEntity(x, y, z);
    	tel.breakBlock(w, x, y, z, meta);
    }
    
    @Override
    public int damageDropped(int metadata) {
    	return metadata;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
    	for (int ix = 0; ix < _subBlocks.length; ix++) {
    		subItems.add(new ItemStack(this, 1, ix));
    	}
    }
    
    public ItemStack getTier1On() {
		return new ItemStack(this, 1, METADATA_TIER1_ON);
	}
	
	public ItemStack getTier1Off() {
		return new ItemStack(this, 1, METADATA_TIER1_OFF);
	}
	
	public ItemStack getTier2On() {
		return new ItemStack(this, 1, METADATA_TIER2_ON);
	}
	
	public ItemStack getTier2Off() {
		return new ItemStack(this, 1, METADATA_TIER2_OFF);
	}
	
	public ItemStack getTier3On() {
		return new ItemStack(this, 1, METADATA_TIER3_ON);
	}
	
	public ItemStack getTier3Off() {
		return new ItemStack(this, 1, METADATA_TIER3_OFF);
	}
}
