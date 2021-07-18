package vg.civcraft.mc.civmodcore.nbt;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.pseudo.PseudoServer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class NBTTests {

	@BeforeClass
	public static void setupBukkit() {
		PseudoServer.setup();
	}

	@Test
	public void testMapDeserialisation() {
		// Setup
		final NBTTagCompound targetNBT = new NBTTagCompound() {{
			set("EntityTag", new NBTTagCompound() {{
				setString("id", "minecraft:vex");
			}});
		}};
		final Map<String, Object> testData = new HashMap<>() {{
			put("EntityTag", new HashMap<String, Object>() {{
				put("id", "minecraft:vex");
			}});
		}};
		// Process
		final NBTTagCompound convertedNBT = NBTSerialization.fromMap(testData);
		// Check
		Assert.assertEquals(targetNBT, convertedNBT);
	}

}
