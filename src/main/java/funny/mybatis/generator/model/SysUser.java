package funny.mybatis.generator.model;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table sys_user
 *
 * @mbggenerated do_not_delete_during_merge
 */
public class SysUser {
    /**
     * Database Column Remarks:
     *   用户id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   用户名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * Database Column Remarks:
     *   密码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.user_password
     *
     * @mbggenerated
     */
    private String userPassword;

    /**
     * Database Column Remarks:
     *   邮箱
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.user_email
     *
     * @mbggenerated
     */
    private String userEmail;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * Database Column Remarks:
     *   简介
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.user_info
     *
     * @mbggenerated
     */
    private String userInfo;

    /**
     * Database Column Remarks:
     *   头像
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.head_img
     *
     * @mbggenerated
     */
    private byte[] headImg;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.id
     *
     * @return the value of sys_user.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.id
     *
     * @param id the value for sys_user.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.user_name
     *
     * @return the value of sys_user.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.user_name
     *
     * @param userName the value for sys_user.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.user_password
     *
     * @return the value of sys_user.user_password
     *
     * @mbggenerated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.user_password
     *
     * @param userPassword the value for sys_user.user_password
     *
     * @mbggenerated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.user_email
     *
     * @return the value of sys_user.user_email
     *
     * @mbggenerated
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.user_email
     *
     * @param userEmail the value for sys_user.user_email
     *
     * @mbggenerated
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.create_time
     *
     * @return the value of sys_user.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.create_time
     *
     * @param createTime the value for sys_user.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.user_info
     *
     * @return the value of sys_user.user_info
     *
     * @mbggenerated
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.user_info
     *
     * @param userInfo the value for sys_user.user_info
     *
     * @mbggenerated
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo == null ? null : userInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.head_img
     *
     * @return the value of sys_user.head_img
     *
     * @mbggenerated
     */
    public byte[] getHeadImg() {
        return headImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.head_img
     *
     * @param headImg the value for sys_user.head_img
     *
     * @mbggenerated
     */
    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
    }
}