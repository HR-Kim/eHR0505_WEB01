package kr.co.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.ehr.cmn.DTO;

public interface UserService {

//	public int update(User user);
//	
//	public String excelDown(Search vo,String ext);
//	
//	public List<User> retrieve(Search vo);
//	public int deleteUser(User user);
//	public User get(String id);
//	
//	// 최초 사용자 베이직 레벨
//	public int add(User user);
//
//	public void tx_upgradeLevels() throws SQLException;
	
	public void tx_upgradeLevels() throws SQLException;
	
	/**수정 */
	public int do_update(DTO dto);

	/**삭제 */
	public int do_delete(DTO dto);

	/**저장 */
	public int do_save(DTO dto);

	/**단건조회 */
	public DTO get_selectOne(DTO dto);
	
	/**목록조회 */
	public List<?> get_retrieve(DTO dto);
	
	/**엑셀다운 */
	public String get_excelDown(DTO dto);	
}
