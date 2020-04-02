package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.Heap;
import model.adt.MyIDictionary;
import model.expressions.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

import java.io.IOException;

public class HeapWriting implements IStatement
{
    private Exp exp;
    private String varName;
    public HeapWriting(String _varName, Exp _exp)
    {
        varName = _varName;
        exp = _exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, IOException
    {
        if(state.getSymTable().isDefined(varName))
        {
            Value oldVal = state.getSymTable().lookup(varName);
            if(oldVal.getType() instanceof RefType)
            {
                RefValue refOldVal = (RefValue) oldVal;
                if(state.getHeap().getFromAddress(refOldVal.getAddress()) != null)
                {
                    Value valExp = exp.eval(state.getSymTable(), (Heap<Integer, Value>) state.getHeap());
                    if(valExp.getType().equals(refOldVal.getLocationType()))
                    {
                        state.getHeap().updateHeap(refOldVal.getAddress(), valExp);
                    }
                    else
                        throw new MyException("Inner type not the same with new type!\n");
                }
                else
                    throw new MyException("There is nothing at this address\n");
            }
            else
                throw new MyException("Not a ref type!\n");
        }
        else
            throw new MyException("The variable is not defined\n");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varName);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WRITE stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "wH(" + exp.toString() + ")";
    }
}
