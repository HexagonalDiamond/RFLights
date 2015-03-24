package com.julianscode.rflights.block;

import java.util.Random;

import com.julianscode.rflights.tileentities.TileEntityLightBeam;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockLightBeam extends BaseContainerBlock<TileEntityLightBeam>{
	public static final boolean DEBUG = true;
	
	public BlockLightBeam() {
		super(Material.vine, TileEntityLightBeam.class);
		setLightLevel(14);
		setLightOpacity(0);
		setHardness(-1);
		setResistance(6000000);
		if (DEBUG)
			setBlockBounds(3/8.0F, 3/8.0F, 3/8.0F, 5/8.0F, 5/8.0F, 5/8.0F);
		else
			setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		//return false;
		return DEBUG;
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 0;
	}
}
