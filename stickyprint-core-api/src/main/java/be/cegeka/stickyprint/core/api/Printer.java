package be.cegeka.stickyprint.core.api;

import lombok.SneakyThrows;

import java.awt.image.BufferedImage;

/**
 * Created by jov on 17-3-2016.
 */
public interface Printer {

    int HEIGHT_58MM = 372;
    int HEIGHT_80MM = 512;
    int WIDTH_120MM = 768;

    //TODO wat met exceptions?
    @SneakyThrows
    void print(BufferedImage image);
}
