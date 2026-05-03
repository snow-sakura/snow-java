package com.bjpowernode.p2padmin.user.model;

import java.util.*;

public class UserInfo {
    private Integer id;

    private String username;

    private String password;

    private Date createDate;

    private Integer loginCount;

    private Date lastLoginTime;

    private Integer staffId;

    private List<PermissionInfo> menuList=new ArrayList<PermissionInfo>();
    private Map urlMap=new HashMap<>();
    private StaffInfo staffInfo;

    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    public Map getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(Map urlMap) {
        this.urlMap = urlMap;
    }

    public List<PermissionInfo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<PermissionInfo> menuList) {
        this.menuList = menuList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }
}