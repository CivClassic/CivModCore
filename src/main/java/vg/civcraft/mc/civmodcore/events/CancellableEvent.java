package vg.civcraft.mc.civmodcore.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class CancellableEvent extends Event implements Cancellable {

	private boolean cancelled;
	private boolean canBeUncancelled;

	public CancellableEvent(boolean canBeUncancelled) {
		this.cancelled = false;
		this.canBeUncancelled = canBeUncancelled;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		if (!this.cancelled || this.canBeUncancelled) {
			this.cancelled = cancelled;
		}
	}

}
