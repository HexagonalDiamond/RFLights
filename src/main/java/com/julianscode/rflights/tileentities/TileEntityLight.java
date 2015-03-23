package com.julianscode.rflights.tileentities;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Julian on 3/22/2015.
 */
public class TileEntityLight extends TileEntity implements IEnergyHandler {

    private EnergyStorage energyStorage;

    private int transferAmount;

    private boolean isOn;

    public TileEntityLight(int transferAmount) {
        this.transferAmount = transferAmount;
        isOn = false;
        energyStorage = new EnergyStorage(2 * transferAmount, transferAmount);
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
        if(energyStorage.getEnergyStored() >= 500) {
            energyStorage.extractEnergy(500, false);
            this.isOn = true;
        } else {
            this.isOn = false;
        }
    }
    public boolean isOn() {
        return this.isOn;
    }
}
