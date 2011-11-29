/*******************************************************************************
 * Copyright (c) 2011 Subgraph.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Subgraph - initial API and implementation
 ******************************************************************************/
package com.subgraph.vega.internal.model.macros;

import java.util.ArrayList;
import java.util.Collection;

import com.db4o.activation.ActivationPurpose;
import com.db4o.activation.Activator;
import com.db4o.collections.ActivatableArrayList;
import com.db4o.ta.Activatable;
import com.subgraph.vega.api.model.macros.IHttpMacro;
import com.subgraph.vega.api.model.macros.IHttpMacroItem;
import com.subgraph.vega.api.model.requests.IRequestLogRecord;

public class HttpMacro implements IHttpMacro, Activatable {
	private transient Activator activator;
	private String name;
	private ActivatableArrayList<IHttpMacroItem> macroItemList;

	public HttpMacro() {
		macroItemList = new ActivatableArrayList<IHttpMacroItem>();
	}

	@Override
	public void setName(String name) {
		activate(ActivationPurpose.READ);
		this.name = name;
		activate(ActivationPurpose.WRITE);
	}

	@Override
	public String getName() {
		activate(ActivationPurpose.READ);
		return name;
	}

	@Override
	public IHttpMacroItem createMacroItem(IRequestLogRecord record) {
		activate(ActivationPurpose.READ);
		HttpMacroItem macroItem = new HttpMacroItem(record);
		macroItemList.add(macroItem);
		return macroItem;
	}

	@Override
	public Collection<IHttpMacroItem> getMacroItems() {
		activate(ActivationPurpose.READ);
		return new ArrayList<IHttpMacroItem>(macroItemList);
	}

	@Override
	public void activate(ActivationPurpose activationPurpose) {
		if (activator != null) {
			activator.activate(activationPurpose);
		}				
	}

	@Override
	public void bind(Activator activator) {
		if (this.activator == activator) {
			return;
		}
		if (activator != null && this.activator != null) {
			throw new IllegalStateException("Object can only be bound to one activator");
		}
		this.activator = activator;			
	}

}
