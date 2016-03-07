/**
 * Created by Wannes on 16/02/2016.
 */


import jpos.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class StickyPrint {

    public static final String IMG_FILE = "imagewithtext.bmp";

    //de volgende waarden zijn "good enough" voor 58mm papier, vooral gevonden door trail&error
    public static final int FONT_SIZE = 200;
    public static final int LIJN1_OFFSET = 40;
    public static final int LIJN2_OFFSET = 42;

    public static final Font FONT = new Font("SansSerif", Font.PLAIN, FONT_SIZE);
    public static final int HEIGHT_58MM = 372;
    public static final int HEIGHT_80MM = 372;
    public static final int WIDTH_120MM = 768;

    public static void main(String[] args) throws JposException, IOException {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");

        POSPrinterControl114 ptr = (POSPrinterControl114) new POSPrinter();
        ptr.open("POSPrinter");
        ptr.claim(1);
        ptr.setDeviceEnabled(true);
        //ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001B|2uC       \u001B|bC\u001B|5CMr. Brawn\n");
        System.out.println(ptr.getRecLineWidth());
        saveImage("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz","ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        printImage(ptr);
        lineFeedAndCut(ptr);


        ptr.setDeviceEnabled(false);
        ptr.release();
        ptr.close();
    }

    private static void lineFeedAndCut(POSPrinterControl114 ptr) throws JposException {
        ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
    }

    private static void printImage(POSPrinterControl114 ptr) throws JposException {
        ptr.setMapMode(POSPrinterConst.PTR_MM_METRIC);
        ptr.setRecLetterQuality(true);

        if (ptr.getCapRecBitmap()==true) {
            ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
            ptr.printBitmap(POSPrinterConst.PTR_S_RECEIPT, IMG_FILE, POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_LEFT);
            ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
        }

    }

    static int getMinimumWidth(int currentWidth, String lijn) {
        BufferedImage dummyImg = new BufferedImage(10000,400,BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2 = dummyImg.createGraphics();
        FontRenderContext fontRenderContext = g2.getFontRenderContext();
        Rectangle2D stringBounds = FONT.getStringBounds(lijn, fontRenderContext);
        int width = Math.max(currentWidth,(int)stringBounds.getWidth()+20);
        return width;
    }

    static void saveImage(String lijn1, String lijn2) throws IOException {
        int imgHeight = HEIGHT_58MM;
        int imgWidth = WIDTH_120MM;

        imgWidth = getMinimumWidth(imgWidth, lijn1);
        imgWidth = getMinimumWidth(imgWidth, lijn2);
        BufferedImage img = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, imgWidth, imgHeight);
        g2.setColor(Color.black);
        g2.setFont(FONT);
        g2.drawString(lijn1,0 ,(imgHeight/2)- LIJN1_OFFSET);
        g2.drawString(lijn2,0,imgHeight- LIJN2_OFFSET);

        File outputFile = new File(IMG_FILE);
        ImageIO.write(rotate90DX(img), "bmp", outputFile);
    }

    public static BufferedImage rotate90DX(BufferedImage src) {
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
}
