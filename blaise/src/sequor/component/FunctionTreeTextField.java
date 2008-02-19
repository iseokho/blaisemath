/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sequor.component;

import java.awt.Color;
import javax.swing.event.ChangeEvent;
import scribo.parser.*;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import scribo.tree.FunctionTreeRoot;
import sequor.model.FunctionTreeModel;

/**
 * This class is a text field synchronized with an underlying function tree. The FunctionTreeModel contains the
 * underlying tree, while this class interoperates with the actual text field.
 * 
 * @author Elisha Peterson
 */
public class FunctionTreeTextField extends JTextField implements ChangeListener {
    
    FunctionTreeModel ftm;
    
    public FunctionTreeTextField(){this(new FunctionTreeModel());}   
    public FunctionTreeTextField(String text,String var){this(new FunctionTreeModel(text,var));}
    public FunctionTreeTextField(FunctionTreeModel ftm){this.ftm=ftm;initEventListening();}
    
    public void initEventListening(){
        setText(ftm.getRoot().argumentString());
        getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e){ftm.setValue(getText());}
            @Override
            public void removeUpdate(DocumentEvent e){ftm.setValue(getText());}
            @Override
            public void changedUpdate(DocumentEvent e){ftm.setValue(getText());}
        });
        ftm.addChangeListener(this);
    }

    // BEAN PATTERNS
    
    /** Calls up the function corresponding to the underlying text tree. */
    public FunctionTreeRoot getF(){return ftm.getRoot();}
    
    // EVENT HANDLING
    
    /** If the underlying tree compiles correctly, set the text color to red; otherwise to black. */
    @Override
    public void stateChanged(ChangeEvent e) {
        setForeground(ftm.isValid()?Color.BLACK:Color.RED);
    }
    
}
