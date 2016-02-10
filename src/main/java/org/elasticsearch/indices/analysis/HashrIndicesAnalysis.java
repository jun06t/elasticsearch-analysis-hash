package org.elasticsearch.indices.analysis;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.component.AbstractComponent;

public class HashrIndicesAnalysis extends AbstractComponent {

    @Inject
    public HashrIndicesAnalysis(Settings settings,
                                   IndicesAnalysisService indicesAnalysisService) {
        super(settings);

        indicesAnalysisService.analyzerProviderFactories().put("hashr",
                new PreBuiltAnalyzerProviderFactory("hashr", AnalyzerScope.INDICES,
                        new JapaneseAnalyzer()));
    }
}
