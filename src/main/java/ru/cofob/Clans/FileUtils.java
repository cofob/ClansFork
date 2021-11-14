package ru.cofob.Clans;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class FileUtils {
    public FileUtils() {
    }

    public static void save(File file, FileConfiguration configuration) {
        try {
            configuration.save(file);
            configuration.load(file);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
