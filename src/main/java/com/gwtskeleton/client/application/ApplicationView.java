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

import java.util.logging.Logger;

import javax.inject.Inject;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.GssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    Logger logger = Logger.getLogger(ApplicationView.class.getName());
    
    @UiField
    SimplePanel contentContainer;
    
    @UiField
    Resources res;
      
    interface Style extends GssResource { 
    	
    }
    
    public interface Resources extends ClientBundle {
    	@Source("ApplicationView.gss")
    	public Style style();
    }

    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    
    @Inject
    ApplicationView(final Binder binder) {
        initWidget(binder.createAndBindUi(this));
        res.style().ensureInjected();
    }

    @Override
    public void setInSlot(final Object slot, final IsWidget content) {
        if (slot == ApplicationPresenter.MAIN_SLOT) {
            contentContainer.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
