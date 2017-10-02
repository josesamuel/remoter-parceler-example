package util.remoter.common;

import remoter.annotations.Remoter;


/**
 * Remoter interface exposed by service...
 */
@Remoter
public interface ISampleService {


    /**
     * Returns a @Parcel class, which internally has another @Remoter interface
     */
    CustomData getParcelerData();


}
