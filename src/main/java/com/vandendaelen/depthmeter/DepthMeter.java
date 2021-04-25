package com.vandendaelen.depthmeter;

import com.vandendaelen.depthmeter.capabilities.DepthCapability;
import com.vandendaelen.depthmeter.capabilities.IDepth;
import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DepthMeter.MODID)
public class DepthMeter {

    public static final String MODID = "depthmeter";
    private static final Logger LOGGER = LogManager.getLogger();

    public DepthMeter() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        DepthMeterItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IDepth.class, new IDepth.Storage(), DepthCapability::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }
}
