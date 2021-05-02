/*
 * Created by JFormDesigner on Mon Apr 05 23:54:55 EEST 2021
 */

package gui;

import java.awt.event.*;
import Domain.Angajat;
import Domain.Carte;
import Service.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class admin extends JFrame implements IBibliotecaObserver, Serializable {
    public admin(Service service) {
        this.service = service;
        try {
            UnicastRemoteObject.exportObject(this,0);
        } catch (RemoteException e) {
            System.out.println("Error exporting object "+e);
        }
    }

    public boolean login(String nume, String pass) throws BibliotecaException, RemoteException {
        Angajat answer = service.login(nume, pass, this);
        System.out.println(answer);
        if (answer != null) {
            try {
                currentAngajat = answer;
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }
    
    public void notifyClient(List<Carte> carti) {
        this.carteList = carti;
        updateCartiTable();
    }

    private void clearTextFileds(){
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
    }

    private void logoutButtonActionPerformed(){
        try {
            service.logout(currentAngajat.getUser());
        }catch (RemoteException | BibliotecaException remoteException) {
            remoteException.printStackTrace();
        }
        dispose();
        Login login = new Login(service);
        login.setVisible(true);
    }

    private void adaugaButtonActionPerformed(){
        String titlu = textField1.getText(),
            autor = textField2.getText(),
            editura = textField3.getText(),
            an_ap = textField4.getText(),
            nr_ex = textField5.getText(),
            id = textField6.getText();
        int an_aparitie = Integer.parseInt(an_ap),
                nr_exemplare = Integer.parseInt(nr_ex),
                id_c = Integer.parseInt(id);
        try {
            service.adaugaCarte(id_c, titlu, autor, editura, an_aparitie, nr_exemplare);
            clearTextFileds();
            JOptionPane.showMessageDialog(null, "Carte adaugata", "Added Successully", JOptionPane.INFORMATION_MESSAGE);
        }catch (RemoteException | BibliotecaException remoteException) {
            JOptionPane.showMessageDialog(null, remoteException.getMessage(), "Eroare adaugare", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void modificaButtonActionPerformed(){
        int[] rows = table1.getSelectedRows();
        if(rows.length == 1) {
            String titlu = textField1.getText(),
                    autor = textField2.getText(),
                    editura = textField3.getText(),
                    an_ap = textField4.getText(),
                    nr_ex = textField5.getText();
            int an_aparitie = Integer.parseInt(an_ap),
                    nr_exemplare = Integer.parseInt(nr_ex);
            for (int row : rows) {
                int id_curent = Integer.parseInt(table1.getModel().getValueAt(row, 0).toString());
                try {
                    service.modificaCarte(id_curent, titlu, autor, editura, an_aparitie, nr_exemplare);
                    clearTextFileds();
                    JOptionPane.showMessageDialog(null, "Carte modificata", "Modified Successully", JOptionPane.INFORMATION_MESSAGE);
                } catch (BibliotecaException | RemoteException remoteException) {
                    JOptionPane.showMessageDialog(null, remoteException.getMessage(), "Eroare modificare", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Se poate modifica doar o singura carte", "Eroare modificare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stergeButtonActionPerformed() {
        int[] rows = table1.getSelectedRows();
        int ok = 0;
        for (int row : rows) {
            int id_c = Integer.parseInt(table1.getModel().getValueAt(row, 0).toString());
            try {
                service.stergeCarte(id_c);
            } catch (BibliotecaException | RemoteException remoteException) {
                ok = 1;
                JOptionPane.showMessageDialog(null, remoteException.getMessage(), "Eroare stergere", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(ok == 0){
            JOptionPane.showMessageDialog(null, "Carte/Carti sterse", "Deleted Successully", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        logoutButtonActionPerformed();
    }

    public void updateCartiTable(){
        contentPanel.remove(table1);
        contentPanel.remove(scrollPane1);
        table1 = new JTable();
        scrollPane1 = new JScrollPane();
        initCartiTable();
    }

    public void initCartiTable(){
        {
            String []columns = new String[]{"ID", "Titlu", "Autor", "Editura", "An ap", "Nr ex"};
            Object [][]rows = new Object[carteList.size()][6];
            int i, r = 0;
            for(i = 0; i < carteList.size(); i++) {
                rows[r][0] = carteList.get(i).getId_c();
                rows[r][1] = carteList.get(i).getTitlu();
                rows[r][2] = carteList.get(i).getAutor();
                rows[r][3] = carteList.get(i).getEditura();
                rows[r][4] = carteList.get(i).getAn_aparitie();
                rows[r][5] = carteList.get(i).getNr_exemplare();
                r++;
            }
            table1.setModel(new DefaultTableModel(rows, columns));
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(1).setPreferredWidth(245);
                cm.getColumn(2).setPreferredWidth(125);
                cm.getColumn(3).setPreferredWidth(155);
                cm.getColumn(4).setPreferredWidth(60);
            }
            table1.setDefaultEditor(Object.class, null);
            table1.getTableHeader().setFont(new Font("Timew New Roman", Font.BOLD, 14));
            table1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 12));
            table1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            table1.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            table1.setGridColor(Color.LIGHT_GRAY);
            table1.getTableHeader().setOpaque(false);
            table1.setPreferredScrollableViewportSize(new Dimension(4, 4));
            table1.setRowSelectionAllowed(true);
            scrollPane1.setViewportView(table1);
            table1.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int[] rows = table1.getSelectedRows();
                    if(rows.length == 1) {
                        for (int row : rows) {
                            textField1.setText(table1.getModel().getValueAt(row, 1).toString());
                            textField2.setText(table1.getModel().getValueAt(row, 2).toString());
                            textField3.setText(table1.getModel().getValueAt(row, 3).toString());
                            textField4.setText(table1.getModel().getValueAt(row, 4).toString());
                            textField5.setText(table1.getModel().getValueAt(row, 5).toString());
                            textField6.setText(table1.getModel().getValueAt(row, 0).toString());
                        }
                    }
                }
            });
        }
        contentPanel.add(scrollPane1);
        scrollPane1.setBounds(25, 55, 915, 370);
    }

    public void initComponents() throws BibliotecaException, RemoteException {
        setTitle("Angajat - " + currentAngajat.getUser());
        this.carteList = service.getAllCarti();
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        label3 = new JLabel();
        textField1 = new JTextField();
        label4 = new JLabel();
        textField2 = new JTextField();
        label5 = new JLabel();
        label6 = new JLabel();
        textField3 = new JTextField();
        textField4 = new JTextField();
        label7 = new JLabel();
        textField5 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        label2 = new JLabel();
        textField6 = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("Panou Administrator");
                label1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 24));
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label1);
                label1.setBounds(295, 0, 325, 25);

                //======== scrollPane1 ========
                initCartiTable();

                //---- label3 ----
                label3.setText("Titlu");
                contentPanel.add(label3);
                label3.setBounds(30, 435, 29, 16);
                contentPanel.add(textField1);
                textField1.setBounds(75, 435, 125, 25);

                //---- label4 ----
                label4.setText("Autor");
                contentPanel.add(label4);
                label4.setBounds(25, 465, 35, 16);
                contentPanel.add(textField2);
                textField2.setBounds(75, 465, 125, 25);

                //---- label5 ----
                label5.setText("Editura");
                contentPanel.add(label5);
                label5.setBounds(260, 435, 44, 16);

                //---- label6 ----
                label6.setText("An aparitie");
                contentPanel.add(label6);
                label6.setBounds(260, 470, 68, 16);
                contentPanel.add(textField3);
                textField3.setBounds(355, 435, 135, 25);
                contentPanel.add(textField4);
                textField4.setBounds(355, 470, 135, 25);

                //---- label7 ----
                label7.setText("Numar exemplare");
                contentPanel.add(label7);
                label7.setBounds(215, 510, 111, 16);
                contentPanel.add(textField5);
                textField5.setBounds(355, 505, 140, 30);

                //---- button1 ----
                button1.setText("Adauga Carte");
                contentPanel.add(button1);
                button1.addActionListener(e -> adaugaButtonActionPerformed());
                button1.setBounds(575, 480, 105, 35);

                //---- button2 ----
                button2.setText("Modifica Carte");
                contentPanel.add(button2);
                button2.addActionListener(e -> modificaButtonActionPerformed());
                button2.setBounds(690, 480, 105, 35);

                //---- button3 ----
                button3.setText("Sterge Carte");
                contentPanel.add(button3);
                button3.addActionListener(e -> stergeButtonActionPerformed());
                button3.setBounds(810, 480, 105, 35);

                //---- label2 ----
                label2.setText("ID");
                contentPanel.add(label2);
                label2.setBounds(45, 505, 20, label2.getPreferredSize().height);
                contentPanel.add(textField6);
                textField6.setBounds(75, 500, 125, textField6.getPreferredSize().height);

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
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("Logout");
                okButton.addActionListener(e -> logoutButtonActionPerformed());
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JLabel label3;
    private JTextField textField1;
    private JLabel label4;
    private JTextField textField2;
    private JLabel label5;
    private JLabel label6;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel label7;
    private JTextField textField5;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label2;
    private JTextField textField6;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    List<Carte> carteList;
    private Angajat currentAngajat;
    private Service service;
}
