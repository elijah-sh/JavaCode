package com.fssc;

import com.common.BaseDto;
import com.common.Cat;
import com.common.Person;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class Claim  extends BaseDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private Date table;

    private Cat cat;

    private List<Person> peoples;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTable() {
        return table;
    }

    public void setTable(Date table) {
        this.table = table;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public List<Person> getPeoples() {
        return peoples;
    }

    public void setPeoples(List<Person> peoples) {
        this.peoples = peoples;
    }
}
