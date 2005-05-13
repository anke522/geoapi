/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2001 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.cs;

// JDK's classes
import java.rmi.RemoteException;


/**
 * A 2D cartographic coordinate system.
 *
 * @version 1.01
 * @since   1.00
 * @author Martin Daly
 *
 * @deprecated Replaced by {@link org.opengis.referencing.crs.ProjectedCRS}.
 */
@Deprecated
public interface CS_ProjectedCoordinateSystem extends CS_HorizontalCoordinateSystem {
    /**
     * Returns the GeographicCoordinateSystem.
     *
     * @throws RemoteException if a remote method call failed.
     *
     * @deprecated Replaced by {@link org.opengis.referencing.crs.ProjectedCRS#getBaseCRS}.
     */
    @Deprecated
    CS_GeographicCoordinateSystem getGeographicCoordinateSystem() throws RemoteException;

    /**
     * Returns the LinearUnits.
     * The linear unit must be the same as the {@link CS_CoordinateSystem} units.
     *
     * @throws RemoteException if a remote method call failed.
     *
     * @deprecated Replaced by {@link org.opengis.referencing.cs.CoordinateSystemAxis#getUnit}.
     */
    @Deprecated
    CS_LinearUnit getLinearUnit() throws RemoteException;

    /**
     * Gets the projection.
     *
     * @throws RemoteException if a remote method call failed.
     *
     * @deprecated Replaced by {@link org.opengis.referencing.crs.ProjectedCRS#getConversionFromBase}.
     */
    @Deprecated
    CS_Projection getProjection() throws RemoteException;
}
