package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String ROLE} into an {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param tag the input wedding id string.
     * @param validWeddingIds a set of wedding ids that exist in the system.
     * @throws ParseException if the given {@code tag} is invalid or the wedding id does not exist.
     */
    public static Tag parseTag(String tag, Set<WeddingId> validWeddingIds) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        WeddingId weddingId;
        try {
            weddingId = parseWeddingId(trimmedTag);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), e);
        }
        if (!validWeddingIds.contains(weddingId)) {
            throw new ParseException("The wedding id " + trimmedTag + " does not exist in the system.");
        }
        return new Tag(weddingId);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     *
     * @param tags the collection of wedding id strings.
     * @param validWeddingIds a set of wedding ids that exist in the system.
     * @throws ParseException if any of the given tags is invalid or does not exist.
     */
    public static Set<Tag> parseTags(Collection<String> tags, Set<WeddingId> validWeddingIds) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagString : tags) {
            tagSet.add(parseTag(tagString, validWeddingIds));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String weddingId} into a {@code WeddingId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weddingId} is invalid.
     */
    public static WeddingId parseWeddingId(String weddingId) throws ParseException {
        requireNonNull(weddingId);
        String trimmedId = weddingId.trim();

        // Check for negative wedding IDs first
        if (trimmedId.matches("^W-\\d+$") || trimmedId.equals("W0")) {
            throw new ParseException(WeddingId.MESSAGE_NEGATIVE_CONSTRAINTS);
        }
        if (!WeddingId.isValidWeddingId(trimmedId)) {
            throw new ParseException(WeddingId.MESSAGE_CONSTRAINTS);
        }
        return new WeddingId(trimmedId);
    }

    /**
     * Parses a {@code String weddingName} into a {@code WeddingName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weddingName} is invalid.
     */
    public static WeddingName parseWeddingName(String weddingName) throws ParseException {
        requireNonNull(weddingName);
        String trimmedName = weddingName.trim();
        if (!WeddingName.isValidName(trimmedName)) {
            throw new ParseException(WeddingName.MESSAGE_CONSTRAINTS);
        }
        return new WeddingName(trimmedName);
    }

    /**
     * Parses a {@code String weddingDate} into a {@code WeddingDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weddingDate} is invalid.
     */
    public static WeddingDate parseWeddingDate(String weddingDate) throws ParseException {
        requireNonNull(weddingDate);
        String trimmedDate = weddingDate.trim();
        if (!WeddingDate.isValidDate(trimmedDate)) {
            throw new ParseException(WeddingDate.MESSAGE_CONSTRAINTS);
        }
        return new WeddingDate(trimmedDate);
    }

    /**
     * Parses a {@code String weddingLocation} into a {@code WeddingLocation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weddingLocation} is invalid.
     */
    public static WeddingLocation parseWeddingLocation(String weddingLocation) throws ParseException {
        requireNonNull(weddingLocation);
        String trimmedLocation = weddingLocation.trim();
        if (!WeddingLocation.isValidLocation(trimmedLocation)) {
            throw new ParseException(WeddingLocation.MESSAGE_CONSTRAINTS);
        }
        return new WeddingLocation(trimmedLocation);
    }
}
