package com.gwtskeleton.server;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.gwtskeleton.shared.Book;

@Path("/")
public class TestResource {
	
	 @GET
	 @Path("/books")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Book> getBooks() {
		 
		List<Book> books = Lists.newArrayList();
		 
		 for(int i=0; i<50; i++){
			 Book b1 = new Book("book"+i);
			 books.add(b1);
		 }
		 
		 return books;
	 }
	 
	 

	 @GET
	 @Path("/books/genres")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<String> getGenres() {
		 
		return Arrays.asList("Novel", "Poem", "Drama", "Short Story", "Myths", "Graphic Novel");
	 }
	 
	 
	 @GET
	 @Path("/books/{book}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Book getBooks(@PathParam(value = "book") String bookId) {
		 Book b1 = new Book("book1");
			 
		 return b1;
	 }
	 
	 
	 
	 @GET
	 @Path("/strings")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<String> getStrings() {
		 
		 return Arrays.asList("books", "books2");
	 }

}
