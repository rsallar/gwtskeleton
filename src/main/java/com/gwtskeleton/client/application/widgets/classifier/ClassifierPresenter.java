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

package com.gwtskeleton.client.application.widgets.classifier;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtskeleton.client.application.ApplicationPresenter;
import com.gwtskeleton.client.application.place.NameTokens;
import com.gwtskeleton.shared.Book;

public class ClassifierPresenter extends Presenter<ClassifierPresenter.MyView, ClassifierPresenter.MyProxy> implements ClassifierUiHandlers {
	
	@NameToken(NameTokens.CLASSIFIER)
	@ProxyCodeSplit
	public interface MyProxy extends ProxyPlace<ClassifierPresenter> {
	}

	Logger logger = Logger.getLogger(ClassifierPresenter.class.getName());

	public interface MyView extends View, HasUiHandlers<ClassifierUiHandlers> {
		void addDomain(String domain);
		void addIndustry(String industry, int count);

	}

	private final RestDispatch dispatcher;
	private final CredentialService service;
	

	@Inject
	ClassifierPresenter(EventBus eventBus,
			MyView view,
			RestDispatch dispatcher,
			MyProxy proxy,
			CredentialService service
			) {

		super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
		logger.fine("building classifier");
		
		this.dispatcher = dispatcher;
		this.service = service;

		getView().setUiHandlers(this);
		

	}

	@Override
	protected void onBind(){
		logger.fine("binding classifier");
				
		for(int i = 0; i<10; i++){
			getView().addDomain("domain"+i);
			getView().addIndustry("industry"+i, i);
		}


		dispatcher.execute(service.getBooks(), new AsyncCallback<List<Book>>() {
			@Override
			public void onFailure(Throwable caught) {
				//Window.alert(caught.getMessage());
				caught.printStackTrace();
				// getView().setServerResponse("An error occured: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Book> result) {
				//Window.alert(""+result.size());

			}
		});

	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	@Override
	public void save(String portalDomain, String portalIndustry, int widgetIndex) {
		
		/*dispatcher.execute(service.save(portalDomain, portalIndustry), new AsyncCallback<List<FacetedField>>() {
			@Override
			public void onFailure(Throwable caught) {
				//Window.alert(caught.getMessage());
				// getView().setServerResponse("An error occured: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<FacetedField> result) {
				//	Window.alert(""+result.size());
			}
		});*/
		
		logger.fine("domain: " +portalDomain + "  portalIndustry: " + portalIndustry); 
		
	}


}
