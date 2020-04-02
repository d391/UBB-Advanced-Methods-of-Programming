package model;

public class MyException extends Exception
{
    private String msg;
    public MyException(String _msg) {msg = _msg;};

    @Override
    public String toString() {
        return msg;
    }
}
