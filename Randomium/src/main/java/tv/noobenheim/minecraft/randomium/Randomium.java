package tv.noobenheim.minecraft.randomium;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import tv.noobenheim.minecraft.randomium.common.blocks.ModBlocks;
import tv.noobenheim.minecraft.randomium.common.blocks.RandomiumOre;
import tv.noobenheim.minecraft.randomium.generation.OreGeneration;
import tv.noobenheim.minecraft.randomium.setup.ClientProxy;
import tv.noobenheim.minecraft.randomium.setup.IProxy;
import tv.noobenheim.minecraft.randomium.setup.ModSetup;
import tv.noobenheim.minecraft.randomium.setup.ServerProxy;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Randomium.MOD_ID)
public class Randomium {
	public static final String MOD_ID = Const.MOD_ID;
	public static final String MOD_NAME = Const.MOD_NAME;
	public static final String VERSION = Const.VERSION;
	
	public static IProxy proxy = DistExecutor.runForDist( ()-> ()-> new ClientProxy(), ()-> ()-> new ServerProxy());
	
	public static ModSetup setup = new ModSetup();

    public Randomium() {
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("randomium-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        setup.init();
        proxy.init();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        	event.getRegistry().register(new RandomiumOre());
        	event.getRegistry().register(new RandomiumOre(Const.RandomiumOre.NETHER));
        	event.getRegistry().register(new RandomiumOre(Const.RandomiumOre.END));
        }
        
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        	Item.Properties properties = new Item.Properties().group(setup.itemGroup);
        	
        	event.getRegistry().register(new BlockItem(ModBlocks.RANDOMIUM, properties).setRegistryName(Const.RandomiumOre.OVERWORLD.getName()));
        	event.getRegistry().register(new BlockItem(ModBlocks.NETHER_RANDOMIUM, properties).setRegistryName(Const.RandomiumOre.NETHER.getName()));
        	event.getRegistry().register(new BlockItem(ModBlocks.END_RANDOMIUM, properties).setRegistryName(Const.RandomiumOre.END.getName()));
        }
        
        @SubscribeEvent
        public static void onCommonSetup(final FMLCommonSetupEvent event) {
        	OreGeneration.setupOreGeneration();
        }
    }
}