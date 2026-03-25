package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonNullArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertTrue(samplePersons.length > 0);
    }

    @Test
    public void getSamplePersons_containsExpectedNumberOfPersons() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(6, samplePersons.length);
    }

    @Test
    public void getSamplePersons_allPersonsHaveValidFields() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        for (Person person : samplePersons) {
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getNotes());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSamplePersons_firstPersonHasCorrectDetails() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        Person firstPerson = samplePersons[0];

        assertEquals("Alex Yeoh", firstPerson.getName().fullName);
        assertEquals("87438807", firstPerson.getPhone().value);
        assertEquals("alexyeoh@example.com", firstPerson.getEmail().value);
        assertEquals("Blk 30 Geylang Street 29, #06-40", firstPerson.getAddress().value);
        assertEquals("Prefers weekend afternoon visits", firstPerson.getNotes().value);
        assertTrue(firstPerson.getTags().contains(new Tag("AC-Service")));
    }

    @Test
    public void getSamplePersons_personWithNotesHasCorrectNotes() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        // Alex Yeoh has notes
        assertEquals("Prefers weekend afternoon visits", samplePersons[0].getNotes().value);

        // Charlotte Oliveiro has notes
        assertEquals("Has dogs at home", samplePersons[2].getNotes().value);

        // Bernice Yu has default notes
        assertEquals(Notes.DEFAULT_NOTE, samplePersons[1].getNotes().value);

        // David Li has notes
        assertEquals("Prefers weekday morning visits", samplePersons[3].getNotes().value);

        // Irfan Ibrahim has default notes
        assertEquals(Notes.DEFAULT_NOTE, samplePersons[4].getNotes().value);

        // Roy Balakrishnan has notes
        assertEquals("Call 5 minutes before arriving", samplePersons[5].getNotes().value);
    }

    @Test
    public void getSampleAddressBook_returnsNonNull() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAb);
    }

    @Test
    public void getSampleAddressBook_containsExpectedNumberOfPersons() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertEquals(6, sampleAb.getPersonList().size());
    }

    @Test
    public void getTagSet_emptyArray_returnsEmptySet() {
        Set<Tag> tags = SampleDataUtil.getTagSet();
        assertTrue(tags.isEmpty());
    }

    @Test
    public void getTagSet_singleTag_returnsSetWithOneTag() {
        Set<Tag> tags = SampleDataUtil.getTagSet("AC-Service");
        assertEquals(1, tags.size());
        assertTrue(tags.contains(new Tag("AC-Service")));
    }

    @Test
    public void getTagSet_multipleTags_returnsSetWithAllTags() {
        Set<Tag> tags = SampleDataUtil.getTagSet("Plumbing", "Electrical Wiring", "AC-Service");
        assertEquals(3, tags.size());
        assertTrue(tags.contains(new Tag("Plumbing")));
        assertTrue(tags.contains(new Tag("Electrical Wiring")));
        assertTrue(tags.contains(new Tag("AC-Service")));
    }

    @Test
    public void getTagSet_duplicateTags_returnsSetWithoutDuplicates() {
        Set<Tag> tags = SampleDataUtil.getTagSet("Plumbing", "Plumbing", "Electrical Wiring");
        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("Plumbing")));
        assertTrue(tags.contains(new Tag("Electrical Wiring")));
    }

    @Test
    public void defaultNotes_isEmptyNotes() {
        assertEquals(Notes.DEFAULT_NOTE, SampleDataUtil.DEFAULT_NOTES.value);
    }
}
