package seedu.address.model.wedding;

/**
 * Represents a basic Wedding Task in the contact book.
 * A Task has a description and a completion status.
 */
public class WeddingTask {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a Task with the given description.
     * The task is initially not done.
     *
     * @param description The description of the task.
     */
    public WeddingTask(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns true if this task is marked as done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task, including
     * its completion status.
     */
    @Override
    public String toString() {
        String statusIcon = isDone ? "X" : " ";
        return "[" + statusIcon + "] " + description;
    }

    /**
     * Compares this Task to another object for equality.
     * Two tasks are considered equal if they share the same description
     * and the same completion status.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WeddingTask)) {
            return false;
        }
        WeddingTask otherTask = (WeddingTask) other;
        return this.description.equals(otherTask.description)
                && this.isDone == otherTask.isDone;
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (isDone ? 1 : 0);
        return result;
    }
}
