package be.cegeka.stickyprint.port.rest;


import be.cegeka.stickyprint.core.api.PaperHeight;
import be.cegeka.stickyprint.core.api.PaperWidth;

import java.beans.PropertyEditorSupport;

public class PaperWidthConverter extends PropertyEditorSupport{

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        PaperWidth paperWidth = PaperWidth.valueOf(text.toUpperCase());
        setValue(paperWidth);
    }
}
