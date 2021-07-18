package vg.civcraft.mc.civmodcore.chat;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public final class ChatUtils {

	/**
	 * This is necessary as {@link ChatColor#values()} has all colours <i>and</i> all formats.
	 */
	@SuppressWarnings("deprecation")
	public static final List<ChatColor> COLOURS = List.of(
			ChatColor.BLACK,
			ChatColor.DARK_BLUE,
			ChatColor.DARK_GREEN,
			ChatColor.DARK_AQUA,
			ChatColor.DARK_RED,
			ChatColor.DARK_PURPLE,
			ChatColor.GOLD,
			ChatColor.GRAY,
			ChatColor.DARK_GRAY,
			ChatColor.BLUE,
			ChatColor.GREEN,
			ChatColor.AQUA,
			ChatColor.RED,
			ChatColor.LIGHT_PURPLE,
			ChatColor.YELLOW);

	/**
	 * Converts an RGB value into a Bungee ChatColor.
	 *
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 * @return Returns a valid Bungee ChatColor.
	 */
	public static ChatColor fromRGB(final byte r, final byte g, final byte b) {
		return ChatColor.of(new Color(r, g, b));
	}

	/**
	 * Attempts to collapse an RGB colour to established Minecraft colours.
	 *
	 * @param colour The given RGB colour.
	 * @return Returns the closest Minecraft match, or null.
	 */
	public static ChatColor collapseColour(final ChatColor colour) {
		if (colour == null) {
			return null;
		}
		final Color color = colour.getColor();
		ChatColor nearestColour = null;
		double nearestDistance = Double.MAX_VALUE;
		for (final ChatColor currentColour : COLOURS) {
			final Color currentColor = currentColour.getColor();
			final double distance = Math.sqrt(
					Math.pow(color.getRed() - currentColor.getRed(), 2)
					- Math.pow(color.getGreen() - currentColor.getGreen(), 2)
					- Math.pow(color.getBlue() - currentColor.getBlue(), 2));
			if (nearestDistance > distance) {
				nearestDistance = distance;
				nearestColour = currentColour;
			}
		}
		return nearestColour;
	}

	// -------------------------------------------- //
	// Color parsing
	// -------------------------------------------- //

	/**
	 * @deprecated Please use MiniMessage instead.
	 * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
	 */
	@Deprecated
	public static String parseColor(String string) {
		string = parseColorAmp(string);
		string = parseColorAcc(string);
		string = parseColorTags(string);
		return string;
	}

	/**
	 * @deprecated Please use MiniMessage instead.
	 * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
	 */
	@Deprecated
	public static String parseColorAmp(String string) {
		string = string.replace("&&", "&");
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	/**
	 * @deprecated Please use MiniMessage instead.
	 * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
	 */
	@Deprecated
	public static String parseColorAcc(String string) {
		return ChatColor.translateAlternateColorCodes('`', string);
	}

	/**
	 * @deprecated Please use MiniMessage instead.
	 * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
	 */
	@Deprecated
	public static String parseColorTags(String string) {
		return string
				.replace("<black>", ChatColor.BLACK.toString())
				.replace("<dblue>", ChatColor.DARK_BLUE.toString())
				.replace("<dgreen>", ChatColor.DARK_GREEN.toString())
				.replace("<daqua>", ChatColor.DARK_AQUA.toString())
				.replace("<dred>", ChatColor.DARK_RED.toString())
				.replace("<dpurple>", ChatColor.DARK_PURPLE.toString())
				.replace("<gold>", ChatColor.GOLD.toString())
				.replace("<lgray>", ChatColor.GRAY.toString()) // This has to be lgray because gray is already claimed.
				.replace("<dgray>", ChatColor.DARK_GRAY.toString())
				.replace("<blue>", ChatColor.BLUE.toString())
				.replace("<green>", ChatColor.GREEN.toString())
				.replace("<aqua>", ChatColor.AQUA.toString())
				.replace("<red>", ChatColor.RED.toString())
				.replace("<lpurple>", ChatColor.LIGHT_PURPLE.toString())
				.replace("<yellow>", ChatColor.YELLOW.toString())
				.replace("<white>", ChatColor.WHITE.toString())
				.replace("<s>", ChatColor.STRIKETHROUGH.toString())
				.replace("<u>", ChatColor.UNDERLINE.toString())
				.replace("<ul>", ChatColor.UNDERLINE.toString())
				.replace("<r>", ChatColor.RESET.toString())
				.replace("<strike>", ChatColor.STRIKETHROUGH.toString())
				.replace("<italic>", ChatColor.ITALIC.toString())
				.replace("<bold>", ChatColor.BOLD.toString())
				.replace("<reset>", ChatColor.RESET.toString())
				// Legacy support
				.replace("<empty>", "") // Just... why?
				.replace("<navy>", ChatColor.DARK_BLUE.toString())
				.replace("<teal>", ChatColor.DARK_AQUA.toString())
				.replace("<silver>", ChatColor.GRAY.toString())
				.replace("<gray>", ChatColor.DARK_GRAY.toString()) // REEE why name this gray?
				.replace("<lime>", ChatColor.GREEN.toString())
				.replace("<rose>", ChatColor.RED.toString())
				.replace("<pink>", ChatColor.LIGHT_PURPLE.toString())
				.replace("<it>", ChatColor.ITALIC.toString())
				.replace("<g>", ChatColor.GREEN.toString()) // Good
				.replace("<b>", ChatColor.RED.toString()) // Bad
				.replace("<i>", ChatColor.WHITE.toString()) // Info
				.replace("<a>", ChatColor.GOLD.toString()) // Art
				.replace("<l>", ChatColor.GREEN.toString()) // Logo
				.replace("<n>", ChatColor.GRAY.toString()) // Notice
				.replace("<h>", ChatColor.LIGHT_PURPLE.toString()) // Highlight
				.replace("<c>", ChatColor.AQUA.toString()) // Parameter
				.replace("<p>", ChatColor.DARK_AQUA.toString()) // Parameter
				.replace("<w>", ChatColor.WHITE.toString()) // Parameter
				.replace("<lp>", ChatColor.LIGHT_PURPLE.toString());
	}

	// -------------------------------------------- //
	// Component Stuff
	// -------------------------------------------- //

	/**
	 * <p>Determines whether a given base component is null or empty.</p>
	 *
	 * <p>This is determined by converting the component into plain text, so a non-null component filled with
	 * nothing but colour codes and hover text will likely return true.</p>
	 *
	 * @param component The component to test if null or empty.
	 * @return Returns true if the component is null or has no visible content.
	 */
	public static boolean isNullOrEmpty(final Component component) {
		if (component == null) {
			return true;
		}
		return StringUtils.isBlank(PlainComponentSerializer.plain().serialize(component));
	}

	/**
	 * Determines whether a given component is a {@code {"text":"","extra":[...]}} component.
	 *
	 * @param component The component to test.
	 * @return Returns whether the given component is a base / container component.
	 */
	public static boolean isBaseComponent(final Component component) {
		if (component == null) {
			return false;
		}
		return StringUtils.isEmpty(component instanceof TextComponent textComponent ? textComponent.content() : null)
				&& !component.children().isEmpty()
				&& component.clickEvent() == null
				&& component.hoverEvent() == null
				&& !component.hasStyling();
	}

	/**
	 * @return Generates a new text component that's specifically <i>NOT</i> italicised. Use this for item names and
	 *         lore.
	 */
	public static TextComponent newComponent() {
		return newComponent("");
	}

	/**
	 * Generates a new text component that's specifically <i>NOT</i> italicised. Use this for item names and lore.
	 *
	 * @param content The text content for the component.
	 * @return Returns the generated text component.
	 */
	public static TextComponent newComponent(final String content) {
		return Component.text(Objects.requireNonNull(content))
				.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
	}

	/**
	 * Clones a component.
	 *
	 * @param component The component to clone.
	 * @return Returns a clone of the given component.
	 */
	public static Component cloneComponent(final Component component) {
		if (component == null) {
			return null;
		}
		final var raw = GsonComponentSerializer.gson().serialize(component);
		return GsonComponentSerializer.gson().deserialize(raw);
	}

	/**
	 * Determines whether two given components are equal to each other.
	 *
	 * @param former The left hand side component.
	 * @param latter The right hand side component.
	 * @return Returns whether the two given components are equal.
	 */
	public static boolean areComponentsEqual(final Component former, final Component latter) {
		if (Objects.equals(former, latter)) {
			return true;
		}
		if (former == null || latter == null) {
			return false;
		}
		if (StringUtils.equals(
				MiniMessage.get().serialize(former),
				MiniMessage.get().serialize(latter))) {
			return true;
		}
		if (StringUtils.equals(
				LegacyComponentSerializer.legacyAmpersand().serialize(former),
				LegacyComponentSerializer.legacyAmpersand().serialize(latter))) {
			return true;
		}
		return false;
	}

}
