/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.sld;

// J2SE direct dependencies
import java.util.List;

// OpenGIS direct dependencies
import org.opengis.filter.expression.Expression;

// Annotations
import org.opengis.annotation.XmlElement;


/**
 * Indicates how text will be drawn.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/02-070.pdf">Implementation specification 1.0</A>
 * @since GeoAPI 1.1
 */
@XmlElement("TextSymbolizer")
public interface TextSymbol extends Symbol {
    /**
     * Returns the expression that will be evaluated to determine what text is
     * displayed.
     */
    @XmlElement("Label")
    Expression getLabel();

    /**
     * Sets the expression that will be evaluated to determine what text is
     * displayed.
     *
     * @see #getLabel
     */
    @XmlElement("Label")
    void setLabel(Expression label);

    /**
     * Returns a list of Fonts to choose from when rendering this symbol.  The
     * renderer must choose the first one in the list that it is capable of
     * rendering.  The returned list is "live" and can be modified by the
     * caller.  (This is why there is no {@code setFonts} method.)
     */
    @XmlElement("Font")
    List<Font> getFonts();

    /**
     * Returns the object that indicates how the text should be placed with
     * respect to the feature geometry.  This object will either be an instance
     * of {@link LinePlacement} or {@link PointPlacement}.
     */
    @XmlElement("LabelPlacement")
    TextPlacement getPlacement();

    /**
     * Sets the object that indicates how the text should be placed with
     * respect to the feature geometry.
     *
     * @see #getPlacement
     */
    @XmlElement("LabelPlacement")
    void setPlacement(TextPlacement placement);

    /**
     * Returns the object that indicates if a Halo will be drawn around the
     * text.  If null, a halo will not be drawn.
     */
    @XmlElement("Halo")
    Halo getHalo();

    /**
     * Sets the object that indicates if a Halo will be drawn around the
     * text.  If null, a halo will not be drawn.
     *
     * @see #getHalo
     */
    @XmlElement("Halo")
    void setHalo(Halo halo);

    /**
     * Returns the object that indicates how the text will be filled.
     */
    @XmlElement("Fill")
    Fill getFill();

    /**
     * Sets the object that indicates how the text will be filled.
     *
     * @see #getFill
     */
    @XmlElement("Fill")
    void setFill(Fill fill);
}
