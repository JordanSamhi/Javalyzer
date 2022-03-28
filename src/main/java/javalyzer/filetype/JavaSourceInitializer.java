package javalyzer.filetype;

import javalyzer.utils.Constants;
import javalyzer.utils.Utils;
import javalyzer.utils.Writer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import soot.options.Options;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaSourceInitializer extends AbstractSootJavaInitializer {

    public JavaSourceInitializer(AbstractSootInitializer next) {
        super(next);
    }

    @Override
    public void specificInitialization(File f) {
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

        Options.v().set_src_prec(Options.src_prec_class);
        List<String> dirs = new ArrayList<>();
        dirs.add(FilenameUtils.getFullPath(classFileDst.getAbsolutePath()));
        Options.v().set_process_dir(dirs);
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
