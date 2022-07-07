package javalyzer.utils;

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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.javatuples.Triplet;

/**
 * This class sets the different option for the application
 *
 * @author Jordan Samhi
 */
public class CommandLineOptions {

    private static final Triplet<String, String, String> INPUT = new Triplet<>("input", "i", "Input file to analyze");
    private static final Triplet<String, String, String> HELP = new Triplet<>("help", "h", "Print this message");
    private static final Triplet<String, String, String> CG = new Triplet<>("callgraph", "cg", "Extract Call Graph (CHA, RTA, VTA, SPARK)");
    private static final Triplet<String, String, String> CFG = new Triplet<>("controlflowgraph", "cfg", "Extract Control Flow Graph(s)" +
            "Single -> class_name:method_name ; Multiple -> class_name:method_name|...|class_name:method_name" +
            "All -> ALL");
    private static final Triplet<String, String, String> FORMAT = new Triplet<>("format", "f", "Set output format (JSON, YAML)");
    private static final Triplet<String, String, String> OUTPUT = new Triplet<>("output", "o", "Set output folder");
    private static final Triplet<String, String, String> PLATFORM = new Triplet<>("platforms", "p", "Set platforms for Android apps");

    private final Options options;
    private final Options firstOptions;
    private final CommandLineParser parser;
    private CommandLine cmdLine;

    private static CommandLineOptions instance;

    public CommandLineOptions() {
        this.options = new Options();
        this.firstOptions = new Options();
        this.initOptions();
        this.parser = new DefaultParser();
    }

    public static CommandLineOptions v() {
        if (instance == null) {
            instance = new CommandLineOptions();
        }
        return instance;
    }

    public void parseArgs(String[] args) {
        this.parse(args);
    }

    /**
     * This method does the parsing of the arguments.
     * It distinguished, real options and help option.
     *
     * @param args the arguments of the application
     */
    private void parse(String[] args) {
        HelpFormatter formatter;
        try {
            CommandLine cmdFirstLine = this.parser.parse(this.firstOptions, args, true);
            if (cmdFirstLine.hasOption(HELP.getValue0())) {
                formatter = new HelpFormatter();
                formatter.printHelp(Constants.TOOL_NAME, this.options, true);
                System.exit(0);
            }
            this.cmdLine = this.parser.parse(this.options, args);
        } catch (ParseException e) {
            Writer.v().perror(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Initialization of all recognized options
     */
    private void initOptions() {
        final Option input = Option.builder(INPUT.getValue1())
                .longOpt(INPUT.getValue0())
                .desc(INPUT.getValue2())
                .hasArg(true)
                .argName(INPUT.getValue0())
                .required(true)
                .build();

        final Option cg = Option.builder(CG.getValue1())
                .longOpt(CG.getValue0())
                .desc(CG.getValue2())
                .hasArg(true)
                .optionalArg(true)
                .argName(CG.getValue0())
                .required(false)
                .build();

        final Option cfg = Option.builder(CFG.getValue1())
                .longOpt(CFG.getValue0())
                .desc(CFG.getValue2())
                .hasArg(true)
                .optionalArg(false)
                .argName(CFG.getValue0())
                .required(false)
                .build();

        final Option format = Option.builder(FORMAT.getValue1())
                .longOpt(FORMAT.getValue0())
                .desc(FORMAT.getValue2())
                .hasArg(true)
                .optionalArg(false)
                .argName(FORMAT.getValue0())
                .required(false)
                .build();

        final Option output = Option.builder(OUTPUT.getValue1())
                .longOpt(OUTPUT.getValue0())
                .desc(OUTPUT.getValue2())
                .hasArg(true)
                .optionalArg(false)
                .argName(OUTPUT.getValue0())
                .required(false)
                .build();

        final Option platform = Option.builder(PLATFORM.getValue1())
                .longOpt(PLATFORM.getValue0())
                .desc(PLATFORM.getValue2())
                .hasArg(true)
                .optionalArg(false)
                .argName(PLATFORM.getValue0())
                .required(false)
                .build();

        final Option help = Option.builder(HELP.getValue1())
                .longOpt(HELP.getValue0())
                .desc(HELP.getValue2())
                .argName(HELP.getValue0())
                .build();

        this.firstOptions.addOption(help);

        this.options.addOption(input);
        this.options.addOption(cg);
        this.options.addOption(output);
        this.options.addOption(platform);
        this.options.addOption(format);
        this.options.addOption(cfg);

        for (Option o : this.firstOptions.getOptions()) {
            this.options.addOption(o);
        }
    }

    public String getInput() {
        return cmdLine.getOptionValue(INPUT.getValue0());
    }

    public boolean hasCG() {
        return this.cmdLine.hasOption(CG.getValue1());
    }

    public boolean hasFormat() {
        return this.cmdLine.hasOption(FORMAT.getValue1());
    }

    public String getCG() {
        return this.cmdLine.getOptionValue(CG.getValue0());
    }

    public String getFormat() {
        return this.cmdLine.getOptionValue(FORMAT.getValue0());
    }

    public boolean hasOutput() {
        return this.cmdLine.hasOption(OUTPUT.getValue1());
    }

    public String getOutput() {
        return this.cmdLine.getOptionValue(OUTPUT.getValue0());
    }

    public boolean hasPlatform() {
        return this.cmdLine.hasOption(PLATFORM.getValue1());
    }

    public String getPlatform() {
        return this.cmdLine.getOptionValue(PLATFORM.getValue0());
    }

    public boolean hasCFG() {
        return this.cmdLine.hasOption(CFG.getValue1());
    }

    public String getCFG() {
        return this.cmdLine.getOptionValue(CFG.getValue0());
    }
}
