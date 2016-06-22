package be.cegeka.stickyprint.core;

import be.cegeka.stickyprint.core.api.*;
import cz.vutbr.web.css.MediaSpec;
import lombok.SneakyThrows;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.io.StreamDocumentSource;
import org.fit.cssbox.layout.BrowserCanvas;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class ImageRenderServiceImpl implements ImageRenderService{


    private static final boolean CROP = true;

    @Override
    @SneakyThrows
    public ImageRenderResult renderImage(HtmlSnippet htmlSnippet, PaperHeight paperHeight, PaperWidth paperWidth) {
        DocumentSource documentSource = new StreamDocumentSource(new ByteArrayInputStream(htmlSnippet.getHtml().getBytes(StandardCharsets.UTF_8)),new URL("file://temp"),"text/html");

        DefaultDOMSource parser = new DefaultDOMSource(documentSource);
        Document doc = parser.parse();
        MediaSpec media = new MediaSpec("print");
        media.setDimensions((float) paperWidth.getSizeInPixels(), (float)paperHeight.getSizeInPixels());
        media.setDeviceDimensions((float)paperWidth.getSizeInPixels(), (float)paperHeight.getSizeInPixels());
        DOMAnalyzer da = new DOMAnalyzer(doc, documentSource.getURL());
        da.setMediaSpec(media);
        da.attributesToStyles();
        da.addStyleSheet((URL)null, htmlSnippet.getCss(), DOMAnalyzer.Origin.USER);
        da.addStyleSheet((URL) null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
        da.addStyleSheet((URL)null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT);
        da.addStyleSheet((URL)null, CSSNorm.formsStyleSheet(), DOMAnalyzer.Origin.AGENT);

        da.getStyleSheets();
        BrowserCanvas contentCanvas = new BrowserCanvas(da.getRoot(), da, documentSource.getURL());
        contentCanvas.setAutoMediaUpdate(false);
        contentCanvas.getConfig().setClipViewport(CROP);
        contentCanvas.getConfig().setLoadImages(true);
        contentCanvas.getConfig().setLoadBackgroundImages(true);
        contentCanvas.createLayout(new Dimension(paperWidth.getSizeInPixels(), paperHeight.getSizeInPixels()));
        return new ImageRenderResult(convertTo1BitImage(contentCanvas.getImage()));
    }

    private static BufferedImage convertTo1BitImage(BufferedImage src) {
        BufferedImage target = new BufferedImage(src.getWidth(),src.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
        Graphics graphics = target.getGraphics();
        graphics.drawImage(src,0,0,null);
        return target;
    }

    private static final String printerStyleSheet() {
        return   "body { font-size: 13vmin; line-height: 1}";
    }

}
