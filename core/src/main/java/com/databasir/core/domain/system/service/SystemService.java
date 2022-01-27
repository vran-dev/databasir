package com.databasir.core.domain.system.service;

import com.databasir.common.codec.Aes;
import com.databasir.common.codec.Rsa;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.system.data.SystemEmailResponse;
import com.databasir.core.domain.system.data.SystemEmailUpdateRequest;
import com.databasir.dao.impl.SysKeyDao;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.SysKeyPojo;
import com.databasir.dao.tables.pojos.SysMailPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final SysKeyDao sysKeyDao;

    private final SysMailDao sysMailDao;

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void postInit() {
        sysKeyDao.selectOptionTopOne()
                .orElseGet(() -> {
                    SysKeyPojo pojo = new SysKeyPojo();
                    pojo.setAesKey(Aes.randomBase64Key());
                    Rsa.RsaBase64Key key = Rsa.generateBase64Key();
                    pojo.setRsaPublicKey(key.getPublicBase64Key());
                    pojo.setRsaPrivateKey(key.getPrivateBase64Key());
                    sysKeyDao.insertAndReturnId(pojo);
                    return pojo;
                });

        String email = "N/A";
        String username = "databasir";
        Optional<UserPojo> userOpt = userDao.selectByEmail(email);
        if (!userOpt.isPresent()) {
            UserPojo admin = new UserPojo();
            admin.setEmail(email);
            admin.setUsername(username);
            admin.setPassword(bCryptPasswordEncoder.encode(username));
            admin.setEnabled(true);
            admin.setNickname("Databasir Admin");
            Integer userId = userDao.insertAndReturnId(admin);
            UserRolePojo role = new UserRolePojo();
            role.setUserId(userId);
            role.setRole("SYS_OWNER");
            userRoleDao.insertAndReturnId(role);
        }
    }

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
                    return response;
                });
    }

    public void updateEmailSetting(SystemEmailUpdateRequest request) {
        Optional<Integer> idOpt = sysMailDao.selectOptionTopOne().map(SysMailPojo::getId);
        SysMailPojo sysMailPojo = new SysMailPojo();
        sysMailPojo.setSmtpHost(request.getSmtpHost());
        sysMailPojo.setSmtpPort(request.getSmtpPort());
        sysMailPojo.setUsername(request.getUsername());
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
