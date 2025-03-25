package seedu.address.ui;


import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

public class WeddingCard extends UiPart<Region> {

    private static final String FXML = "WeddingListCard.fxml";

    public final Wedding wedding;

    @FXML
    private HBox cardPane;
    @FXML
    private Label weddingName;
    @FXML
    private Label weddingId;
    @FXML
    private Label weddingDate;
    @FXML
    private Label weddingVenue;

    /**
     * Creates a {@code WeddingCard} with the given {@code Wedding} and index to display.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        weddingId.setText(displayedIndex + ". ");
        weddingName.setText(wedding.getWeddingName().toString());
        weddingDate.setText(wedding.getWeddingDate().toString());
        weddingVenue.setText(wedding.getWeddingLocation().toString());
    }


}
