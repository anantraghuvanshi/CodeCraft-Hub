package com.CodeCraft.hubbackend.model;

import java.util.List;

public class DashboardInsights {
    private Long totalTasks;
    private Long pendingTasks;
    private Long completedTasks;
    private Long inProgressTasks;
    private List<Task> upcomingDeadlines;

    public Long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Long getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(Long pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public Long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Long getInProgressTasks() {
        return inProgressTasks;
    }

    public void setInProgressTasks(Long inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }

    public List<Task> getUpcomingDeadlines() {
        return upcomingDeadlines;
    }

    public void setUpcomingDeadlines(List<Task> upcomingDeadlines) {
        this.upcomingDeadlines = upcomingDeadlines;
    }
}
