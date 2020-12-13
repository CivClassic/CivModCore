package vg.civcraft.mc.civmodcore.api;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import vg.civcraft.mc.civmodcore.chat.ChatUtils;

/**
 * @deprecated Use {@link ChatUtils} instead.
 */
public class ColourAPI {

	/**
	 * @deprecated Use {@link ChatUtils#COLOURS} instead.
	 */
	@Deprecated
	public static final List<ChatColor> COLOURS = ChatUtils.COLOURS;

	/**
	 * @deprecated Use {@link ChatUtils#fromRGB(byte, byte, byte)} instead.
	 */
	@Deprecated
	public static ChatColor fromRGB(final byte r, final byte g, final byte b) {
		return ChatUtils.fromRGB(r, g, b);
	}

	/**
	 * @deprecated Use {@link ChatUtils#collapseColour(ChatColor)} instead.
	 */
	@Deprecated
	public static ChatColor collapseColour(final ChatColor colour) {
		return ChatUtils.collapseColour(colour);
	}

}
