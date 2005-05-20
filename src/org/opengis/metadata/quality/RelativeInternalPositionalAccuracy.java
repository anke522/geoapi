/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.quality;

// J2SE extensions
import javax.units.Unit;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Closeness of the relative positions of features in the scope to their respective
 * relative positions accepted as or being true.
 *  
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.1
 */
@UML(identifier="DQ_RelativeInternalPositionalAccuracy", specification=ISO_19115)
public interface RelativeInternalPositionalAccuracy extends PositionalAccuracy {
}
