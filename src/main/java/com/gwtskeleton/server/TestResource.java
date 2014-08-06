package com.gwtskeleton.server;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.gwtskeleton.shared.Book;

@Path("/")
public class TestResource {
	
	 @GET
	 @Path("/books")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Book> getBooks() {
		 Book b1 = new Book("book1");
		 Book b2 = new Book("book2");
		 
		 return Arrays.asList(b1, b2);
	 }
	 
	 @GET
	 @Path("/books/{book}")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Book getBooks(@PathParam(value = "book") String bookId) {
		 Book b1 = new Book("book1");
		 Book b2 = new Book("book2");
		 
		 return b1;
	 }
	 
	 
	 
	 @GET
	 @Path("/strings")
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<String> getStrings() {
		 
		 return Arrays.asList("books", "books2");
	 }

}
