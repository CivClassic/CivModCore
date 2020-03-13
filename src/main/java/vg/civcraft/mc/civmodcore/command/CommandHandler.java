package vg.civcraft.mc.civmodcore.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vg.civcraft.mc.civmodcore.util.TextUtil;

public abstract class CommandHandler {

    private static final String cmdMustBePlayer = "<b>This command can only be used by in-game players.";

    public Map<String, Command> commands = new HashMap<>();

    public abstract void registerCommands();

    protected void addCommands(Command command) {
        commands.put(command.getIdentifier().toLowerCase(), command);
    }

    public boolean execute(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
        if (commands.containsKey(cmd.getName().toLowerCase())) {
            Command command = commands.get(cmd.getName().toLowerCase());

            if (command.getSenderMustBePlayer() && (!(sender instanceof Player))) {
                sender.sendMessage(TextUtil.parse(cmdMustBePlayer));
                return true;
            }

            if (args.length < command.getMinArguments() || (command.getErrorOnTooManyArgs() && args.length > command.getMaxArguments())) {
                helpPlayer(command, sender);
                return true;
            }

            command.setSender(sender);
            command.setArgs(args);
            command.execute(sender, args);
        }
        return true;
    }

    public List<String> complete(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
        if (commands.containsKey(cmd.getName().toLowerCase())) {
            Command command = commands.get(cmd.getName().toLowerCase());

            if (command.getSenderMustBePlayer() && (!(sender instanceof Player))) {
                sender.sendMessage(TextUtil.parse(cmdMustBePlayer));
                return null;
            }

            command.setSender(sender);
            command.setArgs(args);
            List<String> completes = command.tabComplete(sender, args);
            String completeArg;
            if (args.length == 0) {
                completeArg = "";
            }
            else {
                completeArg = args[args.length - 1];
            }
            if (completes == null) {
                completes = new LinkedList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (TextUtil.startsWith(player.getName(), completeArg)) {
                        completes.add(player.getName());
                    }
                }
                return completes;
            }
            else {
                return completes;
            }
        }
        return null;
    }

    protected void helpPlayer(Command command, CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Command: " + command.getName());
        sender.sendMessage(ChatColor.RED + "Description: " + command.getDescription());
        sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
    }

}
