package vg.civcraft.mc.civmodcore.nbt;

import javax.annotation.Nonnull;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.NotImplementedException;

public interface NBTSerializable {

	/**
	 * Serializes this class onto a given NBTCompound.
	 *
	 * @param nbt The NBTCompound to serialize into, which <i>should</i> NEVER be null, so feel free to throw an
	 *            {@link NBTSerializationException} if it is. You can generally assume that the NBTCompound is new and
	 *            therefore empty, but you <i>may</i> wish to check that.
	 */
	void toNBT(@Nonnull final NBTTagCompound nbt);

	/**
	 * <p>Deserializes a given NBTCompound into a new class instance.</p>
	 *
	 * <p><b>NOTE:</b> When copying this to your extension class, change the return type to that class.</p>
	 *
	 * @param nbt The NBTCompound to deserialize from, which <i>should</i> NEVER be null, so feel free to throw an
	 *            {@link NBTSerializationException} if it is.
	 * @return Returns a new instance of this class.
	 */
	@Nonnull
	static NBTSerializable fromNBT(@Nonnull final NBTTagCompound nbt) {
		throw new NotImplementedException("Please implement me on your class!");
	}

}
