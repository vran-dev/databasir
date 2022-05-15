package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.search.SearchService;
import com.databasir.core.domain.search.data.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "SearchController", description = "全局搜索 API")
public class SearchController {

    private final SearchService searchService;

    @GetMapping(Routes.Search.SEARCH)
    @Operation(summary = "搜索")
    public JsonData<SearchResponse> search(@RequestParam(name = "query") String keyword) {
        return JsonData.ok(searchService.search(keyword));
    }
}
