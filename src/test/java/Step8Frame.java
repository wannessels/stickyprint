/**
 * Created by Wannes on 7/03/2016.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.sql.Time;

import jp.co.epson.upos.UPOSConst;
import jpos.*;
import jpos.events.*;

/**
 *  Outline      A code for the printing by
 *              "the rotation printing mode" is added.
 *  @author     s.muroga
 *  @version    1.00  (2001.05.07)
 */
public class Step8Frame extends JFrame implements OutputCompleteListener{

    POSPrinterControl114 ptr = (POSPrinterControl114)new POSPrinter();

    //	A maximum of 2 line widths will be considered
    public final int MAX_LINE_WIDTHS = 2;

    JPanel contentPane;
    JPanel jPanel_Receipt = new JPanel();
    TitledBorder titledBorder1;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JButton jButton_Print = new JButton();
    JButton jButton_AsynchronousPrinting = new JButton();
    JButton jButton_PrintReceipt = new JButton();
    JButton jButton_Close = new JButton();

    /**Constract "Frame"*/
    public Step8Frame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**Form the conponent*/
    private void jbInit() throws Exception  {
        //setIconImage(Toolkit.getDefaultToolkit().createImage(Step1Frame.class.getResource("[Your Icon]")));
        contentPane = (JPanel) this.getContentPane();
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Receipt");
        contentPane.setLayout(gridBagLayout1);
        this.setSize(new Dimension(300, 300));
        this.setTitle("Step 8  Rotate printing");
        jPanel_Receipt.setLayout(gridBagLayout2);
        jPanel_Receipt.setBorder(titledBorder1);
        jButton_Print.setText("Print");
        jButton_Print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton_Print_actionPerformed(e);
            }
        });
        jButton_Close.setText("Close");
        jButton_Close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton_Close_actionPerformed(e);
            }
        });
        jButton_AsynchronousPrinting.setText("Asynchronous Printing");
        jButton_AsynchronousPrinting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton_AsynchronousPrinting_actionPerformed(e);
            }
        });
        jButton_PrintReceipt.setText("Print Receipt");
        jButton_PrintReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton_PrintReceipt_actionPerformed(e);
            }
        });
        contentPane.add(jPanel_Receipt, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 20, 20));
        jPanel_Receipt.add(jButton_Print, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 130, 0));
        jPanel_Receipt.add(jButton_AsynchronousPrinting, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));
        jPanel_Receipt.add(jButton_PrintReceipt, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 10), 0, 0));
        contentPane.add(jButton_Close, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 0, 0));
    }

    /**
     * Outline     The processing code required in order to enable
     *            or to disable use of service is written here.
     * @exception JposException  This exception is fired toward the failure of
     *                          the method which JavaPOS defines.
     */
    /**When the window was closed*/
    protected void processWindowEvent(WindowEvent e){
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            this.closing();
        }
        /**When the window was opened*/
        else if (e.getID() == WindowEvent.WINDOW_OPENED){
            // JavaPOS's code for Step7
            // Set OutputCompleteEvent listener
            ptr.addOutputCompleteListener(this);
            // JavaPOS's code for Step7--END

            // JavaPOS's code for Step1
            try {
                //Open the device.
                //Use the name of the device that connected with your computer.
                ptr.open("POSPrinter");

                //Get the exclusive control right for the opened device.
                //Then the device is disable from other application.
                ptr.claim(1000);

                //Enable the device.
                ptr.setDeviceEnabled(true);
            }
            catch(JposException ex){
                changeButtonStatus();
            }
            // JavaPOS's code for Step3
            try{
                // JavaPOS's code for Step5
                //Even if using any printers, 0.01mm unit makes it possible to print neatly.
                ptr.setMapMode(POSPrinterConst.PTR_MM_METRIC);
                // JavaPOS's code for Step5--END

                //Output by the high quality mode
                ptr.setRecLetterQuality(true);

                // JavaPOS's code for Step6
                if (ptr.getCapRecBitmap() == true)
                    //Register a bitmap
                    if (ptr.getCapRecBitmap() == true)
                    {
                        boolean bSetBitmapSuccess = false;
                        for (int iRetryCount = 0; iRetryCount < 5; iRetryCount++)
                        {
                            try{
                                //Register a bitmap
                                ptr.setBitmap(1, POSPrinterConst.PTR_S_RECEIPT, "javapos.bmp",
                                        (ptr.getRecLineWidth() / 2), POSPrinterConst.PTR_BM_CENTER);
                                bSetBitmapSuccess = true;
                                break;
                            } catch (JposException ex)
                            {
                                if (ex.getErrorCode() == UPOSConst.UPOS_E_FAILURE && ex.getErrorCodeExtended() == 0 && ex.getMessage().equals("It is not initialized."))
                                {
                                    try{
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex2)
                                    {
                                    }
                                }
                            }
                        }
                        if (!bSetBitmapSuccess)
                        {
                            JOptionPane.showMessageDialog(this, "Failed to set bitmap.", "", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                // JavaPOS's code for Step6--END

                // JavaPOS's code for Step8
                //Check on rotation print function
                if ((ptr.getCapRecLeft90() == false) || (ptr.getCapRecRight90() == false)){
                    //Not function to [Print Receipt] button is disable
                    this.jButton_PrintReceipt.setEnabled(false);
                }
                // JavaPOS's code for Step8--END
            }
            catch(JposException ex){
            }
            // JavaPOS's code for Step3--END
        }
        // JavaPOS's code for Step1--END
    }

    // JavaPOS's code for Step7
    public void outputCompleteOccurred(OutputCompleteEvent outComE){
        String stOutputCompleteEventText = new Integer(outComE.getOutputID()).toString();
        JOptionPane.showMessageDialog(this, "Complete printing : ID = " + stOutputCompleteEventText,
                "AsyncPrint", JOptionPane.PLAIN_MESSAGE);
    }
    // JavaPOS's code for Step7--END

    //*************************************Button***********************************
    /**
     * Outline      A method "Print" calls some another method.
     *             They are method for printing.
     */
    void jButton_Print_actionPerformed(ActionEvent e) {
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());

        String time = df.format(Calendar.getInstance().getTime()) + " " + t.toString() + "\n";

        String    bcData = "4902720005074";

        int[] RecLineChars = new int[MAX_LINE_WIDTHS];
        long lRecLineCharsCount;

        String[] item = {"apples", "grapes", "bananas", "lemons", "oranges"};
        String[] price = {"10.00", "20.00", "30.00", "40.00", "50.00"};

        // JavaPOS's code for Step2
        try{
            // JavaPOS's code for Step6
            if (ptr.getCapRecPresent() == true){
                //Batch processing mode
                ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);
                // JavaPOS's code for Step6--END

                // JavaPOS's code for Step3
                //Print a registered bitmap.
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1B");
                // JavaPOS's code for Step3--END

                // Print address
                //   ESC|N = Normal char
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|N123xxstreet,xxxcity,xxxxstate \n");
                //Print phone number
                //   ESC|rA = Right side char
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|rATEL 9999-99-9999   C#2\n");

                // JavaPOS's code for Step5
                //Make 2mm speces
                //   ESC|#uF = Line Feed
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|200uF");
                // JavaPOS's code for Step5--END

                //Print date
                //   ESC|cA = Centaring char
                lRecLineCharsCount = getRecLineChars(RecLineChars);
                String CharLists = ptr.getRecLineCharsList();
                String aCharList[] = new String[MAX_LINE_WIDTHS];
                int iCounter=0;
                StringTokenizer st = new StringTokenizer(CharLists,",");
                while (st.hasMoreTokens())
                {
                    aCharList[iCounter]=st.nextToken();
                    iCounter++;
                }
                for (int i=0;i<iCounter;i++)
                {
                    if(aCharList[i]!=null){
                        RecLineChars[i]= Integer.parseInt(aCharList[i]);
                    }
                }
                if (lRecLineCharsCount >= 2) {
                    ptr.setRecLineChars(RecLineChars[1]);
                    ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA" + time + "\n");
                    ptr.setRecLineChars(RecLineChars[0]);
                }
                else {
                    ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA" + time + "\n");
                }

                // JavaPOS's code for Step5
                //Make 5mm speces
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|500uF");

                //Print buying goods
                double total = 0.0;
                String printData = "";
                for (int i = 0; i < item.length; i++){
                    printData = makePrintString(ptr.getRecLineChars(), item[i], "$" + price[i]);
                    ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, printData + "\n");
                    total += new Double(price[i]).doubleValue();
                }

                //Make 2mm speces
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|200uF");

                //Print the total cost
                printData = makePrintString(ptr.getRecLineChars(), "Tax excluded.",
                        formatForDouble(total));
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bC" + printData + "\n");

                printData = makePrintString(ptr.getRecLineChars(), "Tax  5.0%",
                        formatForDouble(total * 0.05));
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|uC" + printData + "\n");

                printData = makePrintString((ptr.getRecLineChars() / 2), "Total",
                        formatForDouble(total * 1.05));
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bC\u001b|2C" + printData + "\n");

                printData = makePrintString(ptr.getRecLineChars(), "Customer's payment", "$200.00");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, printData + "\n");

                printData = makePrintString(ptr.getRecLineChars(), "Change",
                        formatForDouble(200.00 - (total * 1.05)));
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, printData + "\n");

                //Make 5mm speces
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|500uF");

                //Barcode printing
                //Print from left side after 1cm spece.
                if (ptr.getCapRecBarCode() == true)
                    ptr.printBarCode(POSPrinterConst.PTR_S_RECEIPT, bcData, POSPrinterConst.PTR_BCS_JAN13,
                            1000, ptr.getRecLineWidth(), POSPrinterConst.PTR_BC_CENTER,
                            POSPrinterConst.PTR_BC_TEXT_BELOW);
                // JavaPOS's code for Step5--END

                //Feed the receipt to the cutter position automatically, and cut.
                //   ESC|#fP = Line Feed and Paper cut
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");

                // JavaPOS's code for Step6
                //print all the buffer data. and exit the batch processing mode.
                ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);
                // JavaPOS's code for Step6--END
            }
        }
        catch(JposException ex){
        }
        // JavaPOS's code for Step2--END
    }

    /**
     * Outline      A method "Asynchronous Printing" calls some another method.
     *             They are method for starting and ending "AsyncMode"
     *             and for printing.
     */
    void jButton_AsynchronousPrinting_actionPerformed(ActionEvent e) {
        // JavaPOS's code for Step7
        try{
            //Afterwards, asynchronous output
            ptr.setAsyncMode(true);

            jButton_Print_actionPerformed(e);

            //Back to the synchronous mode
            ptr.setAsyncMode(false);
        }
        catch(JposException ex){
        }
        // JavaPOS's code for Step7--END
    }

    /**
     * Outline      A method "Print Receipt" calls some another method.
     *             They are method for starting and ending
     *            "the rotation printing mode" and for printing.
     */
    void jButton_PrintReceipt_actionPerformed(ActionEvent e) {
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());

        String time = df.format(Calendar.getInstance().getTime()) + " " + t.toString() + "\n";

        // JavaPOS's code for Step8
        try{
            if (ptr.getCapRecPresent() == true){
                // keep a current LineSpacing.
                int orgSpacing = ptr.getRecLineSpacing();

                //Batch processing mode
                ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_TRANSACTION);

                //Enter the sideway mode.
                ptr.rotatePrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_RP_LEFT90);

                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|4C\u001b|bC   Receipt     ");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|3C\u001b|2uC       Mr. Brawn\n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|2uC                                                  \n\n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|2uC\u001b|3C        Total payment              $\u001b|4C21.00  \n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1C\n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, time + " Received\n\n");
                ptr.setRecLineSpacing(ptr.getRecLineHeight());
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|uC Details               \n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1C                          \u001b|2CJPOS Store\n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|uC Tax excluded    $20.00\n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1C                          \u001b|bCZip code 999-9999\n");
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|uC Tax(5%)        $1.00\u001b|N    Phone#(9999)99-9998\n");

                ptr.rotatePrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_RP_NORMAL);

                //Feed the receipt to the cutter position automatically, and cut.
                //   ESC|#fP = Line Feed and Paper cut
                ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");

                //print all the buffer data. and exit the batch processing mode.
                ptr.transactionPrint(POSPrinterConst.PTR_S_RECEIPT, POSPrinterConst.PTR_TP_NORMAL);

                ptr.setRecLineSpacing(orgSpacing);
            }
        }
        catch(JposException ex){
        }
        // JavaPOS's code for Step8--END
    }

    /**
     * Outline      When the button "close" is pushed, The method
     *            "closing" is called.
     */
    void jButton_Close_actionPerformed(ActionEvent e) {
        this.closing();
    }

    /**
     *  Outline      An appropriate interval is converted into the length of
     *              the tab about two texts.
     *               And make a printing data.
     *  @param      int lineChars   The width of the territory which it
     *                             prints on is converted into the number of
     *                             characters, and that value is specified.
     *  @param      String text1    It is necessary as an information for
     *                             deciding the interval of the text.
     *  @param      String text2   It is necessary as an information for
     *                             deciding the interval of the text, too.
     *  @return     String space    printing data.
     */
    public String makePrintString(int lineChars,String text1,String text2){
        int spaces = 0;
        String tab = "";
        try{
            spaces = lineChars - (text1.length() + text2.length());
            for (int j = 0 ; j < spaces ; j++){
                tab += " ";
            }
        }
        catch(Exception ex){
        }
        return text1 + tab + text2;
    }

    /**
     * Outline     The value received as a "double" type is changed into
     *            the "String" type. The indication of the currency unit
     *            and so on is indicated.
     * @param   double  contents
     * @return  String  newFormNo   The value received as a "double" pattern
     *                             is changed into the "String" type and returned.
     */
    // "123.4" -> "$123.40" for instance.
    public String formatForDouble(double contents){
        String newFormNo = "";
        try{
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            newFormNo = nf.format(contents);
        }
        catch(Exception ex){
        }
        return newFormNo;
    }

    private long getRecLineChars(int[] RecLineChars) throws JposException
    {
        long lRecLineChars = 0;
        long lCount;
        int i;

        // Calculate the element count.
        String[] temp = ptr.getRecLineCharsList().split(",");
        lCount = temp[0].length();

        if(lCount == 0) {
            lRecLineChars = 0;
        }
        else {
            if (lCount > MAX_LINE_WIDTHS)
            {
                lCount = MAX_LINE_WIDTHS;
            }

            for( i = 0; i < lCount; i++) {
                RecLineChars[i] = Integer.parseInt(temp[i]);
            }

            lRecLineChars = lCount;
        }

        return lRecLineChars;
    }

    /**
     * Outline       When the method "changeButtonStatus" was called,
     *              all buttons other than a button "closing" become invalid.
     */
    public void changeButtonStatus(){
        this.jButton_Print.setEnabled(false);
        this.jButton_AsynchronousPrinting.setEnabled(false);
        this.jButton_PrintReceipt.setEnabled(false);
    }

    /**
     * Outline     When the method "closing" is called,
     *            the following code is run.
     */
    void closing(){
        // JavaPOS's code for Step1
        try{
            // JavaPOS's code for Step7
            // Remove OutputCompleteEvent listener
            ptr.removeOutputCompleteListener(this);
            // JavaPOS's code for Step7--END

            //Cancel the device.
            ptr.setDeviceEnabled(false);

            //Release the device exclusive control right.
            ptr.release();

            //Finish using the device.
            ptr.close();
        }
        catch(JposException ex){
        }
        // JavaPOS's code for Step1--END
        System.exit(0);
    }
}