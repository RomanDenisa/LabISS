package service;

import domain.*;
import domain.validators.Validator;
import repository.BugRepository;
import repository.EmployeeRepository;
import utils.Observable;
import utils.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BugService implements Observable {

    private BugRepository bugRepo;
    private EmployeeRepository empRepo;
    private Validator<Bug> bugVal;
    private List<Observer> observers = new ArrayList<>();

    public BugService(BugRepository bugRepo, Validator<Bug> bugVal,EmployeeRepository empRepo) {
        this.bugRepo = bugRepo;
        this.bugVal = bugVal;
        this.empRepo = empRepo;
    }

    public void add(String name,String description,Integer testerId){
        Bug bug = new Bug(name,description, BugStatus.New, LocalDateTime.now().toString());
        CompanyEmployee emp = empRepo.findOne(testerId);
        bug.setTester((Tester)emp);
        bug.setAdditionalInfo("No additional info yet");
        bugVal.validate(bug);
        bugRepo.add(bug);
        notifyObservers();
    }

    public void update(Integer bugId, BugStatus bugStatus, String additionalInfo)
    {
        Bug bug = new Bug();
        bug.setBugStatus(bugStatus);
        bug.setId(bugId);
        bug.setAdditionalInfo(additionalInfo);
        bugRepo.update(bug);
        notifyObservers();
    }

    public void update(Integer bugId, BugStatus bugStatus){
        Bug bug = new Bug();
        bug.setBugStatus(bugStatus);
        bug.setId(bugId);
        bugRepo.update(bug);
        notifyObservers();
    }

    public void update(Integer bugId, BugStatus bugStatus, Integer programmerId){
        Bug bug = new Bug();
        bug.setBugStatus(bugStatus);
        bug.setId(bugId);
        CompanyEmployee emp = empRepo.findOne(programmerId);
        bug.setProgrammer((Programmer)emp);
        bugRepo.update(bug);
        notifyObservers();
    }
    public List<Bug> getBugsFoundByTester(Integer id){
        return StreamSupport.stream(bugRepo.getBugsFoundByTester(id).spliterator(),false)
                .collect(Collectors.toList());
    }

    public List<Bug> getBugsFoundByProgrammer(Integer id){
        return StreamSupport.stream(bugRepo.getBugsFoundByProgrammer(id).spliterator(),false)
                .collect(Collectors.toList());
    }

    public List<Bug> getBugsFixedFoundByTester(Integer id){
        return StreamSupport.stream(bugRepo.getBugsFixedFoundByTester(id).spliterator(),false)
                .collect(Collectors.toList());
    }

    public List<Bug> getBugsWithStatus(BugStatus status){
        return StreamSupport.stream(bugRepo.getBugsWithStatus(status).spliterator(),false)
                .collect(Collectors.toList());
    }

    public List<ReportDTO> getActivityProgrammers(Integer month){
        List<Bug> bugs = StreamSupport.stream(bugRepo.getBugsWithStatus(BugStatus.Solved).spliterator(),false).collect(Collectors.toList());
        Map<Integer, ReportDTO> countBugsSolved = new HashMap<>();

        for (Bug b: bugs)
        {
            LocalDate date1 =  LocalDate.of(1999,month,1);
            if(LocalDateTime.parse(b.getDate()).getMonthValue() == date1.getMonthValue()) {
                if (countBugsSolved.get(b.getProgrammer().getId()) == null) {
                    countBugsSolved.put(b.getProgrammer().getId(), new ReportDTO(b.getProgrammer().getFirstName(),
                            b.getProgrammer().getLastName(), 1));
                } else {
                    ReportDTO count = countBugsSolved.get(b.getProgrammer().getId());
                    count.setActivity(count.getActivity() + 1);
                    countBugsSolved.put(b.getProgrammer().getId(), count);
                }
            }
        }
        return countBugsSolved.values().stream().collect(Collectors.toList());
    }

    public List<ReportDTO> getActivityTesters(Integer month){
        List<Bug> bugs = StreamSupport.stream(bugRepo.getAll().spliterator(),false).collect(Collectors.toList());
        Map<Integer, ReportDTO> countBugsFound = new HashMap<>();

        for (Bug b: bugs)
        {
            LocalDate date1 =  LocalDate.of(1999,month,1);
            if(LocalDateTime.parse(b.getDate()).getMonthValue() == date1.getMonthValue()) {
                if (countBugsFound.get(b.getTester().getId()) == null) {
                    countBugsFound.put(b.getTester().getId(), new ReportDTO(b.getTester().getFirstName(),
                            b.getTester().getLastName(), 1));
                } else {
                    ReportDTO count = countBugsFound.get(b.getTester().getId());
                    count.setActivity(count.getActivity() + 1);
                    countBugsFound.put(b.getTester().getId(), count);
                }
            }
        }
        return countBugsFound.values().stream().collect(Collectors.toList());
    }

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(x -> x.update());
    }
}
