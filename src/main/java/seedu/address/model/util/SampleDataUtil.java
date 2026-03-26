package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.log.LogEntry;
import seedu.address.model.person.log.LogHistory;
import seedu.address.model.person.log.LogMessage;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Notes DEFAULT_NOTES = new Notes(Notes.DEFAULT_NOTE);

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Notes("Prefers weekend afternoon visits"),
                getLogHistory(
                    getLogEntry(2026, 3, 11, 15, 45,
                            "Completed routine AC servicing. Recommended filter replacement next quarter."),
                    getLogEntry(2026, 3, 4, 10, 30,
                            "Customer requested weekend slot for next follow-up visit.")),
                getTagSet("AC-Service")),
            new Person(new Name("Bernice Yu"), new Phone("9927 2758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), DEFAULT_NOTES,
                getLogHistory(
                    getLogEntry(2026, 2, 27, 9, 15,
                            "Fixed kitchen sink leak and tested water pressure after repair.")),
                getTagSet("Plumbing", "Electrical Wiring")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("9321-0283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Notes("Has dogs at home"),
                getTagSet("Plumbing")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Notes("Prefers weekday morning visits"), getTagSet("AC-Service")),
            new Person(new Name("Irfan Ibrahim"), new Phone("9249 2021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), DEFAULT_NOTES,
                getTagSet("Electrical Wiring")),
            new Person(new Name("Roy Balakrishnan"), new Phone("9262-4417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Notes("Call 5 minutes before arriving"),
                getLogHistory(
                    getLogEntry(2026, 3, 20, 18, 5,
                            "Arrived after confirmation call and repaired compressor wiring issue."),
                    getLogEntry(2026, 1, 16, 11, 20,
                            "Performed quarterly maintenance and cleaned condenser coils.")),
                getTagSet("AC-Service"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    private static LogHistory getLogHistory(LogEntry... entries) {
        return new LogHistory(Arrays.asList(entries));
    }

    private static LogEntry getLogEntry(int year, int month, int day, int hour, int minute, String message) {
        return new LogEntry(LocalDateTime.of(year, month, day, hour, minute), new LogMessage(message));
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
