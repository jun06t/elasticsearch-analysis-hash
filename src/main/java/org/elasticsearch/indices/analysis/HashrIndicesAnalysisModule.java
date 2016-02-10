package org.elasticsearch.indices.analysis;

import org.elasticsearch.common.inject.AbstractModule;

/**
 */
public class HashrIndicesAnalysisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HashrIndicesAnalysis.class).asEagerSingleton();
    }
}