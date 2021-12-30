package com.databasir.core.render;

import com.databasir.core.meta.pojo.DatabaseMeta;
import com.databasir.core.render.markdown.MarkdownRender;

import java.io.IOException;
import java.io.OutputStream;

public interface Render {

    void rendering(DatabaseMeta meta, OutputStream outputStream) throws IOException;

    static Render markdownRender(RenderConfig configuration) {
        return MarkdownRender.of(configuration);
    }
}
