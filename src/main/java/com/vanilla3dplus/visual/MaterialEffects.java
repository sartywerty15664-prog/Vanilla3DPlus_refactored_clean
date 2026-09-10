package com.vanilla3dplus.visual;

import com.vanilla3dplus.config.Vanilla3DPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import java.util.Locale;

public final class MaterialEffects {
    private MaterialEffects() {}

        public static void updateMaterialLight(
                Minecraft client, VisualState state
        ) {

            if (!Vanilla3DPlusConfig.materialEffects) {
                return;
            }

            Player player = client.player;
            ClientLevel level = client.level;

            var random = level.getRandom();

            if (state.tick % 5 != 0) {
                return;
            }

            ItemStack held =
                    player.getMainHandItem();

            if (held.isEmpty()) {
                return;
            }

            String name =
                    held.getItem()
                            .toString()
                            .toLowerCase(Locale.ROOT);

            boolean metal =
                    VisualMath.containsAny(
                            name,
                            "iron",
                            "diamond",
                            "netherite",
                            "gold",
                            "copper",
                            "mace",
                            "sword",
                            "pickaxe",
                            "axe",
                            "shovel",
                            "hoe",
                            "spear"
                    );

            boolean wood =
                    VisualMath.containsAny(
                            name,
                            "wood",
                            "wooden",
                            "stick",
                            "bamboo"
                    );
    if (!metal || wood) {
                return;
            }

            float brightness =
                    level.getMaxLocalRawBrightness(
                            player.blockPosition()
                    );

            boolean bright =
                    brightness >= 11
                            || level.getSkyDarken() <= 2;

            if (!bright) {
                return;
            }

            if (random.nextFloat() > 0.34f) {
                return;
            }

            level.addParticle(
                    ParticleTypes.ENCHANT,

                    player.getX()
                            + (random.nextDouble() - 0.5)
                            * 0.45,

                    player.getY()
                            + 1.0
                            + random.nextDouble() * 0.35,

                    player.getZ()
                            + (random.nextDouble() - 0.5)
                            * 0.45,

                    0,
                    0.015,
                    0
            );
        }
}
