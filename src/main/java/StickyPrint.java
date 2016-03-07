/**
 * Created by Wannes on 16/02/2016.
 */


import jp.co.epson.upos.UPOSConst;
import jpos.*;
import jpos.events.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class StickyPrint {

    public static final String IMG_FILE = "imagewithtext.bmp";

    public static void main(String[] args) throws JposException, IOException {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");

        POSPrinterControl114 ptr = (POSPrinterControl114) new POSPrinter();
        ptr.open("POSPrinter");
        ptr.claim(1);
        ptr.setDeviceEnabled(true);
        //ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001B|2uC       \u001B|bC\u001B|5CMr. Brawn\n");
        System.out.println(ptr.getRecLineWidth());
        saveImage("Text","API?");
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
        // JavaPOS's code for Step5--END

        //Output by the high quality mode
        ptr.setRecLetterQuality(true);

        if (ptr.getCapRecBitmap()==true) {
            ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
            ptr.printBitmap(POSPrinterConst.PTR_S_RECEIPT, IMG_FILE, POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_LEFT);
            ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);

        }
        //ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1B");

    }

    static void saveImage(String lijn1, String lijn2) throws IOException {
        //int imgHeight = 512; //voor 80mm papier
        int imgHeight=372; // voor 58mm papier
        int imgWidth=768; // ongeveer 12cm, cfr post-it
        //int imgWidth=1024; //

        BufferedImage img = new BufferedImage(imgWidth,imgHeight,BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, imgWidth, imgHeight);
        g2.setColor(Color.black);
        Font font = new Font("SansSerif", Font.PLAIN, 200);
        g2.setFont(font);
        g2.drawString(lijn1,0 ,(imgHeight/2)-48);
        g2.drawString(lijn2,0,imgHeight-48);

        File outputfile = new File(IMG_FILE);
        ImageIO.write(rotate90DX(img), "bmp", outputfile);
    }

    public static BufferedImage rotate90DX(BufferedImage src) {
        int newWidth = src.getHeight();
        int newHeight = src.getWidth();
        AffineTransform transform = new AffineTransform();
        transform.translate(newWidth, 0);
        transform.rotate(Math.toRadians(90));

        BufferedImage result = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = (Graphics2D) result.createGraphics();

        g2d.drawImage(src, transform, null);
        g2d.dispose();

        return result;
    }
}
