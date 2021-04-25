package com.vandendaelen.depthmeter.misc;

import java.util.Arrays;

public enum DepthLevels {
    VOID(Integer.MIN_VALUE, -1),
    LAVA(0, 12),
    CAVE(13, 50),
    SURFACE(51,128),
    SKY(129, Integer.MAX_VALUE);

    private final int minValue;
    private final int maxValue;

    private DepthLevels(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
    }

    public static DepthLevels from(int y) {
        return Arrays.stream(DepthLevels.values())
                .filter(range -> y >= range.minValue && y <= range.maxValue)
                .findAny()
                .orElse(VOID);
    }
}
