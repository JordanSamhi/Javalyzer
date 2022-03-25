package javalyzer.filetype;

import javalyzer.utils.Constants;
import javalyzer.utils.Environment;
import javalyzer.utils.Utils;
import javalyzer.utils.Writer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import soot.G;
import soot.Scene;
import soot.options.Options;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JavaInitializer extends SootInitializerImpl {

    public JavaInitializer(SootInitializerImpl next) {
        super(next);
    }

    @Override
    public void initializeSoot(File f) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        String dst = String.format("%s%s%s%s", tmpDir, File.separator, Utils.getRandomString(15), File.separator);
        String srcFilePath = String.format("%s%s", dst, f.getName());
        String compiled = "compiled/";
        String classFilePath = String.format("%s%s%s.class", dst, File.separator, FilenameUtils.getBaseName(f.getName()));
        String classFilePathDst = String.format("%s%s%s.class", dst, compiled, FilenameUtils.getBaseName(f.getName()));
        File srcFile = new File(srcFilePath);
        try {
            FileUtils.copyFile(f, srcFile);
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        jc.run(null, null, null, srcFile.getAbsolutePath());
        File classFile = new File(classFilePath);
        File classFileDst = new File(classFilePathDst);
        try {
            FileUtils.moveFile(classFile, classFileDst);
        } catch (IOException e) {
            Writer.v().perror(e.getMessage());
        }
        srcFile.delete();

        G.reset();
        if(Environment.v().isHasCG()) {
            Options.v().set_whole_program(true);
            Options.v().setPhaseOption("cg", "enabled:true");
            Options.v().setPhaseOption(String.format("cg.%s", Environment.v().getCgAlgo()), "enabled:true");
        } else {
            Options.v().set_whole_program(false);
            Options.v().setPhaseOption("cg", "enabled:false");
        }
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        List<String> includeList = new LinkedList<>();
        includeList.add("java.lang.*");
        includeList.add("javax.management.*");
        includeList.add("java.util.*");
        includeList.add("javax.xml.*");
        includeList.add("java.io.*");
        includeList.add("sun.*");
        includeList.add("com.sun.*");
        includeList.add("java.net.*");
        includeList.add("javax.servlet.*");
        includeList.add("javax.crypto.*");
        includeList.add("org.apache.*");
        includeList.add("de.test.*");
        includeList.add("soot.*");
        includeList.add("com.example.*");
        includeList.add("libcore.icu.*");
        includeList.add("securibench.*");
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_include(includeList);
        Options.v().set_output_format(Options.output_format_none);
        Options.v().set_src_prec(Options.src_prec_class);
        Options.v().set_soot_classpath(Utils.getRtJar());
        List<String> dirs = new ArrayList<>();
        dirs.add(FilenameUtils.getFullPath(classFileDst.getAbsolutePath()));
        Options.v().set_process_dir(dirs);
        Scene.v().loadNecessaryClasses();
    }

    @Override
    public String getType() {
        return Constants.JAVA_SOURCE_TYPE;
    }

    @Override
    public String getFileType() {
        return Constants.JAVA;
    }
}
