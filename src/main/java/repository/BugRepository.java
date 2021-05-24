package repository;

import domain.Bug;
import domain.BugStatus;

public interface BugRepository extends Repository<Integer, Bug>{

    Iterable<Bug> getBugsFoundByTester(Integer id);

    Iterable<Bug> getBugsFoundByProgrammer(Integer id);

    Iterable<Bug> getBugsFixedFoundByTester(Integer id);

    Iterable<Bug> getBugsWithStatus(BugStatus status);
}
