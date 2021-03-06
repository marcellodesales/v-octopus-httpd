package com.googlecode.voctopus.request.handler;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import com.googlecode.voctopus.RequestResponseMediator.ReasonPhase;


/**
 * The request handler for binary content. It doesn't have the resource lines, since the connection will be piped
 * directly on the input stream.
 * 
 * @author marcello Feb 24, 2008 8:36:09 AM
 */
public class BinaryContentRequestHandlerStrategy extends AbstractRequestHandler {

    /**
     * Creates a new handler for binary content
     * 
     * @param uri
     * @param requestedFile
     * @param handlerFound
     * @param reasonPhrase
     */
    public BinaryContentRequestHandlerStrategy(URI uri, File requestedFile, String mimeType,
            ReasonPhase reasonPhrase) {
        super(uri, requestedFile, RequestType.BINARY, mimeType);
        this.status = reasonPhrase;
    }

    public String[] getResourceLines() throws IOException {
        return null;
    }

    public String[] getParticularResponseHeaders() {
        // TODO Auto-generated method stub
        return null;
    }
}
