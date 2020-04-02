package model.value;

import model.type.BoolType;
import model.type.Type;

public class BoolValue implements Value
{
    private boolean val;
    public BoolValue(boolean v){val = v;}
    public boolean getValue(){return val;}
    public String toString() {if(val == true) return "true"; return "false";}

    @Override
    public Type getType()
    {
        return new BoolType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolValue)
            return true;
        else
            return false;
    }
}
