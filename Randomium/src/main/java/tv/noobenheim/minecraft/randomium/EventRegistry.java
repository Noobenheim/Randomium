package tv.noobenheim.minecraft.randomium;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import tv.noobenheim.minecraft.randomium.common.blocks.ModBlocks;
import tv.noobenheim.minecraft.randomium.generation.OreGeneration;
import tv.noobenheim.minecraft.randomium.setup.ModSetup;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
    	event.getRegistry().register(ModBlocks.RANDOMIUM);
    	event.getRegistry().register(ModBlocks.NETHER_RANDOMIUM);
    	event.getRegistry().register(ModBlocks.END_RANDOMIUM);
    }
    
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
    	Item.Properties properties = new Item.Properties().group(ModSetup.itemGroup);
    	
    	event.getRegistry().register(new BlockItem(ModBlocks.RANDOMIUM, properties).setRegistryName(Const.RandomiumOre.OVERWORLD.getName()));
    	event.getRegistry().register(new BlockItem(ModBlocks.NETHER_RANDOMIUM, properties).setRegistryName(Const.RandomiumOre.NETHER.getName()));
    	event.getRegistry().register(new BlockItem(ModBlocks.END_RANDOMIUM, properties).setRegistryName(Const.RandomiumOre.END.getName()));
    }
    
    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
    	OreGeneration.setupOreGeneration();
    }
	
	@SubscribeEvent
	public static void onConfigLoad(final ModConfig.Loading configEvent) {
		
	}
	
	@SubscribeEvent
	public static void onConfigReload(final ModConfig.ConfigReloading configEvent) {
		Config.syncConfig();
	}
	
	@SubscribeEvent
    public static void onConfigChanged(OnConfigChangedEvent event) {
    	if( event.getModID().equals(Const.MOD_ID) ) {
    		Config.syncConfig();
    	}
    }
}
