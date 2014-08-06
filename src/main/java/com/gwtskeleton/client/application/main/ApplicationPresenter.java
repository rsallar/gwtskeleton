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

package com.gwtskeleton.client.application.main;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtskeleton.client.application.classifier.ClassifierPresenter;
import com.gwtskeleton.client.application.dropdown.DropdownPresenter;
import com.gwtskeleton.client.place.NameTokens;
import com.gwtskeleton.client.place.TokenParameters;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
        implements ApplicationUiHandlers {
	
	Logger logger = Logger.getLogger(ApplicationPresenter.class.getName());
	
    @ProxyStandard
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<ApplicationPresenter> {
    }
    
    public enum Tab{
    	TAB1, TAB2, TAB3;
    }

    public static final Object MainSlot = new Object();
    
    public interface MyView extends View, HasUiHandlers<ApplicationUiHandlers> {
        void resetAndFocus();

        void setError(String errorText);
    }

    private final PlaceManager placeManager;
    
    @Inject ClassifierPresenter classifier;
    @Inject DropdownPresenter dropdown;
    
    PresenterWidget<?> current = classifier;

    @Inject
    ApplicationPresenter(EventBus eventBus,
                         MyView view,
                         MyProxy proxy,
                         PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.Root);

        
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {
        super.onBind();
       
        setInSlot(MainSlot, current);
    }


    @Override
    protected void onReset() {
        super.onReset();

        getView().resetAndFocus();
    }

    private void sendNameToServer(String name) {
        getView().setError("");
       /* if (!FieldVerifier.isValidName(name)) {
            getView().setError("Please enter at least four characters");
            return;
        }*/

        PlaceRequest responsePlaceRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.response)
                .with(TokenParameters.TEXT_TO_SERVER, name)
                .build();
        placeManager.revealPlace(responsePlaceRequest);
    }


	@Override
	public void clickOn(Tab tab) {
		
		switch(tab){
		case TAB1 : 
			removeFromSlot(MainSlot, current);
			current = classifier;
			setInSlot(MainSlot, classifier);
			break;
		case TAB2:
			removeFromSlot(MainSlot, current);
			current = classifier;	
			break;
		case TAB3:
			removeFromSlot(MainSlot, current);
			current = dropdown;
			break;
		}
		
		setInSlot(MainSlot, current);
	}
}
