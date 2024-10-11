package com.actelion.research.gui.generic;

public class GenericClipboardEvent extends GenericEvent {
    public static final int WHAT_COPY = 0;
    public static final int WHAT_PASTE = 1;

    private static final String[] WHAT_MESSAGES = {"copy", "paste"};

    private final String pasteData;

    public GenericClipboardEvent(int what, Object source, String data) {
        super(what, source);
        this.pasteData = data;
    }

    public String getPasteData() {
        return pasteData;
    }

    @Override
    public String toString() {
        int what = getWhat();
        String message =  WHAT_MESSAGES[what];

        if (what == WHAT_COPY) {
            return message + ":";
        } else if (what == WHAT_PASTE) {
            String data = getPasteData();
            return message + " with data: " + data;
        }

        return "Unsupported what: " + what + " with data: " + getPasteData();
    }
}
