package vg.civcraft.mc.civmodcore.inventorygui.legacy;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @deprecated Replaced with PointlessButton
 * @see vg.civcraft.mc.civmodcore.inventorygui.PointlessButton
 * */
@Deprecated
public class DecorationStack extends Clickable {

	public DecorationStack(ItemStack item) {
		super(item);
	}

	@Override
	public void clicked(Player player) {}

}
