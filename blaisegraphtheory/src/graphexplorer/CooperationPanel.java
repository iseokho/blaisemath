/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CooperationPanel.java
 *
 * Created on Jul 14, 2010, 11:15:16 AM
 */

package graphexplorer;

import graphexplorer.ExplorerStatActions.StatEnum;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import org.bm.blaise.scio.graph.metrics.NodeMetric;

/**
 *
 * @author elisha
 */
public class CooperationPanel extends javax.swing.JPanel {

    /** Creates new form CooperationPanel */
    public CooperationPanel() {
        initComponents();
    }

    public NodeMetric getMetric() {
        Object item = jComboBox1.getSelectedItem();
        if (!(item instanceof StatEnum))
            return null;
        return ExplorerStatActions.metricOf((StatEnum) item);
    }

    public Collection<Integer> getSubset() {
        String[] spl = jTextField1.getText().split(",");
        HashSet<Integer> result = new HashSet<Integer>();
        for (String s : spl)
            result.add(Integer.decode(s));
        return result;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Metric:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jLabel1, gridBagConstraints);

        jComboBox1.setModel(new DefaultComboBoxModel(graphexplorer.ExplorerStatActions.StatEnum.values()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jComboBox1, gridBagConstraints);

        jLabel2.setText("Subset:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jLabel2, gridBagConstraints);

        jTextField1.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jTextField1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
