package com.vandendaelen.depthmeter.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class DepthMeterCapabilities {
    @CapabilityInject(IDepth.class)
    public static final Capability<IDepth> DEPTH = null;
}
