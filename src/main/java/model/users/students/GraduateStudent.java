package model.users.students;

import enums.DegreeType;
import enums.School;
import exceptions.LowHIndexException;
import model.research.ResearchPaper;
import model.research.ResearcherDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraduateStudent extends Student {
    private static final long serialVersionUID = 1L;

    private DegreeType degreeType;
    private ResearcherDecorator supervisor;
    private List<ResearchPaper> diplomaPapers;

    public GraduateStudent(String firstName, String lastName, String login,
                           String password, School school, DegreeType degreeType, int yearOfStudy) {
        super(firstName, lastName, login, password, school, yearOfStudy);
        this.degreeType = degreeType;
        this.diplomaPapers = new ArrayList<>();
    }

    public void setSupervisor(ResearcherDecorator supervisor) throws LowHIndexException {
        if (supervisor == null) {
            throw new IllegalArgumentException("Supervisor cannot be null");
        }
        if (supervisor.calculateHIndex() < 3) {
            throw new LowHIndexException(
                    "Supervisor h-index must be at least 3, but is: " + supervisor.calculateHIndex());
        }
        this.supervisor = supervisor;
    }

    public ResearcherDecorator getSupervisor() {
        return supervisor;
    }

    public void addDiplomaPaper(ResearchPaper paper) {
        if (paper != null && !diplomaPapers.contains(paper)) {
            diplomaPapers.add(paper);
        }
    }

    public List<ResearchPaper> getDiplomaPapers() {
        return diplomaPapers;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType degreeType) {
        this.degreeType = degreeType;
    }

    @Override
    public String toString() {
        return "GraduateStudent[name=" + getFullName()
                + ", degree=" + degreeType
                + ", school=" + getSchool()
                + ", diplomaPapers=" + diplomaPapers.size() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraduateStudent)) return false;
        return Objects.equals(getId(), ((GraduateStudent) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}