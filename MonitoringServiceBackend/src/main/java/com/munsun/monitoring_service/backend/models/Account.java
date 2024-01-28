package com.munsun.monitoring_service.backend.models;

import com.munsun.monitoring_service.backend.models.embedded.PlaceLivingEmbedded;
import com.munsun.monitoring_service.backend.security.enums.Role;

/**
 * Entity for storing user information
 */
public class Account {
    private Long id;
    private String login;
    private String password;
    private PlaceLivingEmbedded placeLiving;
    private Role role;
    private boolean isBlocked;

    public Account(Long id, String login, String password, PlaceLivingEmbedded placeLiving, Role role, boolean isBlocked) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.placeLiving = placeLiving;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public Account(Long id) {
        this.id = id;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public PlaceLivingEmbedded getPlaceLiving() {
        return placeLiving;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlaceLiving(PlaceLivingEmbedded placeLiving) {
        this.placeLiving = placeLiving;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (isBlocked != account.isBlocked) return false;
        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (login != null ? !login.equals(account.login) : account.login != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (placeLiving != null ? !placeLiving.equals(account.placeLiving) : account.placeLiving != null) return false;
        return role == account.role;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (placeLiving != null ? placeLiving.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (isBlocked ? 1 : 0);
        return result;
    }
}
