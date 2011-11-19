
package com.googlecode.mipnp.view.statusicon;

import com.googlecode.mipnp.controller.MainController;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Pierre-Luc Plourde <pierrelucplourde2@gmail.com>
 */
public class ServerStatus {

    private MainController controller;
    JPopupMenu menu;
    JXTrayIcon sysTrayIcon;
    
    public ServerStatus(MainController controller, Image img){
        this(controller, img, null);
    }
    
    public ServerStatus(MainController controller, Image img,String txt){
        this.controller = controller;
        this.menu = new JPopupMenu("Menu");
        this.sysTrayIcon = new JXTrayIcon(img);
        if(txt != null){
            sysTrayIcon.setToolTip(txt);
        }
        JMenuItem mi_addFolder = new JMenuItem("Configure"); //TODO: Or config
        JMenuItem mi_start = new JMenuItem("Start");
        JMenuItem mi_stop = new JMenuItem("Stop");
        JMenuItem mi_exit = new JMenuItem("Exit");
        
        mi_addFolder.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //TODO: Display something maybe just the pref window or a special one.
                ServerStatus.this.controller.displayConfiguration();
            }  
        });
        
        mi_start.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    ServerStatus.this.controller.restart();
                } catch (IOException ex) {
                    Logger.getLogger(ServerStatus.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerStatus.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        mi_stop.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    ServerStatus.this.controller.stop();
                } catch (IOException ex) {
                    Logger.getLogger(ServerStatus.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerStatus.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        mi_exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ServerStatus.this.controller.exit();
            }
        });
        
        
        menu.add(mi_addFolder);
        menu.add(mi_start);
        menu.add(mi_stop);
        menu.addSeparator();
        menu.add(mi_exit);
        
        sysTrayIcon.setJPopupMenu(menu);
    }
    
    public void AddInTray(){
        if(SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            
            try {
                tray.add(sysTrayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        }
    }
    
}
