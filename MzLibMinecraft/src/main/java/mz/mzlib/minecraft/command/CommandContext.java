package mz.mzlib.minecraft.command;

import mz.mzlib.minecraft.command.argument.ArgumentsReader;
import mz.mzlib.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CommandContext
{
    public CommandSender sender;
    public String command;
    public ArgumentsReader argsReader;
    public boolean doExecute;
    
    public CommandContext(CommandSender sender, String command, String args, boolean doExecute)
    {
        this.sender = sender;
        this.command = command;
        this.argsReader = new ArgumentsReader(args);
        this.doExecute = doExecute;
    }
    
    public List<String> argNames = new ArrayList<>();
    public boolean successful = true;
    
    public CommandContext(CommandContext trunk)
    {
        this.sender = trunk.sender;
        this.command = trunk.command;
        this.argsReader = trunk.argsReader.clone();
        this.doExecute = trunk.doExecute;
        this.argNames = new ArrayList<>(trunk.argNames);
        this.successful = trunk.successful;
        
        this.suggestions = new ArrayList<>();
        this.argErrors = new ArrayList<>();
        this.forks = new ArrayList<>();
    }
    
    public List<String> suggestions = new ArrayList<>();
    public List<Text> argErrors = new ArrayList<>();
    public List<CommandContext> forks = new ArrayList<>();
    
    public void addArgError(Text error)
    {
        this.successful=false;
        this.argErrors.add(error);
    }
    
    public CommandContext fork()
    {
        CommandContext result = new CommandContext(this);
        this.forks.add(result);
        return result;
    }
    
    public List<List<String>> getAllArgNames()
    {
        List<List<String>> result = new ArrayList<>();
        for(CommandContext fork: this.forks)
            result.addAll(fork.getAllArgNames());
        result.add(this.argNames);
        return result;
    }
    
    public boolean isAnySuccessful()
    {
        return this.successful || this.forks.stream().anyMatch(CommandContext::isAnySuccessful);
    }
    
    public List<String> getAllSuggestions()
    {
        List<String> result = new ArrayList<>();
        for(CommandContext fork: this.forks)
            result.addAll(fork.getAllSuggestions());
        result.addAll(this.suggestions);
        return result;
    }
    
    public List<Text> getAllArgErrors()
    {
        List<Text> result = new ArrayList<>();
        for(CommandContext fork: this.forks)
            result.addAll(fork.getAllArgErrors());
        result.addAll(this.argErrors);
        return result;
    }
}
