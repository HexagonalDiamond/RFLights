package com.julianscode.rflights.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by Julian on 3/22/2015.
 */
public class ItemBlockLight extends ItemBlock {
    public ItemBlockLight(Block block) {
        super(block);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setUnlocalizedName("rfLight");
        this.setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        if(this.hasSubtypes) {
            int metadata = itemStack.getItemDamage();
            return super.getUnlocalizedName(itemStack) + "." + Integer.toString(metadata);
        }
        else {
            return super.getUnlocalizedName(itemStack);
        }
    }

    @Override
    public String getUnlocalizedName() {
        if(this.hasSubtypes) {
            return super.getUnlocalizedName() + ".0";
        }
        else {
            return super.getUnlocalizedName();
        }
    }
}
