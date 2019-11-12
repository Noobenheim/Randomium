package tv.noobenheim.minecraft.randomium.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tv.noobenheim.minecraft.randomium.common.blocks.ModBlocks;

public class ModSetup {
	public static ItemGroup itemGroup = new ItemGroup("randomium") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModBlocks.RANDOMIUM);
		}
	};
	
	public static void init() {
		
	}
}