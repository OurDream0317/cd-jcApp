package com.tuozhi.zhlw.common.datasource;

public enum DataSourceType {

    Master("master"),
    Slave1("slave1"),
    Slave2("slave2");

    private String name;

    DataSourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
