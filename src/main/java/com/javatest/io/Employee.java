package com.javatest.io;

/**
 * Created by ShenShuaihu on 2018/8/6.
 */
public class Employee {
    private  int pid;
    private  String department;
    public String name;
    private String sex;
    private  String employeeId;
    private String groupId;
    private String direction;
    private String remarks;

    @Override
    public String toString() {
        return "Employee{" +
                "pid=" + pid +
                ", department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", employeeId=" + employeeId +
                ", groupId=" + groupId +
                ", direction='" + direction + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getPid() {
        return pid;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDirection() {
        return direction;
    }

    public String getRemarks() {
        return remarks;
    }

    public void getSa(String name) {
        System.out.println("E : "+name);

    }
}
