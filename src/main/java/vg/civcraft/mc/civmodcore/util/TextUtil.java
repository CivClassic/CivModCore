package vg.civcraft.mc.civmodcore.util;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import vg.civcraft.mc.civmodcore.chat.ChatUtils;

/**
 * The goal with this is to move most APIs to {@link ChatUtils} as possible.
 */
public class TextUtil {

	public static String formatDuration(long time) {
		return formatDuration(time, TimeUnit.MILLISECONDS);
	}

	public static String formatDuration(long time, TimeUnit unit) {
		long totalSeconds = TimeUnit.SECONDS.convert(time, unit);
		long seconds = totalSeconds % 60;
		long totalMinutes = totalSeconds / 60;
		long minutes = totalMinutes % 60;
		long totalHours = totalMinutes / 60;
		StringBuilder sb = new StringBuilder();
		if (totalHours > 0) {
			sb.append(totalHours);
			sb.append(" h ");
		}
		if (minutes > 0) {
			sb.append(minutes);
			sb.append(" min ");
		}
		if (seconds > 0) {
			sb.append(seconds);
			sb.append(" sec");
		}
		return sb.toString().trim();
	}

	// -------------------------------------------- //
	// Top-level parsing functions.
	// -------------------------------------------- //

	public static String parse(String str, Object... args) {
		return String.format(parse(str), args);
	}

	public static String parse(String str) {
		return parseColor(str);
	}

	// -------------------------------------------- //
	// Color parsing
	// -------------------------------------------- //

	/**
	 * @deprecated Use {@link ChatUtils#parseColor(String)} instead.
	 */
	@Deprecated
	public static String parseColor(String string) {
		return ChatUtils.parseColor(string);
	}

	/**
	 * @deprecated Use {@link ChatUtils#parseColorAmp(String)} instead.
	 */
	@Deprecated
	public static String parseColorAmp(String string) {
		return ChatUtils.parseColorAmp(string);
	}

	/**
	 * @deprecated Use {@link ChatUtils#parseColorAcc(String)} instead.
	 */
	@Deprecated
	public static String parseColorAcc(String string) {
		return ChatUtils.parseColorAcc(string);
	}

	/**
	 * @deprecated Use {@link ChatUtils#parseColorTags(String)} instead.
	 */
	@Deprecated
	public static String parseColorTags(String string) {
		return ChatUtils.parseColorTags(string);
	}

	/**
	 * @deprecated Use {@link ChatUtils#parseColorTags(String)} instead.
	 */
	@Deprecated
	public static String parseTags(String sting) {
		return parseColorTags(sting);
	}

	// -------------------------------------------- //
	// Standard utils like UCFirst, implode and repeat.
	// -------------------------------------------- //

	public static String upperCaseFirst(String string) {
		Preconditions.checkArgument(string != null);
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	/**
	 * @deprecated Use {@link StringUtils#repeat(String, int)} instead.
	 */
	@Deprecated
	public static String repeat(String string, int times) {
		Preconditions.checkArgument(string != null);
		return StringUtils.repeat(string, times);
	}

	/**
	 * @deprecated Use {@link StringUtils#join(Object[], String)} instead.
	 */
	@Deprecated
	public static String implode(List<String> list, String glue) {
		Preconditions.checkArgument(list != null);
		Preconditions.checkArgument(glue != null);
		return StringUtils.join(list, glue);
	}

	public static String implodeCommaAnd(List<String> list, String comma, String and) {
		if (list.size() == 0) {
			return "";
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		String lastItem = list.get(list.size() - 1);
		String nextToLastItem = list.get(list.size() - 2);
		String merge = nextToLastItem + and + lastItem;
		list.set(list.size() - 2, merge);
		list.remove(list.size() - 1);
		return implode(list, comma);
	}

	public static String implodeCommaAnd(List<String> list) {
		return implodeCommaAnd(list, ", ", " and ");
	}

	// -------------------------------------------- //
	// Paging and chrome-tools like titleize
	// -------------------------------------------- //

	private static final String titleizeLine = repeat("_", 52);
	private static final int titleizeBalance = -1;

	public static String titleize(String str) {
		return titleize("<a>", str);
	}

	public static String titleize(String colorCode, String str) {
		String center = ".[ " + parseColorTags("<l>") + str + parseColorTags(colorCode) + " ].";
		int centerlen = ChatColor.stripColor(center).length();
		int pivot = titleizeLine.length() / 2;
		int eatLeft = (centerlen / 2) - titleizeBalance;
		int eatRight = (centerlen - eatLeft) + titleizeBalance;

		if (eatLeft < pivot) {
			return parseColorTags(colorCode) + titleizeLine.substring(0, pivot - eatLeft) + center + titleizeLine.substring(pivot + eatRight);
		}
		else {
			return parseColorTags(colorCode) + center;
		}
	}

	public static ArrayList<String> getPage(List<String> lines, int pageHumanBased, String title) {
		ArrayList<String> ret = new ArrayList<>();
		int pageZeroBased = pageHumanBased - 1;
		int pageheight = 9;
		int pagecount = (lines.size() / pageheight) + 1;

		ret.add(titleize(title + " " + pageHumanBased + "/" + pagecount));

		if (pagecount == 0) {
			ret.add(parseColorTags("<i>Sorry. No Pages available."));
			return ret;
		}
		else if (pageZeroBased < 0 || pageHumanBased > pagecount) {
			ret.add(parseColorTags("<i>Invalid page. Must be between 1 and " + pagecount));
			return ret;
		}

		int from = pageZeroBased * pageheight;
		int to = from + pageheight;
		if (to > lines.size()) {
			to = lines.size();
		}

		ret.addAll(lines.subList(from, to));

		return ret;
	}

	/**
	 * Static utility method for easily sending formatting strings to players.
	 *
	 * @param player The player
	 * @param message The message
	 */
	@Deprecated
	public static void msg(Player player, String message) {
		if (player != null && player.isOnline()) {
			player.sendMessage(parse(message));
		}
	}

	/**
	 * Static utility method for easily sending formatting strings to players.
	 *
	 * @param player The player
	 * @param message The message
	 * @param args Additional arguments which are used later in a String.format()
	 */
	@Deprecated
	public static void msg(Player player, String message, Object... args) {
		if (player != null && player.isOnline()) {
			player.sendMessage(parse(message, args));
		}
	}

	// -------------------------------------------- //
	// Misc
	// -------------------------------------------- //

	/**
	 * @deprecated Use {@link StringUtils#equals(CharSequence, CharSequence)} instead.
	 */
	@Deprecated
	public static boolean stringEquals(String former, String latter) {
		return StringUtils.equals(former, latter);
	}

	/**
	 * @deprecated Use {@link StringUtils#equalsIgnoreCase(CharSequence, CharSequence)} instead.
	 */
	@Deprecated
	public static boolean stringEqualsIgnoreCase(String former, String latter) {
		return StringUtils.equalsIgnoreCase(former, latter);
	}

	/**
	 * @deprecated Use {@link StringUtils#startsWithIgnoreCase(CharSequence, CharSequence)} instead.
	 */
	@Deprecated
	public static boolean startsWith(String container, String contained) {
		return StringUtils.startsWithIgnoreCase(container, contained);
	}

	/**
	 * @deprecated Use {@link ChatUtils#isNullOrEmpty(BaseComponent)} instead.
	 */
	@Deprecated
	public static boolean isNullOrEmpty(BaseComponent component) {
		return ChatUtils.isNullOrEmpty(component);
	}

	/**
	 * @deprecated Use {@link ChatUtils#textComponent(Object, net.md_5.bungee.api.ChatColor...)} instead.
	 */
	@Deprecated
	public static TextComponent textComponent(final Object value, final net.md_5.bungee.api.ChatColor... formats) {
		return ChatUtils.textComponent(value, formats);
	}

}
