package net.prismarray.openhivebedwars.util;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldCopy {

    public static void copyMapToContainer(String originalName, String newName, File location) {
        copyFileStructure(new File(location + File.separator + originalName), new File(Bukkit.getWorldContainer() + File.separator + newName));
    }

    // METHODS BELOW ARE COURTESY OF SPIGOTMC USER IPat
    // https://www.spigotmc.org/members/ipat.181124/
    // https://www.spigotmc.org/threads/world-copy.37932/

    public static boolean unloadWorld(World world) {
        return world!=null && Bukkit.getServer().unloadWorld(world, false);
    }

    public static void copyWorld(World originalWorld, String newWorldName) {
        copyFileStructure(originalWorld.getWorldFolder(), new File(Bukkit.getWorldContainer(), newWorldName));
        new WorldCreator(newWorldName).createWorld();
    }

    private static void copyFileStructure(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String[] files = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyFileStructure(srcFile, destFile);
                    }
                } else {
                    InputStream in = Files.newInputStream(source.toPath());
                    OutputStream out = Files.newOutputStream(target.toPath());
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteAFileStructure(File target) {
        // TODO: implement
        throw new NotImplementedException();
    }

}
