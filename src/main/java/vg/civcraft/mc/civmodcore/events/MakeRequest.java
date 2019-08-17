package vg.civcraft.mc.civmodcore.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class MakeRequest extends Event implements Cancellable {

	private boolean cancelled = false;
	private boolean fulfilled = false;

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isFulfilled() {
		return this.cancelled && this.fulfilled;
	}

	public void fulfill() {
		this.cancelled = true;
		this.fulfilled = true;
	}

}
