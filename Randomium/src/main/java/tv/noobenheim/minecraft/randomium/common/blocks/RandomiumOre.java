package tv.noobenheim.minecraft.randomium.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;
import tv.noobenheim.minecraft.randomium.Config;
import tv.noobenheim.minecraft.randomium.Const;

public class RandomiumOre extends Block {
	private Const.RandomiumOre type;
	private List<ItemDrop> dropTable = null;
	private double totalWeight = 0.0;
	
	private Random random = new Random();
	
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
		this.type = type;
	}
	
	public void readDropTable() {
		switch( type ) {
			case OVERWORLD:
				loadDropTable(Config.RANDOMIUM_ORES_OVERWORLD_ORES.get());
				break;
			case NETHER:
				loadDropTable(Config.RANDOMIUM_ORES_NETHER_ORES.get());
				break;
			case END:
				loadDropTable(Config.RANDOMIUM_ORES_END_ORES.get());
				break;
		}
	}
	
	public void loadDropTable(List<? extends String> drops) {
		// reset tables
		if( dropTable != null ) {
			dropTable.clear();
		} else {
			dropTable = new ArrayList<>();
		}
		totalWeight = 0.0;
		
		for( String string : drops ) {
			String[] split = string.split(":");
			if( split.length < 3 ) {
				continue;
			}
			String domain = split[0];
			String name = split[1];
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(domain, name));
			if( item == null ) {
				continue;
			}
			int weight;
			try {
				weight = Integer.parseInt(split[2]);
				
				int minAmount = 1;
				if( split.length >= 4 ) {
					minAmount = Integer.parseInt(split[3]);
				}
				int maxAmount = 1;
				if( split.length >= 5 ) {
					maxAmount = Integer.parseInt(split[4]);
				}
				addToDropTable(item, weight, minAmount, maxAmount);
			} catch( NumberFormatException e ) {
				continue;
			}
		}
	}
	
	public void addToDropTable(Item item, int weight, int minAmount, int maxAmount) {
		this.dropTable.add(new ItemDrop(item, weight, minAmount, maxAmount));
		this.totalWeight += weight;
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> stack = new ArrayList<>();
		ItemDrop drop = getRandomItem();
		if( drop != null ) {
			int amount = drop.getMinAmount();
			if( amount != drop.getMaxAmount() ) {
				amount = random.nextInt(drop.getMaxAmount()-drop.getMinAmount())+drop.getMinAmount();
			}
			stack.add(new ItemStack(drop.getItem(), amount));
		}
		
		return stack;
	}
	
	public ItemDrop getRandomItem() {
		if( dropTable == null ) {
			readDropTable();
		}
		double randomNumber = random.nextDouble()*totalWeight;
		
		double cumulativeWeight = 0.0;
		for( ItemDrop drop : dropTable ) {
			cumulativeWeight += drop.getWeight();
			if( cumulativeWeight > randomNumber ) {
				return drop;
			}
		}
		
		return null;
	}
	
	private class ItemDrop {
		private Item item;
		private int weight;
		private int minAmount;
		private int maxAmount;
		
		public ItemDrop(Item item, int weight, int minAmount, int maxAmount) {
			this.item = item;
			this.weight = weight;
			this.minAmount = minAmount;
			this.maxAmount = maxAmount;
		}

		public Item getItem() {
			return item;
		}

		public int getWeight() {
			return weight;
		}

		public int getMinAmount() {
			return minAmount;
		}

		public int getMaxAmount() {
			return maxAmount;
		}
	}
}