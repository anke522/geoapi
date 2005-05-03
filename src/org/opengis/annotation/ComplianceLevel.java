/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.annotation;


/**
 * Compliance level for elements. The international standards defines an extensive set of
 * metadata elements. Typically only a subset of the full number of elements is used.
 * However, it is essential that a basic minimum number of metadata elements be maintained
 * for a dataset.
 * 
 * @author ISO 19115
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 * @since 1.1
 */
public enum ComplianceLevel {
    /**
     * Core metadata elements required to identify a dataset, typically for catalogue purposes.
     * This level specifies metadata elements answering the following questions: "Does a dataset
     * on a specific topic exist (what)?", "For a specific place (where)?", "For a specific date
     * or period (when)?" and "A point of contact to learn more about or order the dataset (who)?".
     * Using the recommended {@linkplain Obligation#OPTIONAL optional} elements in addition to the
     * {@linkplain Obligation#MANDATORY mandatory} elements will increase interoperability,
     * allowing users to understand without ambiguity the geographic data and the related metadata
     * provided by either the producer or the distributor.
     */
    CORE,

    /**
     * Indicates a required element of the spatial profile.
     */
    SPATIAL,

    /**
     * Indicates a required element of the feature profile.
     */
    FEATURE,

    /**
     * Indicates a required element of the data provider profile.
     */
    DATA_PROVIDER,

    /**
     * Indicates a required element of the display object profile.
     */
    DISPLAY_OBJECT,

    /**
     * Indicates a required element of the editable display object profile.
     */
    EDITABLE_DISPLAY_OBJECT,

    /**
     * Indicates a required element of the feature display object profile.
     */
    FEATURE_DISPLAY_OBJECT
}