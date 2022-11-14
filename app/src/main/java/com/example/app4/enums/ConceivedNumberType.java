package com.example.app4.enums;

import com.example.app4.R;

public enum ConceivedNumberType {
    TWO_NUMBER(0, 5, R.string.show_hint_label),
    THREE_DIGITS(1, 7, R.string.show_hint_label_3),
    FOUR_DIGITS(2, 10, R.string.show_hint_label_4);

    private int mode;
    private int hint;
    private int attempt;

    ConceivedNumberType(int mode, int attempt, int hint) {
        this.mode = mode;
        this.hint = hint;
        this.attempt = attempt;
    }

    public int getMode() {
        return mode;
    }

    public int getHint() {
        return hint;
    }

    public int getAttempt() {
        return attempt;
    }
}
