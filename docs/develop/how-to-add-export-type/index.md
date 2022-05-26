# 如何扩展导出文件类型？

> Note: 本节内容默认读者有 Java 开发能力 

## 实现步骤

### 一、扩展文件类型

在 `com.databasir.core.domain.document.generator.DocumentFileType` 中新增文件类型

```java
@AllArgsConstructor
@Getter
public enum DocumentFileType {

    MARKDOWN("md", "Markdown"),

    PLANT_UML_ER_SVG("svg", "UML SVG"),

    PLANT_UML_ER_PNG("png", "UML PNG"),
    ;

    private String fileExtension;

    private String name;

}
```

`DocumentFilType` 是一个枚举类型，构造器需要传入两个参数

| 名称          | 说明                                                         |      |
| ------------- | ------------------------------------------------------------ | ---- |
| fileExtension | 代表导出文件的后缀，用户导出项目文件时系统会使用 `项目名 + "." + {{fileExtension}}` 去命名文件 |      |
| name          | 展示给用户的标签                                             |      |

比如我现在要新增一个 excel 格式的导出类型，那么新增一个枚举值 `EXCEL`

```java
@AllArgsConstructor
@Getter
public enum DocumentFileType {

    MARKDOWN("md", "Markdown"),

    PLANT_UML_ER_SVG("svg", "UML SVG"),

    PLANT_UML_ER_PNG("png", "UML PNG"),
    
    EXCEL("xlsx", "Excel")
    ;

    private String fileExtension;

    private String name;

}
```



### 二、实现文件生成逻辑

接下来就是定义一个类，该类需要实现接口 `com.databasir.core.domain.document.generator.DocumentFileGenerator` 

```java
public interface DocumentFileGenerator {

    boolean support(DocumentFileType type);

    void generate(DocumentFileGenerateContext context, OutputStream outputStream);

    @Getter
    @Builder
    class DocumentFileGenerateContext {

        @NonNull
        private DocumentFileType documentFileType;

        @NonNull
        private DatabaseDocumentResponse databaseDocument;

    }
}
```

其中

- support 方法标识该类支持生成的文件类型
- generate 是具体的生成逻辑

继续完善的 excel 导出的扩展，记住实现类一定要加上spring 的 `@Component` 注解，不然 [Databasir](https://github.com/vran-dev/databasir) 无法找到该实现类

```java
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MarkdownDocumentFileGenerator implements DocumentFileGenerator {
    
    @Override
    public boolean support(DocumentFileType type) {
        // 这里表示该实现类是支持 EXCEL 格式导出的
        return type == DocumentFileType.EXCEL;
    }

    @Override
    public void generate(DocumentFileGenerateContext context, OutputStream outputStream) {
        Path path = .... // excel path
        byte[] bytes = Files.readAllBytes(path);
        // 将文件的字节流通过 stream 写出即可
        outputStream.write(bytes)
    }
}
```





## 示例

[Databasir](https://github.com/vran-dev/databasir) 目前已经提供了 markdown、png、svg 三种格式的文件导出，其中 png 和 svg 文件是基于 plantuml 导出的实体关系图。

对应的源码如下，读者可以参考

- SVG：`com.databasir.core.domain.document.generator.plantuml.PlantUmlErSvgFileGenerator`
- PNG：`com.databasir.core.domain.document.generator.plantuml.PlantUmlPngFileGenerator`
- Markdown：`com.databasir.core.domain.document.generator.MarkdownDocumentFileGenerator`