package be.cegeka.stickyprint.core.api;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "aPrintLine")
public class PrintLine {

    private String lineToPrint;


}


