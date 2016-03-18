package be.cegeka.stickyprint.core.api;

import java.awt.image.BufferedImage;

public class ImageRenderResult {

    private final BufferedImage result;

    public ImageRenderResult(BufferedImage result){
        this.result = result;
    };

    public BufferedImage getResult() {
        return result;
    }
}
