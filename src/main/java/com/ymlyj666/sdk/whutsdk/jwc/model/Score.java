package com.ymlyj666.sdk.whutsdk.jwc.model;

/**
 * Created by 19110 on 2016/12/31.
 */

public class Score {
    private String term;//学年学期
    private String courseId;//课程代码
    private String courseName;//课程名称
    private String courseType;//课程性质
    private String credit;//学分
    private String score;//总评成绩
    private String scoreVerifyType;//成绩审核属性
    private String maxScore;//最高成绩
    private String firstScore;//首次成绩
    private String examStatus;//考试状态
    private String scoreType;//成绩类型
    private String isRevamp;//是否重修
    private String gp;//学分绩点

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreVerifyType() {
        return scoreVerifyType;
    }

    public void setScoreVerifyType(String scoreVerifyType) {
        this.scoreVerifyType = scoreVerifyType;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(String firstScore) {
        this.firstScore = firstScore;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getIsRevamp() {
        return isRevamp;
    }

    public void setIsRevamp(String isRevamp) {
        this.isRevamp = isRevamp;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }
}
