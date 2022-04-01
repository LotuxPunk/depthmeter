package com.vandendaelen.depthmeter.items;

import com.vandendaelen.depthmeter.DepthMeter;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DepthMeterItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DepthMeter.MODID);

    public static final RegistryObject<Item> DEPTHMETER = ITEMS.register("depthmeter", DepthMeterItem::new);
}
