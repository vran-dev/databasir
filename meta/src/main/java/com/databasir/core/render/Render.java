package com.databasir.core.render;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.render.markdown.MarkdownTemplateRender;

import java.io.IOException;
import java.io.OutputStream;

public interface Render {

    void rendering(DatabaseMeta meta, OutputStream outputStream) throws IOException;

    static Render markdownRender(RenderConfig configuration) {
        return new MarkdownTemplateRender(configuration);
    }

}
