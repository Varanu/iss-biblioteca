/*
 * Created by JFormDesigner on Mon Apr 05 00:20:43 EEST 2021
 */

package gui;

import Domain.*;
import Service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.table.*;

/**
 * @author unknown
 */
public class bibliotecaGUI extends JFrame implements IBibliotecaObserver, Serializable {
    public bibliotecaGUI(Service service) {
        this.service = service;
        try {
            UnicastRemoteObject.exportObject(this,0);
        } catch (RemoteException e) {
            System.out.println("Error exporting object "+e);
        }
    }


    public boolean login_utilizator(String nume, String pass) throws BibliotecaException, RemoteException  {
        Utilizator answer = service.login_utilizator(nume, pass, this);
        if (answer != null) {
            try {
                currentUtilizator = answer;
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    public void notifyClient(List<Carte> carti) throws RemoteException {
        this.carteList = carti;
        updateCartiTable();
        this.imprumuturiList = service.getImprumuturi(this.currentUtilizator);
        updateImprumuturiTable();
    }


    private void imprumutaButtonActionPerfomed() throws RemoteException, BibliotecaException {
        int[] rows = table1.getSelectedRows();
        if (rows.length == 1  || (rows.length > 1 && textField5.getText().equals(""))){
            for (int row : rows) {
                String titlu = table1.getModel().getValueAt(row, 0).toString();
                String autor = table1.getModel().getValueAt(row, 1).toString();
                String editura = table1.getModel().getValueAt(row, 2).toString();
                int an_aparitie = Integer.parseInt(table1.getModel().getValueAt(row, 3).toString());
                int nr_ex = Integer.parseInt(table1.getModel().getValueAt(row, 4).toString());
                int ex_imprumutate;
                if(!textField5.getText().equals("")) {
                    ex_imprumutate = Integer.parseInt(textField5.getText());
                } else{
                  ex_imprumutate = 1;
                }
                try {
                    service.imprumutaCarte(currentUtilizator.getId_u(), titlu, autor, editura, an_aparitie, nr_ex, ex_imprumutate);
                    textField5.setText("");
                } catch (BibliotecaException remoteException) {
                    JOptionPane.showMessageDialog(null, remoteException.getMessage(), "Eroare imprumut", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nu se poate introduce numarul de exemplare pentru mai multe carti", "Eroare imprumut", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void restituieButtonActionPerfomed() throws RemoteException, BibliotecaException{
        int[] rows = table2.getSelectedRows();
        if (rows.length == 1  || (rows.length > 1 && textField7.getText().equals(""))){
            for (int row : rows) {
                String titlu = table2.getModel().getValueAt(row, 0).toString();
                String autor = table2.getModel().getValueAt(row, 1).toString();
                String data_imprumut = table2.getModel().getValueAt(row, 2).toString();
                String data_restituire = table2.getModel().getValueAt(row, 3).toString();
                int nr_ex = Integer.parseInt(table2.getModel().getValueAt(row, 4).toString());
                int ex_restituite;
                if(!textField7.getText().equals("")) {
                    ex_restituite = Integer.parseInt(textField7.getText());
                } else{
                    ex_restituite = 1;
                }
                try {
                    service.restituieCarte(currentUtilizator.getId_u(), titlu, autor, data_imprumut, data_restituire, nr_ex, ex_restituite);
                    textField7.setText("");
                } catch (BibliotecaException remoteException) {
                    JOptionPane.showMessageDialog(null, remoteException.getMessage(), "Eroare restituire", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nu se poate introduce numarul de exemplare pentru mai multe carti", "Eroare restituire", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logoutButtonActionPerformed() throws RemoteException, BibliotecaException {
        service.logout(currentUtilizator.getUsername());
        dispose();
        Login login = new Login(service);
        login.setVisible(true);
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
            String []columns = new String[]{"Titlu", "Autor", "Editura", "An ap", "Nr ex"};
            Object [][]rows = new Object[carteList.size()][5];
            int i, r = 0;
            for(i = 0; i < carteList.size(); i++) {
                rows[r][0] = carteList.get(i).getTitlu();
                rows[r][1] = carteList.get(i).getAutor();
                rows[r][2] = carteList.get(i).getEditura();
                rows[r][3] = carteList.get(i).getAn_aparitie();
                rows[r][4] = carteList.get(i).getNr_exemplare();
                r++;
            }
            table1.setModel(new DefaultTableModel(rows, columns));
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(0).setPreferredWidth(245);
                cm.getColumn(1).setPreferredWidth(125);
                cm.getColumn(2).setPreferredWidth(155);
                cm.getColumn(3).setPreferredWidth(60);
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
        }
        contentPanel.add(scrollPane1);
        scrollPane1.setBounds(15, 65, 670, 365);
    }

    public void initImprumuturiTable(){
        {
            String []columns = new String[]{"Titlu", "Autor", "Data Impr", "Data Rest", "Nr ex"};
            Object [][]rows = new Object[imprumuturiList.size()][5];
            int i, r = 0;
            for(i = 0; i < imprumuturiList.size(); i++) {
                Carte c = imprumuturiList.get(i).getCarte();
                rows[r][0] = c.getTitlu();
                rows[r][1] = c.getAutor();
                rows[r][2] = imprumuturiList.get(i).getData_imprumut();
                rows[r][3] = imprumuturiList.get(i).getData_restituire();
                rows[r][4] = imprumuturiList.get(i).getNr_exemplare();
                r++;
            }
            table2.setModel(new DefaultTableModel(rows, columns));
            {
                TableColumnModel cm = table2.getColumnModel();
                cm.getColumn(0).setPreferredWidth(245);
                cm.getColumn(1).setPreferredWidth(135);
                cm.getColumn(2).setPreferredWidth(135);
                cm.getColumn(3).setPreferredWidth(135);
            }
            table2.setDefaultEditor(Object.class, null);
            table2.getTableHeader().setFont(new Font("Timew New Roman", Font.BOLD, 14));
            table2.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 12));
            table2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            table2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            table2.setGridColor(Color.LIGHT_GRAY);
            table2.getTableHeader().setOpaque(false);
            table2.setPreferredScrollableViewportSize(new Dimension(4, 4));
            table2.setRowSelectionAllowed(true);
            scrollPane2.setViewportView(table2);
        }
        contentPanel.add(scrollPane2);
        scrollPane2.setBounds(720, 65, 535, 365);
    }

    public void updateImprumuturiTable(){
        contentPanel.remove(table2);
        contentPanel.remove(scrollPane2);
        table2 = new JTable();
        scrollPane2 = new JScrollPane();
        initImprumuturiTable();
    }

    public void initComponents() throws BibliotecaException, RemoteException {
        setTitle("Utilizator - " + currentUtilizator.getUsername());
        this.imprumuturiList = service.getImprumuturi(this.currentUtilizator);
        this.carteList = service.getAllCarti();
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        separator1 = new JSeparator();
        label2 = new JLabel();
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
        separator2 = new JSeparator();
        label8 = new JLabel();
        label9 = new JLabel();
        textField6 = new JTextField();
        textField7 = new JTextField();
        label10 = new JLabel();
        textField8 = new JTextField();
        label12 = new JLabel();
        label13 = new JLabel();
        textField10 = new JTextField();
        button2 = new JButton();
        label11 = new JLabel();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        separator3 = new JSeparator();
        okButton = new JButton();
        buttonBar = new JPanel();

        //======== this ========
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    logoutButtonActionPerformed();
                } catch (RemoteException | BibliotecaException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        });
        setResizable(false);
        setForeground(Color.gray);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(null);

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("Lista carti");
                label1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 24));
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label1);
                label1.setBounds(235, 10, 170, 25);

                //======== scrollPane1 ========
                initCartiTable();
                //---- separator1 ----
                separator1.setBackground(Color.white);
                separator1.setForeground(Color.white);
                contentPanel.add(separator1);
                separator1.setBounds(10, 445, 1325, 10);

                //---- label2 ----
                label2.setText("Imprumuta Carte");
                label2.setHorizontalAlignment(SwingConstants.CENTER);
                label2.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 18));
                contentPanel.add(label2);
                label2.setBounds(130, 475, 220, label2.getPreferredSize().height);



                //---- label7 ----
                label7.setText("Numar exemplare");
                contentPanel.add(label7);
                label7.setBounds(new Rectangle(new Point(25, 565), label7.getPreferredSize()));
                contentPanel.add(textField5);
                textField5.setBounds(165, 560, 140, 30);

                //---- button1 ----
                button1.setText("Imprumuta");
                button1.addActionListener(e -> {
                    try {
                        imprumutaButtonActionPerfomed();
                    } catch (RemoteException | BibliotecaException remoteException) {
                        remoteException.printStackTrace();
                    }
                });
                contentPanel.add(button1);
                button1.setBounds(new Rectangle(new Point(345, 585), button1.getPreferredSize()));

                //---- separator2 ----
                separator2.setOrientation(SwingConstants.VERTICAL);
                separator2.setForeground(Color.white);
                separator2.setBackground(Color.white);
                contentPanel.add(separator2);
                separator2.setBounds(535, 455, 10, 225);

                //---- label8 ----
                label8.setText("Returneaza Carte");
                label8.setHorizontalAlignment(SwingConstants.CENTER);
                label8.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 18));
                contentPanel.add(label8);
                label8.setBounds(790, 475, 220, 22);

                //---- label12 ----
                label12.setText("Numar exemplare");
                contentPanel.add(label12);
                label12.setBounds(575, 565, 140, 30);
                contentPanel.add(textField7);
                textField7.setBounds(715, 560, 125, 30);

                //---- button2 ----
                button2.setText("Returneaza");
                button2.addActionListener(e -> {
                    try {
                        restituieButtonActionPerfomed();
                    } catch (RemoteException | BibliotecaException remoteException) {
                        remoteException.printStackTrace();
                    }
                });
                contentPanel.add(button2);
                button2.setBounds(new Rectangle(new Point(915, 585), button2.getPreferredSize()));

                //---- label11 ----
                label11.setText("Returneaza carti");
                label11.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 24));
                label11.setHorizontalAlignment(SwingConstants.CENTER);
                contentPanel.add(label11);
                label11.setBounds(865, 15, 260, 25);

                //======== scrollPane2 ========
                initImprumuturiTable();

                //---- separator3 ----
                separator3.setOrientation(SwingConstants.VERTICAL);
                separator3.setForeground(Color.white);
                separator3.setBackground(Color.white);
                contentPanel.add(separator3);
                separator3.setBounds(700, 10, 10, 435);
            }
            dialogPane.add(contentPanel);
            contentPanel.setBounds(0, 0, 1275, 620);

            //---- okButton ----
            okButton.setText("Logout");
            okButton.addActionListener(e -> {
                try {
                    logoutButtonActionPerformed();
                } catch (RemoteException | BibliotecaException remoteException) {
                    remoteException.printStackTrace();
                }
            });
            dialogPane.add(okButton);
            okButton.setBounds(new Rectangle(new Point(1150, 620), okButton.getPreferredSize()));

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
        contentPane.add(dialogPane, BorderLayout.NORTH);

        //======== buttonBar ========
        {
            buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
            buttonBar.setForeground(Color.gray);
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
        contentPane.add(buttonBar, BorderLayout.SOUTH);
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
    private JSeparator separator1;
    private JLabel label2;
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
    private JSeparator separator2;
    private JLabel label8;
    private JLabel label9;
    private JTextField textField6;
    private JTextField textField7;
    private JLabel label10;
    private JTextField textField8;
    private JLabel label12;
    private JLabel label13;
    private JTextField textField10;
    private JButton button2;
    private JLabel label11;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JSeparator separator3;
    private JButton okButton;
    private JPanel buttonBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private Utilizator currentUtilizator;
    private Service service;

    List<Carte> carteList;
    List<Imprumuturi> imprumuturiList;
}
