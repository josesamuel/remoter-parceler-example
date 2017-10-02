package util.remoter.common;

import org.parceler.Parcel;

/**
 * A custom class annotated with @Parcel, which exposes a @Remote interface
 */
@Parcel
public class CustomData {

    IEchoService echoService;

    public CustomData() {
    }

    public CustomData(IEchoService echoService) {
        this.echoService = echoService;
    }

    public IEchoService getEchoService() {
        return echoService;
    }
}
