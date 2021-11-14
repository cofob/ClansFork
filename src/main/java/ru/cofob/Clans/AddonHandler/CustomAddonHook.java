package ru.cofob.Clans.AddonHandler;

import ru.cofob.Clans.ClanConfiguration;
import ru.cofob.Clans.Main;
import org.apache.commons.lang.Validate;

public abstract class CustomAddonHook extends AddonCommand {
    private String command;
    private String message;

    public CustomAddonHook(String command, String message) {
        Validate.isTrue(command != null && !command.isEmpty(), "Command can not be null or empty!");
        Validate.isTrue(message != null && !message.isEmpty(), "Message can not be null or empty!");
        this.command = command;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCommand() {
        return this.command;
    }

    public ClanConfiguration getClanConfiguration() {
        return Main.getPlugin().getClanConfiguration();
    }
}
