package com.lego.perception.system.controller;
import com.framework.common.page.Page;
import com.framework.common.page.PagedResult;
import com.framework.common.sdto.RespDataVO;
import com.framework.common.sdto.RespVO;
import com.framework.common.sdto.RespVOBuilder;
import com.lego.framework.base.annotation.Operation;
import com.lego.framework.base.annotation.Resource;
import com.lego.framework.base.context.RequestContext;
import com.lego.perception.system.service.IRoleService;
import com.lego.perception.system.service.IUserService;
import com.lego.framework.system.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("user/v1")
@Resource(value = "user", desc = "用户管理")
@Api(value = "UserController",description = "用户管理")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;


    @Autowired
    private IRoleService roleService;


    @RequestMapping(value = "/findPagedList/{pageSize}/{curPage}", method = RequestMethod.GET)
    @Operation(value = "find", desc = "查询")
    @ApiOperation("分页查询")
    public RespVO<PagedResult<User>> page(@ModelAttribute User user, @PathParam("") Page page) {
        user.setDeleteFlag(2);
        return RespVOBuilder.success(userService.findPagedList(user, page));
    }

    @ApiOperation("根据用户id查询到用户的信息+角色")
    @RequestMapping(value = "/findByRole", method = RequestMethod.GET)
    public RespVO<User> findByRole(@RequestParam(value = "id") Long id) {
        User query = new User();
        query.setId(id);
        query.setDeleteFlag(2);
        return RespVOBuilder.success(userService.findRoleList(query));
    }


    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @ApiOperation("查询")
    public RespVO<RespDataVO<User>> findList(@ModelAttribute User user) {
        user.setDeleteFlag(2);
        return RespVOBuilder.success(userService.findList(user));
    }

    @RequestMapping(value = "/findListByUserIds", method = RequestMethod.POST)
    @ApiOperation("查询")
    public RespVO<RespDataVO<User>> findListByUserIds(@RequestBody User user) {
        user.setDeleteFlag(2);
        return RespVOBuilder.success(userService.findList(user));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Operation(value = "add", desc = "新增")
    @ApiOperation("新增")
    public RespVO insert(@RequestBody User user) {
        if (null == user) {
            return RespVOBuilder.failure("参数缺失");
        }
        return userService.insert(user);
    }

    @RequestMapping(value = "/insertList", method = RequestMethod.POST)
    @Operation(value = "add", desc = "新增")
    @ApiOperation("批量新增")
    public RespVO insert(@RequestBody List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            return RespVOBuilder.failure("参数缺失");
        }
        return userService.insertList(users);
    }

    @RequestMapping(value = "/updateAndInsert", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("更新和删除")
    public RespVO updateAndInsert(@RequestBody User user) {

        return userService.updateAndInsert(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("用户，更新用户自己信息")
    public RespVO update(@RequestBody User user) {

        return userService.update(user);

    }

    @RequestMapping(value = "/update_other_user", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("后台管理更新其他用户信息")
    public RespVO updateOtherUser(@RequestBody User user) {

        return userService.updateOtherUser(user);
    }

    @RequestMapping(value = "/updateList", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("批量更新")
    public RespVO updateList(@RequestBody List<User> users) {

        return userService.updateList(users);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("删除")
    public RespVO delete(@RequestParam("id") Long id) {

        return userService.delete(id);
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    @Operation(value = "delete", desc = "删除")
    @ApiOperation("批量删除")
    public RespVO delete(@RequestBody List<Long> ids) {

        return userService.deleteList(ids);
    }

    /**
     * 验证码验证和用户修改
     *
     * @param user
     * @param otherPhone
     * @param code
     * @return
     */
    @RequestMapping(value = "/updateUserAndValidate", method = RequestMethod.POST)
    @Operation(value = "update", desc = "更新")
    @ApiOperation("验证码验证后，修改用户手机号")
    public RespVO updateUserAndValidate(@RequestBody User user, @RequestParam("otherPhone") String otherPhone,
                                        @RequestParam("code") String code) {
        Long userId = RequestContext.getCurrent().getUserId();
        //user.setId(curUser.getId());
        return userService.updateUserAndValidate(user, otherPhone, code);
    }

    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    @ApiOperation("修改用户密码(不需要手机验证码)")
    public RespVO updateUserPassword(@RequestBody User user) {

        return userService.updateUserPassword(user);
    }

    @ApiOperation("查询用户")
    @RequestMapping(value = "/findUserById", method = RequestMethod.POST)
    public RespVO<User> findUserById(@RequestBody User user) {
        return RespVOBuilder.success(userService.findById(user));
    }

    @ApiOperation("查询全部用户")
    @RequestMapping(value = "/findListByUsers", method = RequestMethod.POST)
    public RespVO<RespDataVO<User>> findListByUsers(@RequestBody User user) {
        return RespVOBuilder.success(userService.findList(user));
    }

}
