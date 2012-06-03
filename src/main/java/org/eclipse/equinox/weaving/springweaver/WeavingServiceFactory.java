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

import org.eclipse.equinox.service.weaving.ISupplementerRegistry;
import org.eclipse.equinox.service.weaving.IWeavingService;
import org.eclipse.equinox.service.weaving.IWeavingServiceFactory;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.State;
import org.osgi.framework.Bundle;

/**
 * The factory to create weavers for ClassFileTransformer components.
 * 
 * @author Martin Lippert
 */
public class WeavingServiceFactory implements IWeavingServiceFactory {

    private final ClassFileTransformerRegistry registry;

	public WeavingServiceFactory(ClassFileTransformerRegistry registry) {
		this.registry = registry;
    }

    /**
     * @see org.eclipse.equinox.service.weaving.IWeavingServiceFactory#createWeavingService(java.lang.ClassLoader,
     *      org.osgi.framework.Bundle, org.eclipse.osgi.service.resolver.State,
     *      org.eclipse.osgi.service.resolver.BundleDescription,
     *      org.eclipse.equinox.service.weaving.ISupplementerRegistry)
     */
    public IWeavingService createWeavingService(final ClassLoader loader,
            final Bundle bundle, final State resolverState,
            final BundleDescription bundleDesciption,
            final ISupplementerRegistry supplementerRegistry) {
        return new WeavingService(bundle, registry);
    }

}
