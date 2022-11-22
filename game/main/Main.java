/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.main;

import game.compoment.PanelGame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main extends JFrame{
    public Main()
    {
        init();
    }    
    private void init(){
        setTitle("code by duan");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        PanelGame panelgame = new PanelGame();
        add(panelgame);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panelgame.start();
            }  
        });
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
