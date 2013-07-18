package bspkrs.worldstatecheckpoints;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.src.mod_WorldStateCheckpoints;

public class CommandWSC extends CommandBase
{
    
    @Override
    public String getCommandName()
    {
        return "wsc";
    }
    
    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "commands.wsc.usage";
    }
    
    @Override
    public void processCommand(ICommandSender icommandsender, String[] args)
    {
        if (args.length >= 1)
        {
            if (args[0].equalsIgnoreCase("save"))
            {
                String name = "";
                for (int i = 1; i < args.length; i++)
                    name += args[i] + " ";
                if (name.endsWith("!") || name.endsWith("."))
                {
                    throw new WrongUsageException("commands.wsc.load.usage");
                }
                mod_WorldStateCheckpoints.cpm.setCheckpoint(name.trim(), name.trim().isEmpty() || name.contains(CheckpointManager.AUTOSAVES_PREFIX));
                return;
            }
            else if (args[0].equalsIgnoreCase("load"))
            {
                if (args.length >= 2)
                {
                    String name = "";
                    for (int i = 1; i < args.length; i++)
                        name += args[i] + " ";
                    
                    String dirName = mod_WorldStateCheckpoints.cpm.getCheckpointDirNameFromDisplayName(name);
                    if (dirName != null)
                        mod_WorldStateCheckpoints.cpm.loadCheckpoint(dirName, dirName.contains(CheckpointManager.AUTOSAVES_PREFIX));
                    else
                        Minecraft.getMinecraft().thePlayer.addChatMessage(String.format("WSC: invalid checkpoint name \"%s\" specified in load command.", name));
                    
                    return;
                }
                else
                    throw new WrongUsageException("commands.wsc.load.usage");
            }
        }
        
        throw new WrongUsageException("commands.wsc.usage", new Object[0]);
    }
}
