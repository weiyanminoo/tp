package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wedding.WeddingTask;

/**
 * Jackson-friendly version of {@link WeddingTask}.
 */
class JsonAdaptedTask {

    private final String description;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description,
                           @JsonProperty("isDone") boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(WeddingTask source) {
        this.description = source.getDescription();
        this.isDone = source.isDone();
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public WeddingTask toModelType() throws IllegalValueException {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalValueException("Task description cannot be empty!");
        }
        WeddingTask task = new WeddingTask(description);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof JsonAdaptedTask)) {
            return false;
        }
        JsonAdaptedTask o = (JsonAdaptedTask) other;
        return Objects.equals(description, o.description)
                && isDone == o.isDone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, isDone);
    }
}
