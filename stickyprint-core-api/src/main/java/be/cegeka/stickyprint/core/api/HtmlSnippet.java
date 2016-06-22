package be.cegeka.stickyprint.core.api;

public class HtmlSnippet {

    private final String html;
    private final String css;

    public HtmlSnippet(String html, String css) {
        this.html = html;
        this.css = css;
    }

    public String getHtml() {
        return html;
    }

    public String getCss() {
        return css;
    }
}
