package vg.civcraft.mc.civmodcore.utilities;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import com.google.common.base.Strings;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.PluginClassLoader;

public final class CivLogger extends Logger {

	private final String prefix;

	private CivLogger(final Logger logger, final String prefix) {
		super(logger.getName(), logger.getResourceBundleName());
		setParent(logger);
		this.prefix = prefix;
	}

	@Override
	public void log(final LogRecord record) {
		if (!Strings.isNullOrEmpty(this.prefix)) {
			record.setMessage("[" + this.prefix + "] " + record.getMessage());
		}
		super.log(record);
	}

	/**
	 * Creates a logger based on a given class. If the given class was loaded by a plugin, it will piggy back off that
	 * plugin's logger.
	 *
	 * @param clazz The class to base the logger on.
	 * @return Returns a new civ logger.
	 */
	public static CivLogger getLogger(@Nonnull final Class<?> clazz) {
		return INTERNAL_generateLogger(clazz.getClassLoader(), clazz);
	}

	/**
	 * Create a logger based on a given class and a deliberately specified plugin. Use this in shared classes and such
	 * which are used by other plugins. For example, {@link vg.civcraft.mc.civmodcore.commands.CommandManager} is used
	 * by multiple plugins, thus it would be disingenuous for it to log as if the messages originate from CivModCore
	 * instead of the plugin that instantiated the class.
	 *
	 * @param pluginClass The instance-originating plugin class.
	 * @param targetClass The target class to log about.
	 * @return Returns a new civ logger.
	 */
	public static CivLogger getLogger(@Nonnull final Class<? extends Plugin> pluginClass,
									  @Nonnull final Class<?> targetClass) {
		return INTERNAL_generateLogger(pluginClass.getClassLoader(), targetClass);
	}

	private static CivLogger INTERNAL_generateLogger(@Nonnull final ClassLoader loader,
													 @Nonnull final Class<?> clazz) {
		if (loader instanceof PluginClassLoader pluginClassLoader) {
			final var plugin = pluginClassLoader.getPlugin();
			if (plugin != null) {
				return new CivLogger(plugin.getLogger(), clazz.getSimpleName());
			}
			// Plugin has been constructed but not initialised yet
			final var descriptionField = FieldUtils.getDeclaredField(PluginClassLoader.class, "description", true);
			try {
				final var description = (PluginDescriptionFile) descriptionField.get(loader);
				final var logger = PaperPluginLogger.getLogger(description);
				return new CivLogger(logger, clazz.getSimpleName());
			}
			catch (final IllegalAccessException ignored) {}
		}
		return new CivLogger(Bukkit.getLogger(), clazz.getSimpleName());
	}

}
