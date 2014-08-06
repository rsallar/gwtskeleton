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

package com.gwtskeleton.client.application.dropdown;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtskeleton.shared.Book;

public class DropdownPresenter extends PresenterWidget<DropdownPresenter.MyView>
implements DropdowUiHandlers {

	Logger logger = Logger.getLogger(DropdownPresenter.class.getName());

	public interface MyView extends View, HasUiHandlers<DropdowUiHandlers> {
		

	}

	

	@Inject
	DropdownPresenter(EventBus eventBus,
			MyView view
			) {

		super(eventBus, view);
		logger.fine("building classifier");
		
		getView().setUiHandlers(this);
		
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

}
