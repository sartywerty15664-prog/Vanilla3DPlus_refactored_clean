package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;

/** Lightweight ambient cloud wisps made entirely from vanilla particles. */
public final class CloudRenderer {
    private CloudRenderer() {}

    public static void update(Minecraft client, VisualState state) {
        if (!Vanilla3DPlusConfig.clouds || client.player == null || client.level == null) return;
        if (state.tick % 8 != 0) return;

        ClientLevel level = client.level;
        Player player = client.player;
        if (level.isRaining() || level.isThundering()) return;

        var random = level.getRandom();
        if (random.nextFloat() > 0.18f) return;

        double angle = random.nextDouble() * Math.PI * 2.0;
        double radius = 10.0 + random.nextDouble() * 14.0;
        double x = player.getX() + Math.cos(angle) * radius;
        double y = player.getY() + 8.0 + random.nextDouble() * 5.0;
        double z = player.getZ() + Math.sin(angle) * radius;

        level.addParticle(ParticleTypes.CLOUD, x, y, z, 0.01, 0.0, 0.01);
    }
}
