package com.gwtskeleton.client.application.classifier;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtskeleton.shared.Book;

@Path("/data")
public interface CredentialService{
	
	@GET @Path("/books")
    RestAction<List<Book>> getBooks();
	
 
	/*@GET @Path("/statistics/countByPortalDomain")
    RestAction<List<FacetedField>> getTopDomains();
	
	@GET @Path("/statistics/countByPortalIndustry")
    RestAction<List<FacetedField>> getAllIndustries();
	
	@POST @Path("/statistics/save")
    RestAction<List<FacetedField>> save(@PathParam("domain") String domain, @PathParam("industry") String industry);*/

}