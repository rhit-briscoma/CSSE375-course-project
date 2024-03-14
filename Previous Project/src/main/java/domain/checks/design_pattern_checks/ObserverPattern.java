package domain.checks.design_pattern_checks;

import domain.MyClassNode;

public class ObserverPattern extends PatternCheck {

    public ObserverPattern(){}

    @Override
    public String performCheck(MyClassNode node) {
        CheckObserverPatternObserverPart checkObserverPatternObserverPart = new CheckObserverPatternObserverPart();
        CheckObserverPatternSubjectPart checkObserverPatternSubjectPart = new CheckObserverPatternSubjectPart();
        if (checkObserverPatternObserverPart.check())
            return "Class " + node.name() + " is an observer and part the observer pattern";
        else if (!checkObserverPatternObserverPart.check())
            return "Class " + node.name() + " is not an observer and not of the observer pattern";
        if (checkObserverPatternSubjectPart.check())
            return "Class " + node.name() + " is a subject and part of the observer pattern";
        else
            return "Class " + node.name() + " is not a subject and not part of the observer pattern";
    }
}
