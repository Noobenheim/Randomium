package tv.noobenheim.minecraft.randomium;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
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

    public Randomium() {
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(EventRegistry.class);
        
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("randomium-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModSetup.init();
        proxy.init();
    }
}