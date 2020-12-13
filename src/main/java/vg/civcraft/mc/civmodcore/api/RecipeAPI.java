package vg.civcraft.mc.civmodcore.api;

import org.bukkit.inventory.Recipe;
import vg.civcraft.mc.civmodcore.inventory.RecipeManager;

/**
 * @deprecated Use {@link RecipeManager} instead.
 */
@Deprecated
public final class RecipeAPI {

	/**
	 * @deprecated Use {@link RecipeManager#matchRecipe(Recipe, Recipe)} instead.
	 */
	@Deprecated
	public static boolean matchRecipe(Recipe base, Recipe other) {
		return RecipeManager.matchRecipe(base, other);
	}

	/**
	 * @deprecated Use {@link RecipeManager#registerRecipe(Recipe)} instead.
	 */
	@Deprecated
	public static boolean registerRecipe(Recipe recipe) {
		return RecipeManager.registerRecipe(recipe);
	}

	/**
	 * @deprecated Use {@link RecipeManager#registerRecipe(Recipe)} instead.
	 */
	@Deprecated
	public static boolean removeRecipe(Recipe recipe) {
		return RecipeManager.removeRecipe(recipe);
	}

}
