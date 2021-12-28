package com.databasir.core.doc.render;

import com.databasir.core.doc.model.DatabaseDoc;
import com.databasir.core.doc.render.markdown.MarkdownRender;

import java.io.IOException;
import java.io.OutputStream;

public interface Render {

    void rendering(DatabaseDoc doc, OutputStream outputStream) throws IOException;

    static Render markdownRender(RenderConfig configuration) {
        return MarkdownRender.of(configuration);
    }
}
