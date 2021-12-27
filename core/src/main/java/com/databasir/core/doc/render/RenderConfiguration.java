package com.databasir.core.doc.render;

public class RenderConfiguration {

    private Boolean renderTables = true;

    private Boolean renderColumns = true;

    private Boolean renderIndexes = true;

    private Boolean renderViews = false;

    private Boolean renderTriggers = false;

    private Boolean renderProducers = false;

    private ColumnValueConverter columnValueConverter = new DefaultColumnValueConverter();
}
