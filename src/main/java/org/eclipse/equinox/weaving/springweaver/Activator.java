/*******************************************************************************
 * Copyright (c) 2009 Martin Lippert and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Martin Lippert                   initial implementation 
 *   Rich Mayfield                    OSGi Weaving Service implementation     
 *******************************************************************************/

package org.eclipse.equinox.weaving.springweaver;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.hooks.weaving.WeavingHook;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.equinox.weaving.springweaver";

	public static boolean verbose = Boolean
			.getBoolean("org.aspectj.osgi.verbose");

	private ClassFileTransformerRegistry registry;

	private static Activator instance;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		instance = this;
		
        if (verbose)
            System.err
                    .println("[org.eclipse.equinox.weaving.springweaver] info Starting Spring LoadTimeWeaver service ...");
        
        this.registry = new ClassFileTransformerRegistry();
        
        final String serviceName = WeavingHook.class.getName();
        final WeavingHook weavingHook = new WeavingHookImpl(registry);
        final Dictionary<String, ?> props = new Hashtable<String, String>();
        context.registerService(serviceName, weavingHook, props);
	}

	public void stop(BundleContext context) throws Exception {
	}
	
	public ClassFileTransformerRegistry getTransformerRegistry() {
		return this.registry;
	}
	
	public static Activator getInstance() {
		return instance;
	}

}
