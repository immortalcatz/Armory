package com.builtbroken.armory;

import com.builtbroken.armory.changes.VanillaChanges;
import com.builtbroken.armory.content.mediveal.MedivealModular;
import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.lib.mod.AbstractMod;
import com.builtbroken.mc.lib.mod.ModCreativeTab;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by robert on 11/18/2014.
 */
@Mod(modid = Armory.DOMAIN, name = Armory.NAME, version = Armory.VERSION, dependencies = "required-after:VoltzEngine")
public final class Armory extends AbstractMod
{
    /** Name of the channel and mod ID. */
    public static final String NAME = "Armory";
    public static final String DOMAIN = "armory";
    public static final String PREFIX = DOMAIN + ":";

    /** The version of WatchYourStep. */
    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;

    public static final String ASSETS_PATH = "/assets/armory/";
    public static final String TEXTURE_PATH = "textures/";
    public static final String GUI_PREFIX = "gui/";
    public static final String GUI_PATH = TEXTURE_PATH + GUI_PREFIX;
    public static final String MODEL_PREFIX = "models/";
    public static final String MODEL_DIRECTORY = ASSETS_PATH + MODEL_PREFIX;

    public static final String MODEL_TEXTURE_PATH = TEXTURE_PATH + MODEL_PREFIX;
    public static final String BLOCK_PATH = TEXTURE_PATH + "blocks/";
    public static final String ITEM_PATH = TEXTURE_PATH + "items/";

    @Mod.Instance(DOMAIN)
    public static Armory INSTANCE;

    @SidedProxy(clientSide = "com.builtbroken.armory.ClientProxy", serverSide = "com.builtbroken.armory.CommonProxy")
    public static CommonProxy proxy;

    public static ModCreativeTab CREATIVE_TAB;

    public Armory()
    {
        super(DOMAIN, "Armory");
        CREATIVE_TAB = new ModCreativeTab("armory");
        getManager().setTab(CREATIVE_TAB);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        ConfigurationArmory.syncConfig(getConfig());

        //Ore
        Engine.requestOres();
        loader.applyModule(new MedivealModular(getManager()));
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        //TODO Check if this event is actually fired, as it might be used only by GuiConfig screen which we don't use
        if (event.modID.equals(Armory.NAME))
        {
            //TODO might need to check if load is needed, and save is needed when this event is called
            getConfig().load();
            ConfigurationArmory.syncConfig(getConfig());
            //TODO exit MC world and/or game if config changes something important
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

        if (ConfigurationArmory.recipeToggle == ConfigurationArmory.RECIPETOGGLE_DEFAULT)
        {
            //TODO create config per armor set
            //TODO add configs to disable existing items
            //TODO add configs to disable mob drops of these items
            List<ItemStack> itemList = new LinkedList<ItemStack>();
            //Leather
            itemList.add(new ItemStack(Items.leather_helmet));
            itemList.add(new ItemStack(Items.leather_chestplate));
            itemList.add(new ItemStack(Items.leather_leggings));
            itemList.add(new ItemStack(Items.leather_boots));
            //Iron
            itemList.add(new ItemStack(Items.iron_helmet));
            itemList.add(new ItemStack(Items.iron_chestplate));
            itemList.add(new ItemStack(Items.iron_leggings));
            itemList.add(new ItemStack(Items.iron_boots));
            //Gold
            itemList.add(new ItemStack(Items.golden_helmet));
            itemList.add(new ItemStack(Items.golden_chestplate));
            itemList.add(new ItemStack(Items.golden_leggings));
            itemList.add(new ItemStack(Items.golden_boots));
            //Diamond
            itemList.add(new ItemStack(Items.diamond_helmet));
            itemList.add(new ItemStack(Items.diamond_chestplate));
            itemList.add(new ItemStack(Items.diamond_leggings));
            itemList.add(new ItemStack(Items.diamond_boots));

            VanillaChanges.RecipieRemover(itemList);
        }
        super.postInit(event);
    }

    @Override
    public CommonProxy getProxy()
    {
        return proxy;
    }
}
