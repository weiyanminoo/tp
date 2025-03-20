package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Edits the details of an existing wedding event in the address book.
 */
public class EditWeddingEventCommand extends Command {

    public static final String COMMAND_WORD = "editWedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the wedding identified by the weddingId used in the displayed wedding list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: weddingId"
            + " n/NAME"
            + " d/DATE"
            + " l/LOCATION"
            + " Example: " + COMMAND_WORD + " W1"
            + " n/John & Jane's Wedding"
            + " d/20-Feb-2025"
            + " l/Grand Ballroom";

    public static final String MESSAGE_EDIT_WEDDING_SUCCESS = "Edited Wedding: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book.";

    private final WeddingId index;
    private final EditWeddingDescriptor editWeddingDescriptor;

    /**
     * Creates an EditWeddingEventCommand to edit the wedding with the specified {@code weddingId}.
     *
     * @param weddingId The ID of the wedding to edit.
     * @param editWeddingDescriptor Details of the wedding fields to edit.
     */
    public EditWeddingEventCommand(WeddingId weddingId, EditWeddingDescriptor editWeddingDescriptor) {
        requireNonNull(weddingId);
        requireNonNull(editWeddingDescriptor);

        this.index = weddingId;
        this.editWeddingDescriptor = new EditWeddingDescriptor(editWeddingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        // Find the wedding by its ID
        Wedding weddingToEdit = lastShownList.stream()
                .filter(wedding -> wedding.getWeddingId().equals(index))
                .findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX));

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

        WeddingId originalId = weddingToEdit.getWeddingId();
        WeddingDate updatedDate = editWeddingDescriptor.getWeddingDate().orElse(weddingToEdit.getWeddingDate());
        WeddingLocation updatedLocation = editWeddingDescriptor.getWeddingLocation()
                                            .orElse(weddingToEdit.getWeddingLocation());
        WeddingName updatedName = editWeddingDescriptor.getWeddingName().orElse(weddingToEdit.getWeddingName());

        return new Wedding(originalId, updatedName, updatedDate, updatedLocation);
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
        return String.format(
                "EditWeddingEventCommand{index=%s, editWeddingDescriptor=%s}",
                index.toString(),
                editWeddingDescriptor.toString());
    }

    /**
     * Stores the details to edit the wedding with. Each non-empty field value will replace the
     * corresponding field value of the wedding.
     */
    public static class EditWeddingDescriptor {

        private WeddingName weddingName;
        private WeddingDate weddingDate;
        private WeddingLocation weddingLocation;

        public EditWeddingDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditWeddingDescriptor(EditWeddingDescriptor toCopy) {
            setWeddingName(toCopy.weddingName);
            setWeddingDate(toCopy.weddingDate);
            setWeddingLocation(toCopy.weddingLocation);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(weddingName, weddingDate, weddingLocation);
        }

        public void setWeddingName(WeddingName weddingName) {
            this.weddingName = weddingName;
        }

        public Optional<WeddingName> getWeddingName() {
            return Optional.ofNullable(weddingName);
        }

        public void setWeddingDate(WeddingDate weddingDate) {
            this.weddingDate = weddingDate;
        }

        public Optional<WeddingDate> getWeddingDate() {
            return Optional.ofNullable(weddingDate);
        }

        public void setWeddingLocation(WeddingLocation weddingLocation) {
            this.weddingLocation = weddingLocation;
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
            return String.format(
                    "EditWeddingDescriptor{weddingName=Optional[%s], weddingDate=Optional[%s], "
                            + "weddingLocation=Optional[%s]}",
                    weddingName == null ? "" : weddingName.toString(),
                    weddingDate == null ? "" : weddingDate.toString(),
                    weddingLocation == null ? "" : weddingLocation.toString());
        }

    }
}
