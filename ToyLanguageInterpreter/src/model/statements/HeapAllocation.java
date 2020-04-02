package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

import java.io.IOException;

public class HeapAllocation implements IStatement
{
    private Exp exp;
    private String varName;
    public HeapAllocation(String _varName, Exp _exp)
    {
        exp = _exp;
        varName = _varName;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, IOException
    {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();
        if(symTbl.isDefined(varName))
        {
            Type varType = symTbl.lookup(varName).getType();
            if(varType instanceof RefType)
            {
                Value val = exp.eval(symTbl, (Heap<Integer, Value>) state.getHeap());
                if(val.getType().equals(((RefType) varType).getInner()))
                {
                    RefValue refValue = new RefValue(heap.getCurrentAddress(), val.getType());
                    heap.put(heap.getCurrentAddress(), val);
                    symTbl.update(varName, refValue);
                }
                else
                    throw new MyException("Location type not the same with given value type\n");
            }
            else
                throw new MyException("Must be a reference type!\n" );
        }
        else
            throw new MyException("Variable not defined! \n");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varName);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
}
