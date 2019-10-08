package tv.noobenheim.minecraft.randomium;

public class Const {
	public static final String MOD_ID = "randomium";
	public static final String MOD_NAME = "Randomium";
	public static final String VERSION = "1.0.0";
	
	public static final String RESOURCE_PREFIX = MOD_ID+":";
	
	private static final String RANDOMIUM_ORE = "randomium_ore";
	public static final String RANDOMIUM_ORE_OVERWORLD = RESOURCE_PREFIX+RANDOMIUM_ORE;
	public static final String RANDOMIUM_ORE_NETHER = RESOURCE_PREFIX+"nether_"+RANDOMIUM_ORE;
	public static final String RANDOMIUM_ORE_END = RESOURCE_PREFIX+"end_"+RANDOMIUM_ORE;
	
	public static enum RandomiumOre {
		OVERWORLD(String.format(RANDOMIUM_ORE_OVERWORLD)),
		NETHER(RANDOMIUM_ORE_NETHER),
		END(RANDOMIUM_ORE_END);

		private final String name;
		
		private RandomiumOre(String name) {
			this.name = name;
		}
		public String getName() {
			return this.name;
		}
	}
}
