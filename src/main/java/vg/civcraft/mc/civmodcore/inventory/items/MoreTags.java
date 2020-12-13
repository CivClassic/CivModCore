package vg.civcraft.mc.civmodcore.inventory.items;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.data.Ageable;
import vg.civcraft.mc.civmodcore.util.KeyedUtils;

public class MoreTags {

	public static final Tag<Material> STAINED_GLASS = new BetterTag<>("stained_glass",
			ImmutableSet.<Material>builder()
					.add(Material.BLACK_STAINED_GLASS)
					.add(Material.WHITE_STAINED_GLASS)
					.add(Material.YELLOW_STAINED_GLASS)
					.add(Material.RED_STAINED_GLASS)
					.add(Material.LIME_STAINED_GLASS)
					.add(Material.GRAY_STAINED_GLASS)
					.add(Material.BLUE_STAINED_GLASS)
					.add(Material.LIGHT_GRAY_STAINED_GLASS)
					.add(Material.LIGHT_BLUE_STAINED_GLASS)
					.add(Material.GREEN_STAINED_GLASS)
					.add(Material.BROWN_STAINED_GLASS)
					.add(Material.PINK_STAINED_GLASS)
					.add(Material.PURPLE_STAINED_GLASS)
					.add(Material.CYAN_STAINED_GLASS)
					.add(Material.MAGENTA_STAINED_GLASS)
					.add(Material.ORANGE_STAINED_GLASS)
					.build());

	public static final Tag<Material> GLASS = new BetterTag<>("glass",
			ImmutableSet.<Material>builder()
					.add(Material.GLASS)
					.addAll(STAINED_GLASS.getValues())
					.build());

	public static final Tag<Material> STAINED_GLASS_PANES = new BetterTag<>("stained_glass_panes",
			ImmutableSet.<Material>builder()
					.add(Material.BLACK_STAINED_GLASS_PANE)
					.add(Material.WHITE_STAINED_GLASS_PANE)
					.add(Material.YELLOW_STAINED_GLASS_PANE)
					.add(Material.RED_STAINED_GLASS_PANE)
					.add(Material.LIME_STAINED_GLASS_PANE)
					.add(Material.GRAY_STAINED_GLASS_PANE)
					.add(Material.BLUE_STAINED_GLASS_PANE)
					.add(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
					.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
					.add(Material.GREEN_STAINED_GLASS_PANE)
					.add(Material.BROWN_STAINED_GLASS_PANE)
					.add(Material.PINK_STAINED_GLASS_PANE)
					.add(Material.PURPLE_STAINED_GLASS_PANE)
					.add(Material.CYAN_STAINED_GLASS_PANE)
					.add(Material.MAGENTA_STAINED_GLASS_PANE)
					.add(Material.ORANGE_STAINED_GLASS_PANE)
					.build());

	public static final Tag<Material> GLASS_PANES = new BetterTag<>("glass_panes",
			ImmutableSet.<Material>builder()
					.add(Material.GLASS_PANE)
					.addAll(STAINED_GLASS_PANES.getValues())
					.build());

	public static final Tag<Material> CONCRETE = new BetterTag<>("concrete",
			ImmutableSet.<Material>builder()
					.add(Material.BLACK_CONCRETE)
					.add(Material.WHITE_CONCRETE)
					.add(Material.YELLOW_CONCRETE)
					.add(Material.RED_CONCRETE)
					.add(Material.LIME_CONCRETE)
					.add(Material.GRAY_CONCRETE)
					.add(Material.BLUE_CONCRETE)
					.add(Material.LIGHT_GRAY_CONCRETE)
					.add(Material.LIGHT_BLUE_CONCRETE)
					.add(Material.GREEN_CONCRETE)
					.add(Material.BROWN_CONCRETE)
					.add(Material.PINK_CONCRETE)
					.add(Material.PURPLE_CONCRETE)
					.add(Material.CYAN_CONCRETE)
					.add(Material.MAGENTA_CONCRETE)
					.add(Material.ORANGE_CONCRETE)
					.build());

	public static final Tag<Material> CONCRETE_POWDER = new BetterTag<>("concrete_powder",
			ImmutableSet.<Material>builder()
					.add(Material.BLACK_CONCRETE_POWDER)
					.add(Material.WHITE_CONCRETE_POWDER)
					.add(Material.YELLOW_CONCRETE_POWDER)
					.add(Material.RED_CONCRETE_POWDER)
					.add(Material.LIME_CONCRETE_POWDER)
					.add(Material.GRAY_CONCRETE_POWDER)
					.add(Material.BLUE_CONCRETE_POWDER)
					.add(Material.LIGHT_GRAY_CONCRETE_POWDER)
					.add(Material.LIGHT_BLUE_CONCRETE_POWDER)
					.add(Material.GREEN_CONCRETE_POWDER)
					.add(Material.BROWN_CONCRETE_POWDER)
					.add(Material.PINK_CONCRETE_POWDER)
					.add(Material.PURPLE_CONCRETE_POWDER)
					.add(Material.CYAN_CONCRETE_POWDER)
					.add(Material.MAGENTA_CONCRETE_POWDER)
					.add(Material.ORANGE_CONCRETE_POWDER)
					.build());

	public static final Tag<Material> LOGS = new BetterTag<>("logs",
			ImmutableSet.<Material>builder()
					.add(Material.ACACIA_LOG)
					.add(Material.BIRCH_LOG)
					.add(Material.DARK_OAK_LOG)
					.add(Material.JUNGLE_LOG)
					.add(Material.OAK_LOG)
					.add(Material.SPRUCE_LOG)
					.build());
	
	public static final Tag<Material> STRIPPED_LOGS = new BetterTag<>("stripped_logs",
			ImmutableSet.<Material>builder()
					.add(Material.STRIPPED_ACACIA_LOG)
					.add(Material.STRIPPED_BIRCH_LOG)
					.add(Material.STRIPPED_DARK_OAK_LOG)
					.add(Material.STRIPPED_JUNGLE_LOG)
					.add(Material.STRIPPED_OAK_LOG)
					.add(Material.STRIPPED_SPRUCE_LOG)
					.build());

	public static final Tag<Material> STRIPPED_PLANKS = new BetterTag<>("stripped_planks",
			ImmutableSet.<Material>builder()
					.add(Material.STRIPPED_ACACIA_WOOD)
					.add(Material.STRIPPED_BIRCH_WOOD)
					.add(Material.STRIPPED_DARK_OAK_WOOD)
					.add(Material.STRIPPED_JUNGLE_WOOD)
					.add(Material.STRIPPED_OAK_WOOD)
					.add(Material.STRIPPED_SPRUCE_WOOD)
					.build());

	public static final Tag<Material> STRIPPED_ALL = new BetterTag<>("stripped_all",
			ImmutableSet.<Material>builder()
					.addAll(STRIPPED_LOGS.getValues())
					.addAll(STRIPPED_PLANKS.getValues())
					.build());

	public static final Tag<Material> INFESTED = new BetterTag<>("infested",
			ImmutableSet.<Material>builder()
					.add(Material.INFESTED_STONE)
					.add(Material.INFESTED_COBBLESTONE)
					.add(Material.INFESTED_STONE_BRICKS)
					.add(Material.INFESTED_MOSSY_STONE_BRICKS)
					.add(Material.INFESTED_CRACKED_STONE_BRICKS)
					.add(Material.INFESTED_CHISELED_STONE_BRICKS)
					.build());

	public static final Tag<Material> DIRT = new BetterTag<>("dirt",
			ImmutableSet.<Material>builder()
					.add(Material.FARMLAND)
					.add(Material.GRASS_PATH)
					.add(Material.GRASS_BLOCK)
					.add(Material.DIRT)
					.add(Material.COARSE_DIRT)
					.add(Material.PODZOL)
					.build());

	public static final Tag<Material> POTIONS = new BetterTag<>("potion",
			ImmutableSet.<Material>builder()
					.add(Material.POTION)
					.add(Material.SPLASH_POTION)
					.add(Material.LINGERING_POTION)
					.build());

	public static final Tag<Material> SWORDS = new BetterTag<>("swords",
			ImmutableSet.<Material>builder()
					.add(Material.WOODEN_SWORD)
					.add(Material.STONE_SWORD)
					.add(Material.IRON_SWORD)
					.add(Material.GOLDEN_SWORD)
					.add(Material.DIAMOND_SWORD)
					.add(Material.NETHERITE_SWORD)
					.build());

	public static final Tag<Material> PICKAXES = new BetterTag<>("pickaxes",
			ImmutableSet.<Material>builder()
					.add(Material.WOODEN_PICKAXE)
					.add(Material.STONE_PICKAXE)
					.add(Material.IRON_PICKAXE)
					.add(Material.GOLDEN_PICKAXE)
					.add(Material.DIAMOND_PICKAXE)
					.add(Material.NETHERITE_PICKAXE)
					.build());

	public static final Tag<Material> AXES = new BetterTag<>("axes",
			ImmutableSet.<Material>builder()
					.add(Material.WOODEN_AXE)
					.add(Material.STONE_AXE)
					.add(Material.IRON_AXE)
					.add(Material.GOLDEN_AXE)
					.add(Material.DIAMOND_AXE)
					.add(Material.NETHERITE_AXE)
					.build());

	public static final Tag<Material> SPADES = new BetterTag<>("spades",
			ImmutableSet.<Material>builder()
					.add(Material.WOODEN_SHOVEL)
					.add(Material.STONE_SHOVEL)
					.add(Material.IRON_SHOVEL)
					.add(Material.GOLDEN_SHOVEL)
					.add(Material.DIAMOND_SHOVEL)
					.add(Material.NETHERITE_SHOVEL)
					.build());

	public static final Tag<Material> HOES = new BetterTag<>("hoes",
			ImmutableSet.<Material>builder()
					.add(Material.WOODEN_HOE)
					.add(Material.STONE_HOE)
					.add(Material.IRON_HOE)
					.add(Material.GOLDEN_HOE)
					.add(Material.DIAMOND_HOE)
					.add(Material.NETHERITE_HOE)
					.build());

	public static final Tag<Material> HELMETS = new BetterTag<>("helmets",
			ImmutableSet.<Material>builder()
					.add(Material.LEATHER_HELMET)
					.add(Material.CHAINMAIL_HELMET)
					.add(Material.IRON_HELMET)
					.add(Material.GOLDEN_HELMET)
					.add(Material.DIAMOND_HELMET)
					.add(Material.NETHERITE_HELMET)
					.build());

	public static final Tag<Material> CHESTPLATES = new BetterTag<>("chestplates",
			ImmutableSet.<Material>builder()
					.add(Material.LEATHER_CHESTPLATE)
					.add(Material.CHAINMAIL_CHESTPLATE)
					.add(Material.IRON_CHESTPLATE)
					.add(Material.GOLDEN_CHESTPLATE)
					.add(Material.DIAMOND_CHESTPLATE)
					.add(Material.NETHERITE_CHESTPLATE)
					.build());

	public static final Tag<Material> LEGGINGS = new BetterTag<>("leggings",
			ImmutableSet.<Material>builder()
					.add(Material.LEATHER_LEGGINGS)
					.add(Material.CHAINMAIL_LEGGINGS)
					.add(Material.IRON_LEGGINGS)
					.add(Material.GOLDEN_LEGGINGS)
					.add(Material.DIAMOND_LEGGINGS)
					.add(Material.NETHERITE_LEGGINGS)
					.build());

	public static final Tag<Material> BOOTS = new BetterTag<>("boots",
			ImmutableSet.<Material>builder()
					.add(Material.LEATHER_BOOTS)
					.add(Material.CHAINMAIL_BOOTS)
					.add(Material.IRON_BOOTS)
					.add(Material.GOLDEN_BOOTS)
					.add(Material.DIAMOND_BOOTS)
					.add(Material.NETHERITE_BOOTS)
					.build());

	/**
	 * This is necessary as {@link Tag#CROPS} is crap and only has a few crops, and {@link Ageable} includes none-crop
	 * blocks like {@link Material#FIRE fire} and {@link Material#FROSTED_ICE frosted ice}.
	 */
	public static final Tag<Material> CROPS = new BetterTag<>("crops",
			ImmutableSet.<Material>builder()
					.add(Material.BAMBOO)
					.add(Material.BEETROOTS)
					.add(Material.CACTUS)
					.add(Material.CARROTS)
					.add(Material.CHORUS_FLOWER)
					.add(Material.CHORUS_PLANT)
					.add(Material.COCOA)
					.add(Material.KELP)
					.add(Material.MELON_STEM)
					.add(Material.NETHER_WART)
					.add(Material.POTATOES)
					.add(Material.PUMPKIN_STEM)
					.add(Material.SUGAR_CANE)
					.add(Material.SWEET_BERRY_BUSH)
					.add(Material.WHEAT)
					.build());

	public static final Tag<Material> POTTABLE = new BetterTag<>("pottable",
			ImmutableSet.<Material>builder()
					.add(Material.ACACIA_SAPLING)
					.add(Material.ALLIUM)
					.add(Material.AZURE_BLUET)
					.add(Material.BAMBOO)
					.add(Material.BIRCH_SAPLING)
					.add(Material.BLUE_ORCHID)
					.add(Material.BROWN_MUSHROOM)
					.add(Material.CACTUS)
					.add(Material.CORNFLOWER)
					.add(Material.CRIMSON_FUNGUS)
					.add(Material.CRIMSON_ROOTS)
					.add(Material.DANDELION)
					.add(Material.DARK_OAK_SAPLING)
					.add(Material.DEAD_BUSH)
					.add(Material.FERN)
					.add(Material.JUNGLE_SAPLING)
					.add(Material.LILY_OF_THE_VALLEY)
					.add(Material.OAK_SAPLING)
					.add(Material.ORANGE_TULIP)
					.add(Material.OXEYE_DAISY)
					.add(Material.PINK_TULIP)
					.add(Material.POPPY)
					.add(Material.RED_MUSHROOM)
					.add(Material.RED_TULIP)
					.add(Material.SPRUCE_SAPLING)
					.add(Material.WARPED_FUNGUS)
					.add(Material.WARPED_ROOTS)
					.add(Material.WHITE_TULIP)
					.add(Material.WITHER_ROSE)
					.build());

	public static final Tag<Material> SKULLS = new BetterTag<>("skulls",
			ImmutableSet.<Material>builder()
					.add(Material.CREEPER_HEAD)
					.add(Material.CREEPER_WALL_HEAD)
					.add(Material.DRAGON_HEAD)
					.add(Material.DRAGON_WALL_HEAD)
					.add(Material.PLAYER_HEAD)
					.add(Material.PLAYER_WALL_HEAD)
					.add(Material.ZOMBIE_HEAD)
					.add(Material.ZOMBIE_WALL_HEAD)
					.build());










	public static class BetterTag<T extends Keyed> implements Tag<T> {

		private final NamespacedKey key;

		private final Set<T> values;

		private BetterTag(final String key, final Set<T> values) {
			this.key = KeyedUtils.fromParts("civmodcore", key);
			this.values = values;
		}

		@Override
		public boolean isTagged(@Nullable T value) {
			return this.values.contains(value);
		}

		@Nonnull
		@Override
		public Set<T> getValues() {
			return this.values;
		}

		@Nonnull
		@Override
		public NamespacedKey getKey() {
			return this.key;
		}

	}

}
