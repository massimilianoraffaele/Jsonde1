package com.jsonde.api.configuration;

import com.jsonde.api.Message;

import java.util.List;
/**
 * 
 * @author admin
 *
 */
public class AgentConfigurationMessage extends Message {

    private String version;
    private List<ClassFilterDto> classFilters;

    /**
     * 
     * @return
     */
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ClassFilterDto> getClassFilters() {
        return classFilters;
    }

    public void setClassFilters(List<ClassFilterDto> classFilters) {
        this.classFilters = classFilters;
    }

}
