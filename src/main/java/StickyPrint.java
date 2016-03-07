import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.POSPrinterControl114;

import java.io.File;
import java.io.IOException;


public class StickyPrint {




    public static void main(String[] args) throws JposException, IOException {
        System.setProperty("com.sun.media.jai.disableMediaLib", "true");

        POSPrinter ptr = new POSPrinter();
        ptr.open("POSPrinter");
        ptr.claim(1);
        ptr.setDeviceEnabled(true);
        //ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001B|2uC       \u001B|bC\u001B|5CMr. Brawn\n");
        System.out.println(ptr.getRecLineWidth());
         // File bitmap = saveImage("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        File bitmap = saveImage("test", "testlijn2");
        printImage(ptr,bitmap);
        lineFeedAndCut(ptr);

        ptr.setDeviceEnabled(false);
        ptr.release();
        ptr.close();
    }

    private static void lineFeedAndCut(POSPrinterControl114 ptr) throws JposException {
        ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
    }

    private static void printImage(POSPrinterControl114 ptr, File bitmap) throws JposException {
        ptr.setMapMode(POSPrinterConst.PTR_MM_METRIC);
        ptr.setRecLetterQuality(true);

        if (ptr.getCapRecBitmap() == true) {
            ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
            ptr.printBitmap(POSPrinterConst.PTR_S_RECEIPT, bitmap.getAbsolutePath(), POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_LEFT);
            ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
        }

    }


    static File saveImage(String lijn1, String lijn2) throws IOException {
        File bitmap = Text22Bitmap.get().createBitmap(lijn1, lijn2);
        return bitmap;
    }


}
