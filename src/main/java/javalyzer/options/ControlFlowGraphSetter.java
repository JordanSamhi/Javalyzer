package javalyzer.options;

import javalyzer.utils.CommandLineOptions;
import javalyzer.utils.Constants;
import javalyzer.utils.Environment;

public class ControlFlowGraphSetter extends EnvironmentConstructorImpl {
    public ControlFlowGraphSetter(EnvironmentConstructorImpl next) {
        super(next);
    }

    @Override
    public void setEnvironment() {
        if (CommandLineOptions.v().hasCFG()) {
            Environment.v().setHasCFG(true);
            String arg = CommandLineOptions.v().getCFG();
            if (arg.equals(Constants.ALL)) {
                Environment.v().addMethodToExtractCFG(Constants.ALL, Constants.ALL);
            } else if (arg.contains("|")) {
                String[] split = arg.split("\\|");
                for (String s : split) {
                    if (!s.contains(":")) {
                        continue;
                    }
                    String[] split1 = s.split(":");
                    if (!(split1.length == 2)) {
                        continue;
                    }
                    String clazz = split1[0];
                    String method = split1[1];
                    Environment.v().addMethodToExtractCFG(clazz, method);
                }
            } else {
                String[] split = arg.split(":");
                if (split.length == 2) {
                    String clazz = split[0];
                    String method = split[1];
                    Environment.v().addMethodToExtractCFG(clazz, method);
                }
            }
        }
    }
}