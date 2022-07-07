package javalyzer.options;

import javalyzer.files.LibrariesManager;
import javalyzer.utils.CommandLineOptions;
import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Utils;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;

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
                for (SootClass sc : Scene.v().getApplicationClasses()) {
                    if (!Utils.isSystemClass(sc) && !LibrariesManager.v().isLibrary(sc)) {
                        for (SootMethod sm : sc.getMethods()) {
                            if (sm.isConcrete()) {
                                Environment.v().addMethodToExtractCFG(sm);
                            }
                        }
                    }
                }
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
                    for (SootClass sc : Scene.v().getApplicationClasses()) {
                        if (sc.getName().equals(clazz)) {
                            for (SootMethod sm : sc.getMethods()) {
                                if (sm.getName().equals(method)) {
                                    if (sm.isConcrete()) {
                                        Environment.v().addMethodToExtractCFG(sm);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                String[] split = arg.split(":");
                if (split.length == 2) {
                    String clazz = split[0];
                    String method = split[1];
                    for (SootClass sc : Scene.v().getApplicationClasses()) {
                        if (sc.getName().equals(clazz)) {
                            for (SootMethod sm : sc.getMethods()) {
                                if (sm.getName().equals(method)) {
                                    if (sm.isConcrete()) {
                                        Environment.v().addMethodToExtractCFG(sm);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}