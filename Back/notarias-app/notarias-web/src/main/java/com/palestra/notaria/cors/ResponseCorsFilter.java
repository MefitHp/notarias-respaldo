package com.palestra.notaria.cors;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

//@Provider
public class ResponseCorsFilter implements ContainerResponseFilter {


	@Override
	 public ContainerResponse filter(ContainerRequest req, ContainerResponse
	 contResp) {
	
	 contResp.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
	 contResp.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET,POST, OPTIONS");
	
	 String reqHead = req.getHeaderValue("Access-Control-Request-Headers");
	 contResp.getHttpHeaders().putSingle("Access-Control-Allow-Headers", reqHead);
	
	 return contResp;
	 }

	// @Override
	// public void filter(ContainerRequestContext req, ContainerResponseContext
	// contResp) throws IOException {
	// log.info( "Executing REST response filter" );
	// // contResp.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
	// // contResp.getHeaders().putSingle("Access-Control-Allow-Methods", "GET,
	// // POST, OPTIONS");
	// //
	// // String reqHead =
	// // req.getHeaderString("Access-Control-Request-Headers");
	// // contResp.getHeaders().putSingle("Access-Control-Allow-Headers",
	// // reqHead);
	//
	// contResp.getHeaders().add("Access-Control-Allow-Origin", "*");
	// contResp.getHeaders().add("Access-Control-Allow-Credentials", "true");
	// contResp.getHeaders().add("Access-Control-Allow-Methods", "OPTIONS, GET,
	// POST, DELETE, PUT");
	// contResp.getHeaders().add("Access-Control-Allow-Headers",
	// "Content-Type, Access-Control-Allow-Origin,
	// Access-Control-Allow-Credentials, Access-Control-Allow-Methods,
	// Access-Control-Allow-Headers, Authorization, X-Requested-With");
	//
	// }

}