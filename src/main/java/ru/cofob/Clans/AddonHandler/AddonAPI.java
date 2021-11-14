package ru.cofob.Clans.AddonHandler;

import java.util.HashMap;
import java.util.Map;

public class AddonAPI {
    private static Map<String, CustomAddonHook> addons = new HashMap();

    public AddonAPI() {
    }

    public static boolean registerAddonHook(CustomAddonHook addonHook) {
        if (addonHook.getCommand() != null && addonHook != null && !addons.containsKey(addonHook.getCommand())) {
            addons.put(addonHook.getCommand(), addonHook);
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, CustomAddonHook> getAddons() {
        return addons;
    }
}
