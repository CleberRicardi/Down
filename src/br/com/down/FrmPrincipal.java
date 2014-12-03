/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.down;



import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Cleber
 */
public class FrmPrincipal extends FrmAncestor {

    
    NewDownloadPanel ndp = new NewDownloadPanel(this);
    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        internalPane.setLayout( new BoxLayout(internalPane, BoxLayout.PAGE_AXIS)); 
        internalPane.add(ndp);
        this.setContentPane(this.getContentPane());
    }
    
    public void recoverDownloads() throws IOException, Exception{
        File dir = new File(Down.getSystemPath()+"\\fileSaver");
  
        File files[] = dir.listFiles();
        

        ndp.setVisible(!(files.length > 0));

        
        for(File file : files){
            FileSaver fs = new FileSaver(file);
            addDownload(fs);
        }
    }
    
    public boolean addDownload(FileSaver fileSaver) throws Exception{       
         try{   
            if (fileSaver.getStringUrl()!=null){
                    internalPane.setLayout( new BoxLayout(internalPane, BoxLayout.PAGE_AXIS)); 
                    DownloadPanel dp = new DownloadPanel();
                    dp.setFileSaver(fileSaver);
                    dp.setDownload(new Download(fileSaver.getStringUrl(),dp,fileSaver.getDownloadFileName()));
                    fileSaver.save();
                    dp.start();
                    internalPane.add(dp);
            }
        }catch(MalformedURLException mue){
           JOptionPane.showMessageDialog(this,"URL Inválida!",Down.getSystemName(),JOptionPane.ERROR_MESSAGE);
           fileSaver.delete();
           return false;
        }catch(Exception exception){
           exception.printStackTrace();
        }
        return true;
    }
        
    public boolean addDownload(String urlFile) throws Exception{
        FileSaver fs = new FileSaver(urlFile);        
        return addDownload(fs);
    }
    
    public void showNewDownloadPanel(){
        ndp.setVisible(true);
    }
        
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        scrollPane = new javax.swing.JScrollPane();
        internalPane = new javax.swing.JPanel();
        mPrincipal = new javax.swing.JMenuBar();
        arquivo = new javax.swing.JMenu();
        addFile = new javax.swing.JMenuItem();
        config = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        scrollPane.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setToolTipText("");

        internalPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        internalPane.setForeground(new java.awt.Color(204, 204, 255));
        internalPane.setPreferredSize(new java.awt.Dimension(450, 593));

        javax.swing.GroupLayout internalPaneLayout = new javax.swing.GroupLayout(internalPane);
        internalPane.setLayout(internalPaneLayout);
        internalPaneLayout.setHorizontalGroup(
            internalPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 488, Short.MAX_VALUE)
        );
        internalPaneLayout.setVerticalGroup(
            internalPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(internalPane);

        arquivo.setText("Arquivo      ");

        addFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuFile.png"))); // NOI18N
        addFile.setText("Adicionar arquivo               ");
        addFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFileActionPerformed(evt);
            }
        });
        arquivo.add(addFile);

        config.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuConfig.png"))); // NOI18N
        config.setText("Configurações");
        config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configActionPerformed(evt);
            }
        });
        arquivo.add(config);

        mPrincipal.add(arquivo);

        setJMenuBar(mPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFileActionPerformed
       showNewDownloadPanel();
    }//GEN-LAST:event_addFileActionPerformed

    private void configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configActionPerformed
        FrmConfig frmConfig = new FrmConfig();
        frmConfig.setVisible(true);
    }//GEN-LAST:event_configActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            recoverDownloads();
        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addFile;
    private javax.swing.JMenu arquivo;
    private javax.swing.JMenuItem config;
    private javax.swing.JPanel internalPane;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar mPrincipal;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables


    @Override
    public void dispose() {
        if( JOptionPane.showConfirmDialog(this, " deseja Sair?",Down.getSystemName(), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ){
            // checkExit = true;
        }
    }
    
    
    

    
}
