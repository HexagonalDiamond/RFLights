package com.julianscode.rflights.block;

import com.julianscode.rflights.tileentities.TileEntityLight;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Julian on 3/22/2015.
 */
public class BlockLight extends Block implements ITileEntityProvider {

    public static int[] lightLevels = {10, 20, 30, 40};

    public BlockLight() {
        super(Material.redstoneLight);
    }
    public TileEntity createNewTileEntity(World w, int meta) {
        // TODO: get from config
        return new TileEntityLight(1000);
    }

    @Override
    public void updateTick(World w, int x, int y, int z, Random r) {
        TileEntityLight tel = (TileEntityLight) w.getTileEntity(x, y, z);
        tel.updateTileEntity();
        if(tel.isOn()) {
            this.setLightLevel(lightLevels[0]);
        } else {
            this.setLightLevel(0);
        }
    }

    @Override
    public int tickRate(World p_149738_1_) {
        return 10;
    }
}
