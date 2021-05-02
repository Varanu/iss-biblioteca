/*
 * Created by JFormDesigner on Sun Apr 04 23:47:03 EEST 2021
 */

package gui;
import Domain.*;
import Service.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;


/**
 * @author unknown
 */
public class Register extends JFrame {
    public Register(Service service) {
        initComponents();
        this.service = service;
    }

    private void registerButtonActionPerformed(ActionEvent e) throws BibliotecaException, RemoteException {
        if(service.registerUtilizator(textField5.getText(), String.valueOf(textField6.getPassword()), textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText()) != null) {
            JOptionPane.showMessageDialog(null, "Utilizator inregistrat", "Register Successully", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
       else{
           textField5.setText("");
           JOptionPane.showMessageDialog(null, "Username existent", "Register Error", JOptionPane.ERROR_MESSAGE);
       }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        label5 = new JLabel();
        textField3 = new JTextField();
        buttonBar = new JPanel();
        textField4 = new JTextField();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        textField5 = new JTextField();
        //textField6 = new JTextField();
        okButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== dialogPane ========
        {

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int x = (screenSize.width - getWidth()) / 2;
            int y = (screenSize.height - getHeight()) / 2;

            //Set the new frame location
            dialogPane.setLocation(x, y);
            dialogPane.setLayout(null);

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("Nume");
                contentPanel.add(label1);
                label1.setBounds(35, 205, 65, 30);

                //---- label2 ----
                label2.setText("Prenume");
                contentPanel.add(label2);
                label2.setBounds(new Rectangle(new Point(30, 255), label2.getPreferredSize()));

                //---- label3 ----
                label3.setText("Adresa");
                contentPanel.add(label3);
                label3.setBounds(new Rectangle(new Point(35, 300), label3.getPreferredSize()));

                //---- label4 ----
                label4.setText("CNP");
                contentPanel.add(label4);
                label4.setBounds(45, 330, label4.getPreferredSize().width, 40);
                contentPanel.add(textField1);
                textField1.setBounds(115, 200, 175, 30);
                contentPanel.add(textField2);
                textField2.setBounds(115, 250, 175, 30);

                //---- label5 ----
                label5.setText("Register");
                label5.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 23));
                label5.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label5);
                label5.setBounds(70, 10, 200, 80);
                contentPanel.add(textField3);
                textField3.setBounds(115, 295, 175, 30);

                //======== buttonBar ========
                {
                    buttonBar.setLayout(null);

                    {
                        // compute preferred size
                        Dimension preferredSize = new Dimension();
                        for(int i = 0; i < buttonBar.getComponentCount(); i++) {
                            Rectangle bounds = buttonBar.getComponent(i).getBounds();
                            preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                            preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                        }
                        Insets insets = buttonBar.getInsets();
                        preferredSize.width += insets.right;
                        preferredSize.height += insets.bottom;
                        buttonBar.setMinimumSize(preferredSize);
                        buttonBar.setPreferredSize(preferredSize);
                    }
                }
                contentPanel.add(buttonBar);
                buttonBar.setBounds(0, 400, 493, buttonBar.getPreferredSize().height);
                contentPanel.add(textField4);
                textField4.setBounds(115, 340, 175, 30);
                contentPanel.add(label6);
                label6.setBounds(new Rectangle(new Point(75, 355), label6.getPreferredSize()));

                //---- label7 ----
                label7.setText("Username");
                contentPanel.add(label7);
                label7.setBounds(new Rectangle(new Point(35, 110), label7.getPreferredSize()));

                //---- label8 ----
                label8.setText("Parola");
                contentPanel.add(label8);
                label8.setBounds(new Rectangle(new Point(35, 140), label8.getPreferredSize()));
                contentPanel.add(textField5);
                textField5.setBounds(115, 100, 170, 30);
                contentPanel.add(textField6);
                textField6.setBounds(115, 135, 170, 30);

                //---- okButton ----
                okButton.setText("Register");
                okButton.addActionListener(e -> {
                    try {
                        registerButtonActionPerformed(e);
                    } catch (BibliotecaException | RemoteException bibliotecaException) {
                        bibliotecaException.printStackTrace();
                    }
                });
                contentPanel.add(okButton);
                okButton.setBounds(new Rectangle(new Point(255, 380), okButton.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel);
            contentPanel.setBounds(0, 0, 380, contentPanel.getPreferredSize().height);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < dialogPane.getComponentCount(); i++) {
                    Rectangle bounds = dialogPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = dialogPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                dialogPane.setMinimumSize(preferredSize);
                dialogPane.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(dialogPane);
        dialogPane.setBounds(0, 0, 380, dialogPane.getPreferredSize().height);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label5;
    private JTextField textField3;
    private JPanel buttonBar;
    private JTextField textField4;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JTextField textField5;
    //private JTextField textField6;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private JPasswordField textField6 = new JPasswordField();
    private Service service;
}
