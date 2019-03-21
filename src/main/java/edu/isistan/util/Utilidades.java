package edu.isistan.util;

import edu.isistan.items.IItem;

public class Utilidades {
	
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((utilidad == null) ? 0 : utilidad.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilidades other = (Utilidades) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (utilidad == null) {
			if (other.utilidad != null)
				return false;
		} else if (!utilidad.equals(other.utilidad))
			return false;
		return true;
	}
	
}
