package seedu.address.testutil;

import seedu.address.logic.commands.EditWeddingEventCommand.EditWeddingDescriptor;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingName;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingLocation;


public class EditWeddingDescriptorBuilder {

    private EditWeddingDescriptor descriptor;

    public EditWeddingDescriptorBuilder() {
        descriptor = new EditWeddingDescriptor();
    }

    public EditWeddingDescriptorBuilder(EditWeddingDescriptor descriptor) {
        this.descriptor = new EditWeddingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditWeddingDescriptor} with fields containing {@code wedding}'s details.
     */
    public EditWeddingDescriptorBuilder(Wedding wedding) {
        descriptor = new EditWeddingDescriptor();
        descriptor.setWeddingName(wedding.getWeddingName());
        descriptor.setWeddingDate(wedding.getWeddingDate());
        descriptor.setWeddingLocation(wedding.getWeddingLocation());
    }

    /**
     * Sets the {@code WeddingName} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withWeddingName(String name) {
        descriptor.setWeddingName(new WeddingName(name));
        return this;
    }

    /**
     * Sets the {@code WeddingDate} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withWeddingDate(String date) {
        descriptor.setWeddingDate(new WeddingDate(date));
        return this;
    }

    /**
     * Sets the {@code WeddingLocation} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withWeddingLocation(String location) {
        descriptor.setWeddingLocation(new WeddingLocation(location));
        return this;
    }

    public EditWeddingDescriptor build() {
        return descriptor;
    }
}
