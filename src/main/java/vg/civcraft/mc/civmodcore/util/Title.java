package vg.civcraft.mc.civmodcore.util;

import lombok.Getter;
import lombok.Setter;
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
 */
public class Title {

	@Getter @Setter
	private String title;
	@Getter @Setter
	private String subtitle;
	@Getter @Setter
	private int fadeIn;
	@Getter @Setter
	private int stay;
	@Getter @Setter
	private int fadeOut;

	public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	/**
	 * Sends the title to the given player, according to the configuration in this instance
	 * @param player Player to send the title to
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