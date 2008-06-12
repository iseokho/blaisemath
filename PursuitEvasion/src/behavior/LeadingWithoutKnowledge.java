/*
 * Leading.java 
 * Created on Aug 28, 2007, 11:21:25 AM
 */
package behavior;

import simulation.Agent;
import scio.coordinate.R2;
import scio.coordinate.V2;

/**
 * @author Elisha Peterson<br><br>
 * 
 * Moves towards a position in front of the target which depends upon the leadFactor.
 */
public class LeadingWithoutKnowledge extends behavior.Behavior {
// starts counter that iterates in the function below allowing the agents to move in enlargening circles
// factors refer to a factor of the sensor radius, allowing for easy change to the size of the circles
// stepSize refers to the amount of time each step represents. With velocity, this determines how far an
// agent moves during each size.
    double counter = 0.0;
    double stepSize = 0.1;
    double newCircleFactor =.5;

    /**
     * Computes desired direction of travel
     * @param self      the agent exhibiting this myBehavior
     * @param target    the agent targeted by the myBehavior
     * @param t         the current time stamp
     * @return          the direction of travel corresponding to this myBehavior
     */
    public R2 direction(Agent self, V2 target, double t) {
        double x = 0;
        double y = 0;
        //System.out.println("counter: "+counter);
        if (target == null) {
            counter++;
            if (counter >= 0 && counter <= ((Math.PI * self.getSensorRange()) / (self.getTopSpeed() * .1))) {
                x =  (.5*self.getSensorRange() )* Math.sin((counter * self.getTopSpeed() * .1) / (.5* self.getSensorRange()));

                y =  .5*self.getSensorRange() - (.5 * self.getSensorRange() )* Math.cos((counter * self.getTopSpeed() * .1) / (.5 * self.getSensorRange()));
                
                return new R2(x, y);
            
            } else if (counter > (Math.PI * self.getSensorRange()) / (self.getTopSpeed() * .1) && counter <= (2*Math.PI *self.getSensorRange()) / (self.getTopSpeed() * .1)) {
                x = (-2*self.getSensorRange())*Math.sin((counter * self.getTopSpeed() * .1) / (.5*self.getSensorRange()) - 1.5*Math.PI);

                y = ( 2*self.getSensorRange())*Math.cos((counter * self.getTopSpeed() * .1) / ( .5*self.getSensorRange()) - 1.5*Math.PI);

                return new R2(x, y);
            
            } else if (counter > (Math.PI * 2*self.getSensorRange()) / (self.getTopSpeed() * .1) && counter <= (Math.PI * 3*self.getSensorRange()))  {  
                
                x = -3*.5 * self.getSensorRange() * Math.sin((counter * self.getTopSpeed() * .1) / (.5 * self.getSensorRange()) - Math.PI);
                
                y= .5*self.getSensorRange()+3*.5*self.getSensorRange()*Math.cos((counter * self.getTopSpeed() * .1) / (.5 * self.getSensorRange()) - Math.PI);
                 
              
                            
            } else {
                counter =0.0;
                counter = 0;
                return R2.ORIGIN;
            }
        }
        counter = 0.0;
        if (target.v.magnitude() == 0) {
            if (self.loc.distance(target) < 15) {
                return R2.ORIGIN;
            }
            return target.minus(self.loc).normalized();
        } else {
            return (target.plus(target.v.multipliedBy(self.getLeadFactor() * self.loc.distance(target) / self.getTopSpeed()))).minus(self.loc).normalized();
        }
    }
}


    

