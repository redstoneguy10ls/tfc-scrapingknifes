package com.redstoneguy10ls.tfcscraping;

import net.dries007.tfc.common.blockentities.ScrapingBlockEntity;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.redstoneguy10ls.tfcscraping.ScrapingHelper.ScalculatePoint;
import static com.redstoneguy10ls.tfcscraping.ScrapingHelper.SdoParticles;
import static net.dries007.tfc.common.blocks.devices.ScrapingBlock.WAXED;

public final class NeoForgeEvents {

	public static void init() {
		// Register all static @SubscribeEvent annotated event methods
		final IEventBus bus = NeoForge.EVENT_BUS;

		bus.addListener(NeoForgeEvents::onPlayerRightClickBlock);
		
	}

	private static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		//christ the spaghetti code

		//BLock entity variable
		BlockEntity block = event.getLevel().getBlockEntity(event.getPos());
		//the player doing the scraping
		Player player = event.getEntity();
		//is this server or client side?
		Level level = event.getLevel();
		//the blocks position
		BlockPos pos = event.getPos();
		//the hit result
		BlockHitResult hit = event.getHitVec();
		//which hand is doing the interacting
		InteractionHand hand = event.getHand();

		//check that the block was infact a scraping block
		if(block instanceof ScrapingBlockEntity scraping)
		{
			//get the item in hand to check if and what kind of knife it is
			final ItemStack stack = player.getItemInHand(hand);

			//if it's a scraping knife, do stuff
			if(Helpers.isItem(stack, ScrapingTags.Items.SCRAPING_KNIVES))
			{
				//calculate where the player is looking at,
				//pretty sure this is the same code tfc runs
				final Vec3 point = ScalculatePoint(player.getLookAngle(), hit.getLocation().subtract(new Vec3(pos.getX(), pos.getY(), pos.getZ())));

				//if the block is waxed then colour it i guess? imma be real don't fully know
				//why this is here maybe theres some conflict
				if(block.getBlockState().getValue(WAXED))
				{
					final DyeColor color = DyeColor.getColor(stack);
					if (color != null && scraping.dye(color))
					{
						SdoParticles(level, pos, scraping, point);
						if (!player.isCreative()) stack.shrink(1);
					}
				} else if (player instanceof LivingEntity living)
				{
					//line scrape
					if(Helpers.isItem(stack,ScrapingTags.Items.LINE_SCRAPING))
					{
						Direction facing = player.getDirection();
						for(double i = 0; i < 1; i = i+0.25)
						{
							if(facing == Direction.NORTH || facing == Direction.SOUTH)
							{
								scraping.onClicked((float)i, (float) point.z);
							}
							else {
								scraping.onClicked((float)point.x, (float) i);
							}
						}
						stack.hurtAndBreak(4, player, living.getSlotForHand(hand));
						SdoParticles(level, pos, scraping, point);

					}
					//quarter scrape
					if(Helpers.isItem(stack,ScrapingTags.Items.QUARTER_SCRAPING))
					{
						final int quadrant = ScrapingHelper.calculateQuadrant(point.x, point.z);
						switch (quadrant)
						{
							case 1:
								scraping.onClicked((float)0, (float) 0);
								scraping.onClicked((float)0.25, (float) 0);
								scraping.onClicked((float)0, (float) 0.25);
								scraping.onClicked((float)0.25, (float) 0.25);
								break;
							case 2:
								scraping.onClicked((float)0.5, (float) 0);
								scraping.onClicked((float)0.75, (float) 0);
								scraping.onClicked((float)0.5, (float) 0.25);
								scraping.onClicked((float)0.75, (float) 0.25);
								break;
							case 3:
								scraping.onClicked((float)0, (float) 0.5);
								scraping.onClicked((float)0.25, (float) 0.5);
								scraping.onClicked((float)0, (float) 0.75);
								scraping.onClicked((float)0.25, (float) 0.75);
								break;
							case 4:
								scraping.onClicked((float)0.5, (float) 0.5);
								scraping.onClicked((float)0.5, (float) 0.75);
								scraping.onClicked((float)0.75, (float) 0.5);
								scraping.onClicked((float)0.75, (float) 0.75);
								break;

						}
						stack.hurtAndBreak(4, player, living.getSlotForHand(hand));
						SdoParticles(level, pos, scraping, point);
					}
					//half scrape
					if(Helpers.isItem(stack,ScrapingTags.Items.HALF_SCRAPING))
					{
						Direction facing = player.getDirection();
						for(double i = 0; i < 1; i = i+0.25)
						{
							if(facing == Direction.NORTH || facing == Direction.SOUTH)
							{
								//if on left side(while facing north) then clear left
								if(point.x < 0.5)
								{
									scraping.onClicked((float)0, (float) i);
									scraping.onClicked((float)0.25, (float) i);
								}
								else
								{
									scraping.onClicked((float)0.5, (float) i);
									scraping.onClicked((float)0.75, (float) i);
								}

							}
							else {
								if(point.z < 0.5)
								{
									scraping.onClicked((float)i, (float) 0);
									scraping.onClicked((float)i, (float) 0.25);
								}
								else
								{
									scraping.onClicked((float)i, (float) 0.5);
									scraping.onClicked((float)i, (float) 0.75);
								}
							}
						}
						stack.hurtAndBreak(4, player, living.getSlotForHand(hand));

						SdoParticles(level, pos, scraping, point);
					}
					//screw it imma be lazy with this one
					//full
					if(Helpers.isItem(stack,ScrapingTags.Items.FULL_SCRAPING))
					{
						for(double x = 0; x < 1; x = x+0.25)
						{
							for(double z = 0; z < 1; z = z+0.25)
							{
								scraping.onClicked((float)x, (float) z);
							}
						}
						stack.hurtAndBreak(4, player, living.getSlotForHand(hand));
						SdoParticles(level, pos, scraping, point);
					}
				}
			}
		}
	}


}