package com.lego.perception.system.mapper;

import com.lego.survey.lib.mybatis.mapper.Mapper;
import com.lego.framework.system.model.entity.User;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {

	/**
	 * 分页查询
	 * 
	 * @param user
	 * @param page
	 * @return
	 */
	PagedResult<User> findPagedList(User user, Page page);

	/**
	 * 查询列表
	 * 
	 * @param User
	 * @return
	 */
	List<User> findList(User User);

	/**
	 * 新增
	 * 
	 * @param user
	 * @return
	 */
	Integer save(User user);

	/**
	 * 批量新增
	 * 
	 * @param user
	 * @return
	 */
	Integer insertList(List<User> user);

	/**
	 * 更新
	 * 
	 * @param user
	 * @return
	 */
	Integer update(User user);

	/**
	 * 根据手机号码更新
	 *
	 * @param user
	 * @return
	 */
	Integer updateByPhone(User user);

	/**
	 * 批量更新
	 * 
	 * @param user
	 * @return
	 */
	Integer updateList(List<User> user);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	Integer deleteList(List<Long> ids);

	/**
	 * 查询用户密码
	 * 
	 * @param id
	 * @return
	 */
	String findPassword(Long id);
}
