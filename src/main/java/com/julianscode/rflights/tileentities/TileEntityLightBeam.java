package com.julianscode.rflights.tileentities;

import com.julianscode.rflights.block.BlockLightBeam;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLightBeam extends TileEntity {
byte intensity[] = new byte[6];
	
	public int getIntensity(ForgeDirection dir) {
		return intensity[dir.ordinal()];
	}
	
	public boolean setIntensity(ForgeDirection dir, int value) {
		// Returns true if intensity was changed
		int i = dir.ordinal();
		if (intensity[i] != value) {
			intensity[i] = (byte)value;
			if (BlockLightBeam.DEBUG)
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		else
			return false;
	}
	
	public boolean allIntensitiesZero() {
		for (int i = 0; i < 6; i++)
			if (intensity[i] != 0)
				return false;
		return true;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		//System.out.printf("TEFloodlightBeam.readFromNBT\n");
		super.readFromNBT(nbt);
		intensity = nbt.getByteArray("intensity");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		//System.out.printf("TEFloodlightBeam.writeToNBT\n");
		super.writeToNBT(nbt);
		nbt.setByteArray("intensity", intensity);
	}
}
