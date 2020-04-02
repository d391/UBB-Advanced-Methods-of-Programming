package model.statements;


import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.Type;

public class NopStmt implements IStatement
{
    private String name;
    private Type type;

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
    @Override
    public String toString()
    {
        return "";
    }
}
