package vg.civcraft.mc.civmodcore.events;

public abstract class MakeRequest extends CancellableEvent {

	private boolean fulfilled = false;

	public MakeRequest() {
		super(false);
	}

	public boolean isFulfilled() {
		return isCancelled() && this.fulfilled;
	}

	public void fulfill() {
		setCancelled(true);
		this.fulfilled = true;
	}

}
