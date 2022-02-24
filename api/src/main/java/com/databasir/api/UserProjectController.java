package com.databasir.api;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.user.data.FavoriteProjectPageCondition;
import com.databasir.core.domain.user.data.FavoriteProjectPageResponse;
import com.databasir.core.domain.user.service.UserProjectService;
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
public class UserProjectController {

    private final UserProjectService userProjectService;

    @GetMapping(Routes.UserProject.LIST)
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
    public JsonData<Void> addFavorite(@PathVariable Integer projectId) {
        DatabasirUserDetails user = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = user.getUserPojo().getId();
        userProjectService.addFavorites(projectId, userId);
        return JsonData.ok();
    }

    @DeleteMapping(Routes.UserProject.REMOVE)
    public JsonData<Void> removeFavorite(@PathVariable Integer projectId) {
        DatabasirUserDetails user = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer userId = user.getUserPojo().getId();
        userProjectService.removeFavorites(projectId,  userId);
        return JsonData.ok();
    }
}
