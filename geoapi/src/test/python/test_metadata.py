#
#    GeoAPI - Programming interfaces for OGC/ISO standards
#    http://www.geoapi.org
#
#    Copyright (C) 2018 Open Geospatial Consortium, Inc.
#    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
#

#
# To run this demo, the PYTHONPATH environmental variable must be set to
# the "geoapi/src/main/python" directory, preferably using absolute path.
# Example on Unix systems:
#
#     export PYTHONPATH=/path_to_geoapi/geoapi/src/main/python
#

from ogc.metadata.base           import Metadata
from ogc.metadata.identification import DataIdentification
from ogc.metadata.citation       import Citation

#
# Information about a data set: title, where they are located, authors, etc.
# For this demo, we provide only the title and an abstract. Since this demo
# establishes a one-to-one relationship between data identification and the
# citation (i.e. the title), we implement both interfaces in the same class
# for convenience.
#
class IdentificationMock(DataIdentification, Citation):
    @property
    def citation(self):
        return self

    @property
    def title(self):
        return "Pseudo data"

    @property
    def abstract(self):
        return "A metadata with hard-coded values for demonstration purpose."

    @property
    def language(self):
        return "English"
        # TODO: what is the Python equivalent of java.util.Locale?

#
# The root metadata object. We must provide a body for all methods
# annotated with @abstractproperty in ogc.metadata.base.Metadata.
#
class MetadataMock(Metadata):
    @property
    def identificationInfo(self):
        return [IdentificationMock()]

    @property
    def contact(self):
        return []

    @property
    def dateInfo(self):
        return []

#
# We could execute the demo with the following code:
#
#     m = MetadataMock()
#     print(m.identificationInfo.citation.title)
#
# Instead we run as a unit test, for possible inclusion
# in Maven build. To execute on command-line:
#
#     python -m unittest discover
#

import unittest

class TestMetadata(unittest.TestCase):
    def test_title(self):
        m = MetadataMock()
        self.assertEqual(m.identificationInfo[0].citation.title, "Pseudo data")
