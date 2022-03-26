package com.databasir.core.domain.user.service;

import com.databasir.common.DatabasirException;
import com.databasir.core.BaseTest;
import com.databasir.core.domain.DomainErrors;
import com.databasir.dao.impl.UserFavoriteProjectDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
class UserProjectServiceTest extends BaseTest {

    @Autowired
    private UserProjectService userProjectService;

    @Autowired
    private UserFavoriteProjectDao userFavoriteProjectDao;

    @Test
    @Sql("classpath:sql/domain/user/AddFavorites.sql")
    void addFavorites() {
        int userId = 1;
        int projectId = 999;
        userProjectService.addFavorites(projectId, userId);
        var data = userFavoriteProjectDao.selectByUserIdAndProjectIds(userId, List.of(projectId));
        Assertions.assertEquals(1, data.size());
    }

    @Test
    void addFavoritesWhenProjectNotExists() {
        DatabasirException ex = Assertions.assertThrows(DatabasirException.class,
                () -> userProjectService.addFavorites(-999, 1));
        Assertions.assertEquals(DomainErrors.PROJECT_NOT_FOUND.getErrCode(), ex.getErrCode());
    }

    @Test
    @Sql("classpath:sql/domain/user/RemoveFavorites.sql")
    void removeFavorites() {
        int projectId = 999;
        int userId = 999;
        // remove exists data
        userProjectService.removeFavorites(projectId, userId);
        var data = userFavoriteProjectDao.selectByUserIdAndProjectIds(userId, List.of(projectId));
        Assertions.assertEquals(0, data.size());

        // remove unknown data
        userProjectService.removeFavorites(-999, -999);
    }
}