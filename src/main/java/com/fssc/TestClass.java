package com.fssc;


public class TestClass implements Comparable
{
    private int id;
    private String name;

    public TestClass()
    {
        this.id = 1;
        this.name = "somebody";
    }

    public TestClass(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public int compareTo(Object obj)
    {
        return 0;
    }

    public String toString()
    {
        return "<"+ id + "> "+"<"+name+">";
    }
}

