package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfirmationManagerTest {

    private ConfirmationManager manager;

    @BeforeEach
    public void setUp() {
        manager = ConfirmationManager.getInstance();
        manager.clearPendingCommand(); // ensure a clean slate
    }

    /**
     * Tests that setting a pending command and then retrieving it returns the same command instance.
     */
    @Test
    public void setAndGetPendingCommand_success() {
        Command dummyCommand = new Command() {
            @Override
            public CommandResult execute(seedu.address.model.Model model) {
                return new CommandResult("dummy");
            }
        };

        manager.setPendingCommand(dummyCommand);
        Command retrieved = manager.getPendingCommand();
        assertSame(dummyCommand, retrieved);
    }

    /**
     * Tests that clearing the pending command results in the pending command being null.
     */
    @Test
    public void clearPendingCommand_success() {
        Command dummyCommand = new Command() {
            @Override
            public CommandResult execute(seedu.address.model.Model model) {
                return new CommandResult("dummy");
            }
        };
        manager.setPendingCommand(dummyCommand);

        manager.clearPendingCommand();
        Command retrieved = manager.getPendingCommand();
        assertNull(retrieved);
    }
}
