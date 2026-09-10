package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;

public final class RainbowRenderer {
    private RainbowRenderer() {}

        public static void update(Minecraft client, VisualState state) {

            if (!Vanilla3DPlusConfig.rainbow) {
                return;
            }

            ClientLevel level = client.level;
            Player player = client.player;

            var random = level.getRandom();

            if (!state.wasRaining
                    || level.isRaining()
                    || level.isThundering()) {
                return;
            }

            if (state.tick % 3 != 0
                    || random.nextFloat() > 0.58f) {
                return;
            }

            /*
             * Daylight check.
             */
            double time =
                    level.getGameTime()
                            % 24000L;

            boolean daylight =
                    time > 0
                            && time < 13000;

            if (!daylight) {
                return;
            }

            double angle =
                    (state.tick * 0.018)
                            % (Math.PI * 2);

            double radius =
                    16
                            + random.nextDouble() * 5;

            double height =
                    5
                            + Math.sin(angle) * 5;

            double x =
                    player.getX()
                            + Math.cos(angle) * radius;

            double z =
                    player.getZ()
                            + Math.sin(angle) * radius;

            double y =
                    player.getY()
                            + height;

            level.addParticle(
                    ParticleTypes.END_ROD,
                    x,
                    y,
                    z,
                    0,
                    0,
                    0
            );
        }
}
