package com.ymlyj666.sdk.whutsdk.jwc.model;

import java.util.List;

/**
 * Created by 19110 on 2017/1/14.
 */
public class XFTJ {
    private String classRank;
    private String facultyRank;
    private String schoolYearGPA;
    private String totalGPA;
    private List<CreditStatus> creditStatuses;

    public XFTJ() {
    }

    public XFTJ(String classRank, String facultyRank, String schoolYearGPA, String totalGPA, List<CreditStatus> creditStatuses) {
        this.classRank = classRank;
        this.facultyRank = facultyRank;
        this.schoolYearGPA = schoolYearGPA;
        this.totalGPA = totalGPA;
        this.creditStatuses = creditStatuses;
    }

    public String getClassRank() {
        return classRank;
    }

    public void setClassRank(String classRank) {
        this.classRank = classRank;
    }

    public String getFacultyRank() {
        return facultyRank;
    }

    public void setFacultyRank(String facultyRank) {
        this.facultyRank = facultyRank;
    }

    public String getSchoolYearGPA() {
        return schoolYearGPA;
    }

    public void setSchoolYearGPA(String schoolYearGPA) {
        this.schoolYearGPA = schoolYearGPA;
    }

    public String getTotalGPA() {
        return totalGPA;
    }

    public void setTotalGPA(String totalGPA) {
        this.totalGPA = totalGPA;
    }

    public List<CreditStatus> getCreditStatuses() {
        return creditStatuses;
    }

    public void setCreditStatuses(List<CreditStatus> creditStatuses) {
        this.creditStatuses = creditStatuses;
    }

    public static class CreditStatus {
        private String courseNature;
        private String required;
        private String acquired;

        public CreditStatus() {
        }

        public CreditStatus(String courseNature, String required, String acquired) {
            this.courseNature = courseNature;
            this.required = required;
            this.acquired = acquired;
        }

        public String getCourseNature() {
            return courseNature;
        }

        public void setCourseNature(String courseNature) {
            this.courseNature = courseNature;
        }

        public String getRequired() {
            return required;
        }

        public void setRequired(String required) {
            this.required = required;
        }

        public String getAcquired() {
            return acquired;
        }

        public void setAcquired(String acquired) {
            this.acquired = acquired;
        }
    }
}
