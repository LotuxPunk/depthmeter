package com.vandendaelen.depthmeter;

import com.vandendaelen.depthmeter.capabilities.IDepth;
import com.vandendaelen.depthmeter.config.DepthMeterConfig;
import com.vandendaelen.depthmeter.data.RecipeBuilder;
import com.vandendaelen.depthmeter.items.DepthMeterItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(DepthMeter.MODID)
public class DepthMeter {

    public static final String MODID = "depthmeter";

    public DepthMeter() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerCapabilities);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onGatherData);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DepthMeterConfig.COMMON_SPEC);

        DepthMeterItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.register(IDepth.class);
    }

    private void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        generator.addProvider(new RecipeBuilder(generator));
    }
}
