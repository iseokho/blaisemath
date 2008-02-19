/*
 * ComboRangeModel.java
 * Created on Sep 7, 2007, 1:17:37 PM
 */

package sequor.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class extends the standard combo box with an underlying IntegerRangeModel to handle
 * which property is selected.
 * <br><br>
 * @author Elisha Peterson
 */
public class ComboBoxEditor extends DefaultComboBoxModel implements ChangeListener{
    ComboBoxRangeModel cbrm;
    public ComboBoxEditor(){super();initializeModels();}
    public ComboBoxEditor(ComboBoxRangeModel cbrm){super(cbrm.s);initializeModels(cbrm);}
    
    @Override
    public void setSelectedItem(Object anObject){cbrm.setValue(this.getIndexOf(anObject));}
    @Override
    public Object getSelectedItem(){return getElementAt(cbrm.getValue());}    
   
    public void initializeModels(){if(cbrm==null){cbrm=new ComboBoxRangeModel();}cbrm.addChangeListener(this);}
    public void initializeModels(ComboBoxRangeModel cbrm){this.cbrm=cbrm;cbrm.addChangeListener(this);}
    
    @Override
    public void stateChanged(ChangeEvent e){}
}
