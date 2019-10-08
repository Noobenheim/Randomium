package tv.noobenheim.minecraft.randomium.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import tv.noobenheim.minecraft.randomium.common.blocks.ModBlocks;

public class ClientProxy implements IProxy {
	@Override
	public void init() {
		//ScreenManager.registerFactory(ModBlocks.RANDOMIUM_CONTAINER, RandomiumScreen::new);
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

}
