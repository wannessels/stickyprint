package be.cegeka.stickyprint.core.api;

import lombok.SneakyThrows;

import java.awt.image.BufferedImage;

/**
 * Created by jov on 17-3-2016.
 */
public interface Printer {
    //TODO wat met exceptions?
    @SneakyThrows
    void print(BufferedImage image);
}
