package javalyzer;

/*-
 * #%L
 * Javalyzer
 *
 * %%
 * Copyright (C) 2022 Jordan Samhi
 *
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import javalyzer.extractors.AbstractExtractor;
import javalyzer.extractors.listbuilders.CFGExtractorBuilder;
import javalyzer.extractors.listbuilders.CGExtractorBuilder;
import javalyzer.extractors.listbuilders.ExtractorListBuilderImpl;
import javalyzer.filetype.AbstractSootInitializer;
import javalyzer.filetype.ApkInitializer;
import javalyzer.filetype.JarInitializer;
import javalyzer.options.*;
import javalyzer.utils.CommandLineOptions;
import javalyzer.utils.Constants;
import javalyzer.utils.Loading;
import javalyzer.utils.Writer;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.profiler.StopWatch;
import soot.PackManager;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Writer.v().pinfo(String.format("%s v%s started on %s\n", Constants.TOOL_NAME, Constants.VERSION, new Date()));
        StopWatch stopWatch = new StopWatch(Constants.TOOL_NAME);
        stopWatch.start(Constants.TOOL_NAME);

        CommandLineOptions.v().parseArgs(args);
        String input = CommandLineOptions.v().getInput();
        File finput = new File(input);
        if (!finput.exists()) {
            Writer.v().perror("File does not exist");
            System.exit(1);
        }

        // Set environment
        EnvironmentConstructorImpl ec = new CallGraphSetter(null);
        ec = new OutputFormatSetter(ec);
        ec = new OutputFolderSetter(ec);
        ec = new AndroidPlatformSetter(ec);
        ec = new ControlFlowGraphSetter(ec);
        ec.exploreOptions();

        // Initialize Soot
        final Loading l = new Loading();
        l.start();
        l.load("Initializing the environment");
        AbstractSootInitializer si = new JarInitializer(null);
        si = new ApkInitializer(si);
        boolean recognized = si.recognizeFileType(finput);
        l.kill(recognized);
        if (!recognized) {
            Writer.v().perror("Input file not recognized");
            System.exit(1);
        }

        // Load Soot
        l.load(String.format("Loading %s.%s", FilenameUtils.getBaseName(input), FilenameUtils.getExtension(input)));
        PackManager.v().runPacks();
        l.kill(true);

        // Extractors based on options
        l.load("Loading extractors");
        ExtractorListBuilderImpl elb = new CGExtractorBuilder(null);
        elb = new CFGExtractorBuilder(elb);
        List<AbstractExtractor> extractors = elb.recognizeOptions();
        l.kill(true);
        if (extractors.size() == 0) {
            Writer.v().pinfo("Noting to do");
        }
        for (AbstractExtractor ae : extractors) {
            ae.extract();
        }

        // Print analysis elapsed time
        l.interrupt();
        stopWatch.stop();
        long elapsedTime = TimeUnit.SECONDS.convert(stopWatch.elapsedTime(), TimeUnit.NANOSECONDS);
        Writer.v().pinfo(String.format("Analysis took %d seconds", elapsedTime));
    }
}