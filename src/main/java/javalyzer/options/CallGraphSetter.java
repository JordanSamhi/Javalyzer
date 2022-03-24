package javalyzer.options;

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

import javalyzer.utils.CommandLineOptions;
import javalyzer.utils.Constants;
import javalyzer.utils.Environment;

public class CallGraphSetter extends EnvironmentConstructorImpl {

    public CallGraphSetter(EnvironmentConstructorImpl next) {
        super(next);
    }

    @Override
    public void setEnvironment() {
        if (CommandLineOptions.v().hasCG()) {
            String cg = CommandLineOptions.v().getCG();
            String cgPhase;
            if (cg != null) {
                cg = cg.toLowerCase();
                switch (cg) {
                    case Constants.RTA:
                        cgPhase = Constants.RTA;
                        break;
                    case Constants.VTA:
                        cgPhase = Constants.VTA;
                        break;
                    case Constants.SPARK:
                        cgPhase = Constants.SPARK;
                        break;
                    default:
                        cgPhase = Constants.CHA;
                }
            } else {
                cgPhase = Constants.CHA;
            }
            Environment.v().setWholeProgramAnalysis(true);
            Environment.v().setHasCG(true);
            Environment.v().setCgAlgo(cgPhase);
        } else {
            Environment.v().setHasCG(false);
            Environment.v().setWholeProgramAnalysis(false);
        }
    }
}
