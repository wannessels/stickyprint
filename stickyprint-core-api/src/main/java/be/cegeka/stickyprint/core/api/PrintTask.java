package be.cegeka.stickyprint.core.api;


import lombok.*;

@AllArgsConstructor(staticName = "aPrintTask")
@EqualsAndHashCode
@ToString
@Value
public class PrintTask {

    @NonNull
    private PrintLine firstLine;


    private PrintLine secondLine;

}
