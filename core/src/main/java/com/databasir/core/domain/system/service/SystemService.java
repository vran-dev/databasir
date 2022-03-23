package com.databasir.core.domain.system.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.system.data.SystemEmailResponse;
import com.databasir.core.domain.system.data.SystemEmailUpdateRequest;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.tables.pojos.SysMailPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final SysMailDao sysMailDao;

    public void renewKey() {
        // TODO
    }

    public Optional<SystemEmailResponse> getEmailSetting() {
        return sysMailDao.selectOptionTopOne()
                .map(mail -> {
                    SystemEmailResponse response = new SystemEmailResponse();
                    response.setSmtpHost(mail.getSmtpHost());
                    response.setSmtpPort(mail.getSmtpPort());
                    response.setUsername(mail.getUsername());
                    response.setCreateAt(mail.getCreateAt());
                    response.setUseSSL(mail.getUseSsl());
                    return response;
                });
    }

    public void deleteSystemEmail() {
        sysMailDao.selectOptionTopOne().ifPresent(d -> {
            sysMailDao.deleteById(d.getId());
        });
    }

    public void updateEmailSetting(SystemEmailUpdateRequest request) {
        SysMailPojo sysMailPojo = new SysMailPojo();
        sysMailPojo.setSmtpHost(request.getSmtpHost());
        sysMailPojo.setSmtpPort(request.getSmtpPort());
        sysMailPojo.setUsername(request.getUsername());

        Optional<Integer> idOpt = sysMailDao.selectOptionTopOne().map(SysMailPojo::getId);
        idOpt.ifPresent(sysMailPojo::setId);
        if (request.getPassword() != null) {
            // TODO encrypt password ?
            sysMailPojo.setPassword(request.getPassword());
        }

        if (idOpt.isPresent()) {
            if (!StringUtils.hasText(request.getPassword())) {
                throw DomainErrors.CONNECT_DATABASE_FAILED.exception();
            }
            sysMailDao.updateById(sysMailPojo);
        } else {
            sysMailDao.insertAndReturnId(sysMailPojo);
        }
    }
}
