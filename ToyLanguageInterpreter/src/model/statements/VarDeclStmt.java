package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.Type;
import model.value.Value;

;

public class VarDeclStmt implements IStatement
{
    private String name;
    private Type type;
    public VarDeclStmt(String _name, Type _type){name = _name; type = _type;}

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(name))
            throw new MyException("Variable already declared!\n");
        symTbl.put(name, type.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name,type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return name + " " + type;
    }
}
