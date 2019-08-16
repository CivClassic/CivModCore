package vg.civcraft.mc.civmodcore.api;

import io.protonull.utilities.Exists;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NBTCompound {

	private NBTTagCompound tag;

	public NBTCompound() {
		this.tag = new NBTTagCompound();
	}

	public NBTCompound(ItemStack item) {
		if (item == null) {
			this.tag = new NBTTagCompound();
		}
		else {
			net.minecraft.server.v1_12_R1.ItemStack temp = CraftItemStack.asNMSCopy(item);
			if (temp == null) {
				this.tag = new NBTTagCompound();
			}
			else {
				this.tag = temp.getTag();
				if (this.tag == null) {
					this.tag = new NBTTagCompound();
				}
			}
		}
	}

	public NBTCompound(NBTTagCompound tag) {
		if (tag == null) {
			this.tag = new NBTTagCompound();
		}
		else {
			this.tag = tag;
		}
	}

	public int size() {
		return this.tag.d();
	}

	public boolean isEmpty() {
		return this.tag.isEmpty();
	}

	public boolean hasKey(String key) {
		return this.tag.hasKey(key);
	}

	public void remove(String key) {
		this.tag.remove(key);
	}

	public void clear() {
		for (String key : this.tag.c()) {
			this.tag.remove(key);
		}
	}

	public NBTTagCompound getRAW() {
		return this.tag;
	}

	public boolean getBoolean(String key) {
		return this.tag.getBoolean(key);
	}

	public void setBoolean(String key, boolean value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setBoolean(key, value);
	}

	public boolean[] getBooleanArray(String key) {
		byte[] cache = this.tag.getByteArray(key);
		boolean[] result = new boolean[cache.length];
		for (int i = 0; i < cache.length; i++) {
			result[i] = cache[i] != 0;
		}
		return result;
	}

	public void setBooleanArray(String key, boolean[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.booleans(values)) {
			byte[] cache = new byte[values.length];
			for (int i = 0; i < values.length; i++) {
				cache[i] = (byte) (values[i] ? 0x1 : 0x0);
			}
			this.tag.setByteArray(key, cache);
		}
		else {
			this.tag.remove(key);
		}
	}

	public byte getByte(String key) {
		return this.tag.getByte(key);
	}

	public void setByte(String key, byte value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setByte(key, value);
	}

	public byte[] getByteArray(String key) {
		return this.tag.getByteArray(key);
	}

	public void setByteArray(String key, byte[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.bytes(values)) {
			this.tag.setByteArray(key, values);
		}
		else {
			this.tag.remove(key);
		}
	}

	public short getShort(String key) {
		return this.tag.getShort(key);
	}

	public void setShort(String key, short value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setShort(key, value);
	}

	public short[] getShortArray(String key) {
		NBTTagList list = this.tag.getList(key, 2);
		short[] result = new short[list.size()];
		for (int i = 0; i < result.length; i++) {
			NBTBase base = list.i(i);
			if (base.getTypeId() != 2) {
				result[i] = 0;
			}
			else if (!(base instanceof NBTTagShort)) {
				result[i] = 0;
			}
			else {
				result[i] = ((NBTTagShort) base).f();
			}
		}
		return result;
	}

	public void setShortArray(String key, short[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.shorts(values)) {
			NBTTagList list = new NBTTagList();
			for (short value : values) {
				list.add(new NBTTagShort(value));
			}
			this.tag.set(key, list);
		}
		else {
			this.tag.remove(key);
		}
	}

	public int getInt(String key) {
		return this.tag.getInt(key);
	}

	public void setInt(String key, int value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setInt(key, value);
	}

	public int[] getIntArray(String key) {
		return this.tag.getIntArray(key);
	}

	public void setIntArray(String key, int[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.integers(values)) {
			this.tag.setIntArray(key, values);
		}
		else {
			this.tag.remove(key);
		}
	}

	public long getLong(String key) {
		return this.tag.getLong(key);
	}

	public void setLong(String key, long value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setLong(key, value);
	}

	public long[] getLongArray(String key) {
		NBTTagList list = this.tag.getList(key, 4);
		long[] result = new long[list.size()];
		for (int i = 0; i < result.length; i++) {
			NBTBase base = list.i(i);
			if (base.getTypeId() != 4) {
				result[i] = 0;
			}
			else if (!(base instanceof NBTTagLong)) {
				result[i] = 0;
			}
			else {
				result[i] = ((NBTTagLong) base).d();
			}
		}
		return result;
	}

	public void setLongArray(String key, long[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.longs(values)) {
			NBTTagList list = new NBTTagList();
			for (long value : values) {
				list.add(new NBTTagLong(value));
			}
			this.tag.set(key, list);
		}
		else {
			this.tag.remove(key);
		}
	}

	public float getFloat(String key) {
		return this.tag.getFloat(key);
	}

	public void setFloat(String key, float value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setFloat(key, value);
	}

	public float[] getFloatArray(String key) {
		NBTTagList list = this.tag.getList(key, 5);
		float[] result = new float[list.size()];
		for (int i = 0; i < result.length; i++) {
			NBTBase base = list.i(i);
			if (base.getTypeId() != 5) {
				result[i] = 0.0f;
			}
			else if (!(base instanceof NBTTagFloat)) {
				result[i] = 0.0f;
			}
			else {
				result[i] = ((NBTTagFloat) base).i();
			}
		}
		return result;
	}

	public void setFloatArray(String key, float[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.floats(values)) {
			NBTTagList list = new NBTTagList();
			for (float value : values) {
				list.add(new NBTTagFloat(value));
			}
			this.tag.set(key, list);
		}
		else {
			this.tag.remove(key);
		}
	}

	public double getDouble(String key) {
		return this.tag.getDouble(key);
	}

	public void setDouble(String key, double value) {
		if (!Exists.string(key)) {
			return;
		}
		this.tag.setDouble(key, value);
	}

	public double[] getDoubleArray(String key) {
		NBTTagList list = this.tag.getList(key, 6);
		double[] result = new double[list.size()];
		for (int i = 0; i < result.length; i++) {
			NBTBase base = list.i(i);
			if (base.getTypeId() != 6) {
				result[i] = 0.0d;
			}
			else if (!(base instanceof NBTTagDouble)) {
				result[i] = 0.0d;
			}
			else {
				result[i] = ((NBTTagDouble) base).asDouble();
			}
		}
		return result;
	}

	public void setDoubleArray(String key, double[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.doubles(values)) {
			NBTTagList list = new NBTTagList();
			for (double value : values) {
				list.add(new NBTTagDouble(value));
			}
			this.tag.set(key, list);
		}
		else {
			this.tag.remove(key);
		}
	}

	public String getString(String key) {
		return this.tag.getString(key);
	}

	public void setString(String key, String value) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.string(value)) {
			this.tag.setString(key, value);
		}
		else {
			this.tag.remove(key);
		}
	}

	public String[] getStringArray(String key) {
		NBTTagList list = this.tag.getList(key, 8);
		String[] result = new String[list.size()];
		for (int i = 0; i < result.length; i++) {
			NBTBase base = list.i(i);
			if (base.getTypeId() != 8) {
				result[i] = "";
			}
			else if (!(base instanceof NBTTagString)) {
				result[i] = "";
			}
			else {
				result[i] = ((NBTTagString) base).c_();
			}
		}
		return result;
	}

	public void setStringArray(String key, String[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.objects(values)) {
			NBTTagList list = new NBTTagList();
			for (String value : values) {
				list.add(new NBTTagString(value));
			}
			this.tag.set(key, list);
		}
		else {
			this.tag.remove(key);
		}
	}

	public NBTCompound getCompound(String key) {
		return new NBTCompound(this.tag.getCompound(key));
	}

	public void setCompound(String key, NBTCompound value) {
		if (!Exists.string(key)) {
			return;
		}
		if (value != null) {
			this.tag.set(key, value.tag);
		}
		else {
			this.tag.remove(key);
		}
	}

	public NBTCompound[] getCompoundArray(String key) {
		NBTTagList list = this.tag.getList(key, 10);
		NBTCompound[] result = new NBTCompound[list.size()];
		for (int i = 0; i < result.length; i++) {
			NBTBase base = list.i(i);
			if (base.getTypeId() != 10) {
				result[i] = new NBTCompound();
			}
			else if (!(base instanceof NBTTagCompound)) {
				result[i] = new NBTCompound();
			}
			else {
				result[i] = new NBTCompound((NBTTagCompound) base);
			}
		}
		return result;
	}

	public void setCompoundArray(String key, NBTCompound[] values) {
		if (!Exists.string(key)) {
			return;
		}
		if (Exists.objects(values)) {
			NBTTagList list = new NBTTagList();
			for (NBTCompound value : values) {
				list.add(value.tag);
			}
			this.tag.set(key, list);
		}
		else {
			this.tag.remove(key);
		}
	}

	public UUID getUUID(String key) {
		return this.tag.a(key);
	}

	public void setUUID(String key, UUID value) {
		if (!Exists.string(key)) {
			return;
		}
		if (value != null) {
			this.tag.a(key, value);
		}
		else {
			this.tag.remove(key);
		}
	}

}
