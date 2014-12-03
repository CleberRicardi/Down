/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.down;

import com.sun.xml.internal.ws.message.saaj.SAAJHeader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sun.security.util.BigInt;

/** 
 *
 * @author Cleber
 */
public class Download extends Thread {
    
    private String stringUrl;
    private String filePath;
    private String fileName;
    private URLConnection con;
    private DownloadPanel downPanel;
    private boolean paused;
    private boolean stopDownload;
    private long totalLoadBytes;
    
    public Download(String stringUrl, DownloadPanel downPanel) throws IOException{
        setDownPanel(downPanel);
        setFilePath(new FileConfig().getDownloadPath());
        setStringUrl(stringUrl);
        setUrlConnection(stringUrl);
    }
    
    public Download(String stringUrl, DownloadPanel downPanel, String fileName) throws IOException, InterruptedException{
        setDownPanel(downPanel);
        setFilePath(new FileConfig().getDownloadPath());
        setStringUrl(stringUrl);
        setUrlConnection(stringUrl);
        setFileName(fileName);
    }
    
    public Download(String stringUrl) throws IOException{
        setFilePath(new FileConfig().getDownloadPath());
        setStringUrl(stringUrl);
        setUrlConnection(stringUrl);
    }
    
    public String getLengthWithType(long lengthBytes){
        double length = (double)lengthBytes; 
        String[] types = {"Bytes","KB","MB","GB","TB","PB","EB","ZB","YB"};
        
        for(int x=0;x<types.length;x++){
            if(length > 1024){
                length /= 1024;
            }else{
                return String.valueOf(new DecimalFormat("#,###.00").format(length))+" "+ types[x];
            }
        }
        
        return null;
    }
    
    public void setDownPanel(DownloadPanel downPanel){
        this.downPanel = downPanel;
    }

    public URLConnection getURLConnection(){
        return con;
    }
    
    public DownloadPanel getDownPanel(){
        return downPanel;
    }
    
    public void setFileName(String fileName) throws IOException, InterruptedException{
        String name = "";
        String extension = "";
        
        if(fileName.lastIndexOf(".") > 0 ){
            name = fileName.substring(0,fileName.lastIndexOf("."));
            extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }else{
            name = fileName;
        }
        
        if(name.length() > 40){
            name = name.substring(0,43)+"..";
        }
        if(extension.length() > 5){
            extension = extension.substring(extension.length()-5, extension.length());
        }
        
        this.fileName = name+extension;
        if(downPanel != null && fileName != ""){
            downPanel.setTxtFileName(this.fileName);
        }
        getDownPanel().saveFileName(this.fileName);
    }
    
    public void setStopDownload(boolean fileDelete){
        this.stopDownload= true;
    }
    
    public boolean isStopDownload(){
        return this.stopDownload;
    }

    public void setStringUrl(String stringUrl){
        this.stringUrl = stringUrl;
    }
    
    public String getFileName(){
        return this.fileName;
    }
    
    public String getStringUrl(){
        return this.stringUrl;
    }
    
    public boolean isPaused(){
        return this.paused;
    }
    
    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public void setFilePath(String filePath){
        if( filePath.charAt(filePath.length()-1) != '\\') {
            filePath += '\\';
        }
        this.filePath = filePath;
    }
   
    public String getFilePath(){
        return this.filePath;
    }
    
    public String getAbsoluteFilePath(){
        return this.getFilePath()+this.getFileName();
    }
    
    public void setProgressValue(int value){
        getDownPanel().setProgressValue(value);
    }
    
    public void setTotalLoadBytes(long totalLoadBytes){
        this.totalLoadBytes = totalLoadBytes;
    }
    
    public long getTotalLoadBytes(){
        return totalLoadBytes;
    }
    public void setUrlConnection(String stringUrl) throws MalformedURLException, IOException{

            FileConfig fc = new FileConfig();

            System.setProperty("http.proxySet", String.valueOf(fc.isUseProxy()));  
            System.setProperty("http.proxyHost", fc.getProxyHost());  
            System.setProperty("http.proxyPort", fc.getProxyPort());  
            System.setProperty("http.proxyType", "4");

            URLConnection con = new URL(stringUrl+"?").openConnection();

            con.setRequestProperty("Proxy-Authorization",
                    "Basic " + new sun.misc.BASE64Encoder().encode((fc.getProxyUser() + ":" + fc.getProxyPass()).getBytes()) );  
            
            this.con = con;

    
    }
    
    public String getFileNameFromUrl(){
        String name = new File(getStringUrl()).getName().replace("%20", " ");
        name = name.replace("?", "");
        return name;
    }
    
    public void openFolder() throws IOException{
        Runtime.getRuntime().exec("explorer "+getFilePath());
    }
    
    public void executeFile() throws IOException{
        //Runtime.getRuntime().exec(getAbsoluteFilePath());
        java.awt.Desktop.getDesktop().open( new File(getAbsoluteFilePath()) ); 
    }
        
    public void reset(){
        totalLoadBytes =0;
    }
    
    public File downloadFile() {
                BufferedInputStream buf = null;
                FileOutputStream fos = null;
		
                try {
                        getDownPanel().setMessage("Preparando diretorio!");
                        File file = null;
                        boolean fileNotCreated = true;
                        int fileRepeat = 0;
                        String fileName = getFileNameFromUrl();
                        new File(getFilePath()).mkdirs();
                        if(getFileName() == null || getFileName().equals("")){
                            while(fileNotCreated){
                                file = new File(getFilePath()+fileName);
                                if(!file.exists()){
                                    file.createNewFile();
                                    fileNotCreated = false;
                                    setFileName(fileName);
                                }else{
                                    fileRepeat++;
                                    fileName = getFileNameFromUrl();
                                    fileName = fileName.substring(0,fileName.lastIndexOf('.'))+"("+fileRepeat+")"+fileName.substring(fileName.lastIndexOf('.'), fileName.length());
                                }
                            }
                        }else{
                            file = new File(getFilePath()+getFileName());
                            if(!file.exists()){
                                file.createNewFile();
                            }
                        }
                        

                        
                               
                        fos = new FileOutputStream(getAbsoluteFilePath(), true);
                        long totalFile = getURLConnection().getContentLength();
                        if(totalFile <= 0){totalFile=100;}
                        int i = 0;
                        long loadBytesTime = 0;
                        long difTime =0;
                        long lastTime = System.currentTimeMillis();
                        byte[] inByte = new byte[1024];
                        totalLoadBytes = file.length();
			
                       //sai do loop quando o arquivo estiver totalmente baixado
                       do{
                            System.out.println("Entrou....no loop");
                            if(totalLoadBytes > 0){
                               getDownPanel().setMessage("Reconectando...");
                            }else{
                               getDownPanel().setMessage("Iniciando Conexão...");
                            }
                            
                            System.out.println("setUrl");
                            setUrlConnection(stringUrl);
                            System.out.println("limpa buffer");
                            if(buf!=null){buf.close();}
                            System.out.println("new buffer");
                            buf = new BufferedInputStream(getURLConnection().getInputStream());
                            System.out.println("buf.skip()");
                            buf.skip((int) totalLoadBytes);
                            
                            
                            //Laço com toda a regra do donwload - Loop da thread
                            while (((i = buf.read(inByte)) != -1) && !isStopDownload()){ 
                                    System.out.println("entrou no loop secundario");
                                    
                                    //grava os bytes no arquivo
                                    System.out.println("Escreveu no arquivo");
                                    fos.write(inByte,0,i); 
                                    
                                    System.out.println("incremenrou os bytes");                                   
                                    totalLoadBytes += i;
                                    
                                    
                                    //altera bara de progresso
                                    System.out.println("seta barra de progresso");                                   
                                    setProgressValue( (int) (totalLoadBytes / (totalFile / 100))  );
                                    //calcula KB/s
                                    loadBytesTime += i;
                                    difTime = System.currentTimeMillis() - lastTime;
                                    if(difTime >= 1000){
                                        System.out.println("entrou na atualização dos b/S, e faz a conta dos bytes");
                                        loadBytesTime = loadBytesTime / (difTime / 1000);
                                        
                                        System.out.println("ajustou os b/s na tela");                                   
                                        getDownPanel().setMessage(getLengthWithType(loadBytesTime)+"/s - "+getLengthWithType(totalLoadBytes) +" de "+getLengthWithType(totalFile));
                                        difTime = 0;
                                        loadBytesTime = 0;
                                        lastTime = System.currentTimeMillis();
                                    }

                                    System.out.println("valida pause");                                   
                                    //validaPaused
                                    while(isPaused() && !isStopDownload()){
                                        getDownPanel().setMessage("Parado");
                                        this.sleep(1500);
                                    }
       
                            }
                            System.out.println("voltou ao loop original");                                   
                            if(isStopDownload()){
                                fos.close();
                                file.delete();
                                break;
                            }
                        }while(totalLoadBytes < totalFile);
                       
                        getDownPanel().setMessage("Finalizando...");		                        
                        getDownPanel().conclude();
                        
			//apos criar o arquivo fisico, retorna referencia para o mesmo
			return new File(getAbsoluteFilePath());
			
		} catch (Exception e) {
			getDownPanel().failure();
			e.printStackTrace();
                } finally {
                        System.out.println("finally");
                        try {    
                            if(buf!=null){buf.close();}
                            if(fos!=null){fos.close();}
                        } catch (Exception ex){}
                }
                        
		   
		return null;
    }
    
    @Override
    public void run(){
        downloadFile();
    }
    
    

}
