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

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.BundleContext;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.SimpleThrowawayClassLoader;
import org.springframework.util.ClassUtils;

public class EquinoxAspectsLoadTimeWeaver implements LoadTimeWeaver, BundleContextAware {
	
	private final ClassLoader classLoader;
	private BundleContext bundleContext;

	public EquinoxAspectsLoadTimeWeaver() {
		this(ClassUtils.getDefaultClassLoader());
	}

	public EquinoxAspectsLoadTimeWeaver(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void addTransformer(ClassFileTransformer transformer) {
		Activator.getInstance().getTransformerRegistry().addTransformer(this.bundleContext.getBundle(), transformer);
		System.out.println("transformer added; " + transformer);
	}

	@Override
	public ClassLoader getInstrumentableClassLoader() {
		return this.classLoader;
	}

	@Override
	public ClassLoader getThrowawayClassLoader() {
		return new SimpleThrowawayClassLoader(getInstrumentableClassLoader());
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

}
