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

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.util.Map;

import org.eclipse.equinox.service.weaving.IWeavingService;
import org.osgi.framework.Bundle;

public class WeavingService implements IWeavingService {

	private final ClassFileTransformerRegistry registry;
	private final Bundle bundle;

	public WeavingService(final Bundle bundle,
			ClassFileTransformerRegistry registry) {
		this.bundle = bundle;
		this.registry = registry;
	}

	/**
	 * @see org.eclipse.equinox.service.weaving.IWeavingService#flushGeneratedClasses(java.lang.ClassLoader)
	 */
	public void flushGeneratedClasses(final ClassLoader loader) {
	}

	/**
	 * @see org.eclipse.equinox.service.weaving.IWeavingService#generatedClassesExistFor(java.lang.ClassLoader,
	 *      java.lang.String)
	 */
	public boolean generatedClassesExistFor(final ClassLoader loader,
			final String className) {
		return false;
	}

	/**
	 * @see org.eclipse.equinox.service.weaving.IWeavingService#getGeneratedClassesFor(java.lang.String)
	 */
	public Map<String, byte[]> getGeneratedClassesFor(final String className) {
		return null;
	}

	/**
	 * @see org.eclipse.equinox.service.weaving.IWeavingService#getKey()
	 */
	public String getKey() {
		return "spring";
	}

	/**
	 * @see org.eclipse.equinox.service.weaving.IWeavingService#preProcess(java.lang.String,
	 *      byte[], java.lang.ClassLoader)
	 */
	public byte[] preProcess(final String name, final byte[] classbytes,
			final ClassLoader loader) throws IOException {

		byte[] result = null;

		ClassFileTransformer[] transformers = this.registry
				.getTransformers(this.bundle);
		for (int i = 0; i < transformers.length; i++) {
			try {
				result = transformers[i].transform(loader, name, null, null,
						classbytes);
			} catch (IllegalClassFormatException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
