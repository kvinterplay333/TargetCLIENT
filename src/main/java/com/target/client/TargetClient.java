
package com.target.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TargetClient implements ClientModInitializer {
    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key.targetclient.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC
            )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new TargetMenuScreen());
                }
            }
        });
    }

    private static class TargetMenuScreen extends Screen {
        protected TargetMenuScreen() {
            super(Text.literal("TargetCLIENT"));
        }

        @Override
        public void render(
            net.minecraft.client.gui.DrawContext context,
            int mouseX,
            int mouseY,
            float delta
        ) {
            renderBackground(context, mouseX, mouseY, delta);

            int centerX = width / 2;
            int centerY = height / 2;

            context.fill(
                centerX - 120, centerY - 85,
                centerX + 120, centerY + 85,
                0xEE160D25
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "TARGETCLIENT",
                centerX, centerY - 65, 0xFFB56CFF
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "TARGET — in development",
                centerX, centerY - 20, 0xFFFFFFFF
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "AIM — in development",
                centerX, centerY + 10, 0xFFFFFFFF
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "Press ESC to close",
                centerX, centerY + 60, 0xFFAAAAAA
            );

            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean shouldPause() {
            return false;
        }
    }
}
