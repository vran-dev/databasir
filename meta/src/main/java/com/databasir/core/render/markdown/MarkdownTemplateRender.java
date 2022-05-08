package com.databasir.core.render.markdown;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.render.Render;
import com.databasir.core.render.RenderConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * use freemarker template to render markdown
 */
@RequiredArgsConstructor
public class MarkdownTemplateRender implements Render {

    private final RenderConfig renderConfig;

    private String templatePath = "template/render/markdown/markdown.ftlh";

    public MarkdownTemplateRender(RenderConfig config, String templatePath) {
        this(config);
        this.templatePath = templatePath;
    }

    @Override
    public void rendering(DatabaseMeta meta, OutputStream outputStream) throws IOException {
        doRendering(meta, outputStream);
    }

    public void doRendering(DatabaseMeta meta, OutputStream outputStream) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setClassForTemplateLoading(getClass(), "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        Map<String, Object> root = new HashMap<>();
        root.put("meta", meta);
        root.put("config", renderConfig);
        Template template = cfg.getTemplate(templatePath);
        try {
            template.process(root, new OutputStreamWriter(outputStream));
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }
}
