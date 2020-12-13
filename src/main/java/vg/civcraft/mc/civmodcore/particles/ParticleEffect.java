package vg.civcraft.mc.civmodcore.particles;

import com.google.common.base.Preconditions;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import vg.civcraft.mc.civmodcore.world.WorldUtils;

public class ParticleEffect {

	private final Particle particle;
	private final float offsetX;
	private final float offsetY;
	private final float offsetZ;
	private final float speed;
	private final int particleCount;

	public ParticleEffect(final Particle particle,
						  final float offsetX,
						  final float offsetY,
						  final float offsetZ,
						  final float speed,
						  final int particleCount) {
		this.particle = particle;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.speed = speed;
		this.particleCount = particleCount;
	}

	/**
	 * @return the type of particle used in this effect
	 */
	public Particle getParticle() {
		return this.particle;
	}

	/**
	 * @return the amount to be randomly offset by in the X axis
	 */
	public float getOffsetX() {
		return this.offsetX;
	}

	/**
	 * @return the amount to be randomly offset by in the Y axis
	 */
	public float getOffsetY() {
		return this.offsetY;
	}

	/**
	 * @return the amount to be randomly offset by in the Z axis
	 */
	public float getOffsetZ() {
		return this.offsetZ;
	}

	/**
	 * @return the speed of the particles
	 */
	public float getSpeed() {
		return this.speed;
	}

	/**
	 * @return the amount of particle to display.
	 */
	public int getParticleCount() {
		return this.particleCount;
	}

	/**
	 * Display an effect defined in the config around a reinforcement.
	 *
	 * @param location
	 *            the location of the reinforcement.
	 */
	public void playEffect(final Location location) {
		Preconditions.checkArgument(WorldUtils.isValidLocation(location));
		final World world = Objects.requireNonNull(location.getWorld());
		world.spawnParticle(this.particle, location, this.particleCount,
				this.offsetX, this.offsetY, this.offsetZ, this.speed, null);
	}

	@Override
	public String toString() {
		return String.format("ParticleEffect{\n\ttype=%s,\n\toffsetX=%f\n\toffsetY=%f\n\toffsetZ=%f\n\t" +
				"speed=%f\n\tparticleCount=%d\n}", this.particle, this.offsetX, this.offsetY, this.offsetZ,
				this.speed, this.particleCount);
	}

}
