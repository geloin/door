/**
 *
 * @author geloin
 *
 * @date 2013-12-23 下午1:15:59
 */
package me.geloin.door;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 
 * @author geloin
 * 
 * @date 2013-12-23 下午1:15:59
 * 
 */
@ContextConfiguration(locations = { "classpath*:/spring/*.xml" })
public class BaseTestCase extends AbstractJUnit4SpringContextTests {

}
