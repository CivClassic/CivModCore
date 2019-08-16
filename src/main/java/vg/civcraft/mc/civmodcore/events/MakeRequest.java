package vg.civcraft.mc.civmodcore.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class MakeRequest extends Event implements Cancellable {

	@Getter @Setter
	private boolean cancelled = false;
	private boolean fulfilled = false;

	public boolean isFulfilled() {
		return this.cancelled && this.fulfilled;
	}

	public void fulfill() {
		this.fulfilled = true;
		this.cancelled = true;
	}

}
