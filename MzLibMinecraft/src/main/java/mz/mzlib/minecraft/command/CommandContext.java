package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.GameObject;
import mz.mzlib.minecraft.command.argument.ArgumentsReader;

import java.util.ArrayList;
import java.util.List;

public class CommandContext
{
    public GameObject sender;
    public String command;
    public ArgumentsReader argsReader;
    public boolean doExecute;
    
    public CommandContext(GameObject sender, String command, String args, boolean doExecute)
    {
        this.sender = sender;
        this.command = command;
        this.argsReader = new ArgumentsReader(args);
        this.doExecute = doExecute;
    }
    
    public List<String> argNames = new ArrayList<>();
    
    public boolean successful = true;
    public List<String> suggestions = new ArrayList<>();
}
