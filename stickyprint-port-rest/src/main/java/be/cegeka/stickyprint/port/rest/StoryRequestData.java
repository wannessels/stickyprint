package be.cegeka.stickyprint.port.rest;

import be.cegeka.stickyprint.core.api.PaperHeight;
import be.cegeka.stickyprint.core.api.PaperWidth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @NoArgsConstructor @ToString
public class StoryRequestData {

    private String number;
    private String title;
    private String sp;
    private PaperWidth paperWidth;
    private PaperHeight paperHeight;
}
