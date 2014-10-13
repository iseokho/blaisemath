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


import static com.google.common.base.Preconditions.checkState;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.googlecode.blaisemath.firestarter.PropertySheetDialog;
import com.googlecode.blaisemath.graphics.core.Graphic;
import com.googlecode.blaisemath.graphics.core.GraphicComposite;
import com.googlecode.blaisemath.graphics.swing.JGraphicComponent;
import com.googlecode.blaisemath.style.AttributeSet;
import com.googlecode.blaisemath.style.editor.AttributeSetPropertyModel;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Window;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingUtilities;

/**
 * Utility library with various actions.
 * @author elisha
 */
public class BlaiseSketchActions {
    
    private BlaiseSketchActions() {
    }

    public static void editGraphic(Graphic<Graphics2D> src, JGraphicComponent comp) {
        Window window = SwingUtilities.getWindowAncestor(comp);
        PropertySheetDialog dialog = new PropertySheetDialog((Frame) window, true, src);
        dialog.setVisible(true);
    }

    public static void editGraphicStyle(Graphic<Graphics2D> src, JGraphicComponent comp) {
        Window window = SwingUtilities.getWindowAncestor(comp);
        AttributeSet style = src.getStyle();
        Map<String,Class<?>> styleClassMap = Maps.newLinkedHashMap();
        for (String k : style.getAllAttributes()) {
            styleClassMap.put(k, style.get(k).getClass());
        }
        AttributeSetPropertyModel pModel = new AttributeSetPropertyModel(style, styleClassMap);
        PropertySheetDialog dialog = new PropertySheetDialog((Frame) window, true, style, pModel);
        dialog.setVisible(true);
    }

    public static void deleteGraphic(Graphic<Graphics2D> src, JGraphicComponent comp) {
        comp.getSelectionModel().removeSelection(src);
        GraphicComposite gc = src.getParent();
        gc.removeGraphic(src);
    }
    
    public static void deleteSelected(JGraphicComponent comp) {
        Set<Graphic<Graphics2D>> selection = comp.getSelectionModel().getSelection();
        for (Graphic g : selection) {
            GraphicComposite gc = g.getParent();
            assert gc != null;
            gc.removeGraphics(selection);
        }
        comp.getSelectionModel().setSelection(Collections.EMPTY_SET);
    }
    
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
    
}