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

package com.gwtskeleton.client.application;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtskeleton.client.application.widgets.booksclassifier.BooksClassifierPresenter;
import com.gwtskeleton.client.application.widgets.booksclassifier.BooksClassifierView;
import com.gwtskeleton.client.application.widgets.dropdown.DropdownPresenter;
import com.gwtskeleton.client.application.widgets.dropdown.DropdownView;
import com.gwtskeleton.client.application.widgets.home.HomePresenter;
import com.gwtskeleton.client.application.widgets.home.HomeView;

public class ApplicationModule extends AbstractPresenterModule {
	Logger logger = Logger.getLogger(ApplicationModule.class.getName());
	
	@Override
	protected void configure() {
		GWT.setUncaughtExceptionHandler(new   UncaughtExceptionHandler(){  
			public void onUncaughtException(Throwable e) {  
				Window.alert("exception: " + e.getMessage());
				logger.log(Level.SEVERE, e.getMessage(), e);
			}  
		});
		
		bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,ApplicationPresenter.MyProxy.class);
		bindPresenter(HomePresenter.class, HomePresenter.MyView.class, HomeView.class, HomePresenter.MyProxy.class);
		bindPresenter(DropdownPresenter.class, DropdownPresenter.MyView.class, DropdownView.class, DropdownPresenter.MyProxy.class);
		bindPresenter(BooksClassifierPresenter.class, BooksClassifierPresenter.MyView.class, BooksClassifierView.class, BooksClassifierPresenter.MyProxy.class);
	}
}