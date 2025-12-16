package mz.mzlib.demo;

import mz.mzlib.demo.game.tictactoe.Tictactoe;
import mz.mzlib.i18n.I18n;
import mz.mzlib.minecraft.Identifier;
import mz.mzlib.minecraft.MinecraftJsUtil;
import mz.mzlib.minecraft.MinecraftServer;
import mz.mzlib.minecraft.command.ChildCommandRegistration;
import mz.mzlib.minecraft.command.Command;
import mz.mzlib.minecraft.item.Item;
import mz.mzlib.minecraft.item.ItemStack;
import mz.mzlib.minecraft.permission.Permission;
import mz.mzlib.minecraft.recipe.IngredientVanilla;
import mz.mzlib.minecraft.recipe.RegistrarRecipeVanilla;
import mz.mzlib.minecraft.recipe.crafting.RecipeCraftingShaped;
import mz.mzlib.minecraft.recipe.smelting.RecipeFurnace;
import mz.mzlib.minecraft.text.Text;
import mz.mzlib.minecraft.ui.UiStack;
import mz.mzlib.minecraft.ui.window.UiWindowRect;
import mz.mzlib.minecraft.ui.window.control.UiWindowList;
import mz.mzlib.module.MzModule;
import mz.mzlib.util.*;

import java.awt.*;
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
            this.register(this.command = new Command("mzlibdemo", "mzd").setNamespace("mzlibdemo")
                .setPermissionChecker(sender -> Command.checkPermission(sender, this.permission)));
            this.register(new ChildCommandRegistration(
                this.command, new Command("list")
                .setPermissionChecker(Command::checkPermissionSenderPlayer)
                .setHandler(context ->
                {
                    UiWindowRect ui = new UiWindowRect(6);
                    ui.region.addChild(UiWindowList.overlappedBuilder(CollectionUtil.newArrayList("aaa", "bbb"))
                        .size(new Dimension(9, 10))
                        .iconGetter(entry -> ItemStack.builder().fromId("stick").customName(
                            Text.literal(entry.getElement())).build())
                        .viewer((entry -> entry.getPlayer().sendMessage(Text.literal(entry.getElement()))))
                        .adder(() -> "new")
                        .build());
                    UiStack.get(context.getSource().getPlayer().unwrap()).go(ui);
//                    UiStack.get(context.getSource().getPlayer().unwrap())
//                        .go(UiWindowList.builder(CollectionUtil.newArrayList("aaa", "bbb"))
//                            .iconGetter((value, player) -> ItemStack.builder().fromId("stick").customName(
//                                Text.literal(value)).build())
//                            .adder(() -> "new")
//                            .remover()
//                            .viewer(((ui, player, index) -> player.sendMessage(Text.literal(ui.getList().get(index)))))
//                            .build());
                })
            ));

            this.register(DemoReload.instance);
            this.register(Tictactoe.instance);
            this.register(DemoBookUi.instance);
            this.register(Inventory10Slots.instance);
            this.register(DemoUIInput.instance);
            this.register(ExampleAsyncFunction.instance);
            this.register(DemoTest.instance);

            this.register(RecipeCraftingShaped.builder()
                .id(Identifier.newInstance("mzlib:test"))
                .width(1).height(1).ingredients(
                    Collections.singletonList(
                        Option.some(IngredientVanilla.of(ItemStack.newInstance(Item.fromId("stick"))))))
                .result(ItemStack.builder().fromId("apple").build()).buildRegistration());
            this.register(RecipeFurnace.builder()
                .id(Identifier.newInstance("mzlib:test_smelting"))
                .ingredient(ItemStack.newInstance(Item.fromId("stick")))
                .result(ItemStack.builder().fromId("apple").build())
                .experience(100.f)
                .buildRegistration());
            MinecraftServer.instance.schedule(
                () -> System.out.println(RegistrarRecipeVanilla.instance.getEnabledRecipes()));
        }
        catch(Throwable e)
        {
            throw RuntimeUtil.sneakilyThrow(e);
        }
    }
}
