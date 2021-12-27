package com.databasir.core.doc.render;

import com.databasir.core.doc.model.DatabaseDoc;

import java.io.OutputStream;

public class Renders {

    private Render markdownRender = null;

    public void render(DatabaseDoc doc,
                       OutputStream outputStream,
                       RenderConfiguration config) {
        markdownRender.rendering(doc, outputStream);
    }
}
