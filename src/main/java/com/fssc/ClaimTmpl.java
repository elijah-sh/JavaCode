package com.fssc;

import com.common.BaseDto;
import com.common.Cat;
import com.common.Person;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ClaimTmpl extends BaseDto {
    private Long id;
    private String name;
    private Claim claim;

    private List<Cat> cats;

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

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }
}
