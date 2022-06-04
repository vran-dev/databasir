package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.DatabaseType;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.databasir.dao.Tables.DATABASE_TYPE;

@Repository
public class DatabaseTypeDao extends BaseDao<DatabaseType> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public DatabaseTypeDao() {
        super(DATABASE_TYPE, DatabaseType.class);
    }

    public boolean existsByDatabaseType(String databaseType) {
        return exists(DATABASE_TYPE.DATABASE_TYPE_.eq(databaseType)
                .and(DATABASE_TYPE.DELETED.eq(false)));
    }

    public boolean existsById(Integer id) {
        return exists(DATABASE_TYPE.ID.eq(id)
                .and(DATABASE_TYPE.DELETED.eq(false)));
    }

    public DatabaseType selectByDatabaseType(String databaseType) {
        return this.selectOne(DATABASE_TYPE.DATABASE_TYPE_.eq(databaseType)
                .and(DATABASE_TYPE.DELETED.eq(false)));
    }

    @Override
    public List<DatabaseType> selectAll() {
        return this.getDslContext().selectFrom(DATABASE_TYPE)
                .where(DATABASE_TYPE.DELETED.eq(false))
                .orderBy(DATABASE_TYPE.ID.desc())
                .fetchInto(DatabaseType.class);
    }

    public int deleteById(Integer id) {
        return this.getDslContext()
                .update(DATABASE_TYPE)
                .set(DATABASE_TYPE.DELETED, true)
                .set(DATABASE_TYPE.DELETED_TOKEN, DATABASE_TYPE.ID)
                .where(DATABASE_TYPE.ID.eq(id)
                        .and(DATABASE_TYPE.DELETED.eq(false)))
                .execute();
    }

    public Optional<DatabaseType> selectOptionalById(Integer id) {
        return super.selectOptionalOne(DATABASE_TYPE.DELETED.eq(false).and(DATABASE_TYPE.ID.eq(id)));
    }

    public void updateDriverFile(Integer id, String driverFile) {
        this.getDslContext()
                .update(DATABASE_TYPE)
                .set(DATABASE_TYPE.JDBC_DRIVER_FILE_PATH, driverFile)
                .where(DATABASE_TYPE.ID.eq(id))
                .execute();
    }
}