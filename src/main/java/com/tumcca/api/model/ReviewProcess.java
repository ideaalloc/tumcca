package com.tumcca.api.model;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-21
 */
public enum ReviewProcess {
    UNDER_REVIEW("UNDER REVIEW"), PASSED("PASSED"), REJECTED("REJECTED");

    final String process;

    ReviewProcess(final String reviewProcess) {
        process = reviewProcess;
    }

    public String getProcess() {
        return process;
    }
}
