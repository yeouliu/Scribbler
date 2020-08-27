/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.scribbler.model;

//import com.yeou.scribbler.model.element.Rectangle;
import java.awt.AWTException; 
import java.awt.Rectangle; 
import java.awt.Toolkit; 
import java.awt.Robot; 
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.io.File; 
import javax.imageio.ImageIO; 

/**
 *
 * @author Yeou Liu
 */
public class Snap {
    public static final long serialVersionUID = 1L; 
    
    static {
        /* works fine! ! */
        System.setProperty("java.awt.headless", "false");
        System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        /* ---> prints true */
    }
    
    public void capture() 
    { 
        try { 
            Thread.sleep(120); 
            Robot r = new Robot(); 
  
            // It saves screenshot to desired path 
            String path = "D:// Shot.jpg"; 
  
            // Used to get ScreenSize and capture image 
            Rectangle capture =  
            new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); 
            BufferedImage Image = r.createScreenCapture(capture); 
            ImageIO.write(Image, "jpg", new File(path)); 
            System.out.println("Screenshot saved"); 
        } 
        catch (AWTException ex) { 
            System.out.println(ex); 
        } catch(InterruptedException ex){
            System.out.println("com.yeou.scribbler.model.Snap.main()");
        }catch(IOException ex){
            System.out.println("com.yeou.scribbler.model.Snap.main()");
        }
    } 
}
