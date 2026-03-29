package seedu.address.ui;

/**
 * Utility for converting a zero-based list index to the display index shown in the log UI.
 */
final class LogEntryDisplayIndex {

    private LogEntryDisplayIndex() {}

    static int calculateDisplayIndex(int zeroBasedIndex, int totalLogs) {
        if (totalLogs <= 0 || zeroBasedIndex < 0 || zeroBasedIndex >= totalLogs) {
            return 1;
        }
        return totalLogs - zeroBasedIndex;
    }
}
