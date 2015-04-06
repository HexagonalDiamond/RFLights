package com.julianscode.rflights.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLightBeam extends TileEntity {
	public int stacked = 1;
	
	@Override
	public void writeToNBT(NBTTagCompound cmd) {
		cmd.setInteger("stacked", stacked);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound cmd) {
		stacked = cmd.getInteger("stacked");
	}
}
