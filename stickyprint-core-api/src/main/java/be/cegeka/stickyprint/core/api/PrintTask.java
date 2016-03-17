package be.cegeka.stickyprint.core.api;


import lombok.*;

@AllArgsConstructor(staticName = "aPrintTaskToPrint")
@RequiredArgsConstructor(staticName = "aPrintTaskToPrint")
@EqualsAndHashCode
@ToString
@Value
public class PrintTask {

    @NonNull
    private PrintLine firstLine;


    private PrintLine secondLine;

}
