package cn.gls.data;



/**
 * @ClassName Status.java
 * @Description GLS返回的状态
 * @Version 1.0
 * @Update 2012-5-27
 * @author "Daniel Zhang"
 */
public final class Statustest {

private static final String BASE_HTTP = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html";

private static final String BASE_GBS = "";
private static final String BASE_WEBDAV = "http://www.webdav.org/specs/rfc2518.html";
/** The specification code. */
private final int code;

/** The description. */
private final String description;

/** The name. */
private volatile String name;

/** The related error or exception. */
private final Throwable throwable;

/** The URI of the specification describing the method. */
private final String uri;

/**
 * The request could not be understood by the server due to malformed syntax.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1">HTTP
 *      RFC - 10.4.1 400 Bad Request</a>
 */
public static final Statustest CLIENT_ERROR_BAD_REQUEST = new Statustest(400);

/**
 * The request could not be completed due to a conflict with the current state
 * of the resource (as experienced in a version control system).
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10">HTTP
 *      RFC - 10.4.10 409 Conflict</a>
 */
public static final Statustest CLIENT_ERROR_CONFLICT = new Statustest(409);

/**
 * The user agent expects some behavior of the server (given in an Expect
 * request-header field), but this expectation could not be met by this server.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.18">HTTP
 *      RFC - 10.4.18 417 Expectation Failed</a>
 */
public static final Statustest CLIENT_ERROR_EXPECTATION_FAILED = new Statustest(417);

/**
 * This status code means that the method could not be performed on the resource
 * because the requested action depended on another action and that action
 * failed.
 * 
 * @see <a href="http://www.webdav.org/specs/rfc2518.html#STATUS_424">WEBDAV RFC
 *      - 10.5 424 Failed Dependency</a>
 */
public static final Statustest CLIENT_ERROR_FAILED_DEPENDENCY = new Statustest(424);

/**
 * The server understood the request, but is refusing to fulfill it as it could
 * be explained in the entity.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4">HTTP
 *      RFC - 10.4.4 403 Forbidden</a>
 */
public static final Statustest CLIENT_ERROR_FORBIDDEN = new Statustest(403);

/**
 * The requested resource is no longer available at the server and no forwarding
 * address is known.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11">HTTP
 *      RFC - 10.4.11 410 Gone</a>
 */
public static final Statustest CLIENT_ERROR_GONE = new Statustest(410);

/**
 * The server refuses to accept the request without a defined Content-Length.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.12">HTTP
 *      RFC - 10.4.12 411 Length Required</a>
 */
public static final Statustest CLIENT_ERROR_LENGTH_REQUIRED = new Statustest(411);

/**
 * The source or destination resource of a method is locked (or temporarily
 * involved in another process).
 * 
 * @see <a href="http://www.webdav.org/specs/rfc2518.html#STATUS_423">WEBDAV RFC
 *      - 10.4 423 Locked</a>
 */
public static final Statustest CLIENT_ERROR_LOCKED = new Statustest(423);

/**
 * The method specified in the Request-Line is not allowed for the resource
 * identified by the Request-URI.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6">HTTP
 *      RFC - 10.4.6 405 Method Not Allowed</a>
 */
public static final Statustest CLIENT_ERROR_METHOD_NOT_ALLOWED = new Statustest(405);

/**
 * The resource identified by the request is only capable of generating response
 * entities whose content characteristics do not match the user's requirements
 * (in Accept* headers).
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7">HTTP
 *      RFC - 10.4.7 406 Not Acceptable</a>
 */
public static final Statustest CLIENT_ERROR_NOT_ACCEPTABLE = new Statustest(406);

/**
 * The server has not found anything matching the Request-URI or the server does
 * not wish to reveal exactly why the request has been refused, or no other
 * response is applicable.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5">HTTP
 *      RFC - 10.4.5 404 Not Found</a>
 */
public static final Statustest CLIENT_ERROR_NOT_FOUND = new Statustest(404);

/**
 * This code is reserved for future use.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.3">HTTP
 *      RFC - 10.4.3 402 Payment Required</a>
 */
public static final Statustest CLIENT_ERROR_PAYMENT_REQUIRED = new Statustest(402);

/**
 * Sent by the server when the user agent asks the server to carry out a request
 * under certain conditions that are not met.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13">HTTP
 *      RFC - 10.4.13 412 Precondition Failed</a>
 */
public static final Statustest CLIENT_ERROR_PRECONDITION_FAILED = new Statustest(412);

/**
 * This code is similar to 401 (Unauthorized), but indicates that the client
 * must first authenticate itself with the proxy.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.8">HTTP
 *      RFC - 10.4.8 407 Proxy Authentication Required</a>
 */
public static final Statustest CLIENT_ERROR_PROXY_AUTHENTIFICATION_REQUIRED = new Statustest(
        407);

/**
 * The server is refusing to process a request because the request entity is
 * larger than the server is willing or able to process.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14">HTTP
 *      RFC - 10.4.14 413 Request Entity Too Large</a>
 */
public static final Statustest CLIENT_ERROR_REQUEST_ENTITY_TOO_LARGE = new Statustest(
        413);

/**
 * Sent by the server when an HTTP client opens a connection, but has never sent
 * a request (or never sent the blank line that signals the end of the request).
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9">HTTP
 *      RFC - 10.4.9 408 Request Timeout</a>
 */
public static final Statustest CLIENT_ERROR_REQUEST_TIMEOUT = new Statustest(408);

/**
 * The server is refusing to service the request because the Request-URI is
 * longer than the server is willing to interpret.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15">HTTP
 *      RFC - 10.4.15 414 Request-URI Too Long</a>
 */
public static final Statustest CLIENT_ERROR_REQUEST_URI_TOO_LONG = new Statustest(414);

/**
 * The request includes a Range request-header field and the selected resource
 * is too small for any of the byte-ranges to apply.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17">HTTP
 *      RFC - 10.4.17 416 Requested Range Not Satisfiable</a>
 */
public static final Statustest CLIENT_ERROR_REQUESTED_RANGE_NOT_SATISFIABLE = new Statustest(
        416);

/**
 * The request requires user authentication.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2">HTTP
 *      RFC - 10.4.2 401 Unauthorized</a>
 */
public static final Statustest CLIENT_ERROR_UNAUTHORIZED = new Statustest(401);

/**
 * This status code means the server understands the content type of the request
 * entity (syntactically correct) but was unable to process the contained
 * instructions.
 * 
 * @see <a href="http://www.webdav.org/specs/rfc2518.html#STATUS_422">WEBDAV RFC
 *      - 10.3 422 Unprocessable Entity</a>
 */
public static final Statustest CLIENT_ERROR_UNPROCESSABLE_ENTITY = new Statustest(422);

/**
 * The server is refusing to service the request because the entity of the
 * request is in a format not supported by the requested resource for the
 * requested method.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16">HTTP
 *      RFC - 10.4.16 415 Unsupported Media Type</a>
 */
public static final Statustest CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE = new Statustest(415);

/**
 * Constructor.
 * 
 * @param code The specification code.
 */
public Statustest(int code) {
    this(code, null, null, null, null);
}

/**
 * Constructor.
 * 
 * @param code The specification code.
 * @param name The name.
 * @param description The description.
 * @param uri The URI of the specification describing the method.
 */
public Statustest(int code, final String name, final String description,
        final String uri) {
    this(code, null, name, description, uri);
}

/**
 * Constructor.
 * 
 * @param code The specification code.
 * @param throwable The related error or exception.
 */
public Statustest(int code, Throwable throwable) {
    this(code, throwable, null, null, null);
}

/**
 * Constructor.
 * 
 * @param code The specification code.
 * @param throwable The related error or exception.
 * @param name The name.
 * @param description The description.
 * @param uri The URI of the specification describing the method.
 */
public Statustest(int code, Throwable throwable, final String name,
        final String description, final String uri) {
    this.code = code;
    this.throwable = throwable;
    this.name = checkName(name);
    this.description = description;
    this.uri = uri;
}
/**
 * Check if the provided name of the status contains forbidden characters
 * such as CR and LF. an IllegalArgumentException is thrown in this case.
 * 
 * @see <a
 *      href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html#sec6.1.1">Status
 *      Code and Reason Phrase</a>
 * @param name
 *            The name to check
 * @return The name if it is correct.
 */
private static String checkName(String name) {
    if (name != null) {
        if (name.contains("\n") || name.contains("\r")) {
            throw new IllegalArgumentException(
                    "Name of the status must not contain CR or LF characters.");
        }
    }

    return name;
}
/**
 * Constructor.
 * 
 * @param status The status to copy.
 * @param description The description to associate.
 */
public Statustest(final Statustest status, final String description) {
    this(status, null, description);
}

/**
 * Constructor.
 * 
 * @param status The status to copy.
 * @param throwable The related error or exception.
 */
public Statustest(final Statustest status, final Throwable throwable) {
    this(status, throwable, null);
}

/**
 * Constructor.
 * 
 * @param status The status to copy.
 * @param throwable The related error or exception.
 * @param description The description to associate.
 */
public Statustest(Statustest status, Throwable throwable, String description) {
    this(status.getCode(), (throwable == null) ? status.getThrowable()
            : throwable, status.getName(), (description == null) ? status
            .getDescription() : description, status.getUri());
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public int getCode() {
    return code;
}

public String getDescription() {
    return description;
}

public Throwable getThrowable() {
    return throwable;
}

public String getUri() {
    String result = this.uri;

    if (result == null) {
        switch (this.code) {
        case 100:
            result = BASE_HTTP + "#sec10.1.1";
            break;
        case 101:
            result = BASE_HTTP + "#sec10.1.2";
            break;
        case 102:
            result = BASE_WEBDAV + "#STATUS_102";
            break;
        case 110:
        case 111:
        case 112:
        case 113:
        case 199:
        case 214:
        case 299:
            result = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.46";
            break;

        case 200:
            result = BASE_HTTP + "#sec10.2.1";
            break;
        case 201:
            result = BASE_HTTP + "#sec10.2.2";
            break;
        case 202:
            result = BASE_HTTP + "#sec10.2.3";
            break;
        case 203:
            result = BASE_HTTP + "#sec10.2.4";
            break;
        case 204:
            result = BASE_HTTP + "#sec10.2.5";
            break;
        case 205:
            result = BASE_HTTP + "#sec10.2.6";
            break;
        case 206:
            result = BASE_HTTP + "#sec10.2.7";
            break;
        case 207:
            result = BASE_WEBDAV + "#STATUS_207";
            break;

        case 300:
            result = BASE_HTTP + "#sec10.3.1";
            break;
        case 301:
            result = BASE_HTTP + "#sec10.3.2";
            break;
        case 302:
            result = BASE_HTTP + "#sec10.3.3";
            break;
        case 303:
            result = BASE_HTTP + "#sec10.3.4";
            break;
        case 304:
            result = BASE_HTTP + "#sec10.3.5";
            break;
        case 305:
            result = BASE_HTTP + "#sec10.3.6";
            break;
        case 307:
            result = BASE_HTTP + "#sec10.3.8";
            break;

        case 400:
            result = BASE_HTTP + "#sec10.4.1";
            break;
        case 401:
            result = BASE_HTTP + "#sec10.4.2";
            break;
        case 402:
            result = BASE_HTTP + "#sec10.4.3";
            break;
        case 403:
            result = BASE_HTTP + "#sec10.4.4";
            break;
        case 404:
            result = BASE_HTTP + "#sec10.4.5";
            break;
        case 405:
            result = BASE_HTTP + "#sec10.4.6";
            break;
        case 406:
            result = BASE_HTTP + "#sec10.4.7";
            break;
        case 407:
            result = BASE_HTTP + "#sec10.4.8";
            break;
        case 408:
            result = BASE_HTTP + "#sec10.4.9";
            break;
        case 409:
            result = BASE_HTTP + "#sec10.4.10";
            break;
        case 410:
            result = BASE_HTTP + "#sec10.4.11";
            break;
        case 411:
            result = BASE_HTTP + "#sec10.4.12";
            break;
        case 412:
            result = BASE_HTTP + "#sec10.4.13";
            break;
        case 413:
            result = BASE_HTTP + "#sec10.4.14";
            break;
        case 414:
            result = BASE_HTTP + "#sec10.4.15";
            break;
        case 415:
            result = BASE_HTTP + "#sec10.4.16";
            break;
        case 416:
            result = BASE_HTTP + "#sec10.4.17";
            break;
        case 417:
            result = BASE_HTTP + "#sec10.4.18";
            break;
        case 422:
            result = BASE_WEBDAV + "#STATUS_422";
            break;
        case 423:
            result = BASE_WEBDAV + "#STATUS_423";
            break;
        case 424:
            result = BASE_WEBDAV + "#STATUS_424";
            break;

        case 500:
            result = BASE_HTTP + "#sec10.5.1";
            break;
        case 501:
            result = BASE_HTTP + "#sec10.5.2";
            break;
        case 502:
            result = BASE_HTTP + "#sec10.5.3";
            break;
        case 503:
            result = BASE_HTTP + "#sec10.5.4";
            break;
        case 504:
            result = BASE_HTTP + "#sec10.5.5";
            break;
        case 505:
            result = BASE_HTTP + "#sec10.5.6";
            break;
        case 507:
            result = BASE_WEBDAV + "#STATUS_507";
            break;

        case 1000:
            result = BASE_GBS
                    + "内部错误";
            break;
        case 1001:
            result = BASE_GBS
                    + "org/restlet/data/Status.html#CONNECTOR_ERROR_COMMUNICATION";
            break;
        case 1002:
            result = BASE_GBS
                    + "org/restlet/data/Status.html#CONNECTOR_ERROR_INTERNAL";
            break;
        }
    }

    return result;
}

}
