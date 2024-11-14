package com.animalshelter;
import org.json.JSONObject;

//Animal can have a constructor that takes name, age, and sex

public class Animal {
    String name;
    int age;
    String sex;

    Animal(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) { // Ensuring age is not negative
            this.age = age;
        }
    }

    // Getter and Setter for sex
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    // json
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("sex", sex);
        return json;
    }
}
