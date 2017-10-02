package util.remoter.remoterservice;

import util.remoter.common.CustomData;
import util.remoter.common.IEchoService;
import util.remoter.common.ISampleService;

public class SampleServiceImpl implements ISampleService, IEchoService {


    @Override
    public CustomData getParcelerData() {
        return new CustomData(this);
    }

    @Override
    public String echo(String string) {
        return string;
    }
}
