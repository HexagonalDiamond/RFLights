package com.julianscode.rflights.tileentities;

import com.julianscode.rflights.RFLights;
import com.julianscode.rflights.RFLightsLog;
import com.julianscode.rflights.block.BlockLight;
import com.julianscode.rflights.block.BlockLightBeam;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.core.util.energy.EnergyStorageAdv;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Julian on 3/22/2015.
 */
public class TileEntityLight extends TileEntity implements IEnergyHandler {

    private EnergyStorageAdv energyStorage;

    private int transferAmount;
    
    private int rfPerTick;

    private boolean isOn;
    
    
    private int metaType;

	private boolean lastActive = false;
        
    public TileEntityLight(int transferAmount, int meta) {
        this.transferAmount = transferAmount;
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
        energyStorage = new EnergyStorageAdv(5000, rfPerTick * 2, 0);
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
        super.writeToNBT(tagCompound);
//        tagCompound.setBoolean("isOn", isOn);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        energyStorage.readFromNBT(tagCompound);
        super.readFromNBT(tagCompound);
//        isOn = tagCompound.getBoolean("isOn");
    }
    
    public boolean isOn() {
        return this.isOn;
    }
    
    private void betterSetBlock(int x, int y, int z, Block b) {
    	worldObj.setBlock(x, y, z, b, 0, 3);
    	worldObj.markBlockForUpdate(x, y, z);
		worldObj.updateLightByType(EnumSkyBlock.Block, x, y, z);
    }
    
    private void setLightBeam(int x, int y, int z) {
    	if(!(worldObj.getBlock(x, y, z) instanceof BlockLightBeam)) {
	    	betterSetBlock(x, y, z, RFLights.instance.blockLightBeam);
    	} else {
    		TileEntityLightBeam blb = (TileEntityLightBeam) worldObj.getTileEntity(x, y, z);
    		blb.stacked++;
    		return;
    	}
    }
    
    public void removeLightBeam(int x, int y, int z) {
    	if(worldObj.getBlock(x, y, z) instanceof BlockLightBeam) {
    		TileEntityLightBeam blb = (TileEntityLightBeam) worldObj.getTileEntity(x, y, z);
    		blb.stacked--;
    		if(blb.stacked <= 0) {
    			betterSetBlock(x, y, z, Blocks.air);
    		}
    	}
    }
    
    private void placeLightBeams(int intensity) {
    	if (!(worldObj instanceof WorldServer)) return;
    	for(int x = this.xCoord - intensity; x < this.xCoord + intensity; x++) {
    		for(int y = this.yCoord - intensity; y < this.yCoord + intensity; y++) {
    			for(int z = this.zCoord - intensity; z < this.zCoord + intensity; z++) {
    				boolean blockExists = true;
    				if(worldObj.blockExists(x, y, z)) {
    					Block block = worldObj.getBlock(x, y, z);
    					blockExists = !block.isAir(worldObj, x, y, z) && !block.isReplaceable(worldObj, x, y, z);
    				} else {
    					blockExists = false;
    				}
    				if(!blockExists) {
    					if(y >= 0) {
    						setLightBeam(x, y, z);
    					}
    				}
            	}
        	}
    	}
    }
    
    private void breakLightBeams(int intensity) {
    	for(int x = this.xCoord - intensity; x < this.xCoord + intensity; x++) {
    		for(int y = this.yCoord - intensity; y < this.yCoord + intensity; y++) {
    			for(int z = this.zCoord - intensity; z < this.zCoord + intensity; z++) {
    				removeLightBeam(x, y, z);
            	}
        	}
    	}
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
    		RFLightsLog.info("changed %b", this.isOn);
    		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    		metaType = (int) Math.floor(meta / 2) * 2;
    		int lightMeta = metaType + (this.isOn ? 1: 0);
    		if(BlockLight.lightLevels[lightMeta] > 14 && this.isOn) {
    			placeLightBeams(BlockLight.lightLevels[lightMeta] - 14);
    		} else if (!this.isOn && BlockLight.lightLevels[metaType + 1] > 14){
    			breakLightBeams(BlockLight.lightLevels[metaType + 1] - 14);
    		}
    		if(!worldObj.isRemote) {
	    		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lightMeta, 3);
	    		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	    		worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
    		}
    		lastActive = this.isOn;
    	}
    }
    
    public void breakBlock(World w, int x, int y, int z, int meta) {
    	worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	RFLightsLog.info("BROKE LAMP!");
    	if(BlockLight.lightLevels[meta] > 14) {
			breakLightBeams(BlockLight.lightLevels[meta] - 14);
		}
    }
}
