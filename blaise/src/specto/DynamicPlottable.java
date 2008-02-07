/*
 * VisualEditor.java
 * 
 * Created on Sep 14, 2007, 7:55:27 AM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package specto;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class takes the Plottable class and adds in support for mouse event handling,
 * allowing the firing of events, handling of menu, etc.
 * <br><br>
 * @author Elisha Peterson
 */
public abstract class DynamicPlottable<V extends Visometry> extends Plottable<V> implements MouseListener,MouseMotionListener {
    
    // HOW TO DETERMINE WHEN TO PROCESS A MOUSE EVENT

    public static final int CLICK_EDIT_RANGE=8;
    
    public boolean adjusting=false;
    public boolean mobile=true;
    
    public void setMoving(boolean newValue){mobile=newValue;}
    
    public boolean clicked(MouseEvent e){return false;}
    
    
    // MOUSE EVENTS
    
    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}
}