/*
 * Simulation.java
 *
 * Created on Aug 28, 2007, 10:29:14 AM
 *
 * Author: Elisha Peterson
 *
 * This class runs a single simulation with given settings.
 */

package simulation;

import goal.Goal;
import sequor.model.DoubleRangeModel;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JPanel;
import sequor.component.Settings;
import sequor.model.ComboBoxRangeModel;
import sequor.model.IntegerRangeModel;
import specto.PlotPanel;
import specto.plotpanel.Plot2D;
import specto.visometry.Euclidean2;
import utility.DataLog;
import utility.DistanceTable;
import utility.DynamicTeamGraph;
import utility.SimulationFactory;

/**
 *
 * @author ae3263
 */
public class Simulation implements ActionListener,PropertyChangeListener {
    
    
    // PROPERTIES
    
    /** Contains all settings used to run the simulation. */
    public SimSettings ss;
    /** Contains list of teams involved. */
    Vector<Team> teams;
    /** The team responsible for returning data for statistical runs. */
    Team primary;
    /** Table of distances (for speed of simulation) */
    DistanceTable dist;
    
    /** The data collected in a simulation. */
    DataLog log;
    /** Whether a batch of several runs is currently processing. */
    boolean batchProcessing;
    
    
    // CONSTRUCTORS
    
    /** Standard constructor */
    public Simulation(){
        ss=new SimSettings();
        setGameType(SimulationFactory.SIMPLE_PE);
        SimulationFactory.setSimulation(this,getGameType());
        dist=null;
        log=new DataLog(this);
        batchProcessing=false;
    }
    /** Constructs given a type of game
     * @param gameType the type of game to simulate */
    public Simulation(int gameType){
        ss=new SimSettings();
        setGameType(gameType);
        SimulationFactory.setSimulation(this,gameType);
        dist=null;
        log=new DataLog(this);
        batchProcessing=false;
    }
    
    
    // METHODS: INITIALIZERS
    
    /** Intializes to a given list of teams. */
    public void initTeams(Vector<Team> newTeams){
        if(newTeams!=null){
            if(teams!=null){for(Team t:teams){t.removeActionListener(this);}}
            teams=newTeams;
            for(Team t:teams){
                t.addActionListener(this);
                t.initStartingLocations();
            }
            log=new DataLog(this);
        }        
        //fireActionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"teamsInitialized"));
    }
    
    /** Returns list of teams used in the simulation. */
    public Vector<Team> getTeams(){return teams;}
    
    // METHODS: DEPLOY SIMULATION
    
    /** Delete paths and get ready to start another simulation. */
    public void reset(){
        for(Team team:teams){team.reset();}
        dist=new DistanceTable(teams);
        log.reset();
    }
    
    /** Resets the starting positions. Useful when positions are initialized randomly. */
    public void initStartingLocations(){
        for(Team team:teams){team.initStartingLocations();}
        dist=new DistanceTable(teams);
        log.reset();
    }
    
    /** Runs several times and computes average result. */
    public Double runSeveral(int numTimes){
        batchProcessing=true;
        Double total=0.0;
        Double current;
        for(int i=0;i<numTimes;i++){
            for(Team team:teams){team.initStartingLocations();team.reset();}
            dist=new DistanceTable(teams);
            current=run();
            if(current!=null){total+=current;}            
        }
        fireActionPerformed("Result of "+numTimes+" run is an average of "+total/numTimes);
        batchProcessing=false;
        return total/numTimes;
    }
    /** Runs default number of steps */
    public Double run(){return run(getNumSteps());}
    /** Tells the simulation to get going!
     * @param numSteps  how many time steps to run the simulation
     * @return          value of the simulated run according to the given team...
     */
    public Double run(int numSteps){
        reset();
        double time=0;
        for(int i=0;i<numSteps;i++){
            iterate(time);
            log.logAll(i);
            for(Team t:teams){
                for(Goal g:t.getCaptureGoals()){
                    log.logCaptures(dist,g);                    
                }
            }
        }
        log.output();
        actionPerformed(new ActionEvent(this,0,"redraw"));
        return primary.getValue();
    }
    
    /** Runs a single iteration of the scenario */
    public void iterate(double time){
        dist.recalculate();
        // TODO check for capture here
        for(Team t:teams){
            t.checkGoal(dist,time);
        }
        for(Team t:teams){
            if(!t.getGoals().isEmpty()){
                t.gatherSensoryData(dist);
                t.communicateSensoryData(dist);
                t.fuseAgentPOV();
                t.assignTasks();
            }
        }
        for(Team t:teams){t.planPaths(time,getStepTime());}
        for(Team t:teams){t.move();}
        //bird.remember();
        time+=getStepTime();
    }
    
    // METHODS: RETURN RESULTS OF SIMULATION IN VARIOUS FORMATS
    
    /** Places simulation's points on the plot. */
    public void placeInitialPointsOn(PlotPanel<Euclidean2> p){
        for(Team t:teams){t.placeInitialPointsOn(p);}
    }
    
    /** Places simulation's paths on the plot. */
    @SuppressWarnings("unchecked")
    public void placePathsOn(PlotPanel p){
        p.addAll(log.getPlotPaths());
    }
    
    /** Places graph element on the plot. */
    public void placeGraphsOn(PlotPanel<Euclidean2> p){
        for(Team t:teams){p.add(new DynamicTeamGraph(t,log));}
    }
    
    /** Places value functions on given plot. */
    public void placeValuesOn(Plot2D p){
        p.addAll(log.getValuePlots());
    }
    
    /** Recomputes animation settings for a plot window */
    public void setAnimationCycle(PlotPanel<Euclidean2> p){
        if(p.getTimer()==null){p.getTimer().restart();p.getTimer().stop();}
        p.getTimer().setNumSteps(getNumSteps()+10);
        if(getStepTime()>.4){
            p.getTimer().setDelay(100);
        }else{
            p.getTimer().setDelay((int)(250*getStepTime()));
        }
    }
    
    /** Returns all teams/agents as a JTree
     * @return a tree model with all agents involved in the simulation */
    public DefaultTreeModel getTreeModel(){
        DefaultMutableTreeNode top=new DefaultMutableTreeNode(this);
        for(Team t:teams){top.add(t.getTreeNode());}
        return new DefaultTreeModel(top);
    }
    
    
    // EVENT HANDLING
    
    // Remaining code deals with action listening
    protected ActionEvent actionEvent=null;
    protected EventListenerList listenerList=new EventListenerList();
    public void addActionListener(ActionListener l){listenerList.add(ActionListener.class, l);}
    public void removeActionListener(ActionListener l){listenerList.remove(ActionListener.class, l);}
    protected void fireActionPerformed(String s){fireActionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,s));}
    protected void fireActionPerformed(ActionEvent e){
        actionEvent=e;
        Object[] listeners=listenerList.getListenerList();
        for(int i=listeners.length-2;i>=0;i-=2){
            if(listeners[i]==ActionListener.class){
                if(actionEvent==null){actionEvent=e;}
                ((ActionListener)listeners[i+1]).actionPerformed(actionEvent);
            }
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        String es=e.getActionCommand();
        // if several runs are being performed, do not bother to display the changes being made
        if(batchProcessing){return;}
        // otherwise, go ahead and redraw/recolor/etc. as appropriate
        if(es.equals("simulationRun")){ // routine run of the simulation
            fireActionPerformed("redraw");
        }
        else if(es.equals("agentDisplayChange")||es.equals("teamDisplayChange")){ // cosmetic change only
            fireActionPerformed("recolor");
        }else if(es.equals("agentSetupChange")||es.equals("teamSetupChange")){ // change in parameters for a particular player or team
            reset();
            run();
            fireActionPerformed("redraw");
        }else if(e.getActionCommand().equals("teamAgentsChange")){ // change in number of agents; must reset the simulation to reload the settings tree
            log=new DataLog(this);
            run();
            fireActionPerformed("reset");
        }else {fireActionPerformed(e);
        }
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    // BEAN PATTERNS: GETTERS & SETTERS
    
    public int getNumTeams(){return ss.numTeams.getValue();}
    public int getGameType(){return ss.gameType.getValue();}
    public double getPitchSize(){return ss.pitchSize.getValue();}
    public double getStepTime(){return ss.stepTime.getValue();}
    public int getNumSteps(){return ss.numSteps.getValue();}
    public int getMaxSteps(){return ss.maxSteps.getValue();}
    public String toString(){return ss.s;}
    
    public void setNumTeams(int newValue){ss.numTeams.setValue(newValue);}
    public void setGameType(int newValue){ss.gameType.setValue(newValue);}
    public void setPitchType(double newValue){ss.pitchSize.setValue(newValue);}
    public void setStepTime(double newValue){ss.stepTime.setValue(newValue);}
    public void setNumSteps(int newValue){ss.numSteps.setValue(newValue);}
    public void setMaxSteps(int newValue){ss.maxSteps.setValue(newValue);}
    public void setString(String newValue){ss.s=newValue;}
    public void setPrimary(Team newValue){primary=newValue;}
    public void setPrimary(int newIndex){if(newIndex<teams.size()){primary=teams.get(newIndex);}}
    
    public JPanel getPanel(){return ss.getPanel();}
    
    // SUBCLASSES
    
    private class SimSettings extends Settings{
        
        /** Number of teams */
        private IntegerRangeModel numTeams=new IntegerRangeModel(2,1,100);
        /** Type of game involved */
        private ComboBoxRangeModel gameType=SimulationFactory.comboBoxRangeModel();
        /** Pitch size */
        private DoubleRangeModel pitchSize=new DoubleRangeModel(60,0,50000,1);
        /** Time taken by a single step [in seconds] */
        private DoubleRangeModel stepTime=new DoubleRangeModel(.1,0,15,.01);
        /** Number of steps to run the simulation before quitting. */
        private IntegerRangeModel numSteps=new IntegerRangeModel(100,0,10000);
        /** If stop is based on reaching a goal, this is the max # of steps to allow. */
        private IntegerRangeModel maxSteps=new IntegerRangeModel(1000,0,10000000);
        /** Display string */
        private String s="PEG Simulation";
        
        SimSettings(){
            super();
            addProperty("Pitch Size",pitchSize,Settings.EDIT_DOUBLE);
            addProperty("Step Time",stepTime,Settings.EDIT_DOUBLE);
            addProperty("# of Steps",numSteps,Settings.EDIT_INTEGER);
            //addProperty("max Steps",maxSteps,Settings.EDIT_INTEGER);
            addProperty("Preset Game",gameType,Settings.EDIT_COMBO);
            //addProperty("# of Teams",numTeams,Settings.EDIT_INTEGER);
            initEventListening();
        }
        
        public void stateChanged(ChangeEvent e) {
            //System.out.println("simulation prop change: "+e.getPropertyName());
            if(e.getSource()==stepTime){      fireActionPerformed("animation");run();
            } else if(e.getSource()==numSteps){fireActionPerformed("animation");run();
            } else if(e.getSource()==pitchSize){System.out.println("Nonfunctional! Starting locs should reference this value!");initStartingLocations();run();
            } else if(e.getSource()==maxSteps){System.out.println("nonfunctional!");
            } else if(e.getSource()==numTeams){System.out.println("nonfunctional!");
            } else if(e.getSource()==gameType){
                SimulationFactory.setSimulation(Simulation.this,getGameType());
                fireActionPerformed("reset");
                dist=null;
                run();                
            }
        }
    }
}