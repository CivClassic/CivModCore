package vg.civcraft.mc.civmodcore.maps;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import net.minecraft.server.v1_16_R3.MaterialMapColor;
import org.apache.commons.collections4.CollectionUtils;
import vg.civcraft.mc.civmodcore.util.CivLogger;

/**
 * <a href="https://minecraft.fandom.com/wiki/Map_item_format#Base_colors">Reference</a>
 */
public enum MapColours {

	NONE(MaterialMapColor.b),
	GRASS(MaterialMapColor.c),
	SAND(MaterialMapColor.d),
	WOOL(MaterialMapColor.e), // White wool
	FIRE(MaterialMapColor.f),
	ICE(MaterialMapColor.g),
	METAL(MaterialMapColor.h),
	PLANT(MaterialMapColor.i),
	SNOW(MaterialMapColor.j),
	CLAY(MaterialMapColor.k),
	DIRT(MaterialMapColor.l),
	STONE(MaterialMapColor.m),
	WATER(MaterialMapColor.n),
	WOOD(MaterialMapColor.o),
	QUARTZ(MaterialMapColor.p),
	COLOR_ORANGE(MaterialMapColor.q),
	COLOR_MAGENTA(MaterialMapColor.r),
	COLOR_LIGHT_BLUE(MaterialMapColor.s),
	COLOR_YELLOW(MaterialMapColor.t),
	COLOR_LIGHT_GREEN(MaterialMapColor.u),
	COLOR_PINK(MaterialMapColor.v),
	COLOR_GRAY(MaterialMapColor.w),
	COLOR_LIGHT_GRAY(MaterialMapColor.x),
	COLOR_CYAN(MaterialMapColor.y),
	COLOR_PURPLE(MaterialMapColor.z),
	COLOR_BLUE(MaterialMapColor.A),
	COLOR_BROWN(MaterialMapColor.B),
	COLOR_GREEN(MaterialMapColor.C),
	COLOR_RED(MaterialMapColor.D),
	COLOR_BLACK(MaterialMapColor.E),
	GOLD(MaterialMapColor.F),
	DIAMOND(MaterialMapColor.G),
	LAPIS(MaterialMapColor.H),
	EMERALD(MaterialMapColor.I),
	PODZOL(MaterialMapColor.J),
	NETHER(MaterialMapColor.K),
	TERRACOTTA_WHITE(MaterialMapColor.L),
	TERRACOTTA_ORANGE(MaterialMapColor.M),
	TERRACOTTA_MAGENTA(MaterialMapColor.N),
	TERRACOTTA_LIGHT_BLUE(MaterialMapColor.O),
	TERRACOTTA_YELLOW(MaterialMapColor.P),
	TERRACOTTA_LIGHT_GREEN(MaterialMapColor.Q),
	TERRACOTTA_PINK(MaterialMapColor.R),
	TERRACOTTA_GRAY(MaterialMapColor.S),
	TERRACOTTA_LIGHT_GRAY(MaterialMapColor.T),
	TERRACOTTA_CYAN(MaterialMapColor.U),
	TERRACOTTA_PURPLE(MaterialMapColor.V),
	TERRACOTTA_BLUE(MaterialMapColor.W),
	TERRACOTTA_BROWN(MaterialMapColor.X),
	TERRACOTTA_GREEN(MaterialMapColor.Y),
	TERRACOTTA_RED(MaterialMapColor.Z),
	TERRACOTTA_BLACK(MaterialMapColor.aa),
	CRIMSON_NYLIUM(MaterialMapColor.ab),
	CRIMSON_STEM(MaterialMapColor.ac),
	CRIMSON_HYPHAE(MaterialMapColor.ad),
	WARPED_NYLIUM(MaterialMapColor.ae),
	WARPED_STEM(MaterialMapColor.af),
	WARPED_HYPHAE(MaterialMapColor.ag),
	WARPED_WART_BLOCK(MaterialMapColor.ah);

	private final MaterialMapColor nms;

	MapColours(@Nonnull final MaterialMapColor nms) {
		this.nms = Objects.requireNonNull(nms);
	}

	public MaterialMapColor asNMS() {
		return this.nms;
	}

	public static void init() {
		final Set<MaterialMapColor> coreMapColours = Set.of(Stream.of(values())
				.map(MapColours::asNMS)
				.toArray(MaterialMapColor[]::new));
		final Set<MaterialMapColor> baseMapColours = Set.of(Stream.of(MaterialMapColor.a)
				.filter(Objects::nonNull)
				.toArray(MaterialMapColor[]::new));
		final Collection<MaterialMapColor> missing = CollectionUtils.disjunction(coreMapColours, baseMapColours);
		if (!missing.isEmpty()) {
			final CivLogger logger = CivLogger.getLogger(MapColours.class);
			logger.warning("The following map colours are missing: " + missing.stream()
					.map(e -> Integer.toString(e.aj))
					.collect(Collectors.joining(",")));
		}
	}

}
