package tv.noobenheim.minecraft.randomium.common.blocks;

import net.minecraftforge.registries.ObjectHolder;
import tv.noobenheim.minecraft.randomium.Const;

public class ModBlocks {
	@ObjectHolder(Const.RANDOMIUM_ORE_OVERWORLD)
	public static RandomiumOre RANDOMIUM;
	
	@ObjectHolder(Const.RANDOMIUM_ORE_NETHER)
	public static RandomiumOre NETHER_RANDOMIUM = new RandomiumOre(Const.RandomiumOre.NETHER);
	
	@ObjectHolder(Const.RANDOMIUM_ORE_END)
	public static RandomiumOre END_RANDOMIUM = new RandomiumOre(Const.RandomiumOre.END);
}