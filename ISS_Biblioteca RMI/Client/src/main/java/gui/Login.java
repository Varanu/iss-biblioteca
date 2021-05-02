package gui;

import Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class Login extends JFrame {

    private JButton btnExit  = new JButton("Login");
    private JButton btnRegister = new JButton("Register");

    private JLabel lbl1 = new JLabel("User");
    private JLabel lbl2 = new JLabel("Password");

    private JTextField txt1 = new JTextField();
    private JPasswordField pass2 = new JPasswordField();

    private Service service;

    public Login(Service service) {
        this.service = service;
        setTitle("Login");
        setSize(242, 258);
        setLocation(new Point(300, 200));
        getContentPane().setLayout(null);
        setResizable(false);

        initComponent();
        initEvent();
    }
    private void initComponent(){
        btnExit.setBounds(156, 205, 80, 25);
        btnRegister.setBounds(10, 205, 80, 25);

        txt1.setBounds(85, 87, 139, 25);
        pass2.setBounds(84, 131, 139, 25);

        lbl1.setBounds(20, 90, 53, 20);
        lbl2.setBounds(20, 134, 59, 20);

        getContentPane().add(btnExit);
        getContentPane().add(btnRegister);
        getContentPane().add(txt1);
        getContentPane().add(lbl1);
        getContentPane().add(lbl2);
        getContentPane().add(pass2);
    }
    private void initEvent() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });

        btnExit.addActionListener(this::btnLoginClick);
        btnRegister.addActionListener(this::btnRegisterClick);

    }
    private void btnLoginClick(ActionEvent evt){
        int ok = 0, ok1 = 0;
        try{
            admin admin = new admin(service);
            if(admin.login(txt1.getText(), String.valueOf(pass2.getPassword()))) {
                admin.initComponents();
                admin.setVisible(true);
                dispose();
            }
        } catch (RemoteException | BibliotecaException remoteException) {
            bibliotecaGUI bibliotecaGUI = new bibliotecaGUI(service);
            try {
                if (bibliotecaGUI.login_utilizator(txt1.getText(), String.valueOf(pass2.getPassword()))) {
                    bibliotecaGUI.initComponents();
                    bibliotecaGUI.setVisible(true);
                    dispose();
                    ok = 1;
                }
            }
            catch (RemoteException | BibliotecaException remoteException2){
                if(!remoteException.getMessage().equals("User already logged in.")) {
                    ok1 = 1;
                    txt1.setText("");
                    pass2.setText("");
                    JOptionPane.showMessageDialog(null, remoteException2.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(ok == 0 && ok1 == 0) {
                txt1.setText("");
                pass2.setText("");
                JOptionPane.showMessageDialog(null, remoteException.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnRegisterClick(ActionEvent evt){
        Register re = new Register(service);
        re.setVisible(true);
    }
}
