package vg.civcraft.mc.civmodcore.api;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Switch;
import org.bukkit.util.BlockIterator;
import vg.civcraft.mc.civmodcore.util.MoreCollectionUtils;
import vg.civcraft.mc.civmodcore.world.BlockProperties;
import vg.civcraft.mc.civmodcore.world.WorldUtils;

/**
 * @deprecated Use {@link WorldUtils} and {@link BlockProperties} instead.
 */
@Deprecated
public final class BlockAPI {
	
	private BlockAPI() { }

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#ALL_SIDES} instead.
	 */
	@Deprecated
	public static final List<BlockFace> ALL_SIDES = ImmutableList.of(
			BlockFace.UP,
			BlockFace.DOWN,
			BlockFace.NORTH,
			BlockFace.SOUTH,
			BlockFace.WEST,
			BlockFace.EAST);

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#PLANAR_SIDES} instead.
	 */
	@Deprecated
	public static final List<BlockFace> PLANAR_SIDES = ImmutableList.of(
			BlockFace.NORTH,
			BlockFace.SOUTH,
			BlockFace.WEST,
			BlockFace.EAST);

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#isValidBlock(Block)} instead.
	 */
	@Deprecated
	public static boolean isValidBlock(Block block) {
		return WorldUtils.isValidBlock(block);
	}

	/**
	 * @deprecated Use
	 *     {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getBlockSidesMapped(Block, Collection, boolean)} instead.
	 */
	@Deprecated
	public static Map<BlockFace, Block> getBlockSidesMapped(Block block, BlockFace... faces) {
		return WorldUtils.getBlockSidesMapped(block, MoreCollectionUtils.collect(ArrayList::new, faces), true);
	}

	/**
	 * @deprecated Use
	 *     {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getBlockSidesMapped(Block, Collection, boolean)} instead.
	 */
	@Deprecated
	public static Map<BlockFace, Block> getBlockSidesMapped(Block block, Collection<BlockFace> faces) {
		return WorldUtils.getBlockSidesMapped(block, faces, true);
	}

	/**
	 * @deprecated Use
	 *     {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getBlockSides(Block, Collection, boolean)} instead.
	 */
	@Deprecated
	public static List<Block> getBlockSides(Block block, Collection<BlockFace> faces) {
		return WorldUtils.getBlockSides(block, faces, true);
	}

	/**
	 * @deprecated Use
	 *     {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getAllBlockSidesMapped(Block, boolean)} instead.
	 */
	@Deprecated
	public static Map<BlockFace, Block> getAllSidesMapped(Block block) {
		return WorldUtils.getBlockSidesMapped(block, WorldUtils.ALL_SIDES, true);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getAllBlockSides(Block, boolean)} instead.
	 */
	@Deprecated
	public static List<Block> getAllSides(Block block) {
		return WorldUtils.getBlockSides(block, WorldUtils.ALL_SIDES, true);
	}

	/**
	 * @deprecated Use
	 *     {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getPlanarBlockSidesMapped(Block, boolean)} instead.
	 */
	@Deprecated
	public static Map<BlockFace, Block> getPlanarSidesMapped(Block block) {
		return WorldUtils.getBlockSidesMapped(block, WorldUtils.PLANAR_SIDES, true);
	}

	/**
	 * @deprecated Use
	 *     {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getPlanarBlockSides(Block, boolean)} instead.
	 */
	@Deprecated
	public static List<Block> getPlanarSides(Block block) {
		return WorldUtils.getBlockSides(block, WorldUtils.PLANAR_SIDES, true);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#turnClockwise(BlockFace)} instead.
	 */
	@Deprecated
	public static BlockFace turnClockwise(BlockFace face) {
		return WorldUtils.turnClockwise(face);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#turnAntiClockwise(BlockFace)} instead.
	 */
	@Deprecated
	public static BlockFace turnAntiClockwise(BlockFace face) {
		return WorldUtils.turnAntiClockwise(face);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getAttachedFace(Switch)} instead.
	 */
	@Deprecated
	public static BlockFace getAttachedFace(Switch attachable) {
		return WorldUtils.getAttachedFace(attachable);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getOtherDoubleChestBlock(Block, boolean)}
	 *     instead.
	 */
	@Deprecated
	public static Block getOtherDoubleChestBlock(Block block) {
		return WorldUtils.getOtherDoubleChestBlock(block, true);
	}

	/**
	 * @deprecated Use {@link vg.civcraft.mc.civmodcore.world.WorldUtils#getBlockIterator(Block, BlockFace, int)}
	 *     instead.
	 */
	@Deprecated
	public static BlockIterator getBlockIterator(Block block, BlockFace face, int range) {
		return WorldUtils.getBlockIterator(block, face, range);
	}

	/**
	 * @deprecated Use {@link BlockProperties#setBlockProperty(Block, String, String)} instead.
	 */
	@Deprecated
	public static boolean setBlockProperty(Block block, String key, String value) {
		return BlockProperties.setBlockProperty(block, key, value);
	}

	/**
	 * @deprecated Use {@link BlockProperties#setBlockProperty(Block, String, String)} instead.
	 */
	@Deprecated
	public static <V extends Comparable<V>> boolean innerSetBlockProperty(Block block, String key, String value) {
		return BlockProperties.setBlockProperty(block, key, value);
	}

}
