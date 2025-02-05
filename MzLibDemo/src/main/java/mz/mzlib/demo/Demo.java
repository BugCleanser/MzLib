package mz.mzlib.demo;

import mz.mzlib.demo.game.tictactoe.Tictactoe;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Config;
import mz.mzlib.util.RuntimeUtil;

import java.io.File;
import java.util.Objects;

public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public File jar;
    public File dataFolder;
    
    public Config config;
    
    public Permission permission = new Permission("mzlibdemo.command.mzlibdemo");
    public Command command;
    
    @Override
    public void onLoad()
    {
        try
        {
            this.config = Config.load(Objects.requireNonNull(this.getClass().getResourceAsStream("/config.json")), new File(this.dataFolder, "config.json"));
            
            this.register(this.permission);
            this.register(I18n.load(this.jar, "lang", 0));
            this.register(this.command = new Command("mzlibdemo").setNamespace("mzlibdemo").setPermissionChecker(sender->Command.checkPermission(sender, this.permission)));
            
            this.register(DemoReload.instance);
            this.register(Tictactoe.instance);
            this.register(DemoBookUi.instance);
            this.register(Inventory10Slots.instance);
            this.register(DemoUIInput.instance);
            this.register(ExampleAsyncFunction.instance);
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
