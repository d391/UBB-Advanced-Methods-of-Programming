package model.value;

import model.type.StringType;
import model.type.Type;

public class StringValue implements Value
{
    private String val;
    public StringValue(String v) { val = v;}
    public String getValue() {return val;}
    public String toString(){return val;}

    @Override
    public Type getType()
    {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringValue)
            return true;
        else
            return false;
    }
}
