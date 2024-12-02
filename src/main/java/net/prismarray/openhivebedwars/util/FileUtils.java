package net.prismarray.openhivebedwars.util;

import net.prismarray.openhivebedwars.OpenHiveBedwars;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {
    public static File[] getYMLFilesInDirectory(File directory) {

        if (!directory.isDirectory()) {
            return null;
        }

        return directory.listFiles(
                (file) -> file.isFile() && file.getName().toLowerCase().endsWith(".yml")
        );
    }

    public static boolean createDirIfNonexistent(File directory) {

        if (directory.exists()) {
            return false;
        }

        OpenHiveBedwars.getInstance().getLogger().info(
                "Directory '" + directory.getPath() + "' does not exist. Creating a new one..."
        );

        try {
            if (!directory.mkdirs()) {
                OpenHiveBedwars.getInstance().getLogger().warning("Directory creation failed.");
                return false;
            }
            return true;

        } catch (SecurityException e) {
            OpenHiveBedwars.getInstance().getLogger().warning("Directory creation failed due to missing permissions:");
            OpenHiveBedwars.getInstance().getLogger().warning(e.getMessage());
            return false;
        }
    }
}
