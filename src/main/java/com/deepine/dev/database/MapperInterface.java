package com.deepine.dev.database;

import java.util.List;

import com.deepine.dev.beans.AccountBean;
import com.deepine.dev.beans.DataBean;
import org.apache.ibatis.annotations.*;

public interface MapperInterface {

    // TABLE CRUD ------------------------------------------------------------ //
    // CREATE TABLE ITEM_TABLE(id int, data1 nvarchar(16), data2 nvarchar(16), data3 nvarchar(16));
    // CREATE SEQUENCE ID_SEQUENCE START WITH 1000 INCREMENT BY 1;

    @Insert("insert into ITEM_TABLE(id, data1, data2, data3) values(ID_SEQUENCE.NEXTVAL, #{data1}, #{data2}, #{data3})")
    void insert_data(DataBean dataBean);

    @Update("update ITEM_TABLE set data1=#{data1}, data2=#{data2}, data3=#{data3} where id=#{id}")
    void update_data(DataBean dataBean);

    @Delete("delete from ITEM_TABLE where id=#{no}")
    void delete_data(int no);

    @Select("select id, data1, data2, data3 from ITEM_TABLE")
    List<DataBean> select_data();

    @Select("select id, data1, data2, data3 from ITEM_TABLE where id=#{no}")
    List<DataBean> select_data_filter(int no);

    
    // LOGIN ------------------------------------------------------------ //
    // CREATE TABLE USER(id varchar(15), password varchar(500), isaccountnonexpired boolean, isaccountnonlocked boolean, iscredentialsnonexpired boolean, isenabled boolean);
    // CREATE TABLE AUTHORITY(username varchar(20), authority_name varchar(20));

    @Select("SELECT * FROM USER WHERE id=#{id}")
    AccountBean readAccount(String id);

    @Select("SELECT authority_name FROM AUTHORITY WHERE username=#{id}")
    List<String> readAuthorities(String id);

    @Insert("INSERT INTO USER VALUES(#{account.id},#{account.password},#{account.isAccountNonExpired},#{account.isAccountNonLocked},#{account.isCredentialsNonExpired},#{account.isEnabled})")
    void insertUser(@Param("account") AccountBean account);

    @Insert("INSERT INTO AUTHORITY VALUES(#{id},#{autority})")
    void insertUserAuthority(@Param("id") String id, @Param("autority") String autority);

}
