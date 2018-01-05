package com.jsonde.gui.dialog.project;

import com.jsonde.api.configuration.ClassFilterDto;

import java.util.List;
/**
 * 
 * @author admin
 *
 */
public interface FiltersPanel {

	/**
	 * 
	 * @param classFilters
	 */
    void setClassFilters(List<ClassFilterDto> classFilters);

    List<ClassFilterDto> getClassFilters();

}
