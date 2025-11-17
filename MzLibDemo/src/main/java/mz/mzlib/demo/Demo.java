package mz.mzlib.demo;

import mz.mzlib.demo.game.tictactoe.Tictactoe;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftJsUtil;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.recipe.RecipeRegistration;
import mz.mzlib.minecraft.recipe.RecipeVanillaShaped;
import mz.mzlib.minecraft.recipe.VanillaIngredient;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.Config;
import mz.mzlib.util.IOUtil;
import mz.mzlib.util.Option;
import mz.mzlib.util.RuntimeUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;

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
            try(InputStream is = IOUtil.openFileInZip(this.jar, "config.js"))
            {
                this.config = Config.loadJs(MinecraftJsUtil.initScope(), is, new File(this.dataFolder, "config.js"));
            }

            this.register(this.permission);
            this.register(I18n.load(this.jar, "lang", 0));
            this.register(this.command = new Command("mzlibdemo").setNamespace("mzlibdemo")
                .setPermissionChecker(sender -> Command.checkPermission(sender, this.permission)));

            this.register(DemoReload.instance);
            this.register(Tictactoe.instance);
            this.register(DemoBookUi.instance);
            this.register(Inventory10Slots.instance);
            this.register(DemoUIInput.instance);
            this.register(ExampleAsyncFunction.instance);
            this.register(DemoTest.instance);

            this.register(RecipeRegistration.of(
                Identifier.newInstance("mzlib:test"), RecipeVanillaShaped.builder()
                    .id(Identifier.newInstance("mzlib:test"))
                    .width(1).height(1).ingredients(
                        Collections.singletonList(
                            Option.some(VanillaIngredient.ofItem(Item.fromId("stick")))))
                    .result(ItemStack.factory().fromId("apple").build()).build()
            ));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
