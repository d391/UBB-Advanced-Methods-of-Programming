package repository;

import model.MyException;
import model.PrgState;
import model.statements.IStatement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Repo implements IRepo
{
    private List<PrgState> prgStateList;
    private String logFilePath;
    public Repo(PrgState mainPrgState, String _logFilePath)
    {
        prgStateList = new LinkedList<>();
        prgStateList.add(mainPrgState);
        logFilePath = _logFilePath;
    }
;
    public void setFilePath(String newFilePath)
    {
        logFilePath = newFilePath;
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStateList;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        prgStateList = newList;
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write("Id: " + prgState.getId() + '\n');
        logFile.write("EXE STACK: \n");
        logFile.write(prgState.getExeStack().toString());
        logFile.write("SYM TABLE: \n");
        logFile.write(prgState.getSymTable().toString());
        logFile.write("HEAP: \n");
        logFile.write(prgState.getHeap().toString());
        logFile.write("OUT: \n");
        logFile.write(prgState.getOut().toString());
        logFile.write("\n");
        logFile.close();
    }

    @Override
    public void logAllPrgStates() throws MyException {
        if(logFilePath == null)
            return;

        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

            List<PrgState> prgList = getPrgList();
            for(PrgState p : prgList){
                //write id
                logFile.write("ID: " + Integer.toString(p.getId()) + "\n");

                //write exeStack to file
                logFile.write("ExeStack:\n");
                Stack<IStatement> stack = p.getExeStack().getStack();
                for(int i = stack.size()-1; i>=0; i--){
                    logFile.write(stack.get(i).toString() + "\n");
                }

                //write symTable to file
                logFile.write("SymTable:\n");
                Hashtable<String, Value> symTable = p.getSymTable().getDictionary();
                Set<String> keys = symTable.keySet();
                for(String key : keys){
                    logFile.write(key + " --> " + symTable.get(key).toString() + "\n");
                }
                logFile.write("\n");
            }

            PrgState p = prgList.get(0);

            //write output to file
            logFile.write("Out:\n");
            List<Value> out = p.getOut().getList();
            for(Value val : out){
                logFile.write(val.toString() + "\n");
            }

            //write heap to file
            logFile.write("Heap:\n");
            HashMap<Integer, Value> heap = p.getHeap().getHeap();
            Set<Integer> heapKeys = heap.keySet();
            for(Integer key : heapKeys){
                logFile.write(key + " -> " + heap.get(key) + "\n");
            }

            //write fileTable to file
            logFile.write("FileTable:\n");
            Set<StringValue> fileKeys = p.getFileTab().getFiles();
            for(StringValue key : fileKeys){
                logFile.write(key.toString() + "\n");
            }

            logFile.write("\n----------------------------------\n\n");
            logFile.close();

        } catch (IOException e) {
            throw new MyException("Error writing to log file");
        }
    }


}
