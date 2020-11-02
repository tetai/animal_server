package cn.zkz.animal.controller;

import cn.zkz.animal.model.po.UserDevice;
import cn.zkz.animal.service.IUserDeviceService;
import cn.zkz.animal.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/userDevice")
@Api(tags = "用户设备相关接口，用户可管理多设备")
public class UserDeviceController {

    private static Logger log = LogManager.getLogger(UserDeviceController.class);


    @Autowired
    private IUserDeviceService userDeviceService;

    @ApiOperation("获取用户设备列表")
    @GetMapping(value = "/openId/findAll")
    public Result findAll(HttpServletRequest request, String openId) {

        List<UserDevice> list = userDeviceService.findAll(openId);
        return Result.success().put("data", list);
    }

    @ApiOperation("增加用户设备列表")
    @PostMapping(value = "/openId/saveOrUpdateOne")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo", value = "UserDevice对象，更新时需要传所有字段，当activeFlag=0表示删除")
    }
    )
    public Result saveOrUpdateOne(HttpServletRequest request, String openId, @RequestBody UserDevice vo) {

        vo.setUserId(openId);
        userDeviceService.save(vo);
        return Result.success();
    }








}
