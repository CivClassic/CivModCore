package vg.civcraft.mc.civmodcore.util;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Allows sending titles to players with full customization, which the Bukkit/Spigot API doesn't offer. A title consists
 * of two parts, the main head line text and a second sub title line, which is displayed smaller than the main title.
 * When sending the title, it'll first have a "fade in" period, during which the title will fade in, at it's end the
 * title displayed will be completely opaque. It'll stay like that for the stay period of time defined and after that
 * take the fade out time to disappear again completely. When overlapping titles, the later one will completely override
 * the previous one.
 *
 */
public class Title {

	private String title;
	private String subtitle;
	private int fadeIn;
	private int stay;
	private int fadeOut;

	public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @return The time it takes for the title to fade in, in ticks
	 */
	public int getFadeIn() {
		return this.fadeIn;
	}

	/**
	 * Sets how long the title takes to fade in, in ticks
	 */
	public void setFadeIn(int ticks) {
		this.fadeIn = ticks;
	}

	/**
	 * @return The time the title will stay visible, in ticks
	 */
	public int getStay() {
		return stay;
	}

	/**
	 * Sets how long the title will stay visible, in ticks
	 */
	public void setStay(int stay) {
		this.stay = stay;
	}

	/**
	 * @return The time it takes for the title to fade out, in ticks
	 */
	public int getFadeOut() {
		return fadeOut;
	}

	/**
	 * Sets how long the title takes to fade out, in ticks
	 */
	public void setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
	}

	/**
	 * Sends the this title to the given player
	 * @param player The player to send the title to
	 */
	public void sendTitle(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
		connection.sendPacket(packet);
		IChatBaseComponent sub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, sub);
		connection.sendPacket(packet);
		IChatBaseComponent main = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
		packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, main);
		connection.sendPacket(packet);
	}

}
