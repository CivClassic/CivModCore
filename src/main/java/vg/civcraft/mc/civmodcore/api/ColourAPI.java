package vg.civcraft.mc.civmodcore.api;

import org.bukkit.Color;

public final class ColourAPI {
	private ColourAPI() {} // Make the class effectively static

    public static int colourToRGB(Color colour) {
    	if (colour == null) {
    		return 0;
		}
    	else {
    		return colour.asRGB();
		}
    }

    public static Color rbgToColour(int rgb) {
		try {
			// Ensure that no error occurs by clamping the rgb in to the
			// bytes that store the rgb values
			return Color.fromRGB(rgb & 0b00000000111111111111111111111111);
		}
		catch (IllegalArgumentException exception) {
			return Color.BLACK;
		}
    }

}
