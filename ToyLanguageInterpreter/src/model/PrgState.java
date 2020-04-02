package model;

import model.adt.*;
import model.statements.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState
{
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value>  symTable;
    private MyIList<Value> out;
    private IStatement originalProgram;
    private IFileTable<StringValue, BufferedReader> fileTab;
    private IHeap<Integer, Value> heap;
    private int id;
    private static AtomicInteger count = new AtomicInteger(0);

    public PrgState(MyIStack<IStatement> _exeStack, MyIDictionary<String, Value> _symTable, MyIList<Value> _out, IFileTable<StringValue, BufferedReader>_fileTab, IStatement _originalProgram, IHeap<Integer, Value> _heap)
    {
        exeStack = _exeStack;
        symTable = _symTable;
        out = _out;
        originalProgram = _originalProgram;
        fileTab = _fileTab;
        heap = _heap;
        exeStack.push(originalProgram);
        id = count.addAndGet(1);
    }

    public MyIStack<IStatement> getExeStack() {return exeStack;}
    public MyIDictionary<String, Value> getSymTable() {return symTable;}
    public MyIList<Value> getOut() {return out;}
    public IFileTable<StringValue, BufferedReader> getFileTab() {return fileTab;}
    public IHeap<Integer, Value> getHeap() {return heap;}
    public int getId(){ return id;}
    static int genNewPrgStateId()
    {
        return 1;
    }

    public Boolean isNotCompleted()
    {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, IOException {
        if(exeStack.isEmpty())
            throw new MyException("Prgstate stack is empty");
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);

    }

    public Set<StringValue> getFileList(){
        return this.fileTab.getFiles();
    }
}
