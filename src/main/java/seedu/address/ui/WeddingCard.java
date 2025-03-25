package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

/**
 * An UI component that displays information of a {@code Wedding}.
 */
public class WeddingCard extends UiPart<Region> {

    private static final String FXML = "WeddingListCard.fxml";

    public final Wedding wedding;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label weddingName;
    @FXML
    private Label weddingId;
    @FXML
    private Label weddingDate;
    @FXML
    private Label weddingLocation;
    @FXML
    private Label weddingTask;

    /**
     * Creates a {@code WeddingCard} with the given {@code Wedding} and index to display.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        id.setText(displayedIndex + ". ");
        weddingName.setText(wedding.getWeddingName().toString());
        weddingId.setText("ID: " + wedding.getWeddingId().toString()); // Make it clearer
        weddingDate.setText(wedding.getWeddingDate().toString());
        weddingLocation.setText(wedding.getWeddingLocation().toString());
        weddingTask.setText(wedding.getTasks().toString());
    }
}
