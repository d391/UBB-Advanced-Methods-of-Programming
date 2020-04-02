package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue  implements Value
{
    private int address;
    private Type locationType;
    public RefValue(int _address, Type _locationType)
    {
        address = _address;
        locationType = _locationType;
    }

    public int getAddress() {return address;}
    public void setAddress(int newAddr) {address = newAddr;}
    public Type getType() { return new RefType(locationType);}
    public Type getLocationType() { return locationType;}

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

}

