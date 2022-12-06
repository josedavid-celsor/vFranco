package com.vFranco.vFranco.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private HttpServletRequest oRequest;

    @Autowired
    UserRepository oUserRepository;

    public String login(@RequestBody UserBean oUserBean) {
        if (oUserBean.getPassword() != null) {
            UserEntity oUserEntity = oUserRepository.findByUsernameAndPassword(oUserBean.getUsername(), oUserBean.getPassword());
            if (oUserEntity != null) {
                return JwtHelper.generateJWT(oUserBean.getUsername());
            } else {
                throw new UnauthorizedException("login or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

    public UserEntity check() {
        String strUserName = (String) oRequest.getAttribute("User");
        if (strUserName != null) {
            UserEntity oUserEntity = oUserRepository.findByUsername(strUserName);
            return oUserEntity;
        } else {
            throw new UnauthorizedException("No active session");
        }
    }

    public boolean isAdmin() {
        UserEntity oUserSessionEntity = oUserRepository.findByUsername((String)  oRequest.getAttribute("User"));
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN.getUsertype())) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        UserEntity oUserSessionEntity = oUserRepository.findByUsername((String)  oRequest.getAttribute("User"));
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN.getUsertype())) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    /*    
    
    public boolean isLoggedIn() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    public Long getUserID() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            return oUsuarioSessionEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }


    public boolean isUser() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                return true;
            }
        }
        return false;
    }


    public void OnlyUsers() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user role");
        } else {
            if (!oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                throw new UnauthorizedException("this request is only allowed to user role");
            }
        }
    }

    public void OnlyAdminsOrUsers() {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        } else {

        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        UsuarioEntity oUsuarioSessionEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioSessionEntity != null) {
            if (oUsuarioSessionEntity.getTipousuario().getId().equals(TipoUsuarioHelper.USER)) {
                if (!oUsuarioSessionEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own data");
                }
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        }
    }

     */
}
