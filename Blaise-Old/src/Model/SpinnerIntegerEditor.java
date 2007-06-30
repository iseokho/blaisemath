package Model;

import Interface.BEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

/**
 * <b>SpinnerIntegerEditor.java</b><br>
 * Author: <i>Elisha Peterson</i><br>
 * Created on <i>February 26, 2007, 1:01 PM</i><br><br>
 * 
 * Implements a spinner whose data (other than stepsize) is based in another
 *   source model, which basically implements the data as a range.
 */
public class SpinnerIntegerEditor extends SpinnerNumberModel implements BEditor {
    
    IntegerRangeModel sourceModel; //the real model
    protected int stepSize=1;
        
    /**
     * Creates a SpinnerIntegerEditor that gets its state from sourceModel.
     */
    public SpinnerIntegerEditor(){initializeModels();}
    public SpinnerIntegerEditor(IntegerRangeModel sourceModel){initializeModels(sourceModel);}
    public SpinnerIntegerEditor(IntegerRangeModel sourceModel,int stepSize){
        initializeModels(sourceModel);
        this.stepSize=stepSize;
    }
    public SpinnerIntegerEditor(int value,int min,int max,int stepSize){
        initializeModels(new IntegerRangeModel(value,min,max));
        this.stepSize=stepSize;
    }
    
    // initializes given a model
    public void initializeModels(IntegerRangeModel model){
        sourceModel=model;
        sourceModel.addChangeListener(this);
    }
    
    // initialize underlying models
    public void initializeModels(){
        if(sourceModel==null){sourceModel=new IntegerRangeModel();}
        sourceModel.addChangeListener(this);
    }
    
    // getters & setters
    public int getIntegerMax(){return sourceModel.getMaximum();}
    public void setMaximum(int newMaximum){sourceModel.setMaximum(newMaximum);}
    
    public int getIntegerMin(){return sourceModel.getMinimum();}
    public void setMinimum(int newMinimum){sourceModel.setMinimum(newMinimum);}
    
    public int getIntegerValue(){return sourceModel.getValue();}
    public void setValue(int newValue){sourceModel.setValue(newValue);}
    
    public void setRangeProperties(int newValue,int newMin,int newMax){
        sourceModel.setRangeProperties(newValue,newMin,newMax);
    }
    
    // more getters & setters
    
    public Comparable getMaximum(){return getIntegerMax();}
    public Comparable getMinimum(){return getIntegerMin();}
    public Object getNextValue(){return getIntegerValue()+stepSize;}
    public Number getNumber(){return getIntegerValue();}
    public Object getPreviousValue(){return getIntegerValue()-stepSize;}
    public Number getStepSize(){return stepSize;}
    public Object getValue(){return getIntegerValue();}

    // the following routines are required for spinners...     
    public void setStepSize(Number stepSize){this.stepSize=stepSize.intValue();}
    public void setValue(Object value){if(value instanceof Number){setValue(((Number)value).intValue());}}
        
    //The only method in the ChangeListener interface...
    public void stateChanged(ChangeEvent e){fireStateChanged();}
}
