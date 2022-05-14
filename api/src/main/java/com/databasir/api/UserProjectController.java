package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.user.data.FavoriteProjectPageCondition;
import com.databasir.core.domain.user.data.FavoriteProjectPageResponse;
import com.databasir.core.domain.user.service.UserProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "UserProjectController", description = "用户关注项目 API")
public class UserProjectController {

    private final UserProjectService userProjectService;

    @GetMapping(Routes.UserProject.LIST)
    @Operation(summary = "获取用户关注项目列表")
    public JsonData<Page<FavoriteProjectPageResponse>> listFavorites(
            @PageableDefault(sort = "id", direction = DESC) Pageable pageable,
            FavoriteProjectPageCondition condition) {
        DatabasirUserDetails user = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = user.getUserPojo().getId();
        return JsonData.ok(userProjectService.listFavorites(pageable, userId, condition));
    }

    @PostMapping(Routes.UserProject.ADD)
    @Operation(summary = "添加用户关注项目")
    public JsonData<Void> addFavorite(@PathVariable Integer projectId) {
        DatabasirUserDetails user = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = user.getUserPojo().getId();
        userProjectService.addFavorites(projectId, userId);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.UserProject.REMOVE)
    @Operation(summary = "删除用户关注项目")
    public JsonData<Void> removeFavorite(@PathVariable Integer projectId) {
        DatabasirUserDetails user = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = user.getUserPojo().getId();
        userProjectService.removeFavorites(projectId, userId);
        return JsonData.ok();
    }
}
