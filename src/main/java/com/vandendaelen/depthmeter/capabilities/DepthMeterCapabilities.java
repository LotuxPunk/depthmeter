package com.vandendaelen.depthmeter.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class DepthMeterCapabilities {
    public static final Capability<IDepth> DEPTH = CapabilityManager.get(new CapabilityToken<>() {
    });
}
