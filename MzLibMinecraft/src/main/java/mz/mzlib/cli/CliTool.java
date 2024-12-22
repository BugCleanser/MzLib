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
        for(;;) {
            try {
                cmd = parser.parse(options, args);

//                MapperCli.cli(cmd);
                System.out.println(CliSorter.getCliMap("net.minecraft.text.Text"));

                if (cmd.hasOption("help")) {
                    formatter.printHelp(getJarName(), options);
                    return;
                }

                if (cmd.hasOption("mapper")) {
                    MapperCli.cli(cmd);
                    return;
                }

                if (cmd.getOptions().length == 0) {
                    System.out.println("未提供任何选项。");
                    formatter.printHelp(getJarName(), options);
                    return;
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                formatter.printHelp(getJarName(), options);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private static String getJarName()
    {
        String command = System.getProperty("sun.java.command");
        String[] parts = command.split("[\\\\/]");
        for(String part: parts)
        {
            if(part.endsWith(".jar"))
            {
                return "java -jar "+part;
            }
        }
        return "MzLibCliTool";
    }
}
