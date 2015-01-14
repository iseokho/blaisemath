/**
 * BlaiseSketchActions.java
 * Created Oct 11, 2014
 */
package com.googlecode.blaisemath.sketch;

/*
 * #%L
 * BlaiseSketch
 * --
 * Copyright (C) 2009 - 2014 Elisha Peterson
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.google.common.base.Function;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.googlecode.blaisemath.firestarter.PropertySheetDialog;
import com.googlecode.blaisemath.graphics.core.Graphic;
import com.googlecode.blaisemath.graphics.core.GraphicComposite;
import com.googlecode.blaisemath.graphics.core.PrimitiveGraphicSupport;
import com.googlecode.blaisemath.graphics.swing.JGraphicComponent;
import com.googlecode.blaisemath.style.AttributeSet;
import com.googlecode.blaisemath.style.ImmutableAttributeSet;
import com.googlecode.blaisemath.style.editor.AttributeSetPropertyModel;
import com.googlecode.blaisemath.util.Points;
import com.googlecode.blaisemath.util.coordinate.CoordinateBean;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Utility library with various actions.
 * @author elisha
 */
public class BlaiseSketchActions {

    private static DataFlavor AS_DATA_FLAVOR;
    private static DataFlavor DIM_DATA_FLAVOR;
    static {
        try {
            AS_DATA_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType 
                    + ";class=com.googlecode.blaisemath.style.AttributeSet");
            DIM_DATA_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType 
                    + ";class=java.awt.Dimension");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** Predicate for those graphics that can be moved. */
    private static final Predicate<Graphic<Graphics2D>> MOVABLE_PREDICATE = new Predicate<Graphic<Graphics2D>>() {
        public boolean apply(Graphic<Graphics2D> input) {
            if (!(input instanceof PrimitiveGraphicSupport)) {
                return false;
            }
            Object primitive = ((PrimitiveGraphicSupport) input).getPrimitive();
            return primitive instanceof Point2D
                || primitive instanceof Shape
                || primitive instanceof CoordinateBean;
        }
    };

    /** Get the coordinate of movable graphics */
    private static final Function<Graphic<Graphics2D>,Point2D> LOC_FUNCTION = new Function<Graphic<Graphics2D>,Point2D>() {
        public Point2D apply(Graphic<Graphics2D> input) {
            checkArgument(input instanceof PrimitiveGraphicSupport);
            return getPrimitiveLocation((PrimitiveGraphicSupport) input);
        }
    };
    
    private BlaiseSketchActions() {
    }
    
    //<editor-fold defaultstate="collapsed" desc="COPY/PASTE">

    public static void copy(Graphic<Graphics2D> sel, JGraphicComponent activeCanvas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void paste(JGraphicComponent activeCanvas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void copyStyle(Graphic<Graphics2D> gfc) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        AttributeSetTransferable transf = new AttributeSetTransferable(gfc.getStyle());
        clipboard.setContents(transf, transf);
    }
    
    public static void pasteStyle(Iterable<Graphic<Graphics2D>> gfcs) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tr = clipboard.getContents(null);
        if (tr.isDataFlavorSupported(AS_DATA_FLAVOR)) {
            try {
                AttributeSet pasteStyle = (AttributeSet) tr.getTransferData(AS_DATA_FLAVOR);
                for (Graphic<Graphics2D> gfc : gfcs) {
                    AttributeSet as = gfc.getStyle();
                    if (!(as instanceof ImmutableAttributeSet)) {
                        for (String s : pasteStyle.getAttributes()) {
                            as.put(s, pasteStyle.get(s));
                        }
                    }
                    gfc.getParent().graphicChanged(gfc);
                }
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void copyDimension(Graphic<Graphics2D> gfc) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Rectangle2D bds = gfc.boundingBox();
        DimensionTransferable transf = new DimensionTransferable(new Dimension((int) bds.getWidth(), (int) bds.getHeight()));
        clipboard.setContents(transf, transf);
    }
    
    public static void pasteDimension(Iterable<Graphic<Graphics2D>> gfcs) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tr = clipboard.getContents(null);
        if (tr.isDataFlavorSupported(DIM_DATA_FLAVOR)) {
            try {
                Dimension pasteDim = (Dimension) tr.getTransferData(DIM_DATA_FLAVOR);
                for (Graphic<Graphics2D> gfc : gfcs) {
                    if (gfc instanceof PrimitiveGraphicSupport) {
                        PrimitiveGraphicSupport pgs = (PrimitiveGraphicSupport) gfc;
                        if (pgs.getPrimitive() instanceof RectangularShape) {
                            RectangularShape rsh = (RectangularShape) pgs.getPrimitive();
                            rsh.setFrame(rsh.getX(), rsh.getY(), pasteDim.getWidth(), pasteDim.getHeight());
                            gfc.getParent().graphicChanged(gfc);
                        }
                    }
                }
            } catch (UnsupportedFlavorException ex) {
                Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EDITING">

    public static void editGraphic(Graphic<Graphics2D> src, JGraphicComponent comp) {
        Window window = SwingUtilities.getWindowAncestor(comp);
        PropertySheetDialog dialog = new PropertySheetDialog((Frame) window, true, src);
        dialog.setVisible(true);
    }


    /**
     * Edits the style attributes of the given graphic. Only supports attributes that
     * the graphic already has.
     * @param gr graphics to add attribute to
     * @param comp current canvas
     */
    public static void editGraphicStyle(Graphic<Graphics2D> gr, JGraphicComponent comp) {
        Window window = SwingUtilities.getWindowAncestor(comp);
        AttributeSet style = gr.getStyle();
        Map<String,Class<?>> styleClassMap = Maps.newLinkedHashMap();
        for (String k : style.getAllAttributes()) {
            Object sty = style.get(k);
            if (sty != null) {
                styleClassMap.put(k, sty.getClass());
            }
        }
        AttributeSetPropertyModel pModel = new AttributeSetPropertyModel(style, styleClassMap);
        PropertySheetDialog dialog = new PropertySheetDialog((Frame) window, true, style, pModel);
        dialog.setVisible(true);
    }

    /**
     * Adds a general attribute to the given graphics.
     * @param gr graphic to add attribute to
     * @param comp current canvas
     */
    public static void addAttribute(Graphic<Graphics2D> gr, JGraphicComponent comp) {
        addAttribute(Collections.singleton(gr), comp);
    }

    /**
     * Adds a general attribute to the given graphics.
     * @param gr graphics to add attribute to
     * @param comp current canvas
     * @todo support values of other types
     */
    public static void addAttribute(Iterable<Graphic<Graphics2D>> gr, JGraphicComponent comp) {
        String name = JOptionPane.showInputDialog("Enter attribute name:");
        if (Strings.isNullOrEmpty(name)) {
            return;            
        }
        String value = JOptionPane.showInputDialog("Enter value for attribute "+name+":");
        if (Strings.isNullOrEmpty(value)) {
            return;
        }
        for (Graphic<Graphics2D> g : gr) {
            g.getStyle().put(name, value);
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="REMOVING">

    public static void deleteGraphic(Graphic<Graphics2D> src, JGraphicComponent comp) {
        comp.getSelectionModel().deselect(src);
        GraphicComposite gc = src.getParent();
        if (gc != null) {
            gc.removeGraphic(src);
        } else {
            src.setParent(null);
        }
    }
    
    public static void deleteSelected(JGraphicComponent comp) {
        Set<Graphic<Graphics2D>> selection = comp.getSelectionModel().getSelection();
        for (Graphic g : selection) {
            GraphicComposite gc = g.getParent();
            if (gc != null) {
                gc.removeGraphics(selection);
            }
        }
        comp.getSelectionModel().setSelection(Collections.EMPTY_SET);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GROUPING">
    
    public static void groupSelected(JGraphicComponent comp) {
        Set<Graphic<Graphics2D>> selection = comp.getSelectionModel().getSelection();
        
        // try to reuse the existing parent graphic if possible
        GraphicComposite<Graphics2D> parentGraphic = comp.getGraphicRoot();
        Set<GraphicComposite> parents = Sets.newHashSet();
        for (Graphic g : selection) {
            parents.add(g.getParent());
        }
        parents.remove(null);
        if (parents.isEmpty()) {
            throw new IllegalStateException();
        } else if (parents.size() == 1) {
            parentGraphic = Iterables.getFirst(parents, null);
        }
        
        // create the new group and add it to the new parent
        GraphicComposite<Graphics2D> groupGraphic = new GraphicComposite<Graphics2D>();
        SketchGraphics.configureGraphic(groupGraphic);
        groupGraphic.addGraphics(selection);
        parentGraphic.addGraphic(groupGraphic);
        
        comp.getSelectionModel().setSelection(Sets.<Graphic<Graphics2D>>newHashSet(groupGraphic));
    }
    
    public static void ungroupSelected(JGraphicComponent comp) {
        Set<Graphic<Graphics2D>> selection = comp.getSelectionModel().getSelection();
        checkState(selection.size() == 1);
        Graphic<Graphics2D> gfc = Iterables.getFirst(selection, null);
        checkState(gfc instanceof GraphicComposite);
        GraphicComposite<Graphics2D> gc = (GraphicComposite<Graphics2D>) gfc;
        Iterable<Graphic<Graphics2D>> childGfcs = gc.getGraphics();
        GraphicComposite<Graphics2D> par = gc.getParent();
        par.removeGraphic(gc);
        par.addGraphics(Lists.newArrayList(childGfcs));
        
        comp.getSelectionModel().setSelection(Sets.newHashSet(childGfcs));
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ARRANGING">

    public static void moveForward(Set<Graphic<Graphics2D>> selection, JGraphicComponent comp) {
        for (Graphic<Graphics2D> g : selection) {
            moveForward(g, comp);
        }
    }

    public static void moveForward(Graphic<Graphics2D> gr, JGraphicComponent comp) {
        GraphicComposite<Graphics2D> parent = gr.getParent();
        List<Graphic<Graphics2D>> gfcs = Lists.newArrayList(parent.getGraphics());
        int idx = gfcs.indexOf(gr);
        if (idx != gfcs.size() - 1) {
            gfcs.remove(gr);
            gfcs.add(idx+1, gr);
        }
        parent.replaceGraphics(Lists.newArrayList(parent.getGraphics()), gfcs);
    }

    public static void moveBackward(Set<Graphic<Graphics2D>> selection, JGraphicComponent comp) {
        for (Graphic<Graphics2D> g : selection) {
            moveBackward(g, comp);
        }
    }

    public static void moveBackward(Graphic<Graphics2D> gr, JGraphicComponent comp) {
        GraphicComposite<Graphics2D> parent = gr.getParent();
        List<Graphic<Graphics2D>> gfcs = Lists.newArrayList(parent.getGraphics());
        int idx = gfcs.indexOf(gr);
        if (idx != 0) {
            gfcs.remove(gr);
            gfcs.add(idx-1, gr);
        }
        parent.replaceGraphics(Lists.newArrayList(parent.getGraphics()), gfcs);
    }

    public static void moveToFront(Set<Graphic<Graphics2D>> selection, JGraphicComponent comp) {
        for (Graphic<Graphics2D> g : selection) {
            moveToFront(g, comp);
        }
    }

    public static void moveToFront(Graphic<Graphics2D> gr, JGraphicComponent comp) {
        GraphicComposite<Graphics2D> parent = gr.getParent();
        List<Graphic<Graphics2D>> gfcs = Lists.newArrayList(parent.getGraphics());
        int idx = gfcs.indexOf(gr);
        if (idx != gfcs.size() - 1) {
            gfcs.remove(gr);
            gfcs.add(gfcs.size(), gr);
        }
        parent.replaceGraphics(Lists.newArrayList(parent.getGraphics()), gfcs);
    }

    public static void moveToBack(Set<Graphic<Graphics2D>> selection, JGraphicComponent comp) {
        for (Graphic<Graphics2D> g : selection) {
            moveToBack(g, comp);
        }
    }

    public static void moveToBack(Graphic<Graphics2D> gr, JGraphicComponent comp) {
        GraphicComposite<Graphics2D> parent = gr.getParent();
        List<Graphic<Graphics2D>> gfcs = Lists.newArrayList(parent.getGraphics());
        int idx = gfcs.indexOf(gr);
        if (idx != 0) {
            gfcs.remove(gr);
            gfcs.add(0, gr);
        }
        parent.replaceGraphics(Lists.newArrayList(parent.getGraphics()), gfcs);
    }
    
    private static Point2D getPrimitiveLocation(PrimitiveGraphicSupport<?,Graphics2D> gr) {
        Object prim = gr.getPrimitive();
        if (prim instanceof Point2D) {
            return (Point2D) prim;
        } else if (prim instanceof CoordinateBean) {
            return ((CoordinateBean<Point2D>) prim).getPoint();
        } else if (prim instanceof RectangularShape) {
            return new Point2D.Double(((RectangularShape) prim).getMinX(), ((RectangularShape) prim).getMinY());
        } else if (prim instanceof Shape) {
            return new Point2D.Double(((Shape) prim).getBounds2D().getMinX(), ((Shape) prim).getBounds2D().getMinY());
        } else {
            throw new IllegalStateException();
        }
    }
    
    private static <O> void setPrimitiveLocation(PrimitiveGraphicSupport<O,Graphics2D> gr, Point2D loc) {
        Object prim = gr.getPrimitive();
        if (prim instanceof Point2D) {
            ((Point2D)prim).setLocation(loc);
        } else if (prim instanceof CoordinateBean) {
            ((CoordinateBean<Point2D>)prim).setPoint(loc);
        } else if (prim instanceof RectangularShape) {
            RectangularShape rsh = (RectangularShape) prim;
            rsh.setFrame(loc.getX(), loc.getY(), rsh.getWidth(), rsh.getHeight());
        } else if (prim instanceof Shape) {
            Point2D loc0 = getPrimitiveLocation(gr);
            AffineTransform at = new AffineTransform();
            at.translate(loc.getX()-loc0.getX(), loc.getY()-loc0.getY());
            gr.setPrimitive((O) at.createTransformedShape((Shape) prim));
        } else {
            throw new IllegalStateException();
        }
        // TODO - fire event recognizing change
    }
    
    public static void alignHorizontal(Set<Graphic<Graphics2D>> selection) {
        Iterable<Graphic<Graphics2D>> sub = Iterables.filter(selection, MOVABLE_PREDICATE);
        if (Iterables.size(sub) < 2) {
            Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.INFO,
                    "Cannot align with fewer than 2 movable predicates.");
            return;
        }
        ImmutableMap<Graphic<Graphics2D>,Point2D> locs = Maps.toMap(sub, LOC_FUNCTION);
        double yAvg = Points.average(locs.values()).getY();
        for (Graphic<Graphics2D> gr : sub) {
            setPrimitiveLocation((PrimitiveGraphicSupport<?, Graphics2D>) gr, new Point2D.Double(locs.get(gr).getX(), yAvg));
        }
    }

    public static void alignVertical(Set<Graphic<Graphics2D>> selection) {
        Iterable<Graphic<Graphics2D>> sub = Iterables.filter(selection, MOVABLE_PREDICATE);
        if (Iterables.size(sub) < 2) {
            Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.INFO,
                    "Cannot align with fewer than 2 movable predicates.");
            return;
        }
        ImmutableMap<Graphic<Graphics2D>,Point2D> locs = Maps.toMap(sub, LOC_FUNCTION);
        double xAvg = Points.average(locs.values()).getY();
        for (Graphic<Graphics2D> gr : sub) {
            setPrimitiveLocation((PrimitiveGraphicSupport<?, Graphics2D>) gr, new Point2D.Double(xAvg, locs.get(gr).getY()));
        }
    }

    public static void distributeHorizontal(Set<Graphic<Graphics2D>> selection) {
        Iterable<Graphic<Graphics2D>> sub = Iterables.filter(selection, MOVABLE_PREDICATE);
        int count = Iterables.size(sub);
        if (count < 2) {
            Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.INFO,
                    "Cannot distribute with fewer than 2 movable predicates.");
            return;
        }
        ImmutableMap<Graphic<Graphics2D>,Point2D> locs = Maps.toMap(sub, LOC_FUNCTION);
        Rectangle2D.Double bds = Points.boundingBox(locs.values(), 0);
        double minx = bds.getMinX();
        double maxx = bds.getMaxX();
        double dx = (maxx-minx)/(count-1);
        double gx = minx;
        // TODO - sort by x first so they stay in same order
        for (Graphic<Graphics2D> gr : sub) {
            setPrimitiveLocation((PrimitiveGraphicSupport<?, Graphics2D>) gr, new Point2D.Double(gx, locs.get(gr).getY()));
            gx += dx;
        }
    }

    public static void distributeVertical(Set<Graphic<Graphics2D>> selection) {
        Iterable<Graphic<Graphics2D>> sub = Iterables.filter(selection, MOVABLE_PREDICATE);
        int count = Iterables.size(sub);
        if (count < 2) {
            Logger.getLogger(BlaiseSketchActions.class.getName()).log(Level.INFO,
                    "Cannot distribute with fewer than 2 movable predicates.");
            return;
        }
        ImmutableMap<Graphic<Graphics2D>,Point2D> locs = Maps.toMap(sub, LOC_FUNCTION);
        Rectangle2D.Double bds = Points.boundingBox(locs.values(), 0);
        double miny = bds.getMinY();
        double maxy = bds.getMaxY();
        double dy = (maxy-miny)/(count-1);
        double gy = miny;
        // TODO - sort by y first so they stay in same order
        for (Graphic<Graphics2D> gr : sub) {
            setPrimitiveLocation((PrimitiveGraphicSupport<?, Graphics2D>) gr, new Point2D.Double(locs.get(gr).getX(), gy));
            gy += dy;
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="HELPER CLASSES">
        
    private static class TypedTransferable<C> implements Transferable, ClipboardOwner {
        private final C object;
        private final DataFlavor flavor;
        private TypedTransferable(C object, DataFlavor flavor) {
            this.object = object;
            this.flavor = flavor;
        }
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { flavor };
        }
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(this.flavor);
        }
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (flavor.equals(this.flavor)) {
                return object;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
        }
    }
        
    private static class AttributeSetTransferable extends TypedTransferable<AttributeSet> {
        public AttributeSetTransferable(AttributeSet object) {
            super(object, AS_DATA_FLAVOR);
        }
    }
        
    private static class DimensionTransferable extends TypedTransferable<Dimension> {
        public DimensionTransferable(Dimension object) {
            super(object, DIM_DATA_FLAVOR);
        }
    }
    
    //</editor-fold>
    
}