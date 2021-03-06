package com.afayp.baseadapter.adapter.sample;

/**
 * Created by Administrator on 2016/9/24.
 */
public class Model {

    private String name;
    private int type;// type 1,2

    public Model() {
    }

    public Model(String name, int type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
