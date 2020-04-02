package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.Type;
import model.value.Value;

public class AssignStmt implements IStatement
{
    private String id;
    private Exp exp;

    public AssignStmt(String _id, Exp _exp){ id = _id; exp = _exp;}
    public String toString()
    {
        return id+" = "+ exp.toString();
    }


    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value val = exp.eval(symTbl, (Heap<Integer, Value>) state.getHeap());
        if(symTbl.isDefined(id))
        {
            Type typId = symTbl.lookup(id).getType();
            if(val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match");
        }
        else throw new MyException("The used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

}
