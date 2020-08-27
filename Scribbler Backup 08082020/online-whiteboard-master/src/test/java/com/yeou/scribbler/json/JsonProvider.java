/*
* @author  Yeou Liu
*/

package com.yeou.scribbler.json;

public interface JsonProvider
{
    public Object fromJson(String json);

    public String toJson(Object object);
}
