/**
 *
 * @author geloin
 *
 * @date 2013-12-23 下午1:16:42
 */
package me.geloin.door;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 下午1:16:42
 * 
 */
public class MyTest extends BaseTestCase {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test() throws Exception {
		System.out.println(mapper.writeValueAsString(""));
	}
}
