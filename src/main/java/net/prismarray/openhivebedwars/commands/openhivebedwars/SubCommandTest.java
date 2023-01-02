package net.prismarray.openhivebedwars.commands.openhivebedwars;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.prismarray.openhivebedwars.OpenHiveBedwars;
import net.prismarray.openhivebedwars.commands.PluginBoundCommandExecutor;
import net.prismarray.openhivebedwars.util.Particles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandTest extends PluginBoundCommandExecutor {

    public SubCommandTest(OpenHiveBedwars plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.game.teamSummoners.get(0).upgrade(Integer.parseInt(args[0]));
        return true;
    }
}
