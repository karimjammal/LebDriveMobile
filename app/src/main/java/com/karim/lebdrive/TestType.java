package com.karim.lebdrive;

public class TestType {

    private String type, description, btnText;
    private boolean expandable;

    public TestType(String type, String description, String btnText) {
        this.type = type;
        this.description = description;
        this.btnText = btnText;
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    @Override
    public String toString() {
        return "TestType{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", btnText='" + btnText + '\'' +
                '}';
    }
}
