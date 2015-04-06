package com.julianscode.rflights.block;

import com.julianscode.rflights.render.BaseIRenderType;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseContainerBlock<TE extends TileEntity>
extends BlockContainer implements BaseIRenderType
{

public int renderID = 0;
Class<? extends TileEntity> tileEntityClass = null;

public BaseContainerBlock(Material material, Class<TE> teClass) {
	super(material);
	//setTextureFile(BaseMod.textureFile);
	tileEntityClass = teClass;
}

@Override
public int getRenderType() {
	return renderID;
}

@Override
public void setRenderType(int id) {
	renderID = id;
}

@Override
public boolean renderAsNormalBlock() {
	return renderID == 0;
}

public TE getTileEntity(IBlockAccess world, int x, int y, int z) {
	return (TE) world.getTileEntity(x, y, z);
}

@Override
public TileEntity createNewTileEntity(World world, int meta) {
	try {
		return tileEntityClass.newInstance();
	}
	catch (Exception e) {
		throw new RuntimeException(e.toString());
	}
}

}