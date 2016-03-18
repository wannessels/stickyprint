package be.cegeka.stickyprint.core;

import be.cegeka.stickyprint.core.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@Service
public class PrintingApplicationServiceImpl implements PrintingApplicationService {
    @Autowired
    private Printer printer;

    static {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }

    //de volgende waarden zijn "good enough" voor 58mm papier, vooral gevonden door trail&error
    public static final int FONT_SIZE = 200;
    public static final int LIJN1_OFFSET = 40;
    public static final int LIJN2_OFFSET = 42;

    public static final Font FONT = new Font("SansSerif", Font.PLAIN, FONT_SIZE);

    @Override
    public PrintingResult print(PrintTask printTask) {
        BufferedImage imageToPrint = createBitmap(printTask.getFirstLine(), printTask.getSecondLine());
        printer.print(imageToPrint);
        return new PrintingResult();
    }

    public BufferedImage createBitmap(PrintLine lijn1, PrintLine lijn2) {

        int imgHeight = Printer.HEIGHT_58MM;
        int imgWidth = getMinimumWidth(Printer.WIDTH_120MM, lijn1);
        imgWidth = getMinimumWidth(imgWidth, lijn2);

        BufferedImage img = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, imgWidth, imgHeight);
        g2.setColor(Color.black);
        g2.setFont(FONT);
        g2.drawString(lijn1.getLineToPrint(),0 ,(imgHeight/2)- LIJN1_OFFSET);
        g2.drawString(lijn2.getLineToPrint(),0,imgHeight- LIJN2_OFFSET);

        return rotate90DX(img);
    }


    private static BufferedImage rotate90DX(BufferedImage src) {
        int newWidth = src.getHeight();
        int newHeight = src.getWidth();
        AffineTransform transform = new AffineTransform();
        transform.translate(newWidth, 0);
        transform.rotate(Math.toRadians(90));

        BufferedImage result = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = result.createGraphics();

        g2d.drawImage(src, transform, null);
        g2d.dispose();

        return result;
    }

    static int getMinimumWidth(int currentWidth, PrintLine lijn) {
        BufferedImage dummyImg = new BufferedImage(10000,400,BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2 = dummyImg.createGraphics();
        FontRenderContext fontRenderContext = g2.getFontRenderContext();
        Rectangle2D stringBounds = FONT.getStringBounds(lijn.getLineToPrint(), fontRenderContext);
        int width = Math.max(currentWidth,(int)stringBounds.getWidth()+20);
        return width;
    }
}
