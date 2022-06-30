package javalyzer.options;

import javalyzer.utils.CommandLineOptions;
import javalyzer.utils.Environment;

public class AndroidPlatformSetter extends EnvironmentConstructorImpl {
    public AndroidPlatformSetter(EnvironmentConstructorImpl next) {
        super(next);
    }

    @Override
    public void setEnvironment() {
        String path = null;
        if (CommandLineOptions.v().hasPlatform()) {
            path = CommandLineOptions.v().getPlatform();
        }
        Environment.v().setPlatformPath(path);
    }
}
