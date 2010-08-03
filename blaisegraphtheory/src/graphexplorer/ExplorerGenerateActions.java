/*
 * ExplorerGenerateActions.java
 * Created May 14, 2010
 */

package graphexplorer;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import org.bm.blaise.scio.graph.Graph;
import org.bm.blaise.scio.graph.GraphFactory;
import org.bm.blaise.scio.graph.LongitudinalGraph;
import org.bm.blaise.scio.graph.PreferentialAttachment;
import org.bm.blaise.scio.graph.RandomGraph;

/**
 * Describes graph-generation actions supporting the graph explorer app.
 * @author Elisha Peterson
 */
class ExplorerGenerateActions {

    /** What this class works with */
    GraphControllerMaster master;

    /** Construction requires a controller */
    public ExplorerGenerateActions(GraphControllerMaster controller) { this.master = controller; }

    public Action GENERATE_EMPTY = new AbstractAction("Empty graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates an empty graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for empty graph (up to 1 million).", 1, 1000000);
            if (num == -1) return;
            GraphController newC = GraphController.getInstance(GraphFactory.getEmptyGraph(num, false), "Empty graph");
            master.setActiveController(newC);
        }
    };

    public Action GENERATE_COMPLETE = new AbstractAction("Complete graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates a complete graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for complete graph (up to 1000).", 1, 1000);
            if (num == -1) return;
            GraphController newC = GraphController.getInstance(GraphFactory.getCompleteGraph(num, false), "Complete graph");
            master.setActiveController(newC);
        }
    };

    public Action GENERATE_CIRCLE = new AbstractAction("Circle graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates a circle graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for circle graph (up to 1 million).", 1, 1000000);
            if (num == -1) return;
            GraphController newC = GraphController.getInstance(GraphFactory.getCycleGraph(num, false), "Circle graph");
            master.setActiveController(newC);
        }
    };

    public Action GENERATE_STAR = new AbstractAction("Star graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates graph with central hub connected to all other vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_H);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for star graph (up to 1 million).", 1, 1000000);
            if (num == -1) return;
            GraphController newC = GraphController.getInstance(GraphFactory.getStarGraph(num), "Star graph");
            master.setActiveController(newC);
        }
    };

    public Action GENERATE_WHEEL = new AbstractAction("Wheel graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generates a wheel graph with specified number of vertices");
            putValue(MNEMONIC_KEY, KeyEvent.VK_W);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            int num = showIntegerInputDialog("Enter number of vertices for wheel graph (up to 1 million).", 1, 1000000);
            if (num == -1) return;
            GraphController newC = GraphController.getInstance(GraphFactory.getWheelGraph(num), "Wheel graph");
            master.setActiveController(newC);
        }
    };

    //
    // RANDOM GRAPHS
    //

    public Action GENERATE_RANDOM = new AbstractAction("Random graph") {
        {
            putValue(SHORT_DESCRIPTION, "Generate random graph by selecting edges at random");
            putValue(MNEMONIC_KEY, KeyEvent.VK_U);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            NewRandomGraphPanel nrgp = new NewRandomGraphPanel();
            JOptionPane.showMessageDialog(null, nrgp);
            Graph<Integer> result = nrgp.getInstance();
            GraphController newC = GraphController.getInstance(result, "Random Graph");
            master.setActiveController(newC);
        }
    };

    public Action GENERATE_PREFERENTIAL = new AbstractAction("Preferential attachment") {
        {
            putValue(SHORT_DESCRIPTION, "Generate random graph using preferential attachment algorithm");
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            setEnabled(true);
        }
        public void actionPerformed(ActionEvent e) {
            NewPreferentialGraphPanel npgp = new NewPreferentialGraphPanel();
            JOptionPane.showMessageDialog(null, npgp);
            Object result = npgp.getInstance();
            GraphController newC = null;
            if (result instanceof Graph)
                newC = GraphController.getInstance((Graph) result, "Random Graph");
            else if (result instanceof LongitudinalGraph)
                newC = GraphController.getInstance((LongitudinalGraph) result, "Random Graph");
            master.setActiveController(newC);
        }
    };

    //
    // UTILITY METHODS
    //

    /**
     * Shows option pane to retrieve an integer value in provided range.
     * @return a value between min and max, or -1 if the user cancelled the dialog
     */
    public static int showIntegerInputDialog(String message, int min, int max) {
        int num = 0;
        do {
            String response = JOptionPane.showInputDialog(message);
            if (response == null) return -1;
            try { num = Integer.decode(response); } catch (NumberFormatException ex) { System.out.println("Improperly formatted number..."); }
        } while (num < min || num > max);
        return num;
    }

    /** 
     * Shows option pane to retrieve a float value in provided range.
     * @return a value between min and max, or -1 if the user cancelled the dialog
     */
    public static float showFloatInputDialog(String message, float min, float max) {
        float num = 0;
        do {
            String response = JOptionPane.showInputDialog(message);
            if (response == null) return -1;
            try { num = Float.parseFloat(response); } catch (NumberFormatException ex) { System.out.println("Improperly formatted number..."); }
        } while (num < min || num > max);
        return num;
    }

    /** 
     * Shows option pane to retrieve a float value in provided range.
     * @return a value between min and max, or null if the user cancelled the dialog
     */
    public static float[] showFloatArrayInputDialog(String message, float min, float max) {
        float[] result = null;
        boolean valid = false;
        do {
            String response = JOptionPane.showInputDialog(message);
            if (response == null) return null;
            String[] responseArr = response.split(",");
            result = new float[responseArr.length];
            try {
                float sum = 0f;
                for (int i = 0; i < responseArr.length; i++) {
                    result[i] = Float.valueOf(responseArr[i]);
                    sum += result[i];
                }
                valid = sum == 1f;
            } catch (NumberFormatException ex) { System.out.println("Improperly formatted number..."); }
        } while (!valid);
        return result;
    }

}
