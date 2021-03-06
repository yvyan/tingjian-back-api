package org.corbin.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.corbin.common.base.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_info")
public class UserInfo  extends BaseEntity implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_account")
    private String userAccount;

    @Column(name="user_mail")
    private String userMail;

    @Column(name = "user_tel")
    private String userTel;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "user_desc")
    private String userDesc;

    @Column(name = "user_sex")
    private Integer userSex;

    @Column(name = "user_head_portrait")
    private String userHeadPortrait;



}

