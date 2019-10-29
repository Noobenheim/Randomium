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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
			double weight;
			try {
				weight = Double.parseDouble(split[2]);
				
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
	
	public void addToDropTable(Item item, double weight, int minAmount, int maxAmount) {
		this.dropTable.add(new ItemDrop(item, weight, minAmount, maxAmount));
		this.totalWeight += weight;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		super.onReplaced(state, worldIn, pos, newState, isMoving);
		
		ItemDrop item = getRandomItem();
		
		if( item != null ) {
			// get amount
			int amount = item.getMinAmount();
			if( amount != item.getMaxAmount() ) {
				amount = random.nextInt(item.getMaxAmount()-item.getMinAmount())+item.getMinAmount();
			}
			spawnAsEntity(worldIn, pos, new ItemStack(item.getItem(), amount));
		}
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
	
	class ItemDrop {
		private Item item;
		private double weight;
		private int minAmount;
		private int maxAmount;
		
		public ItemDrop(Item item, double weight, int minAmount, int maxAmount) {
			this.item = item;
			this.weight = weight;
			this.minAmount = minAmount;
			this.maxAmount = maxAmount;
		}

		public Item getItem() {
			return item;
		}

		public double getWeight() {
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