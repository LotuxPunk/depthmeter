package com.vandendaelen.depthmeter.items;

import com.vandendaelen.depthmeter.DepthMeter;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DepthMeterItemGroups {
    public static ItemGroup MAIN = new ItemGroup(DepthMeter.MODID + ".main") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(DepthMeterItems.DEPTHMETER.get());
        }
    };

}
