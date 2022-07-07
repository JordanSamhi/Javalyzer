package javalyzer.extractors.listbuilders;

import javalyzer.extractors.AbstractExtractor;
import javalyzer.extractors.ControlFlowGraphExtractor;
import javalyzer.utils.CommandLineOptions;

public class CFGExtractorBuilder extends ExtractorListBuilderImpl {
    public CFGExtractorBuilder(ExtractorListBuilderImpl next) {
        super(next);
    }

    @Override
    public AbstractExtractor getExtractorHandled() {
        return new ControlFlowGraphExtractor();
    }

    @Override
    public boolean getCondition() {
        return CommandLineOptions.v().hasCFG();
    }
}
