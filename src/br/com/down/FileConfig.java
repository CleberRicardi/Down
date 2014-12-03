/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.down;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Cleber
 */
public class FileConfig{
    
    public FileConfig() throws IOException{
        load();
    }
    
    private String downloadPath;
    private boolean useProxy;
    private String proxyHost;
    private String proxyPort;
    private String proxyUser;
    private String proxyPass;

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPass() {
        return proxyPass;
    }

    public void setProxyPass(String proxyPass) {
        this.proxyPass = proxyPass;
    }
    
    public boolean save() throws IOException{
        try{    
            File file = new File(Down.getSystemPath()+"\\config.txt");
            if(!file.exists()){
                file.createNewFile();
            }

            Properties props = new Properties();
            props.load(new FileInputStream(file));

            props.setProperty("downloadPath", getDownloadPath());
            props.setProperty("proxyHost", getProxyHost());
            props.setProperty("proxyPort", getProxyPort());
            props.setProperty("proxyUser", getProxyUser());
            props.setProperty("proxyPass", getProxyPass());
            props.setProperty("useProxy", String.valueOf(isUseProxy()));

            FileOutputStream fos = new FileOutputStream(file);
            props.store(fos, "#ConfiguraçõesDown");

            fos.flush();
            fos.close();
            return true;
        }catch(Exception ex){
            return false;
        }    
    }
    
    public void load() throws IOException{
        try{
            File file = new File(Down.getSystemPath()+"\\config.txt");
            Properties props = new Properties();
            try {props.load(new FileInputStream(file));}catch(Exception ex){}


            this.setDownloadPath(props.getProperty("downloadPath", Down.getSystemPath()));
            this.setProxyHost(props.getProperty("proxyHost", ""));
            this.setProxyPort(props.getProperty("proxyPort", ""));
            this.setProxyUser(props.getProperty("proxyUser", ""));
            this.setProxyPass(props.getProperty("proxyPass", ""));            
            this.setUseProxy(props.getProperty("useProxy", "").equals("true"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
   
    
    
}
