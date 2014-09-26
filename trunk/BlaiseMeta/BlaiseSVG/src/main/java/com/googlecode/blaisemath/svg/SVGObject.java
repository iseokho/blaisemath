/**
 * SVGObject.java
 * Created Sep 26, 2014
 */

package com.googlecode.blaisemath.svg;

/*
 * #%L
 * BlaiseGraphics
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

import com.googlecode.blaisemath.style.AttributeSet;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 *   Common interface for SVG types.
 * </p>
 * @author elisha
 */
public abstract class SVGObject {
    
    public static final String ID_ATTR = "id";

    private final String tag;
    private AttributeSet style = new AttributeSet();

    public SVGObject(String tag) {
        this.tag = tag;
    }

    
    //<editor-fold defaultstate="collapsed" desc="PROPERTY PATTERNS">
    //
    // PROPERTY PATTERNS
    //
    
    /**
     * Get the tag associated with the object.
     * @return tag
     */
    @XmlTransient
    public String getTag() {
        return tag;
    }
    
    /**
     * Get the id associated with the object.
     * @return id
     */
    @XmlAttribute
    public String getId() {
        return style.getString(ID_ATTR);
    }
    
    /**
     * Set the id associated with the object.
     * @param id new id
     */
    public void setId(String id) {
        style.put(ID_ATTR, id);
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(AttributeSetAdapter.class)
    public AttributeSet getStyle() {
        return style;
    }

    public void setStyle(AttributeSet style) {
        this.style = style;
    }
    
    //</editor-fold>
    
}
