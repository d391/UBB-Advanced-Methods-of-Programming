package controller;

import model.MyException;
import model.PrgState;
import model.statements.IStatement;
import model.value.RefValue;
import model.value.Value;
import repository.IRepo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepo repo;
    private ExecutorService executor;
    private PrgState mainPrg;

    public Controller(IRepo _repo) {
        repo = _repo;
        mainPrg = repo.getPrgList().get(0);
        this.executor = Executors.newFixedThreadPool(2);
    }

    /*private List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream().filter(v-> v instanceof RefValue).map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}).collect(Collectors.toList());
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->(symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    private Map<Integer, Value> conservativeGarbageCollector(List<PrgState> prgList){
        List<List<Integer>> listOfTables = new ArrayList<>();
        for(PrgState p : prgList){
            listOfTables.add(getAddrFromSymTable(p.getSymTable().values()));
        }

        List<Integer> fullTable = listOfTables.stream().flatMap(List::stream).distinct().collect(Collectors.toList());

        HashMap<Integer, Value> heap = prgList.get(0).getHeap().getHeap();
        Collection<Integer> heapAddr = getAddrFromHeap(heap.values());
        return heap.entrySet().stream()
                .filter(e -> (fullTable.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void oneStepForAllPrg(List<PrgState> prgStateList) throws MyException, IOException, InterruptedException {
        List<Callable<PrgState>> callList = prgStateList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::oneStep))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {try{return future.get();}
                    catch(InterruptedException | ExecutionException exc) {System.out.println(exc.getMessage());}
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            prgStateList.addAll(newPrgList);

            repo.logAllPrgStates();

            repo.setPrgList(prgStateList);
        } catch (InterruptedException | MyException e) {
            e.printStackTrace();
        }
    }

    public void allStep() throws InterruptedException, IOException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0){

            //garbageCollector()
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public List<PrgState> getPrgList(){
        return removeCompletedPrg(repo.getPrgList());
    }

    public PrgState getCompletedPrg(){
        if(!mainPrg.isNotCompleted())
            return mainPrg;
        return null;
    }

    public boolean isDone(){
        return getPrgList().isEmpty();
    }

    public void oneStep() throws MyException, IOException, InterruptedException {
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        repo.logAllPrgStates();

        if(prgList.size() > 0){
            prgList.get(0).getHeap().setHeap((HashMap<Integer, Value>) conservativeGarbageCollector(prgList));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        if(prgList.size() == 0){
            executor.shutdownNow();
            repo.setPrgList(prgList);
        }

    }
}*/
    private Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream().filter(e -> (symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, Value> conservativeGarbageCollector(List<PrgState> prgList) {
        List<List<Integer>> listOfTables = new ArrayList<>();
        for (PrgState p : prgList) {
            listOfTables.add(getAddrFromSymTable(p.getSymTable().values()));
        }

        List<Integer> fullTable = listOfTables.stream().flatMap(List::stream).distinct().collect(Collectors.toList());

        HashMap<Integer, Value> heap = prgList.get(0).getHeap().getHeap();
        Collection<Integer> heapAddr = getAddrFromHeap(heap.values());
        return heap.entrySet().stream()
                .filter(e -> (fullTable.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream().filter(v -> v instanceof RefValue).map(v -> {
            RefValue v1 = (RefValue) v;
            return v1.getAddress();
        }).collect(Collectors.toList());
    }

    private List<PrgState> removeCompletedPrg(List<PrgState> prgList) {
        return prgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    /*public IStatement getStmt() {
        return null;
    }*/

    private void oneStepForAll(List<PrgState> prgList) {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException exc) {
                            System.out.println(exc.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);

            repo.logAllPrgStates();

            repo.setPrgList(prgList);
        } catch (InterruptedException | MyException e) {
            e.printStackTrace();
        }
    }

    public void oneStep() throws MyException {
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        repo.logAllPrgStates();

        if (prgList.size() > 0) {
            prgList.get(0).getHeap().setHeap((HashMap<Integer, Value>) conservativeGarbageCollector(prgList));
            oneStepForAll(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }

        if (prgList.size() == 0) {
            executor.shutdownNow();
            repo.setPrgList(prgList);
        }
    }

    public void allStep() throws MyException {
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        repo.logAllPrgStates();

        while (prgList.size() > 0) {
            prgList.get(0).getHeap().setHeap((HashMap<Integer, Value>) conservativeGarbageCollector(prgList));
            oneStepForAll(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

    public PrgState getCompletedPrg() {
        if (!mainPrg.isNotCompleted())
            return mainPrg;
        return null;
    }

    public List<PrgState> getPrgList() {
        return removeCompletedPrg(repo.getPrgList());
    }

    public boolean isDone() {
        return getPrgList().isEmpty();
    }

    public void shutDown() {
        executor.shutdownNow();
    }
}