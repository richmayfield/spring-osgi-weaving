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

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;

import org.osgi.framework.Bundle;
import org.osgi.framework.hooks.weaving.WeavingHook;
import org.osgi.framework.hooks.weaving.WovenClass;

public class WeavingHookImpl implements WeavingHook {

    private final ClassFileTransformerRegistry registry;

    public WeavingHookImpl(ClassFileTransformerRegistry registry) {
        this.registry = registry;
    }

    /**
     * @see org.osgi.framework.hooks.weaving.WeavingHook#weave(org.osgi.framework.hooks.weaving.WovenClass)
     */
    @Override
    public void weave(WovenClass wc) {
        byte[] result = null;

        Bundle bundle = wc.getBundleWiring().getBundle();
        ClassFileTransformer[] transformers = this.registry.getTransformers(bundle);
        for (int i = 0; i < transformers.length; i++) {
            try {
                byte[] transformed = transformers[i].transform(
                        wc.getBundleWiring().getClassLoader(), wc.getClassName(), null, null,
                        (result != null ? result : wc.getBytes()));
                if (transformed != null) {
                    result = transformed;
                }
            } catch (IllegalClassFormatException e) {
                e.printStackTrace();
            }
        }

        if (result != null) {
            wc.setBytes(result);
        }
    }
}
