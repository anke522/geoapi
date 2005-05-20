/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.crs;

// OpenGIS direct dependencies
import org.opengis.referencing.operation.Conversion;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A coordinate reference system that is defined by its coordinate
 * {@linkplain Conversion conversion} from another coordinate reference system
 * (not by a {@linkplain org.opengis.referencing.datum.Datum datum}).
 *
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.0
 */
@UML(identifier="SC_GeneralDerivedCRS", specification=ISO_19111)
public interface GeneralDerivedCRS extends SingleCRS {
    /**
     * Returns the base coordinate reference system.
     */
    @UML(identifier="baseCRS", obligation=MANDATORY, specification=ISO_19111)
    CoordinateReferenceSystem getBaseCRS();

    /**
     * Returns the conversion from the {@linkplain #getBaseCRS base CRS} to this CRS.
     *
     * @rename <code>definedByConversion</code> may be a precise description of the association,
     *         but is confusing as a method name. OGC document 01-009 used <code>toBase()</code>
     *         method name.   By analogy with 01-009, this new interface specifies a method name
     *         which contains the <code>FromBase</code> words.
     */
    @UML(identifier="definedByConversion", obligation=MANDATORY, specification=ISO_19111)
    Conversion getConversionFromBase();
}
