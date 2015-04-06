package com.julianscode.rflights.tileentities;

import com.julianscode.rflights.RFLights;
import com.julianscode.rflights.RFLightsLog;
import com.julianscode.rflights.block.BlockLight;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.core.util.energy.EnergyStorageAdv;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Julian on 3/22/2015.
 */
public class TileEntityLight extends TileEntity implements IEnergyHandler {

    private EnergyStorageAdv energyStorage;
    
    private int rfPerTick;

    private boolean isOn;
    
    
    private int metaType;

	private boolean lastActive = false;
       
	public TileEntityLight() {}
	
    public TileEntityLight(int transferAmount, int meta) {
        isOn = false;
        if(meta == 0) {
        	// Tier 1
        	rfPerTick = RFLights.instance.tier1rfpt;
        } else if(meta == 2) {
        	// Tier 2
        	rfPerTick = RFLights.instance.tier2rfpt;
        } else if(meta == 4) {
        	// Tier 3
        	rfPerTick = RFLights.instance.tier3rfpt;
        }
        energyStorage = new EnergyStorageAdv(5000, rfPerTick * 2, rfPerTick);
    }
    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return 0;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return energyStorage.receiveEnergy(i, b);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
    	tagCompound = energyStorage.writeToNBT(tagCompound);
        super.writeToNBT(tagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
    	
    	if(energyStorage == null) {
    		energyStorage = new EnergyStorageAdv(5000, rfPerTick * 2, rfPerTick); 
    	}
    	
        energyStorage.readFromNBT(tagCompound);
               
        RFLightsLog.info("Loaded energy: %d", energyStorage.getEnergyStored());
        super.readFromNBT(tagCompound);
    }
    
    public boolean isOn() {
        return this.isOn;
    }
    
    private void updateLight() {
    	int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		metaType = (int) Math.floor(meta / 2) * 2;
		int lightMeta = metaType + (this.isOn ? 1: 0);

		if(!worldObj.isRemote) {
    		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lightMeta, 3);
    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
		}
		lastActive = this.isOn;
    }
    
    @Override
    public void updateEntity() {
    	if(energyStorage.getEnergyStored() >= rfPerTick) {
            energyStorage.extractEnergy(rfPerTick, false);
            this.isOn = true;
    	} else {
			this.isOn = false;
    	}
    	
    	if(this.isOn != lastActive ) {
    		updateLight();
    	}
    }
    
    public void breakBlock(World w, int x, int y, int z, int meta) {
    	worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}
