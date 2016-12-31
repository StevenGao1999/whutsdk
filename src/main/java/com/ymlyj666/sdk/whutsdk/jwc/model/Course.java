package com.ymlyj666.sdk.whutsdk.jwc.model;

/**
 * Created by 19110 on 2016/12/31.
 */
public class Course {
    private String name;//课程名称
    private String time;//上课时间
    private String teacher;//教师
    private String place;//地点
    private Course nextCourse;//单双周等情况

    public Course() {
    }

    public Course(String html) {
//        System.out.println(html);
        int comma = html.indexOf(',');
        int lb = html.indexOf('(');
        int rb = html.indexOf(')');
        if (comma > rb) {
            //课程名称带有括号的情况
            //社会焦点问题研究(GX)(第1-8周9-11节, 邵献平 老师,5-501)&nbsp;&nbsp;
            lb = html.indexOf('(', lb + 1);
            rb = html.indexOf(')', rb + 1);
        }
        name = html.substring(0, lb).trim();
        String[] ss = html.substring(lb + 1, rb).split(",");
        time = ss[0].trim();
        teacher = ss[1].trim().split(" ")[0];
        if (ss.length > 2) {
            place = ss[2].trim();
        } else {
            place = "";
        }
        String[] cs = html.split("&nbsp;&nbsp;");
        if (cs.length > 1 && cs[1].contains("(")) {
            nextCourse = new Course(cs[1]);
        } else {
            nextCourse = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Course getNextCourse() {
        return nextCourse;
    }

    public void setNextCourse(Course nextCourse) {
        this.nextCourse = nextCourse;
    }
}
