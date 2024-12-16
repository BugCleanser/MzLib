package mz.mzlib.cli;

import org.apache.commons.cli.*;

public class CliTool
{
    public static void main(String[] args)
    {
        Options options = new Options();
        
        Option helpOption = new Option("h", "help", false, "显示帮助信息");
        options.addOption(helpOption);
        
        Option mapperOption = new Option("m", "mapper", false, "运行 Mapper 脚手架");
        options.addOption(mapperOption);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        
        try {
            cmd = parser.parse(options, args);
            
            if (cmd.hasOption("help")) {
                formatter.printHelp("MzLibCliTool", options);
                return;
            }
            
            if (cmd.getOptions().length == 0) {
                System.out.println("未提供任何选项。");
                formatter.printHelp("MzLibCliTool", options);
                return;
            }
            
            if (cmd.hasOption("mapper")) {
                MapperCli.cli(cmd);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("MzLibCliTool", options);
        }
    }
}
