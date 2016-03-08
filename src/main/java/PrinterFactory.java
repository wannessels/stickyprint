import jpos.POSPrinter;

public class PrinterFactory {


    private static final PrinterFactory INSTANCE = new PrinterFactory();


    public static PrinterFactory get() {
        return INSTANCE;
    }

    Printer createPrinter(){

        POSPrinter posPrinter = new POSPrinter();
        return new Printer(posPrinter);
    }

}
