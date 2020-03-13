package vg.civcraft.mc.civmodcore.api;

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public final class BlockAPI {

    public static final Set<BlockFace> ALL_SIDES = ImmutableSet.of(
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST);

    public static final Set<BlockFace> PLANAR_SIDES = ImmutableSet.of(
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST);

    private BlockAPI() {
    }

    /**
     * Returns a map of a block's relatives.
     *
     * @param block The block to get the relatives of.
     * @param faces An array of the faces, which will be the keys of the returned map.
     * @return Returns an immutable map of the block's relatives.
     */
    public static Map<BlockFace, Block> getBlockSides(Block block, BlockFace... faces) {
        return getBlockSides(block, Arrays.asList(faces == null ? new BlockFace[0] : faces));
    }

    /**
     * Returns a map of a block's relatives.
     *
     * @param block The block to get the relatives of.
     * @param faces A collection of the faces, which will be the keys of the returned map.
     * @return Returns an immutable map of the block's relatives.
     */
    public static Map<BlockFace, Block> getBlockSides(Block block, Collection<BlockFace> faces) {
        EnumMap<BlockFace, Block> results = new EnumMap<>(BlockFace.class);
        if (block != null && faces != null) {
            faces.forEach((face) -> results.put(face, block.getRelative(face)));
        }
        return Collections.unmodifiableMap(results);
    }

}
