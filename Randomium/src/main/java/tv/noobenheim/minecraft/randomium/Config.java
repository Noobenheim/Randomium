package tv.noobenheim.minecraft.randomium;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {
	public static final String CATEGORY_GENERAL = "general";
	public static final String SUBCATEGORY_RANDOMIUM = "randomium";
	
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	
	public static ForgeConfigSpec COMMON_CONFIG;

	public static ForgeConfigSpec.BooleanValue RANDOMIUM_ORE_IN_END;
	public static ForgeConfigSpec.BooleanValue RANDOMIUM_ORE_IN_OVERWORLD;
	public static ForgeConfigSpec.BooleanValue RANDOMIUM_ORE_IN_NETHER;

	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_VEIN_SIZE_END;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_VEIN_SIZE_OVERWORLD;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_VEIN_SIZE_NETHER;

	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_VEINS_PER_CHUNK_END;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_VEINS_PER_CHUNK_OVERWORLD;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_VEINS_PER_CHUNK_NETHER;

	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_LOWEST_Y_OVERWORLD;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_HIGHEST_Y_OVERWORLD;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_LOWEST_Y_NETHER;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_HIGHEST_Y_NETHER;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_LOWEST_Y_END;
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_HIGHEST_Y_END;
	
	public static ForgeConfigSpec.IntValue RANDOMIUM_ORE_HARVEST_LEVEL;
	
	static {
		COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);
		COMMON_BUILDER.pop();
		
		setupRandomiumConfig();
		
		COMMON_CONFIG = COMMON_BUILDER.build();
	}
	
	private static void setupRandomiumConfig() {
		COMMON_BUILDER.comment("Randomium Ore Settings").push(SUBCATEGORY_RANDOMIUM);

		RANDOMIUM_ORE_HARVEST_LEVEL = COMMON_BUILDER.comment("Pickaxe Harvest Level for Randomium Ore")
			.defineInRange("randomiumOre.harvestLevel", 2, 1, 32);
		
		RANDOMIUM_ORE_IN_OVERWORLD = COMMON_BUILDER.comment("Whether or not you want Randomium Ore to spawn in the Overworld")
			.define("randomiumOre.overworld.enabled", true);
		RANDOMIUM_ORE_VEIN_SIZE_OVERWORLD = COMMON_BUILDER.comment("Vein size for Randomium Ore in the Overworld")
			.defineInRange("randomiumOre.overworld.veinsize", 4, 1, Integer.MAX_VALUE);
		RANDOMIUM_ORE_VEINS_PER_CHUNK_OVERWORLD = COMMON_BUILDER.comment("Maximum number of Randomium Ore veins per chunk in the Overworld")
			.defineInRange("randomiumOre.overworld.maxveins", 6, 1, 1_000_000);
		RANDOMIUM_ORE_LOWEST_Y_OVERWORLD = COMMON_BUILDER.comment("Lowest Y that Randomium Ore can spawn in the Overworld")
			.defineInRange("randomiumOre.overworld.minY", 0, 0, 256);
		RANDOMIUM_ORE_HIGHEST_Y_OVERWORLD = COMMON_BUILDER.comment("Highest Y that Randomium Ore can spawn in the Overworld")
				.defineInRange("randomiumOre.overworld.maxY", 40, 0, 256);

		RANDOMIUM_ORE_IN_NETHER = COMMON_BUILDER.comment("Whether or not you want Randomium Ore to spawn in the Nether")
			.define("randomiumOre.nether.enabled", true);
		RANDOMIUM_ORE_VEIN_SIZE_NETHER = COMMON_BUILDER.comment("Vein size for Randomium Ore in the Nether")
			.defineInRange("randomiumOre.nether.veinsize", 4, 1, Integer.MAX_VALUE);
		RANDOMIUM_ORE_VEINS_PER_CHUNK_NETHER = COMMON_BUILDER.comment("Maximum number of Randomium Ore veins per chunk in the Nether")
			.defineInRange("randomiumOre.nether.maxveins", 6, 1, 1_000_000);
		RANDOMIUM_ORE_LOWEST_Y_NETHER = COMMON_BUILDER.comment("Lowest Y that Randomium Ore can spawn in the Nether")
				.defineInRange("randomiumOre.nether.minY", 0, 0, 256);
		RANDOMIUM_ORE_HIGHEST_Y_NETHER = COMMON_BUILDER.comment("Highest Y that Randomium Ore can spawn in the Nether")
				.defineInRange("randomiumOre.nether.maxY", 256, 0, 256);
		
		RANDOMIUM_ORE_IN_END = COMMON_BUILDER.comment("Whether or not you want Randomium Ore to spawn in The End")
			.define("randomiumOre.end.enabled", true);
		RANDOMIUM_ORE_VEIN_SIZE_END = COMMON_BUILDER.comment("Vein size for Randomium Ore in The End")
			.defineInRange("randomiumOre.end.veinsize", 4, 1, Integer.MAX_VALUE);
		RANDOMIUM_ORE_VEINS_PER_CHUNK_END = COMMON_BUILDER.comment("Maximum number of Randomium Ore veins per chunk in The End")
			.defineInRange("randomiumOre.end.maxveins", 6, 1, 1_000_000);
		RANDOMIUM_ORE_LOWEST_Y_END = COMMON_BUILDER.comment("Lowest Y that Randomium Ore can spawn in The End")
				.defineInRange("randomiumOre.end.minY", 0, 0, 256);
		RANDOMIUM_ORE_HIGHEST_Y_END = COMMON_BUILDER.comment("Highest Y that Randomium Ore can spawn in The End")
				.defineInRange("randomiumOre.end.maxY", 256, 0, 256);
		
		
		COMMON_BUILDER.pop();
	}
	
	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		
		configData.load();
		spec.setConfig(configData);
	}
	
	public static void syncConfig() {
		
	}
	
	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		
	}
	
	@SubscribeEvent
	public static void onReload(final ModConfig.ConfigReloading configEvent) {
		
	}
	
	@SubscribeEvent
    public static void onConfigChanged(OnConfigChangedEvent event) {
    	if( event.getModID().equals(Const.MOD_ID) ) {
    		syncConfig();
    	}
    }
}