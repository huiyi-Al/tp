package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Tests the SearchPredicate for expected positive and negative behavior
 * in most efficient and effective manner.
 *
 * <ul>
 *   <li>Equality between same objects, returns true.</li>
 *   <li>Equality between objects of same values, returns true.</li>
 *   <li>Equality between objects of different values, returns false.</li>
 *   <li>Equality between object and a different type, returns false.</li>
 *   <li>Equality between object and null, returns false.</li>
 *   <li>Testing any person against an empty predicate, returns false.</li>
 *   <li>Testing a person where any field matches at least one query token, returns true.</li>
 *   <li>Testing a person whose name matches (same case), returns true.</li>
 *   <li>Testing a person whose name matches (different case), returns true.</li>
 *   <li>Testing a person whose name does not match, returns false.</li>
 *   <li>Testing a person whose email matches (same case), returns true.</li>
 *   <li>Testing a person whose email matches (different case), returns true.</li>
 *   <li>Testing a person whose email does not match, returns false.</li>
 *   <li>Testing a person whose phone matches, returns true.</li>
 *   <li>Testing a person whose phone does not match, returns false.</li>
 *   <li>Testing a person whose physical address matches (same case), returns true.</li>
 *   <li>Testing a person whose physical address matches (different case), returns true.</li>
 *   <li>Testing a person where all fields match, returns true.</li>
 *   <li>Testing a person where no fields match, returns false.</li>
 *   <li>String representation with an empty argument map, matches expected format.</li>
 *   <li>String representation with all fields present, matches expected format.</li>
 * </ul>
 */
public class SearchPredicateTest {
    private static final String EXPECTED_TO_STRING_FORMAT = """
            {0}'{subNames='{1}'}', \
            {2}'{subNumbers='{3}'}', \
            {4}'{subEmailAddresses='{5}'}', \
            {6}'{subPhysicalAddresses='{7}'}'\
            """;
    private static final Person TEST_PERSON_NAME_MATCH_ONLY =
            new PersonBuilder().withName("Alan").withPhone("92355671").build();
    private static final Person TEST_PERSON_PHONE_MATCH_ONLY =
            new PersonBuilder().withName("Cassie").withPhone("91237452").build();
    private static final Person TEST_PERSON_EMAIL_MATCH_ONLY =
            new PersonBuilder().withName("Elaine").withPhone("82473774").withEmail("elaine03@nus.org").build();
    private static final Person TEST_PERSON_ADDRESS_MATCH_ONLY =
            new PersonBuilder().withName("Fred").withPhone("84567813").withAddress("Bishan").build();
    private static final Person TEST_PERSON_ALL_FIELDS_MATCH =
            new PersonBuilder().withName("Bertha").withPhone("84567342").withEmail("b@nus.edu")
                    .withAddress("Woodgrove").build();
    private static final Person TEST_PERSON_NO_FIELDS_MATCH =
            new PersonBuilder().withName("David").withPhone("98765432").build();

    private static final String TEST_NAME_QUERY = "al er";
    private static final String TEST_PHONE_QUERY = "123 456";
    private static final String TEST_EMAIL_QUERY = "3@nus.org edu";
    private static final String TEST_ADDRESS_QUERY = "bis ood";
    private static final String TEST_NAME_QUERY_ALTERNATIVE = "qw er";

    private static final ArgumentMultimap TEST_ARGMAP_EMPTY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_EMPTY;

    private static final ArgumentMultimap TEST_ARGMAP_NAME_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_NAME_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_NAME_ONLY_UPPERCASE;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_NAME_ONLY_UPPERCASE;

    private static final ArgumentMultimap TEST_ARGMAP_NAME_ONLY_ALTERNATIVE;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_NAME_ONLY_ALTERNATIVE;

    private static final ArgumentMultimap TEST_ARGMAP_PHONE_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_PHONE_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_EMAIL_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_EMAIL_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_EMAIL_ONLY_UPPERCASE;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_EMAIL_ONLY_UPPERCASE;

    private static final ArgumentMultimap TEST_ARGMAP_ADDRESS_ONLY;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_ADDRESS_ONLY;

    private static final ArgumentMultimap TEST_ARGMAP_ADDRESS_ONLY_UPPERCASE;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_ADDRESS_ONLY_UPPERCASE;

    private static final ArgumentMultimap TEST_ARGMAP_ALL_PRESENT;
    private static final SearchPredicate TEST_SEARCH_PREDICATE_ALL_PRESENT;

    static {
        TEST_ARGMAP_EMPTY = new ArgumentMultimap();
        TEST_SEARCH_PREDICATE_EMPTY = new SearchPredicate(TEST_ARGMAP_EMPTY);

        TEST_ARGMAP_NAME_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_NAME_ONLY.put(PREFIX_NAME, TEST_NAME_QUERY);
        TEST_SEARCH_PREDICATE_NAME_ONLY = new SearchPredicate(TEST_ARGMAP_NAME_ONLY);

        TEST_ARGMAP_NAME_ONLY_UPPERCASE = new ArgumentMultimap();
        TEST_ARGMAP_NAME_ONLY_UPPERCASE.put(PREFIX_NAME, TEST_NAME_QUERY.toUpperCase());
        TEST_SEARCH_PREDICATE_NAME_ONLY_UPPERCASE = new SearchPredicate(TEST_ARGMAP_NAME_ONLY_UPPERCASE);

        TEST_ARGMAP_NAME_ONLY_ALTERNATIVE = new ArgumentMultimap();
        TEST_ARGMAP_NAME_ONLY_ALTERNATIVE.put(PREFIX_NAME, TEST_NAME_QUERY_ALTERNATIVE);
        TEST_SEARCH_PREDICATE_NAME_ONLY_ALTERNATIVE = new SearchPredicate(TEST_ARGMAP_NAME_ONLY_ALTERNATIVE);

        TEST_ARGMAP_EMAIL_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_EMAIL_ONLY.put(PREFIX_EMAIL, TEST_EMAIL_QUERY);
        TEST_SEARCH_PREDICATE_EMAIL_ONLY = new SearchPredicate(TEST_ARGMAP_EMAIL_ONLY);

        TEST_ARGMAP_EMAIL_ONLY_UPPERCASE = new ArgumentMultimap();
        TEST_ARGMAP_EMAIL_ONLY_UPPERCASE.put(PREFIX_EMAIL, TEST_EMAIL_QUERY.toUpperCase());
        TEST_SEARCH_PREDICATE_EMAIL_ONLY_UPPERCASE = new SearchPredicate(TEST_ARGMAP_EMAIL_ONLY_UPPERCASE);

        TEST_ARGMAP_PHONE_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_PHONE_ONLY.put(PREFIX_PHONE, TEST_PHONE_QUERY);
        TEST_SEARCH_PREDICATE_PHONE_ONLY = new SearchPredicate(TEST_ARGMAP_PHONE_ONLY);

        TEST_ARGMAP_ADDRESS_ONLY = new ArgumentMultimap();
        TEST_ARGMAP_ADDRESS_ONLY.put(PREFIX_ADDRESS, TEST_ADDRESS_QUERY);
        TEST_SEARCH_PREDICATE_ADDRESS_ONLY = new SearchPredicate(TEST_ARGMAP_ADDRESS_ONLY);

        TEST_ARGMAP_ADDRESS_ONLY_UPPERCASE = new ArgumentMultimap();
        TEST_ARGMAP_ADDRESS_ONLY_UPPERCASE.put(PREFIX_ADDRESS, TEST_ADDRESS_QUERY.toUpperCase());
        TEST_SEARCH_PREDICATE_ADDRESS_ONLY_UPPERCASE = new SearchPredicate(TEST_ARGMAP_ADDRESS_ONLY_UPPERCASE);

        TEST_ARGMAP_ALL_PRESENT = new ArgumentMultimap();
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_NAME, TEST_NAME_QUERY);
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_PHONE, TEST_PHONE_QUERY);
        TEST_ARGMAP_ALL_PRESENT.put(PREFIX_EMAIL, TEST_EMAIL_QUERY);
        TEST_SEARCH_PREDICATE_ALL_PRESENT = new SearchPredicate(TEST_ARGMAP_ALL_PRESENT);
    }

    public SearchPredicateTest() {
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_EMPTY.equals(TEST_SEARCH_PREDICATE_EMPTY));
        assertTrue(TEST_SEARCH_PREDICATE_NAME_ONLY.equals(TEST_SEARCH_PREDICATE_NAME_ONLY));
        assertTrue(TEST_SEARCH_PREDICATE_PHONE_ONLY.equals(TEST_SEARCH_PREDICATE_PHONE_ONLY));
        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.equals(TEST_SEARCH_PREDICATE_ALL_PRESENT));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ArgumentMultimap argMapAllPresentCopy = new ArgumentMultimap();
        argMapAllPresentCopy.put(PREFIX_NAME, TEST_NAME_QUERY);
        argMapAllPresentCopy.put(PREFIX_PHONE, TEST_PHONE_QUERY);
        argMapAllPresentCopy.put(PREFIX_EMAIL, TEST_EMAIL_QUERY);
        SearchPredicate searchPredicateAllPresentCopy = new SearchPredicate(argMapAllPresentCopy);

        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.equals(searchPredicateAllPresentCopy));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        // Empty vs non-empty
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.equals(TEST_SEARCH_PREDICATE_PHONE_ONLY));
        // Different fields populated
        assertFalse(TEST_SEARCH_PREDICATE_NAME_ONLY.equals(TEST_SEARCH_PREDICATE_PHONE_ONLY));
        // Same field populated with different values
        assertFalse(TEST_SEARCH_PREDICATE_NAME_ONLY.equals(TEST_SEARCH_PREDICATE_NAME_ONLY_ALTERNATIVE));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.equals(123));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.equals(null));
    }

    @Test
    public void test_anyPerson_emptyPredicateReturnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.test(TEST_PERSON_NO_FIELDS_MATCH));
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.test(TEST_PERSON_NAME_MATCH_ONLY));
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.test(TEST_PERSON_PHONE_MATCH_ONLY));
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.test(TEST_PERSON_EMAIL_MATCH_ONLY));
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.test(TEST_PERSON_ADDRESS_MATCH_ONLY));
        assertFalse(TEST_SEARCH_PREDICATE_EMPTY.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_anyFieldCanMatchMultiple_predicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_NAME_ONLY.test(TEST_PERSON_NAME_MATCH_ONLY));
        assertTrue(TEST_SEARCH_PREDICATE_NAME_ONLY.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_onlyNameMatchesSameCase_namePredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_NAME_ONLY.test(TEST_PERSON_NAME_MATCH_ONLY));
    }

    @Test
    public void test_onlyNameMatchesDifferentCase_namePredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_NAME_ONLY_UPPERCASE.test(TEST_PERSON_NAME_MATCH_ONLY));
    }

    @Test
    public void test_onlyNameMatches_allPresentPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.test(TEST_PERSON_NAME_MATCH_ONLY));
    }

    @Test
    public void test_nameDoesNotMatch_namePredicateReturnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_NAME_ONLY.test(TEST_PERSON_PHONE_MATCH_ONLY));
    }

    @Test
    public void test_onlyEmailMatchesSameCase_emailPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_EMAIL_ONLY.test(TEST_PERSON_EMAIL_MATCH_ONLY));
    }

    @Test
    public void test_onlyEmailMatchesDifferentCase_emailPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_EMAIL_ONLY_UPPERCASE.test(TEST_PERSON_EMAIL_MATCH_ONLY));
    }

    @Test
    public void test_onlyEmailMatches_allPresentPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.test(TEST_PERSON_EMAIL_MATCH_ONLY));
    }

    @Test
    public void test_emailDoesNotMatch_emailPredicateReturnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_EMAIL_ONLY.test(TEST_PERSON_PHONE_MATCH_ONLY));
    }

    @Test
    public void test_onlyPhoneMatches_phonePredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_PHONE_ONLY.test(TEST_PERSON_PHONE_MATCH_ONLY));
    }

    @Test
    public void test_onlyPhoneMatches_allPresentPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.test(TEST_PERSON_PHONE_MATCH_ONLY));
    }

    @Test
    public void test_onlyAddressMatchesSameCase_addressPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ADDRESS_ONLY.test(TEST_PERSON_ADDRESS_MATCH_ONLY));
    }

    @Test
    public void test_onlyAddressMatchesDifferentCase_addressPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ADDRESS_ONLY_UPPERCASE.test(TEST_PERSON_ADDRESS_MATCH_ONLY));
    }

    @Test
    public void test_onlyAddressMatches_allPresentPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.test(TEST_PERSON_ADDRESS_MATCH_ONLY));
    }

    @Test
    public void test_addressDoesNotMatch_addressPredicateReturnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_ADDRESS_ONLY.test(TEST_PERSON_PHONE_MATCH_ONLY));
    }

    @Test
    public void test_phoneDoesNotMatch_phonePredicateReturnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_PHONE_ONLY.test(TEST_PERSON_NAME_MATCH_ONLY));
    }

    @Test
    public void test_allFieldsMatch_namePredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_NAME_ONLY.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_allFieldsMatch_phonePredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_PHONE_ONLY.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_allFieldsMatch_emailPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_EMAIL_ONLY.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_allFieldsMatch_addressPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ADDRESS_ONLY.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_allFieldsMatch_allPresentPredicateReturnsTrue() {
        assertTrue(TEST_SEARCH_PREDICATE_ALL_PRESENT.test(TEST_PERSON_ALL_FIELDS_MATCH));
    }

    @Test
    public void test_noFieldMatches_allPresentPredicateReturnsFalse() {
        assertFalse(TEST_SEARCH_PREDICATE_ALL_PRESENT.test(TEST_PERSON_NO_FIELDS_MATCH));
    }

    @Test
    public void toString_emptyArgMap_returnsExpectedString() {
        String expected = MessageFormat.format(
                EXPECTED_TO_STRING_FORMAT,
                FullNamePredicate.class.getCanonicalName(),
                new ArrayList<>(),
                PhoneNumberPredicate.class.getCanonicalName(),
                new ArrayList<>(),
                EmailAddressPredicate.class.getCanonicalName(),
                new ArrayList<>(),
                PhysicalAddressPredicate.class.getCanonicalName(),
                new ArrayList<>()
        );
        assertEquals(expected, TEST_SEARCH_PREDICATE_EMPTY.toString());
    }

    @Test
    public void toString_allPresentArgMap_returnsExpectedString() {
        String expected = MessageFormat.format(
                EXPECTED_TO_STRING_FORMAT,
                FullNamePredicate.class.getCanonicalName(),
                TEST_ARGMAP_ALL_PRESENT.getValueWhitespaceSeparated(PREFIX_NAME),
                PhoneNumberPredicate.class.getCanonicalName(),
                TEST_ARGMAP_ALL_PRESENT.getValueWhitespaceSeparated(PREFIX_PHONE),
                EmailAddressPredicate.class.getCanonicalName(),
                TEST_ARGMAP_ALL_PRESENT.getValueWhitespaceSeparated(PREFIX_EMAIL),
                PhysicalAddressPredicate.class.getCanonicalName(),
                TEST_ARGMAP_ALL_PRESENT.getValueWhitespaceSeparated(PREFIX_ADDRESS)
        );
        assertEquals(expected, TEST_SEARCH_PREDICATE_ALL_PRESENT.toString());
    }
}
