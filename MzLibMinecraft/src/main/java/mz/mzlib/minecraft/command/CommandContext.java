package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandContext
{
    public GameObject sender;
    public String command;
    public Scanner argsReader;
    public boolean doExecute;
    
    public CommandContext(GameObject sender, String command, String args, boolean doExecute)
    {
        this.sender = sender;
        this.command = command;
        this.argsReader = new Scanner(args);
        this.doExecute = doExecute;
    }
    
    public List<String> argNames=new ArrayList<>();
    
    public boolean successful=true;
    public List<String> suggestions=new ArrayList<>();
}
