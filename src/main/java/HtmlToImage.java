import org.burgetr.webvector.TransformWorker;
import org.fit.cssbox.demo.ImageRenderer;

import java.awt.*;
import java.io.IOException;

/**
 * Created by jov on 18-3-2016.
 */
public class HtmlToImage {

    public static void main(String[] args) throws IOException {
        TransformWorker worker = new TransformWorker("http://www.google.be", "c:\\temp\\image.png", ImageRenderer.Type.PNG, "print", new Dimension(1000,500), true,
                true, false);
        worker.execute();
        System.in.read();
    }
}
