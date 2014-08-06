package com.gwtskeleton.client.application.classifier;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestService;
import com.gwtskeleton.client.domain.FacetedField;

@Path("/gwttest")
public interface CredentialService{
	
	@GET @Path("/data")
    RestAction<List<String>> getTestData();
	
 
	/*@GET @Path("/statistics/countByPortalDomain")
    RestAction<List<FacetedField>> getTopDomains();
	
	@GET @Path("/statistics/countByPortalIndustry")
    RestAction<List<FacetedField>> getAllIndustries();
	
	@POST @Path("/statistics/save")
    RestAction<List<FacetedField>> save(@PathParam("domain") String domain, @PathParam("industry") String industry);*/

}