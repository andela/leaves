package com.worldtreeinc.leaves.activity;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by chidi on 12/9/15.
 */
public class EntryFeeWatcher implements InputFilter {
    private final int decimaldigits;

    public EntryFeeWatcher(int numberOfDecimalDigits) {
        this.decimaldigits = numberOfDecimalDigits;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int periodPosition = -1, destlen = dest.length();

        for (int i = 0; i < destlen; i++) {
            char c = dest.charAt(i);
            if (c == '.' || c == ',') {
                periodPosition = i;
                break;
            }
        }

        if (periodPosition >= 0) {
            if (source.equals(".") || source.equals(",")) {
                return "";
            } else if (dend <= periodPosition) {
                return null;
            } else if (destlen - periodPosition > decimaldigits) {
                return "";
            }
        }

        return null;
    }
}