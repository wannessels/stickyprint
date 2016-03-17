package be.cegeka.stickyprint.port.jposprinter;

import be.cegeka.stickyprint.core.api.Printer;
import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Component
public class JposPrinter implements Printer {
    private POSPrinter posPrinter;

    public JposPrinter() {
        this.posPrinter = new POSPrinter();
    }

    //TODO wat met exceptions?
    @Override
    @SneakyThrows
    public void print(BufferedImage image) {
        File tempFile = File.createTempFile("stickyprint",".bmp");
        try {
            ImageIO.write(image, "bmp", tempFile);
            print(tempFile);
        } finally {
            tempFile.delete();
        }
    }


    public void print(File bitmap) throws JposException {
        posPrinter.open("POSPrinter");
        posPrinter.claim(1);
        posPrinter.setDeviceEnabled(true);

        printImage(bitmap);
        lineFeedAndCut();

        posPrinter.setDeviceEnabled(false);
        posPrinter.release();
        posPrinter.close();

    }


    private void lineFeedAndCut() throws JposException {
        posPrinter.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
    }

    private void printImage(File bitmap) throws JposException {
        posPrinter.setMapMode(POSPrinterConst.PTR_MM_METRIC);
        posPrinter.setRecLetterQuality(true);

        if (posPrinter.getCapRecBitmap() == true) {
            posPrinter.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
            posPrinter.printBitmap(POSPrinterConst.PTR_S_RECEIPT, bitmap.getAbsolutePath(), POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_LEFT);
            posPrinter.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
        }

    }


}
