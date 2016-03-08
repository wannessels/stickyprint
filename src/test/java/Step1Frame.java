/**
 * Created by Wannes on 17/02/2016.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jpos.*;


/**
 *  Outline      The code for starting service and the code
 *              for printing are described.
 *  @author     s.muroga
 *  @version    1.00  (2001.04.24)
 */
public class Step1Frame extends JFrame {

    POSPrinterControl114 ptr = (POSPrinterControl114)new POSPrinter();

    JPanel contentPane;
    JPanel jPanel_reciept = new JPanel();
    TitledBorder titledBorder1;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JButton jButton_Print = new JButton();

    /**Constract "Frame"*/
    public Step1Frame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**Form the component*/
    private void jbInit() throws Exception  {
        //setIconImage(Toolkit.getDefaultToolkit().createImage(Step1Frame.class.getResource("[Your Icon]")));
        contentPane = (JPanel) this.getContentPane();
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"Receipt");
        contentPane.setLayout(gridBagLayout1);
        this.setSize(new Dimension(300, 180));
        this.setTitle("Step 1  Print \"Hello JavaPOS\"");
        jPanel_reciept.setLayout(gridBagLayout2);
        jPanel_reciept.setBorder(titledBorder1);
        jButton_Print.setText("Print");
        jButton_Print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton_Print_actionPerformed(e);
            }
        });
        contentPane.add(jPanel_reciept, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 20, 20));
        jPanel_reciept.add(jButton_Print, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 10, 5, 10), 130, 0));
    }

    /**
     * Outline     The processing code required in order to enable
     *            or to disable use of service is written here.
     * @exception JposException  This exception is fired toward the failure of
     *                          the method which JavaPOS defines.
     */
    /**When the window was closed*/
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.closing();
        }
        /**When the window open*/
        else if (e.getID() == WindowEvent.WINDOW_OPENED) {
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
            catch(JposException ex) {
            }
        }
        // JavaPOS's code for Step1--END
    }

    //***********************Button*************************************************
    /**
     * Outline      The code for using the most standard method "PrintNormal"
     *             to print is described.
     */
    void jButton_Print_actionPerformed(ActionEvent e) {
        // JavaPOS's code for Step1
        try{
            //printNormal(int station, String data)
            //A string is sent by using the method "printNormal", and it is printed.
            // "\n" is the standard code for starting a new line.
            // When the end of the line have no "\n",printing by
            //  using the method "printNormal" doesn't start, may be.
            ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT,"Hello JavaPOS\n");
        }
        catch(JposException ex){
        }
        // JavaPOS's code for Step1--END
    }

    //***********************Method*************************************************
    /**
     * Outline     The code to finish a service.
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