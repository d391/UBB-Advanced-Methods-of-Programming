package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type
{
    public boolean equals(Object another)
    {
        if(another instanceof StringType)
            return true;
        else
            return false;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    public String toString()
    {
        return "bool";
    }
}
