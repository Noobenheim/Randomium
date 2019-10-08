package tv.noobenheim.minecraft.randomium.generation;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import tv.noobenheim.minecraft.randomium.Config;
import tv.noobenheim.minecraft.randomium.common.blocks.ModBlocks;

public class OreGeneration {
	private static final CountRangeConfig cfgOverworld = new CountRangeConfig(
															Config.RANDOMIUM_ORE_VEINS_PER_CHUNK_OVERWORLD.get(),
															Config.RANDOMIUM_ORE_LOWEST_Y_OVERWORLD.get(),
															0,
															Config.RANDOMIUM_ORE_HIGHEST_Y_OVERWORLD.get());
	private static final CountRangeConfig cfgNether = new CountRangeConfig(
															Config.RANDOMIUM_ORE_VEINS_PER_CHUNK_NETHER.get(),
															Config.RANDOMIUM_ORE_LOWEST_Y_NETHER.get(),
															0,
															Config.RANDOMIUM_ORE_HIGHEST_Y_NETHER.get());
	private static final CountRangeConfig cfgEnd = new CountRangeConfig(
															Config.RANDOMIUM_ORE_VEINS_PER_CHUNK_END.get(),
															Config.RANDOMIUM_ORE_LOWEST_Y_END.get(),
															0,
															Config.RANDOMIUM_ORE_HIGHEST_Y_END.get());
	
	public static void setupOreGeneration() {
		for( Biome biome : ForgeRegistries.BIOMES.getValues() ) {
			if( biome.getCategory() == Biome.Category.THEEND ) {
				if( !Config.RANDOMIUM_ORE_IN_END.get() )
					continue;
				
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					Biome.createDecoratedFeature(
						CustomBlockFeature.CUSTOM_ORE,
						new CustomBlockConfig(
							CustomBlockConfig.CustomBlockType.END_STONE,
							ModBlocks.END_RANDOMIUM.getDefaultState(),
							Config.RANDOMIUM_ORE_VEIN_SIZE_END.get()
						),
						Placement.COUNT_RANGE,
						cfgEnd
					)
				);
				continue;
			} else if( biome.getCategory() == Biome.Category.NETHER ) {
				if( !Config.RANDOMIUM_ORE_IN_NETHER.get() )
					continue;
				
				biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					Biome.createDecoratedFeature(
						Feature.ORE,
						new OreFeatureConfig(
							OreFeatureConfig.FillerBlockType.NETHERRACK,
							ModBlocks.NETHER_RANDOMIUM.getDefaultState(),
							Config.RANDOMIUM_ORE_VEIN_SIZE_NETHER.get()
						),
						Placement.COUNT_RANGE,
						cfgNether
					)
				);
				continue;
			}
			// overworld
			if( !Config.RANDOMIUM_ORE_IN_OVERWORLD.get() )
				continue;
			
			biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
					Biome.createDecoratedFeature(
					Feature.ORE,
					new OreFeatureConfig(
						OreFeatureConfig.FillerBlockType.NATURAL_STONE,
						ModBlocks.RANDOMIUM.getDefaultState(),
						Config.RANDOMIUM_ORE_VEIN_SIZE_OVERWORLD.get()
					),
					Placement.COUNT_RANGE,
					cfgOverworld
				)
			);
		}
	}
}
