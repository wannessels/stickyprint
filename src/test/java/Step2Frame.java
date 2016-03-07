/**
 * Created by Wannes on 17/02/2016.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.text.DateFormat;
import java.sql.Time;
import jpos.*;


/**
 *  Outline      The code for printing the various types
 *              of characters is added.
 *  @author     s.muroga
 *  @version    1.00  (2001.05.07)
 */
public class Step2Frame extends JFrame {

    POSPrinterControl114 ptr = (POSPrinterControl114)new POSPrinter();

    JPanel contentPane;
    JPanel jPanel_Receipt = new JPanel();
    TitledBorder titledBorder1;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JButton jButton_Print = new JButton();
    JButton jButton_Close = new JButton();

    /**Constract "Frame"*/
    public Step2Frame() {
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
        this.setSize(new Dimension(300, 210));
        this.setTitle(" Step 2  Print various types of characters.");
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
        contentPane.add(jPanel_Receipt, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 20, 20));
        jPanel_Receipt.add(jButton_Print, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 130, 0));
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
        }
        // JavaPOS's code for Step1--END
    }

    //*************************************Button***********************************
    /**
     * Outline      A method "Print" calls some another method.
     *             They are method for printing.
     */
    void jButton_Print_actionPerformed(ActionEvent e) {
        DateFormat df = DateFormat.getDateInstance();
        Time t = new Time(System.currentTimeMillis());

        String time = df.format(Calendar.getInstance().getTime()) + " " + t.toString() + "\n";

        // JavaPOS's code for Step2
        try{
            // Print address
            //   ESC|N = Normal char
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|N123xxstreet,xxxcity,xxxxstate \n");
            //Print phone number
            //   ESC|rA = Right side char
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|rATEL 9999-99-9999   C#2\n");
            //Print date
            //   ESC|cA = Centaring char
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|cA" + time + "\n\n");
            //Print buying goods
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "apples                  $20.00\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "grapes                  $30.00\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "bananas                 $40.00\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "lemons                  $50.00\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "oranges                 $60.00\n\n");
            //Print the total cost
            //   ESC|bC = Bold
            //   ESC|uC = Underline
            //   ESC|2C = Wide charcter
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bCTax excluded.          $200.00\u001b|N\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|uCTax  5.0%               $10.00\u001b|N\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|bC\u001b|2CTotal   $210.00\u001b|N\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "Customer's payment     $250.00\n");
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "Change                  $40.00\n\n");

            //Feed the receipt to the cutter position automatically, and cut.
            //   ESC|#fP = Line Feed and Paper cut
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
        }
        catch(JposException ex){
        }
        // JavaPOS's code for Step2--END
    }

    /**
     * Outline      When the button "close" is pushed, The method
     *            "closing" is called.
     */
    void jButton_Close_actionPerformed(ActionEvent e) {
        this.closing();
    }

    /**
     * Outline       The status of the button described in the following
     *              is changed inactively.
     */
    public void changeButtonStatus(){
        this.jButton_Print.setEnabled(false);
    }

    /**
     * Outline     When the method "closing" is called,
     *            the following code is run.
     */
    void closing(){
        // JavaPOS's code for Step1
        try{
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
