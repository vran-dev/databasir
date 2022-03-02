package com.databasir.api;

import com.databasir.common.JsonData;
import com.databasir.core.domain.app.OpenAuthAppService;
import com.databasir.core.domain.app.data.*;
import com.databasir.core.domain.app.handler.OpenAuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequiredArgsConstructor
public class OpenAuth2AppController {

    private final OpenAuthHandler openAuthHandler;

    private final OpenAuthAppService openAuthAppService;

    /**
     * 无需授权
     */
    @GetMapping("/oauth2/authorization/{registrationId}")
    @ResponseBody
    public JsonData<String> authorization(@PathVariable String registrationId) {
        String authorization = openAuthHandler.authorization(registrationId);
        return JsonData.ok(authorization);
    }

    /**
     * 无需授权
     */
    @GetMapping("/oauth2/apps")
    @ResponseBody
    public JsonData<List<OAuthAppResponse>> listApps() {
        return JsonData.ok(openAuthAppService.listAll());
    }

    @GetMapping(Routes.OAuth2App.LIST_PAGE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @ResponseBody
    public JsonData<Page<OAuthAppPageResponse>> listPage(@PageableDefault(sort = "id", direction = DESC)
                                                                 Pageable page,
                                                         OAuthAppPageCondition condition) {
        return JsonData.ok(openAuthAppService.listPage(page, condition));
    }

    @GetMapping(Routes.OAuth2App.GET_ONE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @ResponseBody
    public JsonData<OAuthAppDetailResponse> getOne(@PathVariable Integer id) {
        return JsonData.ok(openAuthAppService.getOne(id));

    }

    @PostMapping(Routes.OAuth2App.CREATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @ResponseBody
    public JsonData<Integer> create(@RequestBody @Valid OAuthAppCreateRequest request) {
        Integer id = openAuthAppService.create(request);
        return JsonData.ok(id);
    }

    @PatchMapping(Routes.OAuth2App.UPDATE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @ResponseBody
    public JsonData<Void> updateById(@RequestBody @Valid OAuthAppUpdateRequest request) {
        openAuthAppService.updateById(request);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.OAuth2App.DELETE)
    @PreAuthorize("hasAnyAuthority('SYS_OWNER')")
    @ResponseBody
    public JsonData<Void> deleteById(@PathVariable Integer id) {
        openAuthAppService.deleteById(id);
        return JsonData.ok();
    }
}
