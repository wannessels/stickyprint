import jpos.JposException;

import java.io.File;
import java.io.IOException;


public class StickyPrint {


    public static void main(String[] args) throws JposException, IOException {

        File bitmap = Text22Bitmap.get().createBitmap("test", "testlijn2");
        Printer printer = PrinterFactory.get().createPrinter();
        printer.print(bitmap);

    }


}
