package com.tinder.entity;

public class Like {
    private int id;
    private long who_id;
    private long whom_id;
    private boolean reaction;

    public long getWho_id() {
        return who_id;
    }

    public void setWho_id(long who_id) {
        this.who_id = who_id;
    }

    public long getWhom_id() {
        return whom_id;
    }

    public void setWhom_id(long whom_id) {
        this.whom_id = whom_id;
    }

    public boolean isReaction() {
        return reaction;
    }

    public void setReaction(boolean reaction) {
        this.reaction = reaction;
    }

    public Like(long who_id, long whom_id, boolean reaction) {
        this.who_id = who_id;
        this.whom_id = whom_id;
        this.reaction = reaction;
    }
    public Like(int id, int who_id, int whom_id, boolean reaction) {
        this.id=id;
        this.who_id = who_id;
        this.whom_id = whom_id;
        this.reaction = reaction;
    }
}
