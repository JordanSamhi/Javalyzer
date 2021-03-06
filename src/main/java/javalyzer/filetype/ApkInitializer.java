package javalyzer.filetype;

import javalyzer.config.SootConfig;
import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Utils;
import javalyzer.utils.Writer;
import soot.G;
import soot.jimple.infoflow.android.InfoflowAndroidConfiguration;
import soot.jimple.infoflow.android.SetupApplication;

import java.io.File;

public class ApkInitializer extends AbstractSootInitializer {

    public ApkInitializer(AbstractSootInitializer next) {
        super(next);
    }

    @Override
    public void initializeSoot(File f) {
        G.reset();
        InfoflowAndroidConfiguration ifac = new InfoflowAndroidConfiguration();
        if (Environment.v().hasPlatform()) {
            ifac.getAnalysisFileConfig().setAndroidPlatformDir(Environment.v().getPlatformPath());
        } else {
            Writer.v().perror("You must specified a platform path for Android apps");
            System.exit(0);
        }
        ifac.getAnalysisFileConfig().setTargetAPKFile(f.getAbsolutePath());
        ifac.setMergeDexFiles(true);
        SetupApplication sa = new SetupApplication(ifac);
        sa.setSootConfig(new SootConfig());
        if (Environment.v().hasCG()) {
            sa.getConfig().setCallgraphAlgorithm(Utils.getCGAlgo(Environment.v().getCgAlgo()));
        } else {
            sa.getConfig().setCallgraphAlgorithm(Utils.getCGAlgo(Constants.CHA));
        }
        sa.constructCallgraph();
    }

    @Override
    public String getType() {
        return Constants.APK_TYPE;
    }

    @Override
    public String getFileType() {
        return Constants.APK;
    }
}
