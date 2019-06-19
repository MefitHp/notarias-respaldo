package com.palestra.notaria.cors;


import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class ResponseCorsFilter implements ContainerResponseFilter {
	 
    public ContainerResponse filter(ContainerRequest req, ContainerResponse contResp) {
                
        contResp.getHttpHeaders().putSingle("Access-Control-Allow-Origin", "*");
        contResp.getHttpHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
 
        String reqHead = req.getHeaderValue("Access-Control-Request-Headers");
        contResp.getHttpHeaders().putSingle("Access-Control-Allow-Headers", reqHead);
               
        return contResp;
    }
 
}