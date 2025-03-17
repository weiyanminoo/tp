package seedu.address.logic.commands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

/**
 * Edits the details of an existing wedding event in the address book.
 */
public class EditWeddingEventCommand extends Command {
    public static final String COMMAND_WORD = "editWedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the wedding identified "
            + "by the index number used in the displayed wedding list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "n/NAME "
            + "d/DATE "
            + "l/LOCATION "
            + "Example: " + COMMAND_WORD + " 1 "
            + "n/John & Jane's Wedding "
            + "d/20-Feb-2025 "
            + "l/Grand Ballroom";

    public static final String MESSAGE_EDIT_WEDDING_SUCCESS = "Edited Wedding: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book.";

    public final WeddingId index;
    public final EditWeddingDescriptor editWeddingDescriptor;

    public EditWeddingEventCommand(WeddingId weddingId, EditWeddingDescriptor editWeddingDescriptor) {
        requireNonNull(weddingId);
        requireNonNull(editWeddingDescriptor);

        this.index =weddingId;
        this.editWeddingDescriptor = new EditWeddingDescriptor(editWeddingDescriptor);

    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (index.getValueInt() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }

        Wedding weddingToEdit = lastShownList.get(index.getValueInt());
        Wedding editedWedding = createEditedWedding(weddingToEdit, editWeddingDescriptor);

        if (!weddingToEdit.equals(editedWedding) && model.hasWedding(editedWedding)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        model.setWedding(weddingToEdit, editedWedding);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding)));
    }

    /**
     * Creates and returns a {@code Wedding} with the details of {@code weddingToEdit}
     * edited with {@code editWeddingDescriptor}.
     */
    public static Wedding createEditedWedding(Wedding weddingToEdit, EditWeddingDescriptor editWeddingDescriptor) {
        assert weddingToEdit != null;

        WeddingDate updatedDate = editWeddingDescriptor.getWeddingDate().orElse(weddingToEdit.getWeddingDate());
        WeddingLocation updatedLocation = editWeddingDescriptor.getWeddingLocation().orElse(weddingToEdit.getWeddingLocation());
        WeddingName updatedName = editWeddingDescriptor.getWeddingName().orElse(weddingToEdit.getWeddingName());

        return new Wedding(updatedName, updatedDate, updatedLocation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditWeddingEventCommand)) {
            return false;
        }

        EditWeddingEventCommand e = (EditWeddingEventCommand) other;
        return index.equals(e.index)
                && editWeddingDescriptor.equals(e.editWeddingDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editWeddingDescriptor", editWeddingDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the wedding with. Each non-empty field value will replace the
     * corresponding field value of the wedding.
     */
    public static class EditWeddingDescriptor {
        public WeddingName weddingName;
        public WeddingDate weddingDate;
        public WeddingLocation weddingLocation;

        public EditWeddingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditWeddingDescriptor(EditWeddingDescriptor toCopy) {
            setWeddingName(toCopy.weddingName);
            setWeddingDate(toCopy.weddingDate);
            setWeddingLocation(toCopy.weddingLocation);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(weddingName, weddingDate, weddingLocation);
        }

        public void setWeddingName(WeddingName weddingName) {
            this.weddingName = weddingName;
        }

        public void setWeddingDate(WeddingDate weddingDate) {
            this.weddingDate = weddingDate;
        }

        public void setWeddingLocation(WeddingLocation weddingLocation) {
            this.weddingLocation = weddingLocation;
        }

        public Optional<WeddingName> getWeddingName() {
            return Optional.ofNullable(weddingName);
        }

        public Optional<WeddingDate> getWeddingDate() {
            return Optional.ofNullable(weddingDate);
        }

        public Optional<WeddingLocation> getWeddingLocation() {
            return Optional.ofNullable(weddingLocation);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditWeddingDescriptor)) {
                return false;
            }

            EditWeddingDescriptor e = (EditWeddingDescriptor) other;
            return Objects.equals(weddingName, e.weddingName)
                    && Objects.equals(weddingDate, e.weddingDate)
                    && Objects.equals(weddingLocation, e.weddingLocation);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("weddingName", weddingName)
                    .add("weddingDate", weddingDate)
                    .add("weddingLocation", weddingLocation)
                    .toString();
        }
    }
}
