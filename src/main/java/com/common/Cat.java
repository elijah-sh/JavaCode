package com.common;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Generated;
@Generated("CAT_TABLE")
@Length(max = 11)
public class Cat  extends BaseDto {
    public Cat(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
