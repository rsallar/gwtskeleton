package com.gwtskeleton.client.application.widgets.booksclassifier;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtskeleton.shared.Book;

@Path("/data")
public interface BooksCredentialService{
	
	@GET @Path("/books")
    RestAction<List<Book>> getBooks();
	
	@GET @Path("/books/genres")
    RestAction<List<String>> getGenres();
	
 
	/*@GET @Path("/statistics/countByPortalDomain")
    RestAction<List<FacetedField>> getTopDomains();
	
	@GET @Path("/statistics/countByPortalIndustry")
    RestAction<List<FacetedField>> getAllIndustries();
	
	@POST @Path("/statistics/save")
    RestAction<List<FacetedField>> save(@PathParam("domain") String domain, @PathParam("industry") String industry);*/

}