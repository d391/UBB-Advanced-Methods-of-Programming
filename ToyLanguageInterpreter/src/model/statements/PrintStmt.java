package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.expressions.Exp;
import model.type.Type;
import model.value.Value;

public class PrintStmt implements IStatement
{
    private Exp exp;
    public PrintStmt(Exp _exp){exp = _exp;}
    public String toString()
    {
        return "print(" +exp.toString()+")";
    }

    public PrgState execute(PrgState state) throws MyException
    {
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        out.add(exp.eval(symTable, (Heap<Integer, Value>) state.getHeap()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;

    }

}
