package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.task.ProjectSimpleTaskResponse;
import com.databasir.dao.tables.pojos.ProjectSyncTask;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProjectSimpleTaskResponseConverterImpl implements ProjectSimpleTaskResponseConverter {

    @Override
    public List<ProjectSimpleTaskResponse> of(List<ProjectSyncTask> pojos) {
        if ( pojos == null ) {
            return null;
        }

        List<ProjectSimpleTaskResponse> list = new ArrayList<ProjectSimpleTaskResponse>( pojos.size() );
        for ( ProjectSyncTask projectSyncTask : pojos ) {
            list.add( of( projectSyncTask ) );
        }

        return list;
    }

    @Override
    public ProjectSimpleTaskResponse of(ProjectSyncTask pojo) {
        if ( pojo == null ) {
            return null;
        }

        ProjectSimpleTaskResponse projectSimpleTaskResponse = new ProjectSimpleTaskResponse();

        projectSimpleTaskResponse.setTaskId( pojo.getId() );
        projectSimpleTaskResponse.setResult( pojo.getResult() );
        projectSimpleTaskResponse.setRunAt( pojo.getRunAt() );
        projectSimpleTaskResponse.setStatus( pojo.getStatus() );

        return projectSimpleTaskResponse;
    }
}
