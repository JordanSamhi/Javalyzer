package javalyzer.filetype;

import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Utils;
import soot.G;
import soot.Scene;
import soot.SootMethod;
import soot.options.Options;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSootJavaInitializer extends AbstractSootInitializer {


    public AbstractSootJavaInitializer(AbstractSootInitializer next) {
        super(next);
    }

    @Override
    public void initializeSoot(File f) {
        G.reset();
        if (Environment.v().isHasCG()) {
            Options.v().set_whole_program(true);
            Options.v().setPhaseOption("cg", "enabled:true");
            Options.v().setPhaseOption(String.format("cg.%s", Environment.v().getCgAlgo()), "enabled:true");
        } else {
            Options.v().set_whole_program(false);
            Options.v().setPhaseOption("cg", "enabled:false");
        }
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().set_soot_classpath(Utils.getRtJar());
        this.specificInitialization(f);
        Scene.v().loadNecessaryClasses();
        this.setEntryPoints();
    }

    public void setEntryPoints() {
        List<SootMethod> entryPoints = Scene.v().getEntryPoints();
        List<SootMethod> eps = new ArrayList<>();
        for (SootMethod ep : entryPoints) {
            if (ep.isPublic() && ep.isStatic() && ep.getSubSignature().equals(Constants.MAIN_METHOD)) {
                eps.add(ep);
            }
        }
        entryPoints.clear();
        entryPoints.addAll(eps);
    }

    public abstract void specificInitialization(File f);
}
