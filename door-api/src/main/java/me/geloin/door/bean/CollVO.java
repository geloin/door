/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 5:12:02 PM
 */
package me.geloin.door.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.geloin.door.utils.Utils;

/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 5:12:02 PM
 */
@SuppressWarnings("serial")
public class CollVO<T> implements Serializable {

	private List<T> data;

	private Long count;

	public List<T> getData() {
		if (Utils.isEmpty(data)) {
			data = new ArrayList<T>();
		}
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
