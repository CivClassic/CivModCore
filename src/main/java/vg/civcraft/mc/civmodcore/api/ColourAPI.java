package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Color;

public final class ColourAPI {
	private ColourAPI() {} // Make the class effectively static

	/**
	 * Converts a Color to an integer representing RBG.
	 * @return Returns the colour as RBG or zero if the colour is null.
	 * */
    public static int colourToRGB(Color colour) {
    	if (colour == null) {
    		return 0;
		}
    	else {
    		return colour.asRGB();
		}
    }

    /**
	 * Converts an integer into a Color.
	 * */
    public static Color rbgToColour(int rgb) {
		try {
			// Ensure that no error occurs by clamping the rgb in to the
			// bytes that store the rgb values
			return Color.fromRGB(rgb & 0x00FFFFFF);
		}
		catch (IllegalArgumentException exception) {
			return Color.BLACK;
		}
    }

}
