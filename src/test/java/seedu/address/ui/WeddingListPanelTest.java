package seedu.address.ui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

public class WeddingListPanelTest {

    @BeforeAll
    public static void initFxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }

    @Test
    public void constructor_withNonNullWeddingList_succeeds() {
        javafx.collections.ObservableList<Wedding> weddingList =
                javafx.collections.FXCollections.observableArrayList();

        weddingList.add(new Wedding(
                new WeddingId("W1"),
                new WeddingName("Sample Wedding"),
                new WeddingDate("01-Jan-2030"),
                new WeddingLocation("Sample Location")
        ));

        WeddingListPanel panel = new WeddingListPanel(weddingList);
        org.junit.jupiter.api.Assertions.assertNotNull(panel.getRoot());
    }
}

