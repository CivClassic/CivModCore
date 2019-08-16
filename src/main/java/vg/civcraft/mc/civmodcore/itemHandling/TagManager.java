package vg.civcraft.mc.civmodcore.itemHandling;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import io.protonull.utilities.Primitives;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagByte;
import net.minecraft.server.v1_12_R1.NBTTagByteArray;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagIntArray;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagShort;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import vg.civcraft.mc.civmodcore.api.NBTCompound;

/**
 * @deprecated Replaced with NBTCompound
 * @see NBTCompound
 *
 * There are still some methods that need to be transferred such as maps and lists
 * */
@Deprecated
public class TagManager extends NBTCompound {

	private static final Logger log = Bukkit.getLogger();

	private NBTTagCompound tag;

	public TagManager() {
		super();
	}

	public TagManager(ItemStack item) {
		super(item);
	}

	private TagManager(NBTTagCompound tag) {
		super(tag);
	}

	@Deprecated
	public List<String> getStringList(String key) {
		return Arrays.asList(getStringArray(key));
	}

	@Deprecated
	public void setStringList(String key, List<String> list) {
		setStringArray(key, list.toArray(new String[0]));
	}

	@Deprecated
	public List<Integer> getIntList(String key) {
		return Arrays.asList(Primitives.IntegerArray(getIntArray(key)));
	}

	public void setIntList(String key, List<Integer> list) {
		setIntArray(key, Primitives.IntegerArray(list.toArray(new Integer[0])));
	}

	@Deprecated
	public List<Short> getShortList(String key) {
		return Arrays.asList(Primitives.ShortArray(getShortArray(key)));
	}

	@Deprecated
	public void setShortList(String key, List<Short> list) {
		setShortArray(key, Primitives.ShortArray(list.toArray(new Short[0])));
	}

	@Override
	public TagManager getCompound(String key) {
		return new TagManager(this.tag.getCompound(key));
	}

	public ItemStack enrichWithNBT(ItemStack item) {
		net.minecraft.server.v1_12_R1.ItemStack temp = CraftItemStack.asNMSCopy(item);
		if (temp == null) {
			log.severe("Failed to create enriched copy of " + item.toString());
			return null;
		}
		temp.setTag(this.tag);
		return CraftItemStack.asBukkitCopy(temp);
	}

	public void setMap(Map<String, Object> map) {
		mapToNBT(this.tag, map);
	}

	public void setList(String key, List<Object> list) {
		this.tag.set(key, listToNBT(new NBTTagList(), list));
	}

	@SuppressWarnings("unchecked")
	public static NBTTagCompound mapToNBT(NBTTagCompound base, Map<String, Object> map) {
		log.info("Representing map --> NBTTagCompound");
		if (map == null || base == null) {
			return base;
		}
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object object = entry.getValue();
			if (object instanceof Map) {
				log.info("Adding map at key " + entry.getKey());
				base.set(entry.getKey(), mapToNBT(new NBTTagCompound(), (Map<String, Object>) object));
			} else if (object instanceof MemorySection) {
				log.info("Adding map from MemorySection at key " + entry.getKey());
				base.set(entry.getKey(), mapToNBT(new NBTTagCompound(), ((MemorySection) object).getValues(true)));
			} else if (object instanceof List) {
				log.info("Adding list at key " + entry.getKey());
				base.set(entry.getKey(), listToNBT(new NBTTagList(), (List<Object>) object));
			} else if (object instanceof String) {
				log.info("Adding String " + object + " at key " + entry.getKey());
				base.setString(entry.getKey(), (String) object);
			} else if (object instanceof Double) {
				log.info("Adding Double " + object + " at key " + entry.getKey());
				base.setDouble(entry.getKey(), (Double) object);
			} else if (object instanceof Float) {
				log.info("Adding Float " + object + " at key " + entry.getKey());
				base.setFloat(entry.getKey(), (Float) object);
			} else if (object instanceof Boolean) {
				log.info("Adding Boolean " + object + " at key " + entry.getKey());
				base.setBoolean(entry.getKey(), (Boolean) object);
			} else if (object instanceof Byte) {
				log.info("Adding Byte " + object + " at key " + entry.getKey());
				base.setByte(entry.getKey(), (Byte) object);
			} else if (object instanceof Short) {
				log.info("Adding Byte " + object + " at key " + entry.getKey());
				base.setShort(entry.getKey(), (Short) object);
			} else if (object instanceof Integer) {
				log.info("Adding Integer " + object + " at key " + entry.getKey());
				base.setInt(entry.getKey(), (Integer) object);
			} else if (object instanceof Long) {
				log.info("Adding Long " + object + " at key " + entry.getKey());
				base.setLong(entry.getKey(), (Long) object);
			} else if (object instanceof byte[]) {
				log.info("Adding bytearray at key " + entry.getKey());
				base.setByteArray(entry.getKey(), (byte[]) object);
			} else if (object instanceof int[]) {
				log.info("Adding intarray at key " + entry.getKey());
				base.setIntArray(entry.getKey(), (int[]) object);
			} else if (object instanceof UUID) {
				log.info("Adding UUID " + object + " at key " + entry.getKey());
				base.a(entry.getKey(), (UUID) object);
			} else if (object instanceof NBTBase) {
				log.info("Adding nbtobject at key " + entry.getKey());
				base.set(entry.getKey(), (NBTBase) object);
			} else {
				log.warning("Unrecognized entry in map-->NBT: " + object.toString());
			}
		}
		return base;
	}

	@SuppressWarnings("unchecked")
	public static NBTTagList listToNBT(NBTTagList base, List<Object> list) {
		log.info("Representing list --> NBTTagList");
		if (list == null || base == null) {
			return base;
		}
		for (Object object : list) {
			if (object instanceof Map) {
				log.info("Adding map to list");
				base.add(mapToNBT(new NBTTagCompound(), (Map<String, Object>) object));
			} else if (object instanceof MemorySection) {
				log.info("Adding map from MemorySection to list");
				base.add(mapToNBT(new NBTTagCompound(), ((MemorySection) object).getValues(true)));
			} else if (object instanceof List) {
				log.info("Adding list to list");
				base.add(listToNBT(new NBTTagList(), (List<Object>) object));
			} else if (object instanceof String) {
				log.info("Adding string " + object + " to list");
				base.add(new NBTTagString((String) object));
			} else if (object instanceof Double) {
				log.info("Adding double " + object + " to list");
				base.add(new NBTTagDouble((Double) object));
			} else if (object instanceof Float) {
				log.info("Adding float " + object + " to list");
				base.add(new NBTTagFloat((Float) object));
			} else if (object instanceof Byte) {
				log.info("Adding byte " + object + " to list");
				base.add(new NBTTagByte((Byte) object));
			} else if (object instanceof Short) {
				log.info("Adding short " + object + " to list");
				base.add(new NBTTagShort((Short) object));
			} else if (object instanceof Integer) {
				log.info("Adding integer " + object + " to list");
				base.add(new NBTTagInt((Integer) object));
			} else if (object instanceof Long) {
				log.info("Adding long " + object + " to list");
				base.add(new NBTTagLong((Long) object));
			} else if (object instanceof byte[]) {
				log.info("Adding byte array to list");
				base.add(new NBTTagByteArray((byte[]) object));
			} else if (object instanceof int[]) {
				log.info("Adding int array to list");
				base.add(new NBTTagIntArray((int[]) object));
			} else if (object instanceof NBTBase) {
				log.info("Adding nbt object to list");
				base.add((NBTBase) object);
			} else {
				log.warning("Unrecognized entry in list-->NBT: " + base.toString());
			}
		}
		return base;
	}

}
