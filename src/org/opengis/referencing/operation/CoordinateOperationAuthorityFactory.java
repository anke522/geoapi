/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.operation;

// OpenGIS direct dependencies and extensions
import org.opengis.referencing.AuthorityFactory;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;  // For javadoc
import org.opengis.referencing.crs.CoordinateReferenceSystem;


/**
 * Creates coordinate transformation objects from codes.
 * The codes are maintained by an external authority.
 * A commonly used authority is EPSG, which is also used
 * in the GeoTIFF standard.
 *
 * @UML abstract CT_CoordinateTransformationAuthorityFactory
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-009.pdf">Implementation specification 1.0</A>
 */
public interface CoordinateOperationAuthorityFactory extends AuthorityFactory {
    /**
     * Creates an operation from a single operation code. 
     * The "Authority" and "Code" values of the created object will be set
     * to the authority of this object, and the code specified by the client,
     * respectively. The other metadata values may or may not be set.
     *
     * @param code Coded value for transformation.
     *
     * @UML operation createFromTransformationCode in 1.0 specification
     * @throws NoSuchAuthorityCodeException if the specified <code>code</code> was not found.
     * @throws FactoryException if the object creation failed for some other reason.
     */
    CoordinateOperation createCoordinateOperation(String code) throws FactoryException;

    /**
     * Creates an operation from coordinate reference system codes.
     *
     * @param  sourceCode   Coded value of source coordinate reference system.
     * @param  targetCode   Coded value of target coordinate reference system.
     *
     * @UML operation createFromCoordinateSystemCodes in 1.0 specification
     * @throws NoSuchAuthorityCodeException if a specified code was not found.
     * @throws FactoryException if the object creation failed for some other reason.
     *
     * @revisit For datum shifts (geographic crs to geographic crs) the epsg database may define
     *          many different coordinate operations for a single shift. For example, the EPSG
     *          database defines 20 operations for shifting from a nad27 (epsg code 4267) to wgs84
     *          (4326) CRS's. Possibles solutions:
     *          <ul>
     *            <li>change the return type from CoordinateOperation to some kind of java.util.Collection.
     *                The collection would have a lazy iterator implementation, which creates the
     *                CoordinateOperations only the first time the user invokes Iterator.next().</li>
     *            <li>Allow some kind of Filter argument.</li>
     *            <li>Return only the list of codes rather than the CoordinateOperations.</li>
     *          </ul>
     */
    CoordinateOperation createFromCoordinateReferenceSystemCodes(String sourceCode, String targetCode) throws FactoryException;
}
