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

import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtskeleton.client.application.main.ApplicationPresenter.Tab;

public class ApplicationView extends ViewWithUiHandlers<ApplicationUiHandlers> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    Logger logger = Logger.getLogger(ApplicationView.class.getName());
    
    @UiField
    TabListItem tab1;
    @UiField
    TabListItem tab2;
    @UiField
    TabListItem tab3;
    
    @UiField
    TabPane tab1Pane;
    
    @Inject
    ApplicationView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.MainSlot) {
        	logger.fine("adding tab1 widget");
        	tab1Pane.add(content);
        }
    }
    
    @Override
    public void removeFromSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.MainSlot) {
        	logger.fine("removing tab1 widget");
        	tab1Pane.remove(content);
        }
    }

    @Override
    public void resetAndFocus() {
     //   nameField.setFocus(true);
      //  nameField.selectAll();
    }

    @Override
    public void setError(String errorText) {
       // error.setText(errorText);
    }

    @UiHandler("tab1")
    void onClickTab1(ClickEvent event) {
    	getUiHandlers().clickOn(Tab.TAB1);
    }	
    
    @UiHandler("tab2")
    void onClickTab2(ClickEvent event) {
    	getUiHandlers().clickOn(Tab.TAB2);
    }	
    @UiHandler("tab3")
    void onClickTab3(ClickEvent event) {
    	getUiHandlers().clickOn(Tab.TAB3);
    }	
}
