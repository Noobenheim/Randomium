package tv.noobenheim.minecraft.randomium.generation;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CustomBlockConfig implements IFeatureConfig {
	public final CustomBlockConfig.CustomBlockType target;
	public final int size;
	public final BlockState state;

	public CustomBlockConfig(CustomBlockConfig.CustomBlockType target, BlockState state, int size) {
		this.state = state;
		this.size = size;
		this.target = target;
	}

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic<>(ops, ops.createMap(
			ImmutableMap.of(
				ops.createString("size"), ops.createInt(this.size),
				ops.createString("target"), ops.createString(this.target.getName()),
				ops.createString("state"), BlockState.serialize(ops, this.state).getValue()
			)));
	}

	public static <T> CustomBlockConfig deserialize(Dynamic<T> dynamic) {
		int size = dynamic.get("size").asInt(0);
		CustomBlockConfig.CustomBlockType targetBlockType = CustomBlockConfig.CustomBlockType.getFromString(dynamic.get("target").asString(""));
		BlockState blockstate = dynamic.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		return new CustomBlockConfig(targetBlockType, blockstate, size);
	}
	   
	public static enum CustomBlockType {
		END_STONE("end_stone", (blockMatch) -> {
			if( blockMatch == null ) {
				return false;
			} else {
				Block block = blockMatch.getBlock();
				return block == Blocks.END_STONE;
			}
		});
		   
		private static final Map<String, CustomBlockConfig.CustomBlockType> vals = Arrays.stream(values()).collect(Collectors.toMap(CustomBlockConfig.CustomBlockType::getName,  (name) -> {
			return name;
		}));
		private final String name;
		private final Predicate<BlockState> blockState;
		   
		private CustomBlockType(String name, Predicate<BlockState> blockState) {
			this.name = name;
			this.blockState = blockState;
		}
		   
		public String getName() {
			return this.name;
		}
		   
		public static CustomBlockConfig.CustomBlockType getFromString(String desc) {
			return vals.get(desc);
		}
		   
		public Predicate<BlockState> getBlockState() {
			return this.blockState;
		}
	}
}