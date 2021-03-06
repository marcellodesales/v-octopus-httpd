package com.googlecode.voctopus.request.handler;

import java.io.File;
import java.net.URI;

import com.googlecode.voctopus.RequestResponseMediator.ReasonPhase;


/**
 * Represents the conditional request that doesn't include a body.
 * 
 * @author marcello Mar 11, 2008 6:58:06 PM
 */
public class CachedRequestHandler extends EmptyBodyRequestHandler {

    /**
     * Creates a new cached handler that generates the 304 response
     * 
     * @param uri is the requested uri
     * @param requestedFile is the physical file requested.
     */
    public CachedRequestHandler(URI uri, File requestedFile) {
        super(uri, requestedFile, RequestType.CACHED, ReasonPhase.STATUS_304);
    }

    public String[] getParticularResponseHeaders() {
        // TODO Auto-generated method stub
        return null;
    }
}
