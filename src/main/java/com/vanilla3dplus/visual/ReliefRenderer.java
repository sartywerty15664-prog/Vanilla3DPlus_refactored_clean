package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;

/**
 * 3D relief hook. The option is kept isolated so a supported world-render callback
 * can be added without touching the client bootstrap or other effects.
 */
public final class ReliefRenderer {
    private ReliefRenderer() {}

    public static void update(Minecraft client, VisualState state) {
        if (!Vanilla3DPlusConfig.relief3D) return;
        // Intentionally no raw OpenGL here: Minecraft 26.2 can use alternative render backends.
    }
}
