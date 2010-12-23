/*
 * NewRandomGraphPanel.java
 * Created on Jul 20, 2010, 3:32:51 PM
 */

package graphexplorer.dialogs;

import javax.swing.Box;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author elisha
 */
public class NewSimpleGraphPanel extends javax.swing.JPanel {

    /** Creates new form NewRandomGraphPanel */
    public NewSimpleGraphPanel() {
        this(100000);
    }

    /**
     * Creates with specified bounds on #s of nodes
     * @param maxNodes the maximum permissible number of nodes to use (ignore if less than 1)
     */
    public NewSimpleGraphPanel(int maxNodes) {
        initComponents();
        add(Box.createHorizontalGlue());
        ((SpinnerNumberModel)numNodesS.getModel()).setMaximum(maxNodes);
        numNodesS.setToolTipText("Enter the number of nodes in the resulting graph (1-" + maxNodes + ")");
    }

    /**
     * Construct with custom configuration
     * @param initialValue initial number of nodes to set (ignore if out of range)
     * @param maxNodes the maximum permissible number of nodes to use (ignore if less than 1)
     * @param onlyUndirected whether to only permit the undirected graph option
     */
    public NewSimpleGraphPanel(int initialValue, int maxNodes, boolean onlyUndirected) {
        initComponents();
        add(Box.createHorizontalGlue());
        SpinnerNumberModel snm = (SpinnerNumberModel) numNodesS.getModel();
        if (maxNodes >= 1)
            snm.setMaximum(maxNodes);
        else
            maxNodes = (Integer) snm.getMaximum();
        if (initialValue >= 1 && initialValue <= maxNodes)
            snm.setValue(initialValue);
        if (onlyUndirected) {
            undirectedRB.setSelected(true);
            directedRB.setEnabled(false);
            directedRB.setToolTipText("Graph of this type can only be generated as undirected.");
        }

        numNodesS.setToolTipText("Enter the number of nodes in the resulting graph (1-" + maxNodes + ")");
    }

    /** @return order specified by this panel */
    public int getOrder() { return (Integer) numNodesS.getValue(); }

    /** @return directed property specified by this panel */
    public boolean getDirected() { return directedRB.isSelected(); }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        edgeTypeBG = new javax.swing.ButtonGroup();
        directedBG = new javax.swing.ButtonGroup();
        undirectedRB = new javax.swing.JRadioButton();
        directedRB = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        numNodesS = new javax.swing.JSpinner();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Basic Graph Properties"));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        directedBG.add(undirectedRB);
        undirectedRB.setSelected(true);
        undirectedRB.setText("undirected");
        add(undirectedRB);

        directedBG.add(directedRB);
        directedRB.setText("directed ");
        add(directedRB);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setMaximumSize(new java.awt.Dimension(2, 32767));
        add(jSeparator1);

        jLabel1.setText("  TOTAL # nodes:");
        add(jLabel1);

        numNodesS.setModel(new javax.swing.SpinnerNumberModel(20, 1, 100000, 1));
        add(numNodesS);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup directedBG;
    private javax.swing.JRadioButton directedRB;
    private javax.swing.ButtonGroup edgeTypeBG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner numNodesS;
    private javax.swing.JRadioButton undirectedRB;
    // End of variables declaration//GEN-END:variables

}
