/*******************************************************************************
 * Copyright (c) 2009 Martin Lippert and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Martin Lippert                   initial implementation      
 *******************************************************************************/

package org.eclipse.equinox.weaving.springweaver;

import java.lang.instrument.ClassFileTransformer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;

public class ClassFileTransformerRegistry {

	private Map<Bundle, List<ClassFileTransformer>> transformers;

	public ClassFileTransformerRegistry() {
		this.transformers = new HashMap<Bundle, List<ClassFileTransformer>>();
	}

	public void addTransformer(Bundle bundle, ClassFileTransformer transformer) {
		List<ClassFileTransformer> concreteTransformers = this.transformers
				.get(bundle);
		if (concreteTransformers == null) {
			concreteTransformers = new ArrayList<ClassFileTransformer>();
			this.transformers.put(bundle, concreteTransformers);
		}

		concreteTransformers.add(transformer);
	}

	public ClassFileTransformer[] getTransformers(Bundle bundle) {
		List<ClassFileTransformer> result = this.transformers.get(bundle);
		if (result != null)
			return (ClassFileTransformer[]) result
					.toArray(new ClassFileTransformer[result.size()]);
		else
			return new ClassFileTransformer[0];
	}

}
