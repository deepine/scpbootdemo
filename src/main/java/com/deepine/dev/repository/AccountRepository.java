package com.deepine.dev.repository;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;

import java.util.List;
import com.deepine.dev.beans.AccountBean;
import com.deepine.dev.database.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    //MyBatis Mapper
    @Autowired
    MapperInterface accountMapper;

    // User 테이블과 Authority 테이블에 회원정보 저장
    public AccountBean save(AccountBean account, String role) {
        accountMapper.insertUser(account);
        accountMapper.insertUserAuthority(account.getId(), role);
        return account;
    }

    // User 테이블에서 회원 ID로 회원정보를 읽어옴
    public AccountBean findById(String id) {
        return accountMapper.readAccount(id);
    }

    // Authority 테이블에서 회원 ID로 권한이름을 읽어옴
    public List<String> findAuthoritiesById(String id) {
        return accountMapper.readAuthorities(id);
    }
}