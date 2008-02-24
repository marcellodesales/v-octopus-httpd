package edu.sfsu.cs.csc867.msales.voctopus.request;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import edu.sfsu.cs.csc867.msales.voctopus.request.handler.HttpRequestHandler;

/**
 * The abstract Http request holds all the information from the request.
 * 
 * @author marcello Feb 20, 2008 2:56:38 PM
 */
public abstract class AbstractHttpRequest implements HttpRequest {

    /**
     * @author marcello
     * Feb 20, 2008 2:58:12 PM
     */
    private enum RequestType {
        GET, PUT, DELETE;
    }

    /**
     * The method type of the request.
     */
    private RequestType methodType;
    /**
     * This is the version used on the request.
     */
    private String version;
    /**
     * This is the resource requested.
     */
    private URI uri;
    /**
     * Request header vars from the request.
     */
    private Map<String, String> headerVars;
    /**
     * The parameters used on the query string.
     */
    private Map<String, String> requestParameters;

    /**
     * The handler is based on the type of handler.
     */
    private HttpRequestHandler requestHandler;

    /**
     * Constructs the abstract request based on the method type, uri, version, header variables and the handler
     * @param methodType
     * @param uri
     * @param version
     * @param headerVars
     * @param requestHandler
     */
    public AbstractHttpRequest(String methodType, URI uri, String version, Map<String, String> headerVars, 
            HttpRequestHandler requestHandler) {
        this.methodType = RequestType.valueOf(methodType.toUpperCase());
        this.uri = uri;
        this.version = version;
        this.headerVars = headerVars;
        this.requestHandler = requestHandler;

        if (this.uri.getQuery() != null && !this.uri.getQuery().equals("")) {
            String[] varsAndValues = this.uri.getQuery().split("&");
            this.requestParameters = new HashMap<String, String>(varsAndValues.length);
            String[] vV;
            for (String varValue : varsAndValues) {
                vV = varValue.split("=");
                requestParameters.put(vV[0], vV[1]);
            };
        }

    }
    
    /* (non-Javadoc)
     * @see edu.sfsu.cs.csc867.msales.voctopus.request.HttpRequest#isResourceBinary()
     */
    public boolean isResourceBinary() {
        return this.requestHandler.isRequestedResourceBinary();
    }

    /* (non-Javadoc)
     * @see edu.sfsu.cs.csc867.msales.voctopus.request.HttpRequest#getRequestedResource()
     */
    public File getRequestedResource() {
        return this.requestHandler.getRequestedResource();
    }

    /* (non-Javadoc)
     * @see edu.sfsu.cs.csc867.msales.voctopus.request.HttpRequest#getStatus()
     */
    public int getStatus() {
        return this.requestHandler.getStatus();
    }
    
    /* (non-Javadoc)
     * @see edu.sfsu.cs.csc867.msales.voctopus.request.HttpRequest#getContentType()
     */
    public String getContentType() {
        return this.requestHandler.getContentType();
    }
    
    /* (non-Javadoc)
     * @see edu.sfsu.cs.csc867.msales.voctopus.request.HttpRequest#getResourceLines()
     */
    public String[] getResourceLines() {
        try {
            return this.requestHandler.getResourceLines();
        } catch (IOException e) {
            return new String[] {""};
        }
    }

    /**
     * @return The method type used on the request.
     */
    public String getMethodType() {
        return this.methodType.toString();
    }

    public URI getUri() {
        return uri;
    }
    
    /**
     * @return The version of the request.
     */
    public String getRequestVersion() {
        return version;
    }

    /**
     * The header vars from the http request.
     * @return
     */
    public Map<String, String> getHeaderVars() {
        return headerVars;
    }

    /**
     * @return The request parameters and values from the query string
     */
    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }
}