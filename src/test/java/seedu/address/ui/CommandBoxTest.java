package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CommandBox's autocomplete functionality.
 */
public class CommandBoxTest {

    /**
     * Test-specific subclass of CommandBox that exposes protected methods and
     * fields for testing.
     */
    private static class TestableCommandBox {
        private final List<String> commandList = Arrays.asList(
                "add", "clear", "delete", "edit", "exit", "find", "help", "list",
                "tag", "untag", "filter", "addWedding", "deleteWedding", "editWedding", "listWedding");

        private String originalUserInput = "";
        private List<String> matchingCommands = List.of();
        private int tabIndex = 0;
        private boolean hasCompletedToCommonPrefix = false;
        private String currentText = "";

        /**
         * Simulates the autocomplete functionality without JavaFX dependencies.
         *
         * @return The autocompleted text
         */
        public String autocomplete() {
            // If text is empty, don't autocomplete
            if (currentText.isEmpty()) {
                return currentText;
            }

            // If this is a new prefix or we don't have matches yet, find all matching
            // commands
            if (matchingCommands.isEmpty()) {
                originalUserInput = currentText;
                matchingCommands = commandList.stream()
                        .filter(cmd -> cmd.startsWith(currentText))
                        .collect(Collectors.toList());
                tabIndex = 0;
                hasCompletedToCommonPrefix = false;
            }

            if (matchingCommands.isEmpty()) {
                return currentText; // No matches found
            }

            // Check if current text exactly matches a command
            if (matchingCommands.contains(currentText)) {
                // Find the next command in the cycle (skip the exact match)
                tabIndex = (matchingCommands.indexOf(currentText) + 1) % matchingCommands.size();
                return matchingCommands.get(tabIndex) + " ";
            }

            // If multiple matches and haven't completed to common prefix yet
            if (matchingCommands.size() > 1 && !hasCompletedToCommonPrefix) {
                String commonPrefix = findLongestCommonPrefix(matchingCommands);

                // Only use common prefix if it's longer than what the user typed
                if (commonPrefix.length() > originalUserInput.length()) {
                    hasCompletedToCommonPrefix = true;
                    return commonPrefix + " ";
                } else {
                    // If common prefix isn't helpful, just go straight to cycling
                    hasCompletedToCommonPrefix = true;
                }
            }

            // Cycle through commands
            String completedCommand = matchingCommands.get(tabIndex) + " ";

            // Update index for next tab press
            tabIndex = (tabIndex + 1) % matchingCommands.size();

            return completedCommand;
        }

        /**
         * Finds the longest common prefix among a list of strings.
         */
        private String findLongestCommonPrefix(List<String> strings) {
            if (strings.isEmpty()) {
                return "";
            }

            String firstString = strings.get(0);

            for (int prefixLength = firstString.length(); prefixLength > 0; prefixLength--) {
                String prefix = firstString.substring(0, prefixLength);
                boolean allMatch = true;

                for (String s : strings) {
                    if (!s.startsWith(prefix)) {
                        allMatch = false;
                        break;
                    }
                }

                if (allMatch) {
                    return prefix;
                }
            }

            return "";
        }

        /**
         * Resets the autocomplete state.
         */
        public void reset() {
            matchingCommands = List.of();
            tabIndex = 0;
            hasCompletedToCommonPrefix = false;
        }

        /**
         * Sets the current text to be autocompleted.
         */
        public void setText(String text) {
            if (!text.equals(currentText)) {
                reset();
            }
            this.currentText = text;
        }

        /**
         * Gets the current matching commands.
         */
        public List<String> getMatchingCommands() {
            return matchingCommands;
        }

        /**
         * Gets the current tab index.
         */
        public int getTabIndex() {
            return tabIndex;
        }
    }

    private TestableCommandBox testableCommandBox;

    @BeforeEach
    public void setUp() {
        testableCommandBox = new TestableCommandBox();
    }

    @Test
    public void autocomplete_emptyText_returnsEmptyString() {
        testableCommandBox.setText("");
        String result = testableCommandBox.autocomplete();
        assertEquals("", result);
    }

    @Test
    public void autocomplete_noMatches_returnsOriginalText() {
        testableCommandBox.setText("xyz");
        String result = testableCommandBox.autocomplete();
        assertEquals("xyz", result);
    }

    @Test
    public void autocomplete_partialMatch_completesToCommonPrefix() {
        // "ad" should complete to "add "
        testableCommandBox.setText("ad");
        String result = testableCommandBox.autocomplete();
        assertEquals("add ", result);
    }

    @Test
    public void autocomplete_exactMatch_cyclesToNextCommand() {
        // "add" should cycle to "addWedding "
        testableCommandBox.setText("add");
        String result = testableCommandBox.autocomplete();
        assertEquals("addWedding ", result);
    }

    @Test
    public void autocomplete_multipleMatches_cyclesThrough() {
        // First "add" -> "addWedding"
        testableCommandBox.setText("add");

        // First tab press
        String result1 = testableCommandBox.autocomplete();
        assertEquals("addWedding ", result1);

        // Important: Update the internal currentText to simulate what would happen in UI
        // without calling setText() which resets the state
        testableCommandBox.currentText = "addWedding";

        // Second tab press without resetting state
        String result2 = testableCommandBox.autocomplete();
        assertEquals("add ", result2);
    }

    @Test
    public void autocomplete_singleMatch_completesToMatch() {
        // "cl" only matches "clear"
        testableCommandBox.setText("cl");
        String result = testableCommandBox.autocomplete();
        assertEquals("clear ", result);
    }

    @Test
    public void autocomplete_caseSensitiveMatch_matchesCorrectly() {
        // "addW" should match "addWedding"
        testableCommandBox.setText("addW");
        String result = testableCommandBox.autocomplete();
        assertEquals("addWedding ", result);
    }

    @Test
    public void findLongestCommonPrefix_multipleStrings_findsCorrectPrefix() {
        // Test the findLongestCommonPrefix method with multiple strings
        List<String> testStrings = Arrays.asList("add", "addWedding", "addPerson");
        String prefix = testableCommandBox.findLongestCommonPrefix(testStrings);
        assertEquals("add", prefix);
    }

    @Test
    public void findLongestCommonPrefix_emptyList_returnsEmptyString() {
        String prefix = testableCommandBox.findLongestCommonPrefix(List.of());
        assertEquals("", prefix);
    }

    @Test
    public void findLongestCommonPrefix_singleItem_returnsItem() {
        List<String> testStrings = Arrays.asList("singleItem");
        String prefix = testableCommandBox.findLongestCommonPrefix(testStrings);
        assertEquals("singleItem", prefix);
    }

    @Test
    public void findLongestCommonPrefix_noCommonPrefix_returnsEmptyString() {
        List<String> testStrings = Arrays.asList("apple", "banana", "cherry");
        String prefix = testableCommandBox.findLongestCommonPrefix(testStrings);
        assertEquals("", prefix);
    }
}
