package com.lego.perception.system.service;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespVO;
import com.lego.framework.system.model.entity.User;
import java.util.List;

public interface IUserService {

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
	RespVO insert(User user,Long userId);

	/**
	 * 批量新增
	 * 
	 * @param user
	 * @return
	 */
	RespVO insertList(List<User> user,Long userId);

	/**
	 * 用户存在更新，用户不存在删除
	 * 
	 * @param user
	 * @return
	 */
	RespVO updateAndInsert(User user,Long userId);

	/**
	 * 更新
	 * 
	 * @param user
	 * @return
	 */
	RespVO update(User user);

	/**
	 * 根据电话号码更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	RespVO updateByPhone(User user);

	/**
	 * 批量更新
	 * 
	 * @param user
	 * @return
	 */
	RespVO updateList(List<User> user,Long userId);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	RespVO delete(Long id);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	RespVO deleteList(List<Long> ids);

	/**
	 * 验证手机号并修改用户信息表单
	 * 
	 * @param user
	 * @param phone
	 * @param code
	 * @return
	 */
	RespVO updateUserAndValidate(User user, String phone, String code);

	/**
	 * 修改用户密码(不需要手机验证码)
	 * 
	 * @param user
	 * @return
	 */
	RespVO updateUserPassword(User user,Long userId);

	/**
	 * 后台更新用户信息
	 * @param user
	 * @return
	 */
	RespVO updateOtherUser(User user,Long userId);


	/**
	 * 有的话更新，没有的话新增
	 * @param user
	 * @return
	 */
	List<User> upsert(List<User> user);

	/**
	 * 根据条件查询用户
	 * @param user
	 * @return
	 */
    User findUser(User user);

	/**
	 * 返回用户信息
	 * @param query
	 * @return
	 */
	User findRoleList(User query);


}
