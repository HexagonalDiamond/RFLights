package com.julianscode.rflights.tileentities;

import com.julianscode.rflights.RFLights;
import com.julianscode.rflights.block.BlockLight;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Julian on 3/22/2015.
 */
public class TileEntityLight extends TileEntity implements IEnergyHandler {

    private EnergyStorage energyStorage;

    private int transferAmount;
    
    private int rfPerTick;

    private boolean isOn;
    
    private boolean lastActive;
    
    private boolean init = true;

    public TileEntityLight(int transferAmount, int meta) {
        this.transferAmount = transferAmount;
        isOn = false;
        energyStorage = new EnergyStorage(2 * this.transferAmount, this.transferAmount);
        if(meta == 1) {
        	// Tier 1
        	rfPerTick = RFLights.instance.tier1rfpt;
        } else if(meta == 3) {
        	// Tier 2
        	rfPerTick = RFLights.instance.tier2rfpt;
        } else if(meta == 5) {
        	// Tier 3
        	rfPerTick = RFLights.instance.tier3rfpt;
        }
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
        energyStorage.writeToNBT(tagCompound);
        tagCompound.setBoolean("isOn", isOn);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        energyStorage.readFromNBT(tagCompound);
        isOn = tagCompound.getBoolean("isOn");
    }

    public void updateTileEntity() {
        if(energyStorage.getEnergyStored() >= rfPerTick) {
            energyStorage.extractEnergy(rfPerTick, false);
            this.isOn = true;
        } else {
            this.isOn = false;
        }
    }
    public boolean isOn() {
        return this.isOn;
    }
    
    @Override
    public void updateEntity() {
    	if(worldObj.isRemote)
    		return;
    	
    	
    	if(energyStorage.getEnergyStored() >= 500) {
            energyStorage.extractEnergy(500, false);
            this.isOn = true;
    	} else {
			this.isOn = false;
    	}
    	
    	if(this.isOn != lastActive || init) {
    		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    		int type = (int) Math.floor(meta / 2) * 2;
    		int lightMeta = type + (this.isOn ? 1: 0);
    		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lightMeta, 1);
    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
    		init = false;
    		lastActive = this.isOn;
    	}
    }
    
    public void breakBlock() {
    	worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
    }
}
