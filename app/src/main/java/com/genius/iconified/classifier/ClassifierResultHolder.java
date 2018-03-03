package com.genius.iconified.classifier;

import java.io.Serializable;
import java.util.List;

/**
 * Created by manjeet on 3/3/18.
 */

public class ClassifierResultHolder implements Serializable {
    private List<ClassifierResult> results;

    public List<ClassifierResult> getResults() {
        return results;
    }

    public void setResults(List<ClassifierResult> results) {
        this.results = results;
    }
}

class ClassifierResult implements Serializable{
    private String iconName;
    private float iconConfidenceLevel;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public float getIconConfidenceLevel() {
        return iconConfidenceLevel;
    }

    public void setIconConfidenceLevel(float iconConfidenceLevel) {
        this.iconConfidenceLevel = iconConfidenceLevel;
    }
}
