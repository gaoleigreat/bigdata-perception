package com.lego.perception.system.service.impl;
import com.lego.perception.system.mapper.RoleMapper;
import com.lego.perception.system.mapper.UserMapper;
import com.lego.perception.system.service.IRoleService;
import com.lego.perception.system.service.IUserRoleService;
import com.lego.perception.system.service.IUserService;
import com.lego.framework.system.model.entity.Role;
import com.lego.framework.system.model.entity.User;
import com.lego.framework.system.model.entity.UserRole;
import com.survey.lib.common.page.Page;
import com.survey.lib.common.page.PagedResult;
import com.survey.lib.common.utils.SecurityUtils;
import com.survey.lib.common.vo.RespVO;
import com.survey.lib.common.vo.RespVOBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserRoleService userRoleService;


	@Value("${user.origin.password:111111}")
	private String password;
	@Autowired
	private RoleMapper roleMapper;


	@Override
	public PagedResult<User> findPagedList(User user, Page page) {
		PagedResult<User> userPagedResult = userMapper.findPagedList(user,page);
		if(!CollectionUtils.isEmpty(userPagedResult.getResultList())){
			Map<Long, List<Long>> roleMap = new HashMap<>();
			List<Long> userIds = new ArrayList<>();
			for(User u : userPagedResult.getResultList()){
				userIds.add(u.getId());
				if(u.getUsername()==null){
					u.setUsername(u.getRealName());
				}
				if(u.getRealName()==null){
					u.setRoleName(u.getUsername());
				}
			}
			UserRole queryParam = new UserRole();
			queryParam.setUserIds(userIds);
			List<UserRole> userRoleProgramList = userRoleService.findList(queryParam);

			List<Long> roleIds = new ArrayList<>();
			if(!CollectionUtils.isEmpty(userRoleProgramList)){
				for(UserRole urp : userRoleProgramList){
					if(null == urp || null == urp.getUserId() || null == urp.getRoleId()){
						continue;
					}
					roleIds.add(urp.getRoleId());
					if(roleMap.containsKey(urp.getUserId())){
						roleMap.get(urp.getUserId()).add(urp.getRoleId());
					}else{
						List<Long> lst = new ArrayList<>();
						lst.add(urp.getRoleId());
						roleMap.put(urp.getUserId(), lst);
					}
				}
			}

			Map<Long, String> roleNameMap = new HashMap<>();
			if(!CollectionUtils.isEmpty(roleIds)){
				Role roleQuery = new Role();
				roleQuery.setRoleIds(roleIds);
				List<Role> roleList = roleService.findList(roleQuery);
				if(!CollectionUtils.isEmpty(roleList)){
					for(Role role : roleList){
						roleNameMap.put(role.getId(), role.getRoleName());
					}
				}
			}

			for(User u : userPagedResult.getResultList()){
				List<String> roleNamesList = new ArrayList<>();
				List<Long> roleList = roleMap.get(u.getId());
				u.setRoleIds(roleMap.get(roleList));
				if(!CollectionUtils.isEmpty(roleList)){
					String s = "";
					for(Long r : roleList){
						s = s +  "，" +(null == roleNameMap.get(r)? "" : roleNameMap.get(r));
						roleNamesList.add((null == roleNameMap.get(r)? "" : roleNameMap.get(r)));
					}
					if(s.length() > 0){
						s = s.substring(1);
					}
					u.setRoleName(s);
					u.setRoleIds(roleList);
				}
			}
		}
		return userPagedResult;
	}

	@Override
	public User findRoleList(User query) {
		List<User> userlist = userMapper.findList(query);
		if(userlist!=null && userlist.size()>0){
			User user = userlist.get(0);
			String roleName = findName(user.getId());
			if(roleName!=null){
				user.setRoleName(roleName);
			}else {
				user.setRoleName("");
			}
			return user;
		}
		return null;
	}


	/**
	 * 根据用户id查询角色名称
	 * @param ids
	 * @return
	 */
	public String findName(Long ids){
		UserRole querys = new UserRole();
		querys.setUserId(ids);
		List<UserRole> userRoleProgramList = userRoleService.findList(querys);
		if(userRoleProgramList==null|| userRoleProgramList.size()<=0){
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
			for(int i = 0;i<userRoleProgramList.size();i++){
				Long rid = userRoleProgramList.get(i).getRoleId();
				Role queryrole = new Role();
				queryrole.setId(rid);
				List<Role> roleList = roleMapper.findList(queryrole);
				String rolename = roleList.get(0).getRoleName();
				stringBuilder.append(rolename+"、");
			}
		stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"");
		return stringBuilder.toString();
	}

	@Override
	public List<User> findList(User user) {
		return userMapper.findList(user);
	}

	/**
	 * 根据uid,roleid判断信息员是否记录
	 * @param uid
	 * @param roleId
	 * @return
	 */
	public UserRole findUserRole(Long uid,Long roleId){
		UserRole userRoleProgram = new UserRole();
		userRoleProgram.setUserId(uid);
		userRoleProgram.setRoleId(roleId);
		List<UserRole> userRoles = userRoleService.findList(userRoleProgram);
		if(userRoles!=null && userRoles.size()>0){
			return userRoles.get(0);
		}
		return null;
	}

	/**
	 * 根据手机号查询用户
	 * @param phone
	 * @return
	 */
	private User findUserByphone(String phone) {
		User query = new User();
		query.setPhone(phone);
		List<User> userList = findList(query);
		if(userList!=null&&userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 根据id查询用户手机号
	 * @param id
	 * @return
	 */
	private String findPhoneById(Long id){
		User query = new User();
		query.setId(id);
		List<User> userList = findList(query);
		if(userList!=null&&userList.size()>0){
			return userList.get(0).getPhone();
		}
		return null;
	}

	@Override
	public List<User> upsert(List<User> users) {
		if(CollectionUtils.isEmpty(users)){
			return Collections.EMPTY_LIST;
		}

		User queryParam = new User();
		List<String> phones = new ArrayList<>();
		for(User unit : users){
			phones.add(unit.getPhone());
		}

		queryParam.setPhones(phones);
		List<User> originList = userMapper.findList(queryParam);

		List<User> inserList = new ArrayList<>();
		List<User> updateList = new ArrayList<>();
		Map<String, User> map = new HashMap<>();
		for(User unit : originList){
			map.put(unit.getPhone(), unit);
		}

		List<User> result = new ArrayList<>();
		for(User unit : users){
			if(map.containsKey(unit.getPhone())){
				unit.setId(map.get(unit.getPhone()).getId());
				unit.setDeleteFlag(2);//恢复状态
				updateList.add(unit);
			}else{
				//Long id = redisTemplate.opsForValue().increment("fupin.userId",1);
				inserList.add(unit);
			}
		}

		if(!CollectionUtils.isEmpty(updateList)){
			userMapper.updateList(updateList);
			result.addAll(updateList);
		}

		if(!CollectionUtils.isEmpty(inserList)){
			userMapper.insertList(inserList);
			result.addAll(inserList);
		}

		return result;
	}

	@Override
	public User findById(User user) {
		List<User> userList = userMapper.findList(user);
		if(userList==null || userList.size()<=0){
			return null;
		}
		return userList.get(0);
	}


	/**
	 * 查询角色名称
	 * @param id
	 * @return
	 */
	public String findRoleName(Long id){
		Role queryRole = new Role();
		queryRole.setId(id);
		List<Role> roleList = roleMapper.findList(queryRole);
		if(roleList!=null && roleList.size()>0){
			return roleList.get(0).getRoleName();
		}
		return null;
	}
	/**
	 * 根据用户id查询用户
	 * @param id
	 * @return
	 */
	public User findByUids(Long id){
		User queryUser = new User();
		queryUser.setId(id);
		List<User> userList = userMapper.findList(queryUser);
		if(userList!=null && userList.size()>0){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public RespVO insert(User user) {
		if (null == user) {
			return RespVOBuilder.failure("参数缺失");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(SecurityUtils.encryptionWithMd5(password));
		}
		user.setPassword(SecurityUtils.encryptionWithMd5(user.getPassword()));

		User queryParam = new User();
		queryParam.setPhone(user.getPhone());
		List<User> userList = findList(queryParam);
		if (!CollectionUtils.isEmpty(userList)) {
			Integer deleteFlag = userList.get(0).getDeleteFlag();
			Long id = userList.get(0).getId();
			if (null != deleteFlag && deleteFlag == 2) {
				return RespVOBuilder.failure("手机号已注册");
			} else {
			/*	UserPriciple curUser = RequestContext.getCurrent().getUser();
				if (null != curUser) {
					user.setLastUpdatedBy(curUser.getId());
				}*/
				user.setLastUpdateDate(new Date());
				user.setId(id);
				user.setDeleteFlag(2);
				userMapper.update(user);
				return RespVOBuilder.success(id);
			}
		}

		/*UserPriciple curUser = RequestContext.getCurrent().getUser();
		if (null != curUser) {
			user.setCreatedBy(curUser.getId());
			user.setLastUpdatedBy(curUser.getId());
		}*/
		user.setCreationDate(new Date());
		user.setLastUpdateDate(new Date());
		userMapper.insert(user);
		return RespVOBuilder.success(user.getId());
	}


	@Override
	public RespVO insertList(List<User> users) {
		if (CollectionUtils.isEmpty(users)) {
			return RespVOBuilder.failure("参数缺失");
		}
	//	UserPriciple curUser = RequestContext.getCurrent().getUser();
		for (User user : users) {
			// password
			if (StringUtils.isEmpty(user.getPassword())) {
				user.setPassword(SecurityUtils.encryptionWithMd5(password));
			}
			user.setPassword(SecurityUtils.encryptionWithMd5(user.getPassword()));

			// 创建人 创建时间
			/*if (null != curUser) {
				user.setCreatedBy(curUser.getId());
				user.setLastUpdatedBy(curUser.getId());
			}*/
			user.setCreationDate(new Date());
			user.setLastUpdateDate(new Date());
		}
		int count = userMapper.insertList(users);
		return RespVOBuilder.success(count);
	}

	@Override
	public RespVO updateAndInsert(User user) {
		if (null == user || StringUtils.isEmpty(user.getPhone())) {
			return RespVOBuilder.failure("参数错误");
		}
		User userQuery = new User();
		userQuery.setPhone(user.getPhone());
		List<User> userList = findList(userQuery);
		if (CollectionUtils.isEmpty(userList)) {
			return insert(user);
		} else {
			User origin = userList.get(0);
			user.setId(origin.getId());
			user.setDeleteFlag(2);
			updateByPhone(user);
			return RespVOBuilder.success(user.getId());
		}
	}



	@Override
	@Transactional(rollbackFor = Exception.class)
	public RespVO update(User user) {
		if (null == user) {
			return RespVOBuilder.failure("参数错误");
		}
		// 手机号码是否存在
		//UserPriciple curUser = RequestContext.getCurrent().getUser();
		if (null != user.getPhone()) {
			User query = new User();
			query.setPhone(user.getPhone());
			List<User> userList = userMapper.findList(query);
			if (!CollectionUtils.isEmpty(userList) && !userList.get(0).getId().equals(user.getId())) {
				return RespVOBuilder.failure("手机号码已存在");
			}
		}

       // String password = userMapper.findPassword(curUser.getId());
		if (StringUtils.isNotEmpty(user.getMail())) {
            // 更改邮箱时，需要输入密码
		    if(StringUtils.isEmpty(user.getPassword())){
		        return RespVOBuilder.failure("修改邮箱时需要验证密码");
            }

		    if(!password.equals(SecurityUtils.encryptionWithMd5(user.getPassword()))){
                return RespVOBuilder.failure( "密码错误");
            }

		}else if(!StringUtils.isEmpty(user.getPassword()) && !password.endsWith(SecurityUtils.encryptionWithMd5(user.getPassword()))){
			// 修改密码，需要输入旧密码
			if (StringUtils.isNotBlank(user.getOldPassword())) {
				if (!StringUtils.isEmpty(password) && password.equals(SecurityUtils.encryptionWithMd5(user.getOldPassword()))) {
					user.setPassword(SecurityUtils.encryptionWithMd5(user.getPassword()));
				} else {
					return RespVOBuilder.failure("输入旧密码错误，请重新输入");
				}
			}
		}
        //user.setLastUpdatedBy(curUser.getId());
        user.setLastUpdateDate(new Date());
        int count = userMapper.update(user);
        return RespVOBuilder.success(count);
	}

	// }

	@Override
	public RespVO updateByPhone(User user) {
		if (null == user || StringUtils.isEmpty(user.getPhone())) {
			return RespVOBuilder.failure("参数缺失");
		}
		int count = userMapper.updateByPhone(user);
		return RespVOBuilder.success(count);
	}

	@Override
	public RespVO updateList(List<User> users) {
		if (CollectionUtils.isEmpty(users)) {
			return RespVOBuilder.failure("参数缺失");
		}

		//UserPriciple curUser = RequestContext.getCurrent().getUser();
		for (User user : users) {
		/*	if (null != curUser) {
				user.setLastUpdatedBy(curUser.getId());
			}*/
			if(StringUtils.isEmpty(user.getPassword())){
			    user.setPassword(SecurityUtils.encryptionWithMd5(user.getPassword()));
            }
			user.setLastUpdateDate(new Date());
		}
		int count = userMapper.updateList(users);
		return RespVOBuilder.success(count);
	}

	@Override
	public RespVO delete(Long id) {
		if (null == id) {
			return RespVOBuilder.failure("参数缺失");
		}
		int count = userMapper.delete(id);
		UserRole quserRoleProgram = new UserRole();
		quserRoleProgram.setUserId(id);
		List<UserRole> userRoleProgramList = userRoleService.findList(quserRoleProgram);
		if(userRoleProgramList!=null&& userRoleProgramList.size()>0){
			for(int i = 0;i<userRoleProgramList.size();i++){
				userRoleService.delete(userRoleProgramList.get(i));
			}
		}
		return RespVOBuilder.success(count);
	}

	@Override
	public RespVO deleteList(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return RespVOBuilder.failure("参数缺失");
		}
		int count = userMapper.deleteList(ids);
		return RespVOBuilder.success(count);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RespVO updateUserAndValidate(User user, String phone, String code) {
		if (!StringUtils.isNotBlank(phone) || !StringUtils.isNotBlank(code)) {
			return RespVOBuilder.failure("参数缺失");
		}

		User query = new User();
		query.setPhone(phone);
		List<User> userList = userMapper.findList(query);
		if (!CollectionUtils.isEmpty(userList)) {
			return RespVOBuilder.failure( "手机号码已存在");
		}

		/*Map<String, Object> validate = smsClient.validate(phone, code);
		if (CollectionUtils.isEmpty(validate)) {
			return ResultMapHelper.getResultMap(ResponseCode.CUSTOM_CODE, ResponseCode.PARAMETER_ERROR);
		}

		if (!validate.get(ResponseCode.CODE).equals(ResponseCode.SUCCESS_CODE)) {
			return validate;
		}*/

		//Long userId = RequestContext.getCurrent().getUser().getId();
		//user.setLastUpdatedBy(userId);
		String password = userMapper.findPassword(1L);
		user.setLastUpdateDate(new Date());
		if (password.equals(SecurityUtils.encryptionWithMd5(user.getPassword()))) {
			user.setPassword(null);
			int count = userMapper.update(user);
			return RespVOBuilder.success(count);
		} else {
			return RespVOBuilder.failure( "密码错误");
		}

	}

	@Override
	@Transactional
	public RespVO updateUserPassword(User user) {
		//UserPriciple curUser = RequestContext.getCurrent().getUser();
		//user.setId(curUser.getId());
		return update(user);
	}


	@Override
	@Transactional
	public RespVO updateOtherUser(User user) {
		if (null == user || null == user.getId()) {
			return RespVOBuilder.failure("参数缺失");
		}

		if (null != user.getPhone()) {
			User query = new User();
			query.setPhone(user.getPhone());
			List<User> userList = userMapper.findList(query);
			if (!CollectionUtils.isEmpty(userList) && !userList.get(0).getId().equals(user.getId())) {
			    return RespVOBuilder.failure("手机号码已存在");
			}
		}

		if (!StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(SecurityUtils.encryptionWithMd5(user.getPassword()));
		}

		user.setId(user.getId());
		//user.setLastUpdatedBy(RequestContext.getCurrent().getUser().getId());
		user.setLastUpdateDate(new Date());
		int count = userMapper.update(user);
		return RespVOBuilder.success(count);
	}

}
