package be.cegeka.stickyprint.core.api;

public interface PrintingApplicationService {

    PrintingResult print(PrintTask printTask);


    ImageRenderResult print(HtmlSnippet htmlSnippet);
}
