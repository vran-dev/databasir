package com.databasir.core.render.markdown;

import java.util.List;

public class MarkdownBuilder {

    private static final String LINE = "\n";

    private static final String DOUBLE_LINE = LINE + LINE;

    private StringBuilder builder = new StringBuilder(1024);

    private MarkdownBuilder() {
    }

    public static MarkdownBuilder builder() {
        return new MarkdownBuilder();
    }

    public MarkdownBuilder primaryTitle(String title) {
        builder.append("# ").append(title).append(DOUBLE_LINE);
        return this;
    }

    public MarkdownBuilder secondTitle(String title) {
        builder.append("## ").append(title).append(DOUBLE_LINE);
        return this;
    }

    public MarkdownBuilder thirdTitle(String title) {
        builder.append("### ").append(title).append(DOUBLE_LINE);
        return this;
    }

    public MarkdownBuilder text(String text) {
        builder.append(text).append(DOUBLE_LINE);
        return this;
    }

    public MarkdownBuilder table(List<String> titles, List<List<String>> rows) {
        if (titles == null || titles.isEmpty()) {
            throw new IllegalArgumentException("titles must not be null or empty");
        }
        // build titles
        builder.append("| ");
        for (String title : titles) {
            builder.append(title).append(" | ");
        }
        builder.append(LINE);

        // build separators
        builder.append("| ");
        for (String title : titles) {
            builder.append("------").append(" | ");
        }
        builder.append(LINE);

        // build rows
        for (List<String> row : rows) {
            builder.append("| ");
            for (String column : row) {
                builder.append(column).append(" | ");
            }
            builder.append(LINE);
        }
        builder.append(LINE);
        return this;
    }

    public MarkdownBuilder orderedList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            builder.append(i + 1).append(". ").append(list.get(i)).append(LINE);
        }
        builder.append(LINE);
        return this;
    }

    public MarkdownBuilder unorderedList(List<String> list) {
        for (String item : list) {
            builder.append("- ").append(item).append(LINE);
        }
        builder.append(LINE);
        return this;
    }

    public MarkdownBuilder blockquotes(String content) {
        builder.append("> ").append(content).append(DOUBLE_LINE);
        return this;
    }

    public MarkdownBuilder code(String languageType, String statement) {
        builder.append("```").append(languageType).append(LINE)
                .append(statement)
                .append("```")
                .append(DOUBLE_LINE);
        return this;
    }

    public MarkdownBuilder link(String text, String link) {
        builder.append("[").append(text).append("]")
                .append("(").append(link).append(")");
        return this;
    }

    public String build() {
        return builder.toString();
    }
}
