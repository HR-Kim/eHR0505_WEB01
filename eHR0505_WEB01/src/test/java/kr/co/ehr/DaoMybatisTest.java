package kr.co.ehr;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.impl.CodeDaoImpl;
import kr.co.ehr.user.service.Level;
import kr.co.ehr.user.service.User;
import kr.co.ehr.user.service.impl.UserDaoImpl;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//@Test NAME_ASCENDING으로 수행.
public class DaoMybatisTest {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private WebApplicationContext context;
	
	
	@Autowired
	UserDaoImpl userDaoImpl;
	
	
	List<User> users;
	
	@Before
	public void setUp() {
		users = Arrays.asList(
				 new User("j01_124","이상무01","1234",Level.BASIC,49,0,"jamesol@paran.com","2019/08/23")
				,new User("j02_124","이상무02","1234",Level.BASIC,50,0,"jamesol@paran.com","2019/08/23") //BASIC -> SILVER
				,new User("j03_124","이상무03","1234",Level.SILVER,50,29,"jamesol@paran.com","2019/08/23")
				,new User("j04_124","이상무04","1234",Level.SILVER,50,30,"jamesol@paran.com","2019/08/23") //SILVER -> GOLD
				,new User("j05_124","이상무05","1234",Level.GOLD,99,99,"jamesol@paran.com","2019/08/23")
				);
	}
	
	@Test
	public void addAndGet() {
		User user = users.get(0);
		LOG.debug("======================================");
		LOG.debug("=01. 기존 데이터 삭제=");
		LOG.debug("======================================");
		for(User vo:users) {
			userDaoImpl.do_delete(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=02. 데이터 추가=");
		LOG.debug("======================================");		
		for(User vo:users) {
			userDaoImpl.do_save(vo);
		}
		
		LOG.debug("======================================");
		LOG.debug("=03. 단건조회=");
		LOG.debug("======================================");		
		for(User vo:users) {
			User userOne = (User) userDaoImpl.get_selectOne(vo);
			checkUser(userOne,vo);
		}
		
	
	}
	
	private void checkUser(User orgUser,User vsUser) { 
		assertThat(orgUser.getU_id(), is(vsUser.getU_id()));
		assertThat(orgUser.getPasswd(), is(vsUser.getPasswd()));
		assertThat(orgUser.getName(), is(vsUser.getName()));	
		assertThat(orgUser.getEmail(), is(vsUser.getEmail()));	
		assertThat(orgUser.getLogin(), is(vsUser.getLogin()));	
		assertThat(orgUser.getRecommend(), is(vsUser.getRecommend()));	
	}
	
	@Test
	@Ignore
	public void do_save() {
		User user = users.get(0);
		userDaoImpl.do_delete(user);
		userDaoImpl.do_save(user);
	}
	
	@Test
	@Ignore
	public void do_delete() {
		User user = users.get(0);
		user.setU_id("j05_139");
		userDaoImpl.do_delete(user);
	}
	
	
	@Test
	@Ignore
	public void getBean() {
		LOG.debug("====================");
		LOG.debug("=context="+context);
		LOG.debug("=userDaoImpl="+userDaoImpl);
		LOG.debug("====================");
		assertThat(context,  is(notNullValue()));
		assertThat(userDaoImpl,  is(notNullValue()));
	}
	
	
	@After
	public void tearDown() {
	
	}
	
	
	
}


