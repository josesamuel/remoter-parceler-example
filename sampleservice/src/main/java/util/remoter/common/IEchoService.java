package util.remoter.common;

import remoter.annotations.Remoter;


/**
 * Remoter echo interface exposed by service
 */
@Remoter
public interface IEchoService {

    /**
     * Returns the string passed
     */
    String echo(String string);
}
