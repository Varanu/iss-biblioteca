package gui;

import Domain.*;
import Service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
/*
public class InscrieriGUI extends JFrame implements IBibliotecaObserver, Serializable {
    private JButton btnCauta  = new JButton("Cauta");
    private JButton btnInscrie = new JButton("Inscrie");
    private JButton btnLogout = new JButton("Logout");

    private JLabel lbl1 = new JLabel("Probe Curente");
    private JLabel lbl2 = new JLabel("Distanta");
    private JLabel lbl3 = new JLabel("Stil");
    private JLabel lbl4 = new JLabel("Cauta Participanti");
    private JLabel lbl5 = new JLabel("Inscrie Participant");
    private JLabel lbl6 = new JLabel("Nume");
    private JLabel lbl7 = new JLabel("Prenume");
    private JLabel lbl8 = new JLabel("Varsta");
    private JLabel lbl9 = new JLabel("Distanta");
    private JLabel lbl10 = new JLabel("Stil");

    private JTextField txt1 = new JTextField();
    private JTextField txt2 = new JTextField();
    private JTextField txt3 = new JTextField();
    private JTextField txt4 = new JTextField();
    private JTextField txt5 = new JTextField();
    private JTextField txt6 = new JTextField();
    private JTextField txt7 = new JTextField();

    private JScrollPane spane = new JScrollPane();
    private JTable table;

    private JScrollPane spane2 = new JScrollPane();
    private JTable table2;

    private JSeparator separator = new JSeparator();
    private JSeparator separator2 = new JSeparator();
    private JSeparator separator3 = new JSeparator();

    private Angajat currentUser;
    private Service service;

    List<Proba> probadto;

    public InscrieriGUI(Service service) {
        this.service = service;
        try {
            UnicastRemoteObject.exportObject(this,0);
        } catch (RemoteException e) {
            System.out.println("Error exporting object "+e);
        }
    }

    public void initComponent() throws ConcursException, RemoteException {
        setTitle("Concurs Inot - " + currentUser.getUser());
        setSize(1000, 507);
        setLocation(new Point(300, 50));

        this.probadto = service.getAllProbe();

        getContentPane().setLayout(null);
        setResizable(false);

        btnCauta.setBounds(785, 434, 80, 25);
        btnInscrie.setBounds(417, 434, 80, 25);
        btnLogout.setBounds(900, 434, 80, 25);

        txt1.setBounds(765, 353, 100, 25);
        txt2.setBounds(765, 386, 100, 25);
        txt3.setBounds(320, 332, 100, 25);
        txt4.setBounds(320, 362, 100, 25);
        txt5.setBounds(320, 392, 100, 25);
        txt6.setBounds(523, 332, 100, 25);
        txt7.setBounds(523, 362, 100, 25);

        lbl1.setBounds(6, 6, 127, 20);
        lbl2.setBounds(706, 356, 59, 20);
        lbl3.setBounds(706, 389, 59, 20);
        lbl4.setBounds(753, 289, 140, 31);
        lbl5.setBounds(353, 289, 140, 31);
        lbl6.setBounds(258, 332, 59, 20);
        lbl7.setBounds(258, 362, 59, 20);
        lbl8.setBounds(258, 392, 59, 20);
        lbl9.setBounds(461, 332, 59, 20);
        lbl10.setBounds(461, 362, 59, 20);

        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(234, 6, 12, 462);
        separator2.setOrientation(SwingConstants.HORIZONTAL);
        separator2.setBounds(247, 277, 747, 12);
        separator3.setOrientation(SwingConstants.VERTICAL);
        separator3.setBounds(649, 289, 12, 179);
        getContentPane().setForeground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().add(btnCauta);
        getContentPane().add(btnInscrie);
        getContentPane().add(btnLogout);
        getContentPane().add(txt1);
        getContentPane().add(txt2);
        getContentPane().add(txt3);
        getContentPane().add(txt4);
        getContentPane().add(txt5);
        getContentPane().add(txt6);
        getContentPane().add(txt7);
        getContentPane().add(lbl1);
        getContentPane().add(lbl2);
        getContentPane().add(lbl3);
        getContentPane().add(lbl4);
        getContentPane().add(lbl5);
        getContentPane().add(lbl6);
        getContentPane().add(lbl7);
        getContentPane().add(lbl8);
        getContentPane().add(lbl9);
        getContentPane().add(lbl10);
        getContentPane().add(btnCauta);
        getContentPane().add(separator);
        getContentPane().add(separator2);
        getContentPane().add(separator3);

        initEvent();
        initProbeTable();
        initParticipantiTable();

    }

    public void updateProbeTable(){
        getContentPane().remove(table);
        getContentPane().remove(spane);

        String []columns = new String[]{"Distanta", "Stil", "Participanti"};
        Object [][]rows = new Object[probadto.size()][3];
        int i, r = 0;
        for(i = 0; i < probadto.size(); i++) {
            rows[r][0] = probadto.get(i).getDistanta();
            rows[r][1] = probadto.get(i).getStil();
            rows[r][2] = probadto.get(i).getNr_part();
            r++;
        }

        table = new JTable(rows, columns);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table.setBounds(0, 0, 229, 300);
        table.setBorder(null);
        table.setRowHeight(20);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Times New Roman", Font.BOLD, 16));
        table.setGridColor(Color.LIGHT_GRAY);
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(45);
        table.getColumnModel().getColumn(2).setPreferredWidth(95);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        spane = new JScrollPane(table);
        spane.setBorder(new EmptyBorder(0, 0, 0, 0));
        getContentPane().setLayout(null);
        spane.setBounds(6, 32, 229, 300);
        getContentPane().add(spane);
    }
    public void initProbeTable(){
    String []columns = new String[]{"Distanta", "Stil", "Participanti"};
    Object [][]rows = new Object[probadto.size()][3];
    int i, r = 0;
    for(i = 0; i < probadto.size(); i++) {
        rows[r][0] = probadto.get(i).getDistanta();
        rows[r][1] = probadto.get(i).getStil();
        rows[r][2] = probadto.get(i).getNr_part();
        r++;
    }

    table = new JTable(rows, columns);
    table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
    table.setBounds(0, 0, 229, 300);
    table.setBorder(null);
    table.setRowHeight(20);
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Times New Roman", Font.BOLD, 16));
    table.setGridColor(Color.LIGHT_GRAY);
    table.getColumnModel().getColumn(0).setPreferredWidth(70);
    table.getColumnModel().getColumn(1).setPreferredWidth(45);
    table.getColumnModel().getColumn(2).setPreferredWidth(95);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    spane = new JScrollPane(table);
    spane.setBorder(new EmptyBorder(0, 0, 0, 0));
    getContentPane().setLayout(null);
    spane.setBounds(6, 32, 229, 300);
    getContentPane().add(spane);
}
    private void initParticipantiTable(){
        String []columns = new String[]{"Nume", "Prenume", "Varsta", "Inscris la probele"};
        Object [][]rows = new Object[0][4];
        table2 = new JTable(rows, columns);
        table2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table2.setBounds(0, 0, 800, 200);
        table2.setBorder(null);
        table2.setRowHeight(20);
        JTableHeader header2 = table2.getTableHeader();
        header2.setFont(new Font("Times New Roman", Font.BOLD, 16));
        table2.setGridColor(Color.LIGHT_GRAY);
        table2.getColumnModel().getColumn(0).setPreferredWidth(85);
        table2.getColumnModel().getColumn(1).setPreferredWidth(85);
        table2.getColumnModel().getColumn(2).setPreferredWidth(75);
        table2.getColumnModel().getColumn(3).setPreferredWidth(500);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        spane2 = new JScrollPane(table2);
        spane2.setBorder(new EmptyBorder(0, 0, 0, 0));
        getContentPane().setLayout(null);
        spane2.setBounds(247, 9, 800, 200);
        getContentPane().add(spane2);
    }
    private void updateParticipantiTable(int row, List<Participant> participants){
        String []columns = new String[]{"Nume", "Prenume", "Varsta", "Inscris la probele"};
        Object [][]rows = new Object[row][4];
        int i, r = 0, j;
        for(i = 0; i < row; i++) {
            rows[r][0] = participants.get(i).getNume();
            rows[r][1] = participants.get(i).getPrenume();
            rows[r][2] = participants.get(i).getVarsta();
            List<Proba> pr = participants.get(i).getProbe();
            String li = "";
            for(j = 0; j < pr.size(); j++){
                if(j == pr.size() - 1)
                    li = li + pr.get(j).getDistanta() + " - " +  pr.get(j).getStil();
                else
                    li = li + pr.get(j).getDistanta() + " - " +  pr.get(j).getStil() + ", ";
            }
            rows[r][3] = li;
            r++;
        }
        table2 = new JTable(rows, columns);
        table2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table2.setBounds(0, 0, 800, 250);
        table2.setBorder(null);
        table2.setRowHeight(20);
        JTableHeader header2 = table2.getTableHeader();
        header2.setFont(new Font("Times New Roman", Font.BOLD, 16));
        table2.setGridColor(Color.LIGHT_GRAY);
        table2.getColumnModel().getColumn(0).setPreferredWidth(85);
        table2.getColumnModel().getColumn(1).setPreferredWidth(85);
        table2.getColumnModel().getColumn(2).setPreferredWidth(75);
        table2.getColumnModel().getColumn(3).setPreferredWidth(500);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        spane2 = new JScrollPane(table2);
        spane2.setBorder(new EmptyBorder(0, 0, 0, 0));
        getContentPane().setLayout(null);
        spane2.setBounds(247, 9, 800, 250);
        getContentPane().add(spane2);
    }
    private void initEvent() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });

        btnCauta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    btnCautaClick(e);
                } catch (ConcursException concursException) {
                    concursException.printStackTrace();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        });

        btnInscrie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    btnInscrieClick(e);
                } catch (ConcursException concursException) {
                    concursException.printStackTrace();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    btnLogoutClick(e);
                } catch (ConcursException concursException) {
                    concursException.printStackTrace();
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }
            }
        });
    }

    private void btnCautaClick(ActionEvent evt) throws ConcursException, RemoteException {
        if(true){
            List<Participant> participantDTOS = service.getAllParticipanti(Integer.valueOf(txt1.getText()), txt2.getText());
            updateParticipantiTable(participantDTOS.size(), participantDTOS);
        }
        else{
            txt1.setText("");
            txt2.setText("");
            JOptionPane.showMessageDialog(null, "Proba nu exista", "Validate Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnInscrieClick(ActionEvent evt) throws ConcursException, RemoteException {
        if(true){
            String nume = txt3.getText(), prenume = txt4.getText(), stil = txt7.getText();
            int varsta = Integer.valueOf(txt5.getText()), distanta = Integer.valueOf(txt6.getText());
            service.inscrieParticipant(nume, prenume, varsta, distanta, stil);
            //updateProbeTable();
        }
        else{
            txt6.setText("");
            txt7.setText("");
            JOptionPane.showMessageDialog(null, "Proba nu exista", "Validate Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnLogoutClick(ActionEvent evt) throws ConcursException, RemoteException {
        service.logout(currentUser.getId_a());
        System.exit(1);
    }

    public boolean login(String nume, String pass) throws ConcursException, RemoteException  {
        Angajat answer = service.login(nume, pass, this);
        if (answer != null) {
            try {
                currentUser = answer;
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    public void notifyClient(List<Proba> proba) {
        this.probadto = proba;
        updateProbeTable();
    }
}
*/