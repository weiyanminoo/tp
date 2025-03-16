package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null email
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // blank email
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only

        // invalid parts
        assertFalse(Role.isValidRole("florist!"));
        assertFalse(Role.isValidRole("florist?"));
        assertFalse(Role.isValidRole("florist@"));
    }

    @Test
    public void equals() {
        Role role = new Role("Photographer");

        // same values -> returns true
        assertTrue(role.equals(new Role("Photographer")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(50f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Photograph3r")));
    }
}
