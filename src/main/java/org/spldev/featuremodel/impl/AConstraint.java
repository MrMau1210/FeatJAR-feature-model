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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spldev.featuremodel.IConstraint;
import org.spldev.featuremodel.IFeature;
import org.spldev.featuremodel.IFeatureModel;
import org.spldev.featuremodel.IPropertyContainer;
import org.spldev.formula.structure.Formula;
import org.spldev.formula.structure.Formulas;
import org.spldev.util.tree.Trees;

/**
 * Represents a propositional constraint below the feature diagram.
 *
 * @author Thomas Thuem
 * @author Florian Proksch
 * @author Stefan Krueger
 * @author Marcus Pinnecke
 * @author Marlen Bernier
 * @author Dawid Szczepanski
 */
public abstract class AConstraint extends AFeatureModelElement implements IConstraint {

	protected final IPropertyContainer propertyContainer;

	protected final List<IFeature> containedFeatureList = new ArrayList<>();

	protected Formula propNode;
	boolean featureSelected;
	boolean isImplicit;
	protected String description;
	/**
	 * Stores the tags of the constraint this group belongs to.
	 */
	protected final Set<String> tags;

	/**
	 * Creates a copy of <code>oldConstraint</code> that belongs to <code>featureModel</code>.
	 *
	 * @param oldConstraint - {@link AConstraint}
	 * @param featureModel - {@link IFeatureModel}
	 */
	protected AConstraint(AConstraint oldConstraint, IFeatureModel featureModel) {
		super(oldConstraint, featureModel);
		setNode(Trees.cloneTree(oldConstraint.propNode));
		featureSelected = oldConstraint.featureSelected;
		isImplicit = oldConstraint.isImplicit;
		description = oldConstraint.description;
		tags = new HashSet<>(oldConstraint.tags);
		propertyContainer = new MapPropertyContainer(oldConstraint.propertyContainer);
	}

	/**
	 * Creates a new {@link AConstraint} for <code>featureModel</code> that has <code>propNode</code> as formula.
	 *
	 * @param featureModel - {@link IFeatureModel}
	 * @param propNode - {@link Node}
	 */
	public AConstraint(IFeatureModel featureModel, Formula propNode) {
		super(featureModel);
		setNode(propNode);
		featureSelected = false;
		isImplicit = false;
		description = "";
		tags = new HashSet<>();
		propertyContainer = new MapPropertyContainer();
	}

	@Override
	public IPropertyContainer getCustomProperties() {
		return propertyContainer;
	}

	/**
	 *
	 * @return All {@link Feature}s contained at this {@link AConstraint}.
	 */
	@Override
	public Collection<IFeature> getContainedFeatures() {
		synchronized (containedFeatureList) {
			return new ArrayList<>(containedFeatureList);
		}
	}

	@Override
	public Formula getNode() {
		return propNode;
	}

	@Override
	public String getDisplayName() {
		return propNode.toString();
	}

	@Override
	public boolean hasHiddenFeatures() {
		for (final IFeature f : getContainedFeatures()) {
			if (f.getStructure().isHidden() || f.getStructure().hasHiddenParent()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setNode(Formula node) {
		propNode = node;
		synchronized (containedFeatureList) {
			containedFeatureList.clear();
			if (propNode != null) {
				for (final String featureName : Formulas.getVariableNames(propNode)) {
					containedFeatureList.add(featureModel.getFeature(featureName));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "AConstraint [propNode=" + propNode + "]";
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Set<String> getTags() {
		synchronized (tags) {
			return new HashSet<>(tags);
		}
	}

	@Override
	public void setTags(Set<String> tags) {
		synchronized (this.tags) {
			this.tags.clear();
			this.tags.addAll(tags);
		}
	}
}
