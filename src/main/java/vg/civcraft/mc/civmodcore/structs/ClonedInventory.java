package vg.civcraft.mc.civmodcore.structs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ClonedInventory implements Inventory {

    private Inventory inventory;

    private ClonedInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getSize() {
        return this.inventory.getSize();
    }

    @Override
    public int getMaxStackSize() {
        return this.inventory.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int size) {
        this.inventory.setMaxStackSize(size);
    }

    @Override
    public String getName() {
        return this.inventory.getName();
    }

    @Override
    public ItemStack getItem(int index) {
        return this.inventory.getItem(index);
    }

    @Override
    public void setItem(int index, ItemStack item) {

    }

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
        return this.inventory.addItem(items);
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
        return this.inventory.removeItem(items);
    }

    @Override
    public ItemStack[] getContents() {
        return this.inventory.getContents();
    }

    @Override
    public void setContents(ItemStack[] items) throws IllegalArgumentException {
        this.inventory.setContents(items);
    }

    @Override
    public ItemStack[] getStorageContents() {
        return this.inventory.getStorageContents();
    }

    @Override
    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
        this.inventory.setStorageContents(items);
    }

    @Deprecated
    @Override
    public boolean contains(int materialId) {
        return this.inventory.contains(materialId);
    }

    @Override
    public boolean contains(Material material) throws IllegalArgumentException {
        return this.inventory.contains(material);
    }

    @Override
    public boolean contains(ItemStack item) {
        return this.inventory.contains(item);
    }

    @Deprecated
    @Override
    public boolean contains(int materialId, int amount) {
        return this.inventory.contains(materialId, amount);
    }

    @Override
    public boolean contains(Material material, int amount) throws IllegalArgumentException {
        return this.inventory.contains(material, amount);
    }

    @Override
    public boolean contains(ItemStack item, int amount) {
        return this.inventory.contains(item, amount);
    }

    @Override
    public boolean containsAtLeast(ItemStack item, int amount) {
        return this.inventory.containsAtLeast(item, amount);
    }

    @Deprecated
    @Override
    public HashMap<Integer, ? extends ItemStack> all(int materialId) {
        return this.inventory.all(materialId);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
        return this.inventory.all(material);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(ItemStack item) {
        return this.inventory.all(item);
    }

    @Deprecated
    @Override
    public int first(int materialId) {
        return this.inventory.first(materialId);
    }

    @Override
    public int first(Material material) throws IllegalArgumentException {
        return this.inventory.first(material);
    }

    @Override
    public int first(ItemStack item) {
        return this.inventory.first(item);
    }

    @Override
    public int firstEmpty() {
        return this.inventory.firstEmpty();
    }

    @Deprecated
    @Override
    public void remove(int materialId) {
        this.inventory.remove(materialId);
    }

    @Override
    public void remove(Material material) throws IllegalArgumentException {
        this.inventory.remove(material);
    }

    @Override
    public void remove(ItemStack item) {
        this.inventory.remove(item);
    }

    @Override
    public void clear(int index) {
        this.inventory.clear(index);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public List<HumanEntity> getViewers() {
        return this.inventory.getViewers();
    }

    @Override
    public String getTitle() {
        return this.inventory.getTitle();
    }

    @Override
    public InventoryType getType() {
        return this.inventory.getType();
    }

    @Override
    public InventoryHolder getHolder() {
        return this.inventory.getHolder();
    }

    @Nonnull
    @Override
    public ListIterator<ItemStack> iterator() {
        return this.inventory.iterator();
    }

    @Override
    public void forEach(Consumer<? super ItemStack> action) {
        this.inventory.forEach(action);
    }

    @Override
    public Spliterator<ItemStack> spliterator() {
        return this.inventory.spliterator();
    }

    @Override
    public ListIterator<ItemStack> iterator(int i) {
        return this.inventory.iterator(i);
    }

    @Override
    public Location getLocation() {
        return this.inventory.getLocation();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public static ClonedInventory cloneInventory(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        if (inventory instanceof ClonedInventory) {
            return (ClonedInventory) inventory;
        }
        Inventory clone = Bukkit.createInventory(inventory.getHolder(), inventory.getType(), inventory.getTitle());
        if (clone == null) {
            return null;
        }
        clone.setContents(Arrays.stream(inventory.getContents()).
                map(item -> item == null ? null : item.clone()).
                toArray(ItemStack[]::new));
        return new ClonedInventory(clone);
    }

}
