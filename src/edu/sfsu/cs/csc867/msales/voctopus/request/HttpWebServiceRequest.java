package edu.sfsu.cs.csc867.msales.voctopus.request;

import java.net.URI;
import java.util.Map;

/**
 * Responsible for the API access.
 * @author marcello
 * Feb 24, 2008 7:51:16 AM
 */
public class HttpWebServiceRequest extends AbstractHttpRequest {

    public HttpWebServiceRequest(String methodType, URI uri, String version, Map<String, String> headerVars) {
        super(methodType, uri, version, headerVars, null);
        // TODO Auto-generated constructor stub
    }

    public String[] getResourceLines() {
        // TODO Auto-generated method stub
        return null;
    }


}