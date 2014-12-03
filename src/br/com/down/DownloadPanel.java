/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.down;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import sun.security.util.Length;

/**
 *
 * @author Cleber
 */
public class DownloadPanel extends javax.swing.JPanel {
    private Download download;
    private boolean downloadConclued = false;
    private boolean downloadFailed = false;
    private FileSaver fileSaver;
    /**
     * Creates new form DownloadPanel
     */
    public DownloadPanel() {
        initComponents();
    }
    
    public void setDownload(Download download){
        this.download = download;
    }
    
    public Download getDownload(){
        return this.download;
    }
    
    public boolean isDownloadConclued(){
        return this.downloadConclued;
    }
    
    public boolean isDownloadFailed(){
        return this.downloadFailed;
    }
    
    public void setDownloadFailed(boolean downloadFailed){
        this.downloadFailed = downloadFailed;
    }
    
    public void setDownloadConclued(boolean donwloadConclued){
        this.downloadConclued = donwloadConclued;
    }
    
    public void setMessage(String message){
        txtMessage.setText(message);
    }
    
    public void setTxtFileName(String fileName){
        txtFile.setText(fileName);
    }
    
    public String getTxtFileName(){
        return txtFile.getText();
    }
    
    public void setFileSaver(FileSaver fileSaver){
        this.fileSaver = fileSaver;
    }
    
    public FileSaver getFileSaver(){
        return this.fileSaver;
    }
    
    public void setProgressValue(int value){
        downProgress.setValue(value);
        downProgress.setToolTipText(String.valueOf(value)+"%");
    }
    
    public String getMessage(){
        return txtFile.getText();
    }
    
    
    public int getProgressValue(){
        return downProgress.getValue();
    }
    
    
    public void saveFileName(String fileName) throws IOException, InterruptedException{
        getFileSaver().setDownloadFileName(fileName);
        getFileSaver().save();
    }
    
    public void cancelDownload() throws InterruptedException{
        if(!isDownloadConclued() && !isDownloadFailed()){
            if( JOptionPane.showConfirmDialog(this, "Download em andamento, deseja cancelar?",Down.getSystemName(), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ){
                download.setStopDownload(true);               
                download =  null;
            }else{
                return;
            }
        }
        
        if(isDownloadFailed()){
            File file = new File(download.getAbsoluteFilePath());
            download =  null;
            file.delete();
        }
        
        conclude();
        this.setVisible(false);
        
    }
    
    public void start() throws IOException, InterruptedException{
        if(isDownloadConclued()){
            setDownload(new Download(getDownload().getStringUrl(), this));
        }else if(isDownloadFailed()){
            setDownload(new Download(getDownload().getStringUrl(),this, getDownload().getFileName()));
        }
        play();
        setProgressValue(0);
        getDownload().start();
        setDownloadConclued(false);
        setDownloadFailed(false);
    }
    
    public void conclude(){
        pausePlay.setText("Reiniciar");
        pausePlay.setIcon(new ImageIcon(getClass().getResource("images\\menuRestart.png")));
        setMessage("Download Concluído!");
        this.setBackground(new Color(142, 240, 240));
        lbIcon.setIcon(new ImageIcon(getClass().getResource("images\\conclude.png")));
        setDownloadConclued(true);       
        getFileSaver().delete();
    }
    
    public void pause(){
        getDownload().setPaused(true);
        pausePlay.setText("Continuar");
        this.setBackground(new Color(255, 252, 128));
        pausePlay.setIcon(new ImageIcon(getClass().getResource("images\\menuPlay.png")));
        lbIcon.setIcon(new ImageIcon(getClass().getResource("images\\Pause.png")));
    }
    
    public void play(){
        getDownload().setPaused(false);
        pausePlay.setText("Pausar");
        pausePlay.setIcon(new ImageIcon(getClass().getResource("images\\menuPause.png")));
        this.setBackground(new Color(128, 255, 128));
        lbIcon.setIcon(new ImageIcon(getClass().getResource("images\\Play.png")));
    }
    
    public void failure(){
        pausePlay.setText("Reiniciar");
        pausePlay.setIcon(new ImageIcon(getClass().getResource("images\\menuRestart.png")));
        setMessage("Falha no download :( ");
        this.setBackground(new Color(255, 70, 70));
        lbIcon.setIcon(new ImageIcon(getClass().getResource("images\\failure.png")));
        setDownloadFailed(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mOptions = new javax.swing.JPopupMenu();
        pausePlay = new javax.swing.JMenuItem();
        delete = new javax.swing.JMenuItem();
        separador = new javax.swing.JPopupMenu.Separator();
        execute = new javax.swing.JMenuItem();
        openFolder = new javax.swing.JMenuItem();
        copyUrl = new javax.swing.JMenuItem();
        downProgress = new javax.swing.JProgressBar();
        txtFile = new javax.swing.JLabel();
        txtMessage = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();

        mOptions.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                mOptionsPopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        pausePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuPause.png"))); // NOI18N
        pausePlay.setText("Pausar");
        pausePlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pausePlayActionPerformed(evt);
            }
        });
        mOptions.add(pausePlay);

        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuDelete.png"))); // NOI18N
        delete.setText("Excluir");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        mOptions.add(delete);
        mOptions.add(separador);

        execute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuFile.png"))); // NOI18N
        execute.setText("Executar Arquivo");
        execute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeActionPerformed(evt);
            }
        });
        mOptions.add(execute);

        openFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuFolder.png"))); // NOI18N
        openFolder.setText("Abrir Pasta");
        openFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFolderActionPerformed(evt);
            }
        });
        mOptions.add(openFolder);

        copyUrl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/menuUrl.png"))); // NOI18N
        copyUrl.setText("Copiar Link");
        copyUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyUrlActionPerformed(evt);
            }
        });
        mOptions.add(copyUrl);

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(32767, 130));
        setPreferredSize(new java.awt.Dimension(569, 130));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                formAncestorRemoved(evt);
            }
        });

        downProgress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downProgressMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                downProgressMouseReleased(evt);
            }
        });

        txtFile.setFont(new java.awt.Font("Utsaah", 1, 18)); // NOI18N
        txtFile.setText("FileName");
        txtFile.setToolTipText("Nome do arquivo");
        txtFile.setMaximumSize(new java.awt.Dimension(44, 21));
        txtFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFileMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtFileMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtFileMouseReleased(evt);
            }
        });

        txtMessage.setText("Message");
        txtMessage.setToolTipText("Status");
        txtMessage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMessageMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtMessageMouseReleased(evt);
            }
        });

        lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/down/images/play.png"))); // NOI18N
        lbIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lbIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(downProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(downProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lbIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbIconMouseClicked

    }//GEN-LAST:event_lbIconMouseClicked

    private void executeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeActionPerformed
        try {
            getDownload().executeFile();
        } catch (IOException ex) {
            Logger.getLogger(DownloadPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_executeActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
       
    }//GEN-LAST:event_formMouseClicked

    private void openFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFolderActionPerformed
        try {
            getDownload().openFolder();
        } catch (IOException ex) {
            Logger.getLogger(DownloadPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openFolderActionPerformed

    private void mOptionsPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_mOptionsPopupMenuWillBecomeVisible
        openFolder.setEnabled(isDownloadConclued());
        execute.setEnabled(isDownloadConclued());
    }//GEN-LAST:event_mOptionsPopupMenuWillBecomeVisible

    private void pausePlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pausePlayActionPerformed

        if(isDownloadConclued() || isDownloadFailed()){
            try {
                    start();
            } catch (Exception ex) {
                Logger.getLogger(DownloadPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(getDownload().isPaused()){
            play();
        }else{
            pause();
        }
    }//GEN-LAST:event_pausePlayActionPerformed

    private void copyUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyUrlActionPerformed
       Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        StringSelection ss = new StringSelection (getDownload().getStringUrl());  
        clip.setContents (ss, ss);  
        
    }//GEN-LAST:event_copyUrlActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        try {
            cancelDownload();
        } catch (InterruptedException ex) {
            Logger.getLogger(DownloadPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void txtMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMessageMouseClicked
        
    }//GEN-LAST:event_txtMessageMouseClicked

    private void txtFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFileMouseClicked

        
    }//GEN-LAST:event_txtFileMouseClicked

    private void downProgressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downProgressMouseClicked
        
    }//GEN-LAST:event_downProgressMouseClicked

    private void txtFileMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFileMousePressed
        
    }//GEN-LAST:event_txtFileMousePressed

    private void txtFileMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFileMouseReleased

        if(evt.getButton() == evt.BUTTON3){
            mOptions.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_txtFileMouseReleased

    private void txtMessageMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMessageMouseReleased

        if(evt.getButton() == evt.BUTTON3){
            mOptions.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_txtMessageMouseReleased

    private void downProgressMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_downProgressMouseReleased

        if(evt.getButton() == evt.BUTTON3){
            mOptions.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_downProgressMouseReleased

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased

        if(evt.getButton() == evt.BUTTON3){
            mOptions.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMouseReleased

    private void formAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorRemoved
        
    }//GEN-LAST:event_formAncestorRemoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem copyUrl;
    private javax.swing.JMenuItem delete;
    private javax.swing.JProgressBar downProgress;
    private javax.swing.JMenuItem execute;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JPopupMenu mOptions;
    private javax.swing.JMenuItem openFolder;
    private javax.swing.JMenuItem pausePlay;
    private javax.swing.JPopupMenu.Separator separador;
    private javax.swing.JLabel txtFile;
    private javax.swing.JLabel txtMessage;
    // End of variables declaration//GEN-END:variables
}
