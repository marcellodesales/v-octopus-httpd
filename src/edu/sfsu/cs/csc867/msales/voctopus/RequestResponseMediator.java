package edu.sfsu.cs.csc867.msales.voctopus;

import java.io.IOException;

import edu.sfsu.cs.csc867.msales.httpd.validation.HttpRequestInterpreterException;
import edu.sfsu.cs.csc867.msales.voctopus.request.HttpRequest;
import edu.sfsu.cs.csc867.msales.voctopus.request.validation.HttpRequestInterpreter;
import edu.sfsu.cs.csc867.msales.voctopus.response.HttpResponse;
import edu.sfsu.cs.csc867.msales.voctopus.response.HttpResponseAbstractFactory;

/**
 * The Request-Response Mediator is responsible for mediating the transactions between the Request and Response
 * instances.
 * 
 * @author marcello Feb 16, 2008 10:26:58 AM
 */
public final class RequestResponseMediator {

    /**
     * Holds the connection from the client on the socket level.
     */
    private HttpClientConnection clientConnection;

    /**
     * It's the interface from the client's request.
     */
    private HttpRequest request;

    /**
     * It's the response based on the request's result.
     */
    private HttpResponse response;

    /**
     * This represents the phases of the request/response. 
     * More information about it at http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     * 
     *  The first digit of the Status-Code defines the class of response. The last two digits do not 
     *  have any categorization role. There are 5 values for the first digit:
     *  <BR>
     * <li>1xx: Informational - Request received, continuing process
     * <li>2xx: Success - The action was successfully received, understood, and accepted
     * <li>3xx: Redirection - Further action must be taken in order to complete the request
     * <li>4xx: Client Error - The request contains bad syntax or cannot be fulfilled
     * <li>5xx: Server Error - The server failed to fulfill an apparently valid request
     * 
     * @author marcello
     * Feb 26, 2008 12:05:05 PM
     */
    public static enum ReasonPhrase {
        STATUS_100("Continue"),
        STATUS_101("Switching Protocols"),
        STATUS_200("OK"),
        STATUS_201("Created"),
        STATUS_202("Accepted"),
        STATUS_203("Non-Authoritative Information"),
        STATUS_204("No Content"),
        STATUS_205("Reset Content"),
        STATUS_206("Partial Content"),
        STATUS_300("Multiple Choices"),
        STATUS_301("Moved Permanently"),
        STATUS_302("Found"),
        STATUS_303("See Other"),
        STATUS_304("Not Modified"),
        STATUS_305("Use Proxy"),
        STATUS_307("Temporary Redirect"),
        STATUS_400("Bad Request"),
        STATUS_401("Unauthorized"),
        STATUS_402("Payment Required"),
        STATUS_403("Forbidden"),
        STATUS_404("Not Found"),
        STATUS_405("Method Not Allowed"),
        STATUS_406("Not Acceptable"),
        STATUS_407("Proxy Authentication Required"),
        STATUS_408("Request Time-out"),
        STATUS_409("Conflict"),
        STATUS_410("Gone"),
        STATUS_411("Length Required"),
        STATUS_412("Precondition Failed"),
        STATUS_413("Request Entity Too Large"),
        STATUS_414("Request-URI Too Large"),
        STATUS_415("Unsupported Media Type"),
        STATUS_500("Internal Server Error"),
        STATUS_501("Not Implemented"),
        STATUS_502("Bad Gateway"),
        STATUS_503("Service Unavailable"),
        STATUS_504("Gateway Time-out"),
        STATUS_505("HTTP Version not supported");

        /**
         * The title of each error message
         */
        private String humanValue;
        
        private ReasonPhrase(String humanValue) {
            this.humanValue = humanValue;
        }
        
        @Override
        public String toString() {
            return this.getCode() + " " + this.humanValue;
        }
        
        /**
         * @param code the code to be generated.
         * @return a new instance of ReasonPhase based on the code
         */
        public static ReasonPhrase getReasonPhrase(int code) {
            for (ReasonPhrase key : ReasonPhrase.values()) {
                if (key.toString().contains(String.valueOf(code))) {
                    return key;
                }
            }
            return STATUS_406;
        }
        
        public int getCode() {
            return Integer.valueOf(this.name().substring(this.name().indexOf("_") + 1));
        }
    }
    
    /**
     * Creates a new mediator based on the connection from the client.
     * @param clientConnection is the connection from the client
     * @throws HttpRequestInterpreterException if the request 
     */
    public RequestResponseMediator(HttpClientConnection clientConnection) throws HttpRequestInterpreterException {
        this.clientConnection = clientConnection;
        try {
            this.request = new HttpRequestInterpreter(this.clientConnection.getConnectionLines()).interpret();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Processing request for " + this.request.getUri());
        this.response = HttpResponseAbstractFactory.getInstance().createNewHttpResponse(this.request);
    }

    /**
     * @return The request from the client.
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * @return The response based on the request.
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * Signals the response to be sent to the client.
     */
    public void sendResponse() {
        try {
            this.response.sendResponse(this.clientConnection.getOutputStream());
        } catch (IOException e) {
            // TODO LOG THIS INFORMATION.
            e.printStackTrace();
        }
    }
}
