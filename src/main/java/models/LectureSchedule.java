package models;

import java.time.LocalTime;

public class LectureSchedule {
    private int scheduleId;
    private int courseId;
    private int divisionId;
    private int teacherId;
    private String lectureDay;
    private String startTime;
    private String endTime;

    // Additional fields for display
    private String courseName;
    private String divisionName;
    private String teacherName;

    // Constructor for inserting new schedules
    public LectureSchedule(int courseId, int divisionId, int teacherId, String lectureDay, String startTime, String endTime) {
        this.courseId = courseId;
        this.divisionId = divisionId;
        this.teacherId = teacherId;
        this.lectureDay = lectureDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor with all fields for displaying schedules
    public LectureSchedule(String courseName, String divisionName, String teacherName, String lectureDay, String startTime, String endTime) {
        this.lectureDay = lectureDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseName = courseName;
        this.divisionName = divisionName;
        this.teacherName = teacherName;
    }

    // Getters and Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getDivisionId() { return divisionId; }
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public String getLectureDay() { return lectureDay; }
    public void setLectureDay(String lectureDay) { this.lectureDay = lectureDay; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDivisionName() { return divisionName; }
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
}
