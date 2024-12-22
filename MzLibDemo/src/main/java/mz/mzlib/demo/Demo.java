package mz.mzlib.demo;

import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.module.MzModule;

public class Demo extends MzModule
{
    public static Demo instance = new Demo();
    
    public Permission permission=new Permission("mzlibdemo.command.mzlibdemo");
    public Command command;
    
    @Override
    public void onLoad()
    {
        this.register(this.permission);
        this.register(this.command =new Command("mzlibDemo".toLowerCase()).setNamespace("mzlibDemo".toLowerCase()).setPermissionChecker(sender->Command.checkPermission(sender, this.permission)));
        
        this.register(Inventory10Slots.instance);
        this.register(DemoBookUi.instance);
        this.register(ExampleAsyncFunction.instance);
    }
}
