/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtskeleton.client.application.widgets.dropdown;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class DropdownView extends ViewWithUiHandlers<DropdowUiHandlers> implements DropdownPresenter.MyView {
	interface Binder extends UiBinder<Widget, DropdownView> {
	}
	Logger logger = Logger.getLogger(DropdownView.class.getName());

		
	@Inject
	DropdownView(Binder uiBinder) {
		logger.fine("building classifier view");

		initWidget(uiBinder.createAndBindUi(this));
	}
}