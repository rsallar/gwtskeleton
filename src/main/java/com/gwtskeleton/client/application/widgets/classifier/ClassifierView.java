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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.constants.ListGroupItemType;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ClassifierView extends ViewWithUiHandlers<ClassifierUiHandlers> implements ClassifierPresenter.MyView {
	interface Binder extends UiBinder<Widget, ClassifierView> {
	}
	Logger logger = Logger.getLogger(ClassifierView.class.getName());
	@UiField
	ListGroup list1;

	@UiField
	ListGroup list2;
	
	@UiField ClassifierViewStyle style;
	
	private List<ListGroupItem> items = new ArrayList<>();
	
	public interface ClassifierViewStyle extends CssResource {
	    String hovered();
	    String dragInProcess();
	    String glow();
	}

	@Inject
	ClassifierView(Binder uiBinder) {
		logger.fine("building classifier view");

		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void addDomain(String domain) {
		ListGroupItem item = new ListGroupItem();
		item.setText(domain);
		item.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		item.addDomHandler(dragStartHandler, DragStartEvent.getType());
		list1.add(item);
	}


	@Override
	public void addIndustry(String industry, int count) {
		ListGroupItem item = new ListGroupItem();
		item.setText(industry);
		Badge badge = new Badge();
		badge.setText(String.valueOf(count));
		
		
		item.add(badge);
		item.addDomHandler(dropHandler, DropEvent.getType());
		item.addDomHandler(dragLeaveHandler, DragLeaveEvent.getType());
		item.addDomHandler(dragEnterHandler, DragEnterEvent.getType());
		item.addDomHandler(dragOverHandler, DragOverEvent.getType());
		
		//item.getWidget(0).addDomHandler(handler, type)
		
		list2.add(item);
	}
	
	private DragOverHandler dragOverHandler = new DragOverHandler(){

		@Override
		public void onDragOver(DragOverEvent event) {
			//VOID on purpose. This handler is needed.
		}
		
	};
	
	private DropHandler dropHandler = new DropHandler() {
		@Override
		public void onDrop(DropEvent event) {
			ListGroupItem lgi = (ListGroupItem) event.getSource();
			
			lgi.setType(ListGroupItemType.DEFAULT);
			Badge badge = (Badge)lgi.getWidget(1);
			
			//badge.removeStyleName(style.glow());
			badge.addStyleName(style.glow());
			
			event.preventDefault();
			String domain = event.getData("domain");
			getUiHandlers().save(domain,lgi.getText(), items.indexOf(lgi));
			//lgi.setText(lgi.getText() + "  " + domain);
			//lgi.setType(ListGroupItemType.SUCCESS);
			//lgi.setText("...");

			event.getSource();
		}
	};
	
	private DragLeaveHandler dragLeaveHandler = new DragLeaveHandler() {
		  
		@Override
		public void onDragLeave(DragLeaveEvent event) {
			logger.fine("drag leave");
			ListGroupItem lgi = (ListGroupItem) event.getSource();
			lgi.setType(ListGroupItemType.DEFAULT);
			lgi.removeStyleName(style.dragInProcess());
			
		}
	};
	
	private DragStartHandler dragStartHandler = new DragStartHandler(){

		@Override
		public void onDragStart(DragStartEvent event) {
			ListGroupItem lgi = (ListGroupItem) event.getSource();
			event.setData("domain", lgi.getText());	
		}

	};
		
	private DragEnterHandler dragEnterHandler = new DragEnterHandler() {
		  
		@Override
		public void onDragEnter(DragEnterEvent event) {
			ListGroupItem lgi = (ListGroupItem) event.getSource();
			items.add(lgi);
			lgi.setType(ListGroupItemType.WARNING);		
			lgi.addStyleName(style.dragInProcess());
		}
	};

}