package vg.civcraft.mc.civmodcore.locations.chunkmeta.block.json;

import com.google.gson.JsonObject;
import org.bukkit.Location;
import vg.civcraft.mc.civmodcore.locations.chunkmeta.block.BlockDataObject;

public abstract class JsonableDataObject extends BlockDataObject<JsonableDataObject> {

    public JsonableDataObject(Location location, boolean isNew) {
        super(location, isNew);
    }

    protected static Location parseLocationFromJson(JsonObject json, JsonBasedBlockChunkMeta meta) {
        JsonObject inner = json.get(" ").getAsJsonObject();
        int x = inner.get("x").getAsInt();
        int y = inner.get("y").getAsInt();
        int z = inner.get("z").getAsInt();
        return new Location(meta.getWorld(), x, y, z);
    }

    public abstract void concreteSerialize(JsonObject base);

    protected JsonObject serialize() {
        JsonObject json = new JsonObject();
        JsonObject inner = new JsonObject();
        inner.addProperty("x", location.getBlockX());
        inner.addProperty("y", location.getBlockY());
        inner.addProperty("z", location.getBlockZ());
        json.add(" ", inner);
        concreteSerialize(json);
        return json;
    }

}
