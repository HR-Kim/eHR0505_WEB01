/**
 * 
 */
package kr.co.ehr.user.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.WorkDiv;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;
import kr.co.ehr.user.service.UserVO;


@Repository
public class UserDaoImpl implements WorkDiv {
	Logger LOG=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private final String NAMESPACE = "kr.co.ehr.user";
	
	public UserDaoImpl() {}
	
	public List<?> get_all() {
		String statement = this.NAMESPACE+".get_all";//kr.co.ehr.user.get_all

		LOG.debug("========================");
		LOG.debug("01.no param");
		LOG.debug("========================");
		
		LOG.debug("========================");
		LOG.debug("02.statement="+statement);
		LOG.debug("========================");
		
		List<User> list = this.sqlSessionTemplate.selectList(statement);
		
		LOG.debug("========================");
		LOG.debug("03.list="+list);
		LOG.debug("========================");		
		
		return list;
	}
	
	@Override
	public int do_update(DTO dto) {
		String statement = this.NAMESPACE+".do_update";//kr.co.ehr.user.do_update
		User user = (User) dto;
		user.setLevel(user.gethLevel().intValue());
		LOG.debug("========================");
		LOG.debug("01.param="+user);
		LOG.debug("========================");
		
		LOG.debug("========================");
		LOG.debug("02.statement="+statement);
		LOG.debug("========================");
		
		int flag = this.sqlSessionTemplate.update(statement, user);
		
		LOG.debug("========================");
		LOG.debug("03.flag="+flag);
		LOG.debug("========================");		
		return flag;
	}

	@Override
	public int do_delete(DTO dto) {
		String statement = this.NAMESPACE+".do_delete";//kr.co.ehr.user.do_delete
		User user = (User) dto;
		LOG.debug("========================");
		LOG.debug("01.param="+user);
		LOG.debug("========================");
		
		LOG.debug("========================");
		LOG.debug("02.statement="+statement);
		LOG.debug("========================");
		
		int flag = this.sqlSessionTemplate.delete(statement, user);
		
		LOG.debug("========================");
		LOG.debug("03.flag="+flag);
		LOG.debug("========================");		
		return flag;
	}

	@Override
	public int do_save(DTO dto) {
		String statement = this.NAMESPACE+".do_save";//kr.co.ehr.user.do_save
		User user = (User) dto;
		user.setLevel(user.gethLevel().intValue());
		LOG.debug("========================");
		LOG.debug("01.param="+user);
		LOG.debug("========================");
		
		LOG.debug("========================");
		LOG.debug("02.statement="+statement);
		LOG.debug("========================");
		
		int flag = this.sqlSessionTemplate.insert(statement, user);
		
		LOG.debug("========================");
		LOG.debug("03.flag="+flag);
		LOG.debug("========================");		
		return flag;
	}

	@Override
	public DTO get_selectOne(DTO dto) {
		String statement = this.NAMESPACE+".get_selectOne";//kr.co.ehr.user.get_selectOne
		User user = (User) dto;
		LOG.debug("========================");
		LOG.debug("01.param="+user);
		LOG.debug("========================");
		
		LOG.debug("========================");
		LOG.debug("02.statement="+statement);
		LOG.debug("========================");
		
		User outUser = this.sqlSessionTemplate.selectOne(statement, user);
		
		LOG.debug("========================");
		LOG.debug("03.outUser="+outUser);
		LOG.debug("========================");		
		return outUser;
	}

	@Override
	public List<?> get_retrieve(DTO dto) {
		Search search = (Search) dto;
		String statement = this.NAMESPACE+".get_retrieve";//kr.co.ehr.user.get_all

		LOG.debug("========================");
		LOG.debug("01.no param:"+search);
		LOG.debug("========================");
		
		LOG.debug("========================");
		LOG.debug("02.statement="+statement);
		LOG.debug("========================");
		
		List<User> list = this.sqlSessionTemplate.selectList(statement,search);
		
		LOG.debug("========================");
		LOG.debug("03.list="+list);
		LOG.debug("========================");		
		
		return list;
	}

	@Override
	public List<?> get_excelDown(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
