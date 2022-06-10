/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2019  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 *
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package org.spldev.featuremodel.impl;

import org.prop4j.Node;

import org.spldev.featuremodel.IConstraint;
import org.spldev.featuremodel.IFeatureModel;

/**
 *
 * @author Sebastian Krieter
 */
public class MultiConstraint extends Constraint {

	private int type = MultiFeature.TYPE_INTERN;

	public MultiConstraint(IFeatureModel featureModel, Node propNode) {
		super(featureModel, propNode);
	}

	public MultiConstraint(MultiConstraint extendedConstraint, IFeatureModel newFeatureModel) {
		super(extendedConstraint, newFeatureModel);
		type = extendedConstraint.type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isFromExtern() {
		return type != MultiFeature.TYPE_INTERN;
	}

	@Override
	public IConstraint clone(IFeatureModel newFeatureModel) {
		return new MultiConstraint(this, newFeatureModel);
	}

}
