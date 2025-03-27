package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Wedding's date
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class WeddingDate {

    public static final String MESSAGE_CONSTRAINTS =
            """
            Wedding date should be in a valid date format:
            E.g. "dd-MMM-yyyy", "dd/MM/yyyy", "dd.MM.yyyy", "dd MMM yyyy"
            """;

    public static final String MESSAGE_DATE_OUT_OF_RANGE = "Wedding date should be a valid date";
    public static final String MESSAGE_DATE_IN_PAST = "Wedding date should not be in the past";

    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
            // === DMY (Day-Month-Year) ===
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd MM yyyy"),
            DateTimeFormatter.ofPattern("ddMMyyyy"),

            DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
            DateTimeFormatter.ofPattern("dd.MMM.yyyy"),
            DateTimeFormatter.ofPattern("dd/MMM/yyyy"),
            DateTimeFormatter.ofPattern("dd MMM yyyy"),
            DateTimeFormatter.ofPattern("ddMMMyyyy"),
            DateTimeFormatter.ofPattern("ddMMMMyyyy"),
            DateTimeFormatter.ofPattern("dd-MMMM-yyyy"),
            DateTimeFormatter.ofPattern("dd MMMM yyyy"),
            DateTimeFormatter.ofPattern("ddMMMM-yyyy"),
            DateTimeFormatter.ofPattern("dd MMMMyyyy"),
            DateTimeFormatter.ofPattern("dd/MMMM/yyyy"),
            DateTimeFormatter.ofPattern("dd.MMMM.yyyy"),
            DateTimeFormatter.ofPattern("dd MMMM yyyy"),
            DateTimeFormatter.ofPattern("d MMM yyyy"),
            DateTimeFormatter.ofPattern("d MMMM yyyy"),
            DateTimeFormatter.ofPattern("dMMMyyyy"),
            DateTimeFormatter.ofPattern("d MMMMyyyy"),
            DateTimeFormatter.ofPattern("ddMMMyyyy"),
            DateTimeFormatter.ofPattern("ddMMMMyyyy"),

            // === MDY (Month-Day-Year) ===
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM.dd.yyyy"),
            DateTimeFormatter.ofPattern("MM dd yyyy"),
            DateTimeFormatter.ofPattern("MMddyyyy"),

            DateTimeFormatter.ofPattern("MMM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MMM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MMM dd yyyy"),
            DateTimeFormatter.ofPattern("MMMdyyyy"),

            DateTimeFormatter.ofPattern("MMMM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MMMM dd yyyy"),
            DateTimeFormatter.ofPattern("MMMMyyyydd"),

            DateTimeFormatter.ofPattern("MMM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMMM dd, yyyy"),
            DateTimeFormatter.ofPattern("MMM d, yyyy"),
            DateTimeFormatter.ofPattern("MMMM d, yyyy"),

            // === YMD (Year-Month-Day) ===
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("yyyy MM dd"),
            DateTimeFormatter.ofPattern("yyyyMMdd"),

            DateTimeFormatter.ofPattern("yyyy-MMM-dd"),
            DateTimeFormatter.ofPattern("yyyy.MMM.dd"),
            DateTimeFormatter.ofPattern("yyyy/MMM/dd"),
            DateTimeFormatter.ofPattern("yyyy MMM dd"),
            DateTimeFormatter.ofPattern("yyyyMMMdd"),

            DateTimeFormatter.ofPattern("yyyy-MMMM-dd"),
            DateTimeFormatter.ofPattern("yyyy MMMM dd"),
            DateTimeFormatter.ofPattern("yyyy/MMMM/dd"),
            DateTimeFormatter.ofPattern("yyyyMMMdd"),
            DateTimeFormatter.ofPattern("yyyy MMMM dd"),
            DateTimeFormatter.ofPattern("yyyy MMMM d"),
            DateTimeFormatter.ofPattern("yyyyMMMMdd"),
            DateTimeFormatter.ofPattern("yyyy.MMMM.dd"),
            DateTimeFormatter.ofPattern("yyyyMMMMd"),

            // === YDM (Year-Day-Month) ===
            DateTimeFormatter.ofPattern("yyyy-dd-MM"),
            DateTimeFormatter.ofPattern("yyyy/dd/MM"),
            DateTimeFormatter.ofPattern("yyyy.dd.MM"),
            DateTimeFormatter.ofPattern("yyyy dd MM"),
            DateTimeFormatter.ofPattern("yyyyddMM"),

            DateTimeFormatter.ofPattern("yyyy-dd-MMM"),
            DateTimeFormatter.ofPattern("yyyy/dd/MMM"),
            DateTimeFormatter.ofPattern("yyyy dd MMM"),
            DateTimeFormatter.ofPattern("yyyyddMMM"),

            DateTimeFormatter.ofPattern("yyyy-dd-MMMM"),
            DateTimeFormatter.ofPattern("yyyy dd MMMM"),
            DateTimeFormatter.ofPattern("yyyyddMMMM"),
            DateTimeFormatter.ofPattern("yyyy d MMM"),
            DateTimeFormatter.ofPattern("yyyy d MMMM"),

            // === DYM (Day-Year-Month) ===
            DateTimeFormatter.ofPattern("dd-yyyy-MM"),
            DateTimeFormatter.ofPattern("dd/yyyy/MM"),
            DateTimeFormatter.ofPattern("dd.yyyy.MM"),
            DateTimeFormatter.ofPattern("dd yyyy MM"),
            DateTimeFormatter.ofPattern("ddyyyyMM"),

            DateTimeFormatter.ofPattern("dd-yyyy-MMM"),
            DateTimeFormatter.ofPattern("dd/yyyy/MMM"),
            DateTimeFormatter.ofPattern("dd yyyy MMM"),
            DateTimeFormatter.ofPattern("ddyyyyMMM"),

            DateTimeFormatter.ofPattern("dd-yyyy-MMMM"),
            DateTimeFormatter.ofPattern("dd yyyy MMMM"),
            DateTimeFormatter.ofPattern("ddyyyyMMMM"),
            DateTimeFormatter.ofPattern("d yyyy MMM"),
            DateTimeFormatter.ofPattern("d yyyy MMMM"),

            // === MYD (Month-Year-Day) ===
            DateTimeFormatter.ofPattern("MM-yyyy-dd"),
            DateTimeFormatter.ofPattern("MM/yyyy/dd"),
            DateTimeFormatter.ofPattern("MM.yyyy.dd"),
            DateTimeFormatter.ofPattern("MM yyyy dd"),
            DateTimeFormatter.ofPattern("MMyyyydd"),

            DateTimeFormatter.ofPattern("MMM-yyyy-dd"),
            DateTimeFormatter.ofPattern("MMM yyyy dd"),
            DateTimeFormatter.ofPattern("MMMyyyydd"),

            DateTimeFormatter.ofPattern("MMMM-yyyy-dd"),
            DateTimeFormatter.ofPattern("MMMM yyyy dd"),
            DateTimeFormatter.ofPattern("MMMMyyyydd"),

            // === Partial Formats: Single Day / Month (No Padding) ===
            // Day-Month-Year
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("d.M.yyyy"),
            DateTimeFormatter.ofPattern("d M yyyy"),
            DateTimeFormatter.ofPattern("dMyyyy"),

            // Month-Day-Year
            DateTimeFormatter.ofPattern("M-d-yyyy"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("M.d.yyyy"),
            DateTimeFormatter.ofPattern("M d yyyy"),
            DateTimeFormatter.ofPattern("Mdyyyy"),

            // Year-Month-Day
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("yyyy.M.d"),
            DateTimeFormatter.ofPattern("yyyy M d"),
            DateTimeFormatter.ofPattern("yyyyMd"),

            // Year-Day-Month
            DateTimeFormatter.ofPattern("yyyy-d-M"),
            DateTimeFormatter.ofPattern("yyyy/d/M"),
            DateTimeFormatter.ofPattern("yyyy.d.M"),
            DateTimeFormatter.ofPattern("yyyy d M"),
            DateTimeFormatter.ofPattern("yyyydM"),

            // Month-Year-Day
            DateTimeFormatter.ofPattern("M-yyyy-d"),
            DateTimeFormatter.ofPattern("M/yyyy/d"),
            DateTimeFormatter.ofPattern("M.yyyy.d"),
            DateTimeFormatter.ofPattern("M yyyy d"),
            DateTimeFormatter.ofPattern("Myyyyd"),

            // Day-Year-Month
            DateTimeFormatter.ofPattern("d-yyyy-M"),
            DateTimeFormatter.ofPattern("d/yyyy/M"),
            DateTimeFormatter.ofPattern("d.yyyy.M"),
            DateTimeFormatter.ofPattern("d yyyy M"),
            DateTimeFormatter.ofPattern("dyyyyM")
    );

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public final String value;

    /**
     * Constructs a {@code WeddingDate}.
     *
     * @param date A valid date string.
     */
    public WeddingDate(String date) throws ParseException {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.value = formatDate(date);
    }

    /**
     * Returns true if a given string is a valid wedding date in one of the accepted formats.
     */
    public static boolean isValidDate(String test) throws ParseException {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate parsedDate = LocalDate.parse(test, formatter);
                if (parsedDate.isBefore(LocalDate.now())) {
                    throw new ParseException(MESSAGE_DATE_IN_PAST);
                }
                return true;
            } catch (DateTimeParseException e) {
                if (e.getMessage().contains("Invalid value")) {
                    throw new ParseException(MESSAGE_DATE_OUT_OF_RANGE);
                }
                // Continue to the next format to check for validity
            }
        }
        return false;
    }

    /**
     * Formats the given date string to the standard output format.
     */
    private static String formatDate(String date) throws ParseException {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate parsedDate = LocalDate.parse(date, formatter);
                return parsedDate.format(OUTPUT_FORMATTER);
            } catch (DateTimeParseException e) {
                // Continue to the next format
            }
        }
        throw new ParseException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingDate
                && value.equals(((WeddingDate) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
