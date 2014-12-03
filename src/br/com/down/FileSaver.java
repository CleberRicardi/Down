/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.down;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.nio.*;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Cleber
 */
public class FileSaver {
    
    private String stringUrl;
    private String downloadFileName;
    private File file;
    private String pathSaver = Down.getSystemPath()+"\\fileSaver";
    
    
    public FileSaver(String stringUrl) throws IOException{
        setStringUrl(stringUrl);
        setDownloadFileName("");
        file = new File(pathSaver+"\\"+createNewFile());
    }
    
    public FileSaver(File file) throws IOException{
        this.file = file;
        load();
    }
    
    public void setStringUrl(String stringUrl){
        this.stringUrl = stringUrl;
    }
    
    public void setDownloadFileName(String downloadFileName){
        this.downloadFileName = downloadFileName;
    }
    
    public String getDownloadFileName(){
        if(downloadFileName==null){downloadFileName="";}
        return this.downloadFileName;
    }
    
    
    public String getStringUrl(){
        return stringUrl;
    }
        
    public void delete(){
        file.delete();
    }
    
    public String createNewFile() throws IOException{
        int endFileName = 0;
        
        new File(pathSaver).mkdirs();
        
        while(new File(pathSaver+"\\down"+endFileName+".txt").exists()){
            endFileName++;
        }
        
        new File(pathSaver+"\\down"+endFileName+".txt").createNewFile();
        
        return "down"+endFileName+".txt";
        
        
    }
    
    public void load() throws IOException{
        if(!this.file.exists()){
            this.file.createNewFile();
            return;
        }
        
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(this.file);
        prop.load(fis);
        
        setStringUrl(prop.getProperty("URL"));
        setDownloadFileName(prop.getProperty("fileName"));

        fis.close();    
        
    }
    
    public void save() throws IOException, InterruptedException{
        if(!file.exists()){
            file.createNewFile();
        }
        
        Properties prop = new Properties();
        
        FileInputStream fis = new FileInputStream(file);
        prop.load(fis);
        
        prop.setProperty("URL", getStringUrl());
        prop.setProperty("fileName", getDownloadFileName());
        


        //FileOutputStream fos =  new FileOutputStream(file);
        FileWriter fw = new FileWriter(file);
        prop.store(fw, "Download em andamento");
  
        fis.close();
        fw.flush();
        fw.close();
    }
    
}
