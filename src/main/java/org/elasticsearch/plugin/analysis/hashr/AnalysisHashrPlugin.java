package org.elasticsearch.plugin.analysis.hashr;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.HashrIndicesAnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.ArrayList;
import java.util.Collection;

public class AnalysisHashrPlugin extends AbstractPlugin {
    @Override
    public String name() {
        return "analysis-hashr";
    }

    @Override
    public String description() {
        return "Hashr analysis support";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        Collection<Class<? extends Module>> classes = new ArrayList<>();
        classes.add(HashrIndicesAnalysisModule.class);
        return classes;
    }

    public void onModule(AnalysisModule module){
        module.addAnalyzer("hashr", HashrAnalysisProvider.class);
    }

}

