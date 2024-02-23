/*
 * Copyright (C) 2024 FeatJAR-Development-Team
 *
 * This file is part of FeatJAR-feature-model.
 *
 * feature-model is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * feature-model is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with feature-model. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatJAR> for further information.
 */
package de.featjar.feature.model.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.featjar.base.data.Result;
import de.featjar.base.data.Sets;
import de.featjar.base.io.IO;
import de.featjar.feature.model.IFeature;
import de.featjar.feature.model.IFeatureModel;
import de.featjar.feature.model.io.xml.XMLFeatureModelFormat;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class XMLFeatureModelFormulaFormatTest {
    @Test
    public void xmlFeatureModelFormat() {
        Result<IFeatureModel> featureModelResult =
                IO.load(Paths.get("src/test/resources/testFeatureModels/car.xml"), new XMLFeatureModelFormat());
        assertTrue(featureModelResult.isPresent());
        IFeatureModel featureModel = featureModelResult.get();
        String[] featureNames = new String[] {
            "Car",
            "Carbody",
            "Radio",
            "Ports",
            "USB",
            "CD",
            "Navigation",
            "DigitalCards",
            "Europe",
            "USA",
            "GPSAntenna",
            "Bluetooth",
            "Gearbox",
            "Manual",
            "Automatic",
            "GearboxTest"
        };
        assertEquals(
                Sets.of(featureNames),
                featureModel.getFeatures().stream()
                        .map(IFeature::getName)
                        .map(Result::get)
                        .collect(Sets.toSet()));
    }
}
