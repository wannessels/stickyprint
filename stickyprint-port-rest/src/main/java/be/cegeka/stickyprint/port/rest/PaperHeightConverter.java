package be.cegeka.stickyprint.port.rest;


import be.cegeka.stickyprint.core.api.PaperHeight;

import java.beans.PropertyEditorSupport;

public class PaperHeightConverter extends PropertyEditorSupport{

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        PaperHeight paperHeight = PaperHeight.valueOf(text.toUpperCase());
        setValue(paperHeight);
    }
}
