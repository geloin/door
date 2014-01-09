/**
 *
 * @author geloin
 *
 * @date 2014-1-9 上午11:49:29
 */
package me.geloin.door.constant;

/**
 * 
 * @author geloin
 * 
 * @date 2014-1-9 上午11:49:29
 * 
 */
public final class Constants {

	private Constants() {
	}

	/**
	 * convert int to UpdateSortOpt
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 上午11:54:47
	 * 
	 * @param value
	 * @return
	 */
	public static UpdateSortOpt convertIntToOpt(Integer value) {
		UpdateSortOpt[] opts = UpdateSortOpt.values();
		for (UpdateSortOpt opt : opts) {
			if (opt.intValue() == value) {
				return opt;
			}
		}
		return null;
	}

	/**
	 * constants for update sort
	 * 
	 * @author geloin
	 * 
	 * @date 2014-1-9 上午11:52:04
	 * 
	 */
	public static enum UpdateSortOpt {
		/**
		 * 置顶
		 */
		TO_TOP(1),

		/**
		 * 上移
		 */
		TO_UP(2),

		/**
		 * 下移
		 */
		TO_DOWN(3),

		/**
		 * 置底
		 */
		TO_BOTTOM(4);

		private Integer value;

		private UpdateSortOpt(Integer value) {
			this.value = value;
		}

		public Integer intValue() {
			return this.value;
		}
	}
}
