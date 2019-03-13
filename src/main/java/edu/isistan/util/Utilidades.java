package edu.isistan.util;

import edu.isistan.items.IItem;

public class Utilidades implements Comparable<Utilidades>{
	
	private IItem item;
	private Float utilidad;
	
	public IItem getItem() {
		return item;
	}
	public void setItem(IItem item) {
		this.item = item;
	}
	public Float getUtilidad() {
		return utilidad;
	}
	public void setUtilidad(Float utilidad) {
		this.utilidad = utilidad;
	}
	
	@Override
	public int compareTo(Utilidades u) {
		if (getUtilidad() == null || u.getUtilidad() == null) {
            return 0;
        }
        return getUtilidad().compareTo(u.getUtilidad());
	}
	
}
