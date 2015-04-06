package com.julianscode.rflights.block;

import java.util.Random;

import com.julianscode.rflights.tileentities.TileEntityLight;
import com.julianscode.rflights.tileentities.TileEntityLightBeam;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightBeam extends Block implements ITileEntityProvider{
	public static final boolean DEBUG = true;
	
	public BlockLightBeam() {
		super(Material.vine);
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
	
	@Override
	public int getLightValue(IBlockAccess w, int x, int y, int z) {
		return 14;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess w, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isAir(IBlockAccess w, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isCollidable() {
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLightBeam();
	}

}
