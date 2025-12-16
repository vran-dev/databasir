package com.databasir.core.domain.log.converter;

import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.domain.log.data.OperationLogPageResponse.InvolvedGroupData;
import com.databasir.core.domain.log.data.OperationLogPageResponse.InvolvedProjectData;
import com.databasir.core.domain.log.data.OperationLogPageResponse.InvolvedUserData;
import com.databasir.core.domain.log.data.OperationLogPageResponse.OperationLogPageResponseBuilder;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.OperationLog;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class OperationLogConverterImpl implements OperationLogConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public OperationLogPageResponse to(OperationLog pojo, Group involvedGroup, User involvedUser, Project involvedProject) {
        if ( pojo == null && involvedGroup == null && involvedUser == null && involvedProject == null ) {
            return null;
        }

        OperationLogPageResponseBuilder operationLogPageResponse = OperationLogPageResponse.builder();

        if ( pojo != null ) {
            if ( pojo.getId() != null ) {
                operationLogPageResponse.id( pojo.getId().intValue() );
            }
            operationLogPageResponse.createAt( pojo.getCreateAt() );
            operationLogPageResponse.isSuccess( pojo.getIsSuccess() );
            operationLogPageResponse.operationCode( pojo.getOperationCode() );
            operationLogPageResponse.operationModule( pojo.getOperationModule() );
            operationLogPageResponse.operationName( pojo.getOperationName() );
            operationLogPageResponse.operationResponse( jsonConverter.toJsonData( pojo.getOperationResponse() ) );
            operationLogPageResponse.operatorNickname( pojo.getOperatorNickname() );
            operationLogPageResponse.operatorUserId( pojo.getOperatorUserId() );
            operationLogPageResponse.operatorUsername( pojo.getOperatorUsername() );
        }
        if ( involvedGroup != null ) {
            operationLogPageResponse.involvedGroup( groupToInvolvedGroupData( involvedGroup ) );
        }
        if ( involvedUser != null ) {
            operationLogPageResponse.involvedUser( userToInvolvedUserData( involvedUser ) );
        }
        if ( involvedProject != null ) {
            operationLogPageResponse.involvedProject( projectToInvolvedProjectData( involvedProject ) );
        }

        return operationLogPageResponse.build();
    }

    protected InvolvedGroupData groupToInvolvedGroupData(Group group) {
        if ( group == null ) {
            return null;
        }

        InvolvedGroupData involvedGroupData = new InvolvedGroupData();

        involvedGroupData.setId( group.getId() );
        involvedGroupData.setName( group.getName() );

        return involvedGroupData;
    }

    protected InvolvedProjectData projectToInvolvedProjectData(Project project) {
        if ( project == null ) {
            return null;
        }

        InvolvedProjectData involvedProjectData = new InvolvedProjectData();

        involvedProjectData.setId( project.getId() );
        involvedProjectData.setName( project.getName() );

        return involvedProjectData;
    }

    protected InvolvedUserData userToInvolvedUserData(User user) {
        if ( user == null ) {
            return null;
        }

        InvolvedUserData involvedUserData = new InvolvedUserData();

        involvedUserData.setEmail( user.getEmail() );
        involvedUserData.setId( user.getId() );
        involvedUserData.setNickname( user.getNickname() );

        return involvedUserData;
    }
}
