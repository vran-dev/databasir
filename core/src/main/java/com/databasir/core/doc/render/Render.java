package com.databasir.core.doc.render;

import com.databasir.core.doc.model.DatabaseDoc;

import java.io.OutputStream;

public interface Render {

    void rendering(DatabaseDoc doc, OutputStream outputStream);

}
