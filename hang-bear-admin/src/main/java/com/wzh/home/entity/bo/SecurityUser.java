package com.wzh.home.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wzh.home.entity.po.UmsUser;

/**
 * <p>
 * security custom user class
 * </p>
 *
 * @author weizhuohang
 * @since 2021/1/8 14:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityUser extends UmsUser implements UserDetails {

    /**
     * 是否启用
     */
    private Boolean isEnabled = Boolean.TRUE;

    /**
     * 权限集合
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
     *         (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
     *         valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
