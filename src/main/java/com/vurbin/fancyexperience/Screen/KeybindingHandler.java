package com.vurbin.fancyexperience.Screen;

import com.vurbin.fancyexperience.Player.PlayerStats;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static com.vurbin.fancyexperience.FancyExperience.MOD_ID;

public class KeybindingHandler {

    private static KeyBinding openMenuKey;
    private static PlayerStats _playerStats = null;

    public KeybindingHandler(PlayerStats playerStats) {
        _playerStats = playerStats;
        String mod_id = MOD_ID;  // Задаем значение mod_id

        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open menu",  // Конкатенируем строку для ключа
                InputUtil.Type.KEYSYM,       // Тип привязки клавиш, KEYSYM для клавиатуры, MOUSE для мыши
                GLFW.GLFW_KEY_K,             // Код клавиши (K)
                mod_id // Конкатенируем строку для категории
        ));

        // Обработчик для нажатия клавиши
        ClientTickEvents.END_CLIENT_TICK.register( client -> {
            if (openMenuKey.isPressed()) {
                openCharacterUpgradeMenu();
            }
        });
    }

    // Метод для открытия экрана прокачки
    public static void openCharacterUpgradeMenu() {
        if(_playerStats != null)
            MinecraftClient.getInstance().setScreen(new UpgradeScreen(_playerStats));
    }
}
