package com.deepine.dev.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.deepine.dev.beans.AccountBean;
import com.deepine.dev.repository.AccountRepository;
//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // /create 접속시 account ID와 Password를 저장한 AccountBean을 전달 받고 USER 및 Authority 테이블에 정보 저장
    public AccountBean save(AccountBean account, String role) {

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountNonExpired(true);
        account.setAccountNonLocked(true);
        account.setCredentialsNonExpired(true);
        account.setEnabled(true);

        return accountRepository.save(account, role);
    }

    // loadUserByUsername함수에서 사용하기 위해 id를 입력하면 Authority 테이블을 검색하여 ROLE정보를 리턴하는 함수
    public Collection<GrantedAuthority> getAuthorities(String id) {

        List<String> string_authorities = accountRepository.findAuthoritiesById(id);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String authority : string_authorities) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        return authorities;
    }

    // 로그인시 호출되는 함수로 id 정보를 넣으면 그 외 정보들을 읽고 autority를 입력하여 AccountBean을 리턴
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        AccountBean account = accountRepository.findById(id);

        account.setAuthorities(this.getAuthorities(id));

        /*
        UserDetails userDetails = new UserDetails() {

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                 return true;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public String getUsername() {
                return account.getId();
            }

            @Override
            public String getPassword() {
                return account.getPassword();
            }

            @Override
            public Collection <? extends GrantedAuthority> getAuthorities() {
                return account.getAuthorities();
            }
        };
        */

        return account;
    }



}