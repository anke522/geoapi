/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2012-2015 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.test.referencing.gigs;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import javax.measure.unit.NonSI;
import javax.measure.quantity.Angle;
import javax.measure.quantity.Length;
import javax.measure.quantity.Dimensionless;

import org.opengis.util.Factory;
import org.opengis.util.FactoryException;
import org.opengis.util.NoSuchIdentifierException;
import org.opengis.parameter.*;
import org.opengis.referencing.cs.*;
import org.opengis.referencing.crs.*;
import org.opengis.referencing.datum.*;
import org.opengis.referencing.operation.*;
import org.opengis.referencing.IdentifiedObject;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.test.Configuration;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assume.*;
import static org.opengis.test.Assert.*;


/**
 * Test for new CRS definition. The test procedures in this series are designed
 * to evaluate the software’s capabilities for adding user-defined CRS and
 * transformation definitions
 *
 * @see org.opengis.test.referencing.ObjectFactoryTest
 * @see org.opengis.test.TestSuite
 *
 * @author  Alexis Manin (Geomatys)
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 *
 * @deprecated Each test method in this class will be soon replaced by its own test class.
 *             The intend is to give to software providers more fine-grain control on the tests.
 */
@Deprecated
@RunWith(Parameterized.class)
public strictfp class Series3000Test extends UserObjectFactoryTestCase {
    /**
     * Factory to use for build user's coordinate reference systems,
     * or {@code null} if none.
     */
    protected final CRSFactory crsFactory;

    /**
     * Factory to use for build user's coordinate systems and axes,
     * or {@code null} if none.
     */
    protected final CSFactory csFactory;

    /**
     * Factory to use for build user's datum, ellipsoid and prime meridians,
     * or {@code null} if none.
     */
    protected final DatumFactory datumFactory;

    /**
     * Factory to use for build user's coordinate operations,
     * or {@code null} if none.
     */
    protected final CoordinateOperationFactory copFactory;

    /**
     * Returns a default set of factories to use for running the tests. Those factories are given
     * in arguments to the constructor when this test class is instantiated directly by JUnit (for
     * example as a {@linkplain org.junit.runners.Suite.SuiteClasses suite} element), instead than
     * subclassed by the implementor. The factories are fetched as documented in the
     * {@link #factories(Class[])} javadoc.
     *
     * @return The default set of arguments to be given to the {@code Series3000Test} constructor.
     */
    @Parameterized.Parameters
    @SuppressWarnings("unchecked")
    public static List<Factory[]> factories() {
        return factories(CRSFactory.class, CSFactory.class, DatumFactory.class,
                CoordinateOperationFactory.class);
    }

    /**
     * Creates a new test using the given factories. If a given factory is {@code null},
     * then the tests which depend on it will be skipped.
     *
     * @param crsFactory   Factory for creating {@link CoordinateReferenceSystem} instances.
     * @param csFactory    Factory for creating {@link CoordinateSystem} instances.
     * @param datumFactory Factory for creating {@link Datum} instances.
     * @param copFactory   Factory for creating {@link CoordinateOperation} instances.
     */
    public Series3000Test(final CRSFactory crsFactory, final CSFactory csFactory,
            final DatumFactory datumFactory, final CoordinateOperationFactory copFactory)
    {
        super(crsFactory, csFactory, datumFactory, copFactory);
        this.crsFactory   = crsFactory;
        this.csFactory    = csFactory;
        this.datumFactory = datumFactory;
        this.copFactory   = copFactory;
    }

    /**
     * Returns information about the configuration of the test which has been run.
     * This method returns a map containing:
     *
     * <ul>
     *   <li>All the following values associated to the {@link org.opengis.test.Configuration.Key} of the same name:
     *     <ul>
     *       <li>{@linkplain #crsFactory}</li>
     *       <li>{@linkplain #csFactory}</li>
     *       <li>{@linkplain #datumFactory}</li>
     *       <li>{@linkplain #copFactory}</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @return The configuration of the test being run.
     */
    @Override
    public Configuration configuration() {
        final Configuration op = super.configuration();
        assertNull(op.put(Configuration.Key.crsFactory,   crsFactory));
        assertNull(op.put(Configuration.Key.csFactory,    csFactory));
        assertNull(op.put(Configuration.Key.datumFactory, datumFactory));
        assertNull(op.put(Configuration.Key.copFactory,   copFactory));
        return op;
    }

    /**
     * Returns the linear unit (compatible with metres) of the given name.
     *
     * @param  name The unit name.
     * @return The linear unit for the given name, or {@code null} if unknown.
     */
    private static Unit<Length> parseLinearUnit(final String name) {
        if (name.equalsIgnoreCase("metre"))          return SI.METRE;
        if (name.equalsIgnoreCase("kilometre"))      return SI.KILOMETRE;
        if (name.equalsIgnoreCase("US survey foot")) return NonSI.FOOT_SURVEY_US;
        if (name.equalsIgnoreCase("ft(US)"))         return NonSI.FOOT_SURVEY_US;
        if (name.equalsIgnoreCase("foot"))           return NonSI.FOOT;
        return null;
    }

    /**
     * Retrieves the angular unit (compatible with degrees) of the given name.
     *
     * @param  name The unit name.
     * @return The angular unit for the given name, or {@code null} if unknown.
     */
    private static Unit<Angle> parseAngularUnit(final String name) {
        if (name.equalsIgnoreCase("degree"))      return NonSI.DEGREE_ANGLE;
        if (name.equalsIgnoreCase("grad"))        return NonSI.GRADE;
        if (name.equalsIgnoreCase("arc-second"))  return NonSI.SECOND_ANGLE;
        if (name.equalsIgnoreCase("microradian")) return NonSI.CENTIRADIAN;
        return null;
    }

    /**
     * Retrieves the scale unit (dimensionless) of the given name.
     *
     * @param  name The unit name.
     * @return The scale unit for the given name, or {@code null} if unknown.
     */
    private static Unit<Dimensionless> parseScaleUnit(final String name) {
        if (name.equalsIgnoreCase("unity"))             return Unit.ONE;
        if (name.equalsIgnoreCase("parts per million")) return Unit.ONE.divide(1000000);
        return null;
    }

    /**
     * Retrieves the unit of the given name.
     *
     * @param  name The unit name.
     * @return The unit for the given name, or {@code null} if unknown.
     */
    private static Unit<?> parseUnit(final String name) {
        Unit<?> unit = parseLinearUnit(name);
        if (unit == null) {
            unit = parseAngularUnit(name);
            if (unit == null) {
                unit = parseScaleUnit(name);
            }
        }
        return unit;
    }

    /**
     * Creates a map containing the given name and code, to be given to object factories.
     *
     * @param  name The name of the object to create.
     * @param  code The GIGS (not EPSG) code of the object to create.
     * @return Properties to be given to the {@code create(…)} method.
     */
    private static Map<String,Object> properties(final String name, final int code) {
        final Map<String,Object> properties = new HashMap<String,Object>(4);
        properties.put(IdentifiedObject.IDENTIFIERS_KEY, new GIGSIdentifier(code));
        properties.put(IdentifiedObject.NAME_KEY, name);
        return properties;
    }

    /**
     * Verifies that the software allows correct definition of a user-defined map projection.
     *
     * <table cellpadding="3" summary="Test description"><tr>
     *   <th nowrap align="left" valign="top">Test method:</th>
     *   <td>Create user-defined projection for each of several different map projections.
     *   </td>
     * </tr><tr>
     *   <th nowrap align="left" valign="top">Test data:</th>
     *   <td>EPSG Dataset and file {@svnurl gigs/GIGS_3005_userProjection.csv}.</td>
     * </tr><tr>
     *   <th nowrap align="left" valign="top">Tested API:</th>
     *   <td>{@link CoordinateOperationFactory#getOperationMethod(String)} and<br>
     *       {@link CoordinateOperationFactory#createDefiningConversion(Map, OperationMethod, ParameterValueGroup)}.</td>
     * </tr><tr>
     *   <th nowrap align="left" valign="top">Expected result:</th>
     *   <td>The geoscience software should accept the test data. The order in which the projection
     *       parameters are entered is not critical, although that given in the test dataset is
     *       recommended.</td>
     * </tr></table>
     *
     * @throws FactoryException If an error (other than {@linkplain NoSuchAuthorityCodeException
     *         unsupported code}) occurred while creating a unit from an EPSG code.
     */
    @Test
    public void test3005() throws FactoryException{
        test3005(null);
    }

    /**
     * Creates the projections and optionally tests them.
     * The behavior of this method depends on whether {@code objects} is null or not:
     *
     * <ul>
     *   <li>If {@code null}, then all projections will be created and tested.</li>
     *   <li>If non-null, then only the ones enumerated in the keys will be created,
     *       but none of them will be tested. The created objects will be stored in the
     *       values of that map.</li>
     * </ul>
     *
     * @param  objects On input, the GIGS codes of objects to create. On output if non-null,
     *         the created but untested objects. If {@code null}, then all objects will be
     *         created and tested.
     * @throws FactoryException If an error occurred while creating an object.
     */
    private void test3005(final Map<String,Conversion> objects) throws FactoryException{
        assumeNotNull(copFactory);
        final ExpectedData data = new ExpectedData("GIGS_3005_userProjection.csv",
                Integer.class,     // [ 0]: GIGS projection code
                String.class,      // [ 1]: GIGS projection name
                String.class,      // [ 2]: EPSG Conversion method name
                String.class,      // [ 3]: Parameter 1 name
                String.class,      // [ 4]: Parameter 1 value
                String.class,      // [ 5]: Parameter 1 unit
                Double.class,      // [ 6]: Parameter 1 value in decimal degrees
                String.class,      // [ 7]: Parameter 2 name
                String.class,      // [ 8]: Parameter 2 value
                String.class,      // [ 9]: Parameter 2 unit
                Double.class,      // [10]: Parameter 2 value in decimal degrees
                String.class,      // [11]: Parameter 3 name
                String.class,      // [12]: Parameter 3 value
                String.class,      // [13]: Parameter 3 unit
                Double.class,      // [14]: Parameter 3 value in decimal degrees
                String.class,      // [15]: Parameter 4 name
                String.class,      // [16]: Parameter 4 value
                String.class,      // [17]: Parameter 4 unit
                Double.class,      // [18]: Parameter 4 value in decimal degrees
                String.class,      // [19]: Parameter 5 name
                String.class,      // [20]: Parameter 5 value
                String.class,      // [21]: Parameter 5 unit
                String.class,      // [22]: Parameter 6 name
                String.class,      // [23]: Parameter 6 value
                String.class,      // [24]: Parameter 6 unit
                String.class,      // [25]: Parameter 7 name
                String.class,      // [26]: Parameter 7 value
                String.class);     // [27]: Parameter 7 unit

        final StringBuilder prefix = new StringBuilder();
        while (data.next()) {
            final String name = data.getString(1);
            if (objects != null && !objects.containsKey(name)) {
                // If the current row is not for an object on the list of
                // items requested by the caller, skip the object creation.
                continue;
            }
            /*
             * Get the OperationMethod defined by the library. Libraries are not required
             * to implement every possible operation methods, in which case unimplemented
             * methods will be reported.  If tests are enabled, then this block will test
             * the following properties:
             *
             *  - The number of source dimensions
             *  - The number of target dimensions
             */
            final int    code       = data.getInt(0);
            final String methodName = data.getString(2);
            final OperationMethod method;
            try {
                method = copFactory.getOperationMethod(methodName);
            } catch (NoSuchIdentifierException e) {
                // Set the type to Projection rather than OperationMethod
                // because the numerical code value is for the projection.
                unsupportedCode(Projection.class, code);
                continue;
            }
            prefix.setLength(0);
            prefix.append("OperationMethod[\"").append(methodName).append("\"]");
            assertNotNull(prefix.toString(), method);
            prefix.append('.');
            if (objects == null) { // Javadoc said that we test only if that map is null.
                validators.validate(method);
                final int prefixLength = prefix.length();
                // Do not test the name, because libraries often have their own hard-coded collection of OperationMethod.
                assertEquals(message(prefix, "getSourceDimensions()"), Integer.valueOf(2), method.getSourceDimensions());
                assertEquals(message(prefix, "getTargetDimensions()"), Integer.valueOf(2), method.getTargetDimensions());
                prefix.setLength(prefixLength);
            }
            /*
             * Create the parameter values. This block opportunistically stores information in
             * the 'parameters' array for comparison purpose after we created the projection.
             * If tests are enabled, then this block will test the following properties.
             *
             *  - Class of values
             *  - Minimum number of occurrences (shall be 0 or 1)
             *  - Maximum number of occurrences (shall be 1)
             */
            prefix.append("parameter[\"");
            int prefixLength = prefix.length();
            final ParameterValueGroup  group = method.getParameters().createValue();
            final ParameterInfo[] parameters = new ParameterInfo[7];
            for (int paramNum = 0; paramNum < parameters.length; paramNum++) {
                final int columnOffset = 3              // The first parameter starts at column 3.
                        + Math.min(paramNum,   4) * 4   // Parameter 1 to 4 use 4 columns.
                        + Math.max(paramNum-4, 0) * 3;  // Parameter 5 to 7 use 4 columns.

                final String paramName = data.getString(columnOffset);
                if (paramName == null) {
                    continue;
                }
                final double value;
                final Unit<?> unit;
                final String unitName = data.getString(columnOffset + 2);
                if (unitName.equalsIgnoreCase("sexagesimal degree")) {
                    value = data.getDouble(columnOffset + 3);
                    unit = NonSI.DEGREE_ANGLE;
                } else {
                    unit = parseUnit(unitName);
                    assertNotNull(unitName, unit); // Failure here would be a geoapi-conformance bug.
                    value = Double.valueOf(data.getString(columnOffset + 1));
                }
                final ParameterValue<?> param = group.parameter(paramName);
                prefix.setLength(prefixLength);
                prefix.append(paramName).append("\"]");
                assertNotNull(prefix.toString(), param);
                param.setValue(value, unit);
                if (objects == null) { // Javadoc said that we test only if that map is null.
                    validators.validate(param);
                    prefix.append(".getDescriptor().");
                    final ParameterDescriptor<?> descriptor = param.getDescriptor();
                    if (descriptor != null) { // It is validator's jobs to decide if null is allowed.
                        final Class<?> valueClass = descriptor.getValueClass();
                        if (!Number.class.isAssignableFrom(valueClass)) {
                            fail(prefix.append("getValueClass(): ").append(valueClass)
                                    .append(" is not assignable to Number.").toString());
                        }
                        assertBetween(message(prefix, "getMinimumOccurs"), 0, 1, descriptor.getMinimumOccurs());
                        assertEquals (message(prefix, "getMaximumOccurs"),    1, descriptor.getMaximumOccurs());
                    }
                }
                parameters[paramNum] = new ParameterInfo(paramName, value, unit);
            }
            /*
             * Create the projection.
             */
            final Map<String,Object> properties = properties(name, code);
            final Conversion projection = copFactory.createDefiningConversion(properties, method, group);
            prefix.setLength(0);
            prefix.append("Projection[\"").append(name).append("\"]");
            assertNotNull(prefix.toString(), projection);
            if (objects != null) {
                assertNull("An object already exists for the same name.", objects.put(name, projection));
                continue;
            }
            /*
             * Now verify the properties of the projection we just created. We require the parameter
             * group to contain at least the values that we gave to it. If the library defines some
             * additional parameters, then those extra parameters will be ignored.
             */
            validators.validate(projection);
            prefix.append('.');
            assertEquals(message(prefix, "getName()"), name, getName(projection));
            assertContainsCode(message(prefix, "getIdentifiers()"), "GIGS", code, projection.getIdentifiers());
            assertContainsNameOrAlias(message(prefix, "getMethod()"), methodName, projection.getMethod());
            final ParameterValueGroup projectionParameters = projection.getParameterValues();
            prefix.append("getParameterValues()");
            assertNotNull(prefix.toString(), projectionParameters);
            prefix.append(".parameter(\"");
            prefixLength = prefix.length();
            for (final ParameterInfo info : parameters) {
                if (info != null) {
                    prefix.setLength(prefixLength);
                    prefix.append(info.name).append("\")");
                    final ParameterValue<?> param = projectionParameters.parameter(info.name);
                    assertNotNull(prefix.toString(), param);
                    prefix.append(".getValue(").append(info.unit).append(')');
                    assertEquals(prefix.toString(), info.value, param.doubleValue(info.unit),
                            TOLERANCE * Math.abs(info.value));
                }
            }
        }
    }

    @Override
    public Object getIdentifiedObject() throws FactoryException {
        throw new UnsupportedOperationException();
    }
}
