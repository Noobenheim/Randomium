package tv.noobenheim.minecraft.randomium.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import tv.noobenheim.minecraft.randomium.Config;
import tv.noobenheim.minecraft.randomium.Const;
import net.minecraftforge.common.ToolType;

public class RandomiumOre extends Block {
	public RandomiumOre() {
		this(Const.RandomiumOre.OVERWORLD);
	}
	public RandomiumOre(Const.RandomiumOre type) {
		super(
			Properties.create(Material.ROCK)
			.sound(SoundType.METAL)
			.hardnessAndResistance(5.0f)
			.harvestLevel(Config.RANDOMIUM_ORE_HARVEST_LEVEL.get())
			.harvestTool(ToolType.PICKAXE)
		);
		setRegistryName(type.getName());
	}
}