package com.jsonde.client.sun;
/**
 * 
 * @author admin
 *
 */
public class VirtualMachineData {

    private String id;
    private String description;

    /**
     * 
     * @param id
     * @param description
     */
    public VirtualMachineData(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VirtualMachineData{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
