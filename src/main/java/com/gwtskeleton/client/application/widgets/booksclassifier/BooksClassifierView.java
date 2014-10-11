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

package com.gwtskeleton.client.application.widgets.booksclassifier;

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
import com.gwtskeleton.shared.Book;

public class BooksClassifierView extends ViewWithUiHandlers<BooksClassifierUiHandlers> implements BooksClassifierPresenter.MyView {
	interface Binder extends UiBinder<Widget, BooksClassifierView> {
	}
	Logger logger = Logger.getLogger(BooksClassifierView.class.getName());
	@UiField
	ListGroup list1;

	@UiField
	ListGroup list2;
	
	@UiField ClassifierViewStyle style;
	
	private List<ListGroupItem> items = new ArrayList<>();
	
	public interface ClassifierViewStyle extends CssResource {
	    String hovered();
	    String dragInProcess();
	    String fluid();
	}

	@Inject
	BooksClassifierView(Binder uiBinder) {
		logger.fine("building classifier view");

		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void addBooks(List<Book> books) {
		for(Book book : books){
			ListGroupItem item = new ListGroupItem();
			item.setText(book.getName());
			item.getElement().setDraggable(Element.DRAGGABLE_TRUE);
			item.addDomHandler(dragStartHandler, DragStartEvent.getType());
			list1.add(item);	
		}
	}
	
	
	@Override
	public void addGenres(List<String> genres) {
		
		for(String genre: genres){
			ListGroupItem item = new ListGroupItem();
			item.setText(genre);
			Badge badge = new Badge();
			badge.setText(String.valueOf(0));
						
			item.add(badge);
			item.addDomHandler(dropHandler, DropEvent.getType());
			item.addDomHandler(dragLeaveHandler, DragLeaveEvent.getType());
			item.addDomHandler(dragEnterHandler, DragEnterEvent.getType());
			item.addDomHandler(dragOverHandler, DragOverEvent.getType());
									
			list2.add(item);
		}
		
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
			event.preventDefault();
			
			Badge badge = ((Badge)(lgi.getWidget(1)));
			int currentNum = Integer.valueOf(badge.getText());
			badge.setText(String.valueOf(currentNum+1));
			
			String genre = event.getData("data");
			getUiHandlers().save(genre ,lgi.getText(), items.indexOf(lgi));

			event.getSource();
		}
	};
	
	private DragLeaveHandler dragLeaveHandler = new DragLeaveHandler() {
		  
		@Override
		public void onDragLeave(DragLeaveEvent event) {
			logger.info("drag leave");
			ListGroupItem lgi = (ListGroupItem) event.getSource();
			lgi.setType(ListGroupItemType.DEFAULT);
			lgi.removeStyleName(style.dragInProcess());
			
		}
	};
	
	private DragStartHandler dragStartHandler = new DragStartHandler(){

		@Override
		public void onDragStart(DragStartEvent event) {
			logger.info("drag start");
			ListGroupItem lgi = (ListGroupItem) event.getSource();
			event.setData("data", lgi.getText());	
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