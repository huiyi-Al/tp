package seedu.address.model.person.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

public class LogHistoryTest {

    private static final LogEntry LOG_ENTRY_1 = new LogEntry(
            LocalDateTime.of(2026, 3, 20, 9, 0),
            new LogMessage("First log"));
    private static final LogEntry LOG_ENTRY_2 = new LogEntry(
            LocalDateTime.of(2026, 3, 21, 10, 0),
            new LogMessage("Second log"));
    private static final LogEntry LOG_ENTRY_3 = new LogEntry(
            LocalDateTime.of(2026, 3, 22, 11, 0),
            new LogMessage("Third log"));

    @Test
    public void constructor_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LogHistory(null));
    }

    @Test
    public void constructor_listWithNullEntry_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LogHistory(Arrays.asList(LOG_ENTRY_1, null)));
    }

    @Test
    public void emptyHistory() {
        LogHistory logHistory = new LogHistory();

        assertTrue(logHistory.isEmpty());
        assertEquals(0, logHistory.size());
        assertEquals(Optional.empty(), logHistory.getLatest());
        assertTrue(logHistory.asUnmodifiableList().isEmpty());
    }

    @Test
    public void constructor_unsortedEntries_normalizesToNewestFirstOrder() {
        LogHistory logHistory = new LogHistory(List.of(LOG_ENTRY_1, LOG_ENTRY_3, LOG_ENTRY_2));

        assertEquals(List.of(LOG_ENTRY_3, LOG_ENTRY_2, LOG_ENTRY_1), logHistory.asUnmodifiableList());
        assertEquals(Optional.of(LOG_ENTRY_3), logHistory.getLatest());
    }

    @Test
    public void add_singleEntry_addedAsLatest() {
        LogHistory updatedHistory = new LogHistory().add(LOG_ENTRY_1);

        assertFalse(updatedHistory.isEmpty());
        assertEquals(1, updatedHistory.size());
        assertEquals(Optional.of(LOG_ENTRY_1), updatedHistory.getLatest());
        assertEquals(List.of(LOG_ENTRY_1), updatedHistory.asUnmodifiableList());
    }

    @Test
    public void add_multipleEntries_keepsNewestFirst() {
        LogHistory updatedHistory = new LogHistory()
                .add(LOG_ENTRY_1)
                .add(LOG_ENTRY_2)
                .add(LOG_ENTRY_3);

        assertEquals(List.of(LOG_ENTRY_3, LOG_ENTRY_2, LOG_ENTRY_1), updatedHistory.asUnmodifiableList());
        assertEquals(Optional.of(LOG_ENTRY_3), updatedHistory.getLatest());
    }

    @Test
    public void add_olderEntry_keepsNewestFirstOrder() {
        LogHistory initialHistory = new LogHistory(List.of(LOG_ENTRY_3, LOG_ENTRY_2));
        LogHistory updatedHistory = initialHistory.add(LOG_ENTRY_1);

        assertEquals(List.of(LOG_ENTRY_3, LOG_ENTRY_2, LOG_ENTRY_1), updatedHistory.asUnmodifiableList());
        assertEquals(Optional.of(LOG_ENTRY_3), updatedHistory.getLatest());
    }

    @Test
    public void delete_firstEntry_deleted() {
        LogHistory originalHistory = createThreeEntryHistory();

        LogHistory updatedHistory = originalHistory.delete(Index.fromOneBased(1));

        assertEquals(List.of(LOG_ENTRY_2, LOG_ENTRY_1), updatedHistory.asUnmodifiableList());
        assertEquals(Optional.of(LOG_ENTRY_2), updatedHistory.getLatest());
    }

    @Test
    public void delete_middleEntry_deleted() {
        LogHistory originalHistory = createThreeEntryHistory();

        LogHistory updatedHistory = originalHistory.delete(Index.fromOneBased(2));

        assertEquals(List.of(LOG_ENTRY_3, LOG_ENTRY_1), updatedHistory.asUnmodifiableList());
        assertEquals(Optional.of(LOG_ENTRY_3), updatedHistory.getLatest());
    }

    @Test
    public void delete_lastEntry_deleted() {
        LogHistory originalHistory = createThreeEntryHistory();

        LogHistory updatedHistory = originalHistory.delete(Index.fromOneBased(3));

        assertEquals(List.of(LOG_ENTRY_3, LOG_ENTRY_2), updatedHistory.asUnmodifiableList());
        assertEquals(Optional.of(LOG_ENTRY_3), updatedHistory.getLatest());
    }

    @Test
    public void delete_invalidIndex_throwsIndexOutOfBoundsException() {
        LogHistory logHistory = createThreeEntryHistory();
        assertThrows(IndexOutOfBoundsException.class, () -> logHistory.delete(Index.fromOneBased(4)));
    }

    @Test
    public void asUnmodifiableList_modifyList_throwsUnsupportedOperationException() {
        LogHistory logHistory = createThreeEntryHistory();
        assertThrows(UnsupportedOperationException.class, () -> logHistory.asUnmodifiableList().remove(0));
    }

    @Test
    public void equals() {
        LogHistory logHistory = createThreeEntryHistory();
        LogHistory sameLogHistory = new LogHistory(List.of(LOG_ENTRY_3, LOG_ENTRY_2, LOG_ENTRY_1));

        // same values -> returns true
        assertTrue(logHistory.equals(sameLogHistory));
        assertEquals(logHistory.hashCode(), sameLogHistory.hashCode());

        // same object -> returns true
        assertTrue(logHistory.equals(logHistory));

        // null -> returns false
        assertFalse(logHistory.equals(null));

        // different type -> returns false
        assertFalse(logHistory.equals(5));

        // different values -> returns false
        assertFalse(logHistory.equals(new LogHistory(List.of(LOG_ENTRY_3, LOG_ENTRY_2))));
    }

    private LogHistory createThreeEntryHistory() {
        return new LogHistory()
                .add(LOG_ENTRY_1)
                .add(LOG_ENTRY_2)
                .add(LOG_ENTRY_3);
    }
}
