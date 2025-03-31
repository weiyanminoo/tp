package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    // List of available commands for autocompletion with proper case
    private static final List<String> COMMAND_LIST = Arrays.asList(
            "add", "clear", "delete", "edit", "exit", "find", "help", "list",
            "tag", "untag", "filter", "addWedding", "deleteWedding", "editWedding",
            "listWID", "listWDate", "listTask", "mark", "unmark",
            "deleteTask", "addTask", "clear");

    private final CommandExecutor commandExecutor;

    // For tracking tab completions
    private String originalUserInput = "";
    private List<String> matchingCommands = List.of();
    private int tabIndex = 0;
    private boolean hasCompletedToCommonPrefix = false;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the
        // command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            // Reset tab completion state when the user types something new that's not from
            // the autocomplete
            if (!commandTextField.getText().equals(originalUserInput)
                    && !isCurrentTextFromCycling()) {
                resetTabCompletionState();
            }
        });

        // Add key pressed event handler for tab autocompletion
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }

    /**
     * Checks if the current text is from cycling through autocompletions.
     */
    private boolean isCurrentTextFromCycling() {
        return matchingCommands.stream()
                .map(cmd -> cmd + " ")
                .anyMatch(cmd -> cmd.equals(commandTextField.getText()));
    }

    /**
     * Resets the tab completion tracking state.
     */
    private void resetTabCompletionState() {
        matchingCommands = List.of();
        tabIndex = 0;
        hasCompletedToCommonPrefix = false;
    }

    /**
     * Handles key press events for custom functionality like tab completion.
     *
     * @param event The key event to handle
     */
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            event.consume(); // Prevent default tab behavior
            autocompleteCommand();
        }
    }

    /**
     * Finds the longest common prefix among a list of strings.
     *
     * @param strings List of strings to find common prefix for
     * @return The longest common prefix
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
     * Autocompletes the current command based on what the user has typed so far.
     * First completes to common prefix, then cycles through specific commands on
     * subsequent tabs.
     */
    private void autocompleteCommand() {
        String currentText = commandTextField.getText().trim();

        // If text is empty, don't autocomplete
        if (currentText.isEmpty()) {
            return;
        }

        // If this is a new prefix or we don't have matches yet, find all matching
        // commands
        if (matchingCommands.isEmpty()) {
            originalUserInput = currentText;
            matchingCommands = COMMAND_LIST.stream()
                    .filter(cmd -> cmd.startsWith(currentText))
                    .collect(Collectors.toList());
            tabIndex = 0;
            hasCompletedToCommonPrefix = false;
        }

        if (matchingCommands.isEmpty()) {
            return; // No matches found
        }

        // Check if current text exactly matches a command
        if (matchingCommands.contains(currentText)) {
            // Find the next command in the cycle (skip the exact match)
            tabIndex = (matchingCommands.indexOf(currentText) + 1) % matchingCommands.size();
            String completedCommand = matchingCommands.get(tabIndex) + " ";
            commandTextField.setText(completedCommand);
            commandTextField.positionCaret(completedCommand.length());
            return;
        }

        // If multiple matches and haven't completed to common prefix yet
        if (matchingCommands.size() > 1 && !hasCompletedToCommonPrefix) {
            String commonPrefix = findLongestCommonPrefix(matchingCommands);

            // Only use common prefix if it's longer than what the user typed
            if (commonPrefix.length() > originalUserInput.length()) {
                commandTextField.setText(commonPrefix + " ");
                commandTextField.positionCaret(commonPrefix.length() + 1);
                hasCompletedToCommonPrefix = true;
                return;
            } else {
                // If common prefix isn't helpful, just go straight to cycling
                hasCompletedToCommonPrefix = true;
            }
        }

        // Cycle through commands
        String completedCommand = matchingCommands.get(tabIndex) + " ";
        commandTextField.setText(completedCommand);
        commandTextField.positionCaret(completedCommand.length());

        // Update index for next tab press
        tabIndex = (tabIndex + 1) % matchingCommands.size();
    }

    /**
     * Handles the Enter button pressed event.
     * <p>
     * The command text is executed and the resulting CommandResult is checked.
     * If the command does not require confirmation, the input field is cleared.
     * Otherwise, the user's input is preserved so that they can edit or confirm.
     * </p>
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            CommandResult commandResult = commandExecutor.execute(commandText);
            // Only clear the command box if the command does NOT require confirmation.
            if (!commandResult.isRequiresConfirmation()) {
                commandTextField.setText("");
            }
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }

        // Reset tab completion state after command execution
        resetTabCompletionState();
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
