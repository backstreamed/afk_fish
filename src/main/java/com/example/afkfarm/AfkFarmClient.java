package com.example.afkfarm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class AfkFarmClient implements ClientModInitializer {

    private static KeyBinding toggleKey;  // M -> ac/kapat
    private boolean holding = false;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.afkfarm.toggle", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M, "category.afkfarm"));

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        while (toggleKey.wasPressed()) {
            holding = !holding;
            client.options.useKey.setPressed(holding);
            msg(client, holding ? "§d[Farm] §aBASILI TUTULUYOR"
                                : "§d[Farm] §cKAPALI");
        }

        // aktifken sag tik basili kalsin (comelme yok)
        if (holding) {
            client.options.useKey.setPressed(true);
        }
    }

    private void msg(MinecraftClient client, String s) {
        if (client.player != null) client.player.sendMessage(Text.literal(s), true);
    }
}
