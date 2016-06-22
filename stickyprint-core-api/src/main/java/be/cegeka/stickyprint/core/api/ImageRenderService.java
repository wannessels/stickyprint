package be.cegeka.stickyprint.core.api;

public interface ImageRenderService {
    public ImageRenderResult renderImage(HtmlSnippet htmlSnippet, PaperHeight paperHeight, PaperWidth paperWidth);
}
