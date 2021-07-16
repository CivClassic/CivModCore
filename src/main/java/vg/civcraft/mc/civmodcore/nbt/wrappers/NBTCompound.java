package vg.civcraft.mc.civmodcore.nbt.wrappers;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.experimental.ExtensionMethod;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import vg.civcraft.mc.civmodcore.nbt.NBTType;
import vg.civcraft.mc.civmodcore.utilities.JavaExtensions;
import vg.civcraft.mc.civmodcore.utilities.UuidUtils;

@ExtensionMethod(JavaExtensions.class)
public class NBTCompound extends NBTTagCompound {

	private static final String UUID_MOST_SUFFIX = "Most";
	private static final String UUID_LEAST_SUFFIX = "Least";
	private static final String UUID_KEY = "uuid";

	/**
	 * Creates a new NBTCompound.
	 */
	public NBTCompound() {
		super();
	}

	/**
	 * Creates a new NBTCompound based on an existing inner-map.
	 */
	public NBTCompound(@Nonnull final Map<String, NBTBase> raw) {
		super(Objects.requireNonNull(raw));
	}

	/**
	 * Creates a new NBTCompound by extracting the inner-map of the given NBTTagCompound.
	 *
	 * @param tag The NBTTagCompound to extract from.
	 */
	public NBTCompound(@Nullable final NBTTagCompound tag) {
		this(tag.orElseGet(NBTTagCompound::new).x);
	}

	/**
	 * Returns the size of the tag compound.
	 *
	 * @return The size of the tag compound.
	 */
	public int size() {
		return this.x.size();
	}

	/**
	 * Clears all values from the tag compound.
	 */
	public void clear() {
		this.x.clear();
	}

	/**
	 * Adopts a copy of the NBT data from another compound.
	 *
	 * @param nbt The NBT data to copy and adopt.
	 */
	public void adopt(@Nonnull final NBTCompound nbt) {
		Preconditions.checkNotNull(nbt);
		this.x.clear();
		this.x.putAll(nbt.x);
	}

	/**
	 * Checks whether a UUID exists at the given key.
	 *
	 * @param key The key of the UUID.
	 * @return Returns true if there's a UUID value for that key.
	 */
	public boolean hasUUID(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		return b(key);
	}

	/**
	 * Gets a UUID value from a key.
	 *
	 * @param key The key of the UUID.
	 * @return Returns a UUID, defaulted to 00000000-0000-0000-0000-000000000000.
	 */
	@Nonnull
	public UUID getUUID(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		return !b(key) ? UuidUtils.IDENTITY : a(key);
	}

	/**
	 * Sets a UUID value to a key.
	 *
	 * @param key The key of the UUID to set.
	 * @param value The UUID value to set.
	 */
	public void setUUID(@Nonnull final String key, @Nonnull final UUID value) {
		setUUID(key, value, false);
	}

	/**
	 * Sets a UUID value to a key.
	 *
	 * @param key The key of the UUID to set.
	 * @param value The UUID value to set.
	 * @param useMojangFormat Whether to save as Mojang's least+most, or the updated int array.
	 */
	public void setUUID(@Nonnull final String key, @Nonnull final UUID value, final boolean useMojangFormat) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(value);
		if (useMojangFormat) {
			setLong(key + UUID_MOST_SUFFIX, value.getMostSignificantBits());
			setLong(key + UUID_LEAST_SUFFIX, value.getLeastSignificantBits());
		}
		else {
			a(key, value);
		}
	}

	/**
	 * Removes a UUID value, which is necessary because Bukkit stores UUIDs by splitting up the two significant parts
	 * into their own values.
	 *
	 * @param key The key of the UUID to remove.
	 */
	public void removeUUID(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		remove(key);
		remove(key + UUID_MOST_SUFFIX);
		remove(key + UUID_LEAST_SUFFIX);
	}

	/**
	 * Gets a tag compound value from a key.
	 *
	 * @param key The key to get the value of.
	 * @return The value of the key, which is never null.
	 */
	@Nonnull
	@Override
	public NBTCompound getCompound(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		return new NBTCompound(super.getCompound(key));
	}

	// ------------------------------------------------------------
	// Array Functions
	// ------------------------------------------------------------

	/**
	 * Gets an array of primitive booleans from a key.
	 *
	 * @param key The key to of the array.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public boolean[] getBooleanArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		final byte[] cache = getByteArray(Objects.requireNonNull(key));
		final boolean[] result = new boolean[cache.length];
		for (int i = 0; i < cache.length; i++) {
			result[i] = cache[i] != 0;
		}
		return result;
	}

	/**
	 * Sets an array of primitive booleans to a key.
	 *
	 * @param key The key to set to array to.
	 * @param booleans The booleans to set to the key.
	 */
	public void setBooleanArray(@Nonnull final String key, @Nonnull final boolean[] booleans) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(booleans);
		final byte[] converted = new byte[booleans.length];
		for (int i = 0; i < booleans.length; i++) {
			converted[i] = (byte) (booleans[i] ? 0x1 : 0x0);
		}
		setByteArray(Objects.requireNonNull(key), converted);
	}

	/**
	 * Gets an array of primitive shorts from a key.
	 *
	 * @param key The key to get the values of.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public short[] getShortArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		final NBTTagList list = getList(key, NBTType.SHORT);
		final short[] result = new short[list.size()];
		for (int i = 0; i < result.length; i++) {
			if (list.get(i) instanceof NBTTagShort nbtShort) {
				result[i] = nbtShort.asShort();
			}
		}
		return result;
	}

	/**
	 * Sets an array of primitive bytes to a key.
	 *
	 * @param key The key to set to values to.
	 * @param shorts The shorts to set to the key.
	 */
	public void setShortArray(@Nonnull final String key, @Nonnull final short[] shorts) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(shorts);
		final NBTTagList list = new NBTTagList();
		for (final short value : shorts) {
			list.add(NBTTagShort.a(value));
		}
		set(key, list);
	}

	/**
	 * Sets an array of primitive longs to a key.
	 *
	 * @param key The key to set to values to.
	 * @param longs The longs to set to the key.
	 */
	public void setLongArray(@Nonnull final String key, @Nonnull final long[] longs) {
		Preconditions.checkNotNull(key);
		a(key, longs);
	}

	/**
	 * Gets an array of primitive floats from a key.
	 *
	 * @param key The key to get the values of.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public float[] getFloatArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		NBTTagList list = getList(key, NBTType.FLOAT);
		final float[] result = new float[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = list.i(i);
		}
		return result;
	}

	/**
	 * Sets an array of primitive floats to a key.
	 *
	 * @param key The key to set to values to.
	 * @param floats The floats to set to the key.
	 */
	public void setFloatArray(@Nonnull final String key, @Nonnull final float[] floats) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(floats);
		final NBTTagList list = new NBTTagList();
		for (final float value : floats) {
			list.add(NBTTagFloat.a(value));
		}
		set(key, list);
	}

	/**
	 * Gets an array of primitive doubles from a key.
	 *
	 * @param key The key to get the values of.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public double[] getDoubleArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		final NBTTagList list = getList(key, NBTType.DOUBLE);
		final double[] result = new double[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = list.h(i);
		}
		return result;
	}

	/**
	 * Sets an array of primitive doubles to a key.
	 *
	 * @param key The key to set to values to.
	 * @param doubles The values to set to the key.
	 */
	public void setDoubleArray(@Nonnull final String key, @Nonnull final double[] doubles) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(doubles);
		final NBTTagList list = new NBTTagList();
		for (final double value : doubles) {
			list.add(NBTTagDouble.a(value));
		}
		set(key, list);
	}

	/**
	 * Gets an array of UUIDs from a key.
	 *
	 * @param key The key to get the values of.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public UUID[] getUUIDArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		final NBTTagList list = getList(key, NBTType.INT_ARRAY);
		final UUID[] result = new UUID[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = GameProfileSerializer.a(list.get(i));
		}
		return result;
	}

	/**
	 * Sets an array of UUIDs to a key.
	 *
	 * @param key The key to set to values to.
	 * @param uuids The uuids to set to the key.
	 */
	public void setUUIDArray(@Nonnull final String key, @Nonnull final UUID[] uuids) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(uuids);
		final NBTTagList list = new NBTTagList();
		List.of(uuids).forEach((uuid) -> list.add(GameProfileSerializer.a(uuid)));
		set(key, list);
	}

	/**
	 * Gets an array of Strings from a key.
	 *
	 * @param key The key to get the values of.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public String[] getStringArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		final NBTTagList list = getList(key, NBTType.STRING);
		final String[] result = new String[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i) instanceof NBTTagString nbtString ? nbtString.asString() : "";
		}
		return result;
	}

	/**
	 * Sets an array of Strings to a key.
	 *
	 * @param key The key to set to values to.
	 * @param strings The strings to set to the key.
	 */
	public void setStringArray(@Nonnull final String key, @Nonnull final String[] strings) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(strings);
		final NBTTagList list = new NBTTagList();
		List.of(strings).forEach((string) -> list.add(NBTTagString.a(string)));
		set(key, list);
	}

	/**
	 * Gets an array of tag compounds from a key.
	 *
	 * @param key The key to get the values of.
	 * @return The values of the key, default: empty array
	 */
	@Nonnull
	public NBTCompound[] getCompoundArray(@Nonnull final String key) {
		Preconditions.checkNotNull(key);
		final NBTTagList list = getList(key, NBTType.COMPOUND);
		final NBTCompound[] result = new NBTCompound[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = new NBTCompound(list.getCompound(i));
		}
		return result;
	}

	/**
	 * Sets an array of tag compounds to a key.
	 *
	 * @param key The key to set to values to.
	 * @param compounds The values to set to the key.
	 */
	public void setCompoundArray(@Nonnull final String key, @Nonnull final NBTTagCompound[] compounds) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(compounds);
		final NBTTagList list = new NBTTagList();
		list.addAll(List.of(compounds));
		set(key, list);
	}

}
