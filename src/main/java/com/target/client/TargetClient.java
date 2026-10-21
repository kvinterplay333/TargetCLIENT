
package com.target.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
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
            DrawContext context,
            int mouseX,
            int mouseY,
            float delta
        ) {
            renderBackground(context, mouseX, mouseY, delta);

            int x = width / 2;
            int y = height / 2;

            context.fill(
                x - 120, y - 85,
                x + 120, y + 85,
                0xEE160D25
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "TARGETCLIENT",
                x, y - 65, 0xFFB56CFF
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "TARGET - in development",
                x, y - 20, 0xFFFFFFFF
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "AIM - in development",
                x, y + 10, 0xFFFFFFFF
            );

            context.drawCenteredTextWithShadow(
                textRenderer, "Press ESC to close",
                x, y + 60, 0xFFAAAAAA
            );

            super.render(context, mouseX, mouseY, delta);
        }

        @Override
        public boolean shouldPause() {
            return false;
        }
    }
}
