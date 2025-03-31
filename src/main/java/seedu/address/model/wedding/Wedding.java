package seedu.address.model.wedding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Wedding event in the contact book.
 */
public class Wedding {
    private static int nextId = 1; // Static counter to keep track of Wedding ID
    private final WeddingId weddingId;
    private final WeddingName weddingName;
    private final WeddingDate weddingDate;
    private final WeddingLocation weddingLocation;
    private final boolean restored;
    private final List<WeddingTask> tasks;

    /**
     * Creates a Wedding object with the given fields.
     */
    public Wedding(WeddingName weddingName, WeddingDate weddingDate, WeddingLocation weddingLocation) {
        this.weddingId = new WeddingId("W" + nextId);
        this.weddingName = weddingName;
        this.weddingDate = weddingDate;
        this.weddingLocation = weddingLocation;
        this.restored = false;

        // Initialize tasks for a newly created wedding
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a Wedding object with a specified ID (useful for restoration),
     * defaulting to not restored.
     */
    public Wedding(WeddingId weddingId, WeddingName weddingName, WeddingDate weddingDate, WeddingLocation location) {
        this(weddingId, weddingName, weddingDate, location, false);
    }

    /**
     * Creates a Wedding object with a specified ID (useful for restoration).
     */

    public Wedding(WeddingId weddingId, WeddingName weddingName, WeddingDate weddingDate,
                   WeddingLocation weddingLocation, boolean isRestored) {
        this.weddingId = weddingId;
        this.weddingName = weddingName;
        this.weddingDate = weddingDate;
        this.weddingLocation = weddingLocation;
        this.restored = isRestored;

        // Initialize tasks for a restored wedding (empty by default)
        this.tasks = new ArrayList<>();

        if (isRestored) {
            int numericPart = Integer.parseInt(weddingId.value.substring(1)); // skip 'W'
            if (numericPart >= nextId) {
                nextId = numericPart + 1;
            }
        }
    }

    /**
     * Returns the list of tasks for this wedding as an unmodifiable list.
     */
    public List<WeddingTask> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Returns a string representation of the tasks for this wedding for UI.
     */
    public String getTasksString() {
        if (tasks.isEmpty()) {
            return "No tasks found for this wedding";
        }

        if (tasks.size() == 1) {
            return "There is 1 task for this wedding";
        }
        return "There are " + tasks.size() + " tasks for this wedding";
    }

    /**
     * Adds a task to this wedding's task list.
     */
    public void addTask(WeddingTask task) {
        tasks.add(task);
    }

    /**
     * Removes a task from this wedding by zero-based index.
     * @return The removed Task.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public WeddingTask removeTask(int index) throws IndexOutOfBoundsException {
        return tasks.remove(index);
    }

    public boolean isRestored() {
        return restored;
    }

    /**
     * For read-only access to the current counter value.
     */
    public static int getNextId() {
        return nextId;
    }

    /**
     * For forcibly resetting the counter, e.g. if a duplicate was detected.
     */
    public static void setNextId(int newValue) {
        nextId = newValue;
    }

    public WeddingId getWeddingId() {
        return weddingId;
    }

    public WeddingName getWeddingName() {
        return weddingName;
    }

    public WeddingDate getWeddingDate() {
        return weddingDate;
    }

    public WeddingLocation getWeddingLocation() {
        return weddingLocation;
    }

    /**
     * Returns true if this wedding and the given {@code otherWedding} have the same wedding ID.
     *
     * @param otherWedding The other wedding to compare against.
     * @return True if both weddings share the same wedding ID, false otherwise.
     */
    public boolean isSameWedding(Wedding otherWedding) {
        if (otherWedding == this) {
            return true;
        }
        if (otherWedding == null) {
            return false;
        }

        return this.weddingName.equals(otherWedding.weddingName)
                && this.weddingDate.equals(otherWedding.weddingDate)
                && this.weddingLocation.equals(otherWedding.weddingLocation);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Wedding)) {
            return false;
        }
        Wedding otherWedding = (Wedding) other;
        return weddingId.equals(otherWedding.weddingId)
                && weddingName.equals(otherWedding.weddingName)
                && weddingDate.equals(otherWedding.weddingDate)
                && weddingLocation.equals(otherWedding.weddingLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weddingId, weddingName, weddingDate, weddingLocation);
    }

    @Override
    public String toString() {
        return String.format("\nWedding: %s (ID: %s)\nDate: %s\nLocation: %s",
                weddingName, weddingId, weddingDate, weddingLocation);
    }
}
