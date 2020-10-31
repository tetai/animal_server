package cn.zkz.animal.controller;

import cn.zkz.animal.model.dto.SortDto;
import cn.zkz.animal.model.po.AnimalInfo;
import cn.zkz.animal.model.po.KeyValue;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.service.IKeyValueService;
import cn.zkz.animal.util.Result;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("animalInfo")
@Api(tags = "动物集合相关接口")
public class AnimalInfoController {

    private static Logger log = LogManager.getLogger(AnimalInfoController.class);

    @Autowired
    private IAnimalInfoService animalInfoService;

    @Autowired
    private IKeyValueService keyValueService;

    private static final String FILE_PATH = "animal_img_file_path";
    private static final String DOMAIN = "domain";

    @GetMapping("/getById")
    @ApiOperation("根据条件分组Id查找动物")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "int类型", paramType = "int")
    }
    )
    public Result getById(int id) {
        log.info("id:" +id);
        return Result.success().put("data", animalInfoService.getById(id));
    }

    @ApiOperation("根据条件分组查找动物集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "animalVo", value = "查询条件,对象字符串，字段查看AnimalInfo, 生日传月日，如0101.1201，startTime, endTime"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "orderByList", value = "排序对象{'sortName': 'animalChinaName', 'descOrAsc':'DESC'}", paramType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "第几页")
    }
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\"code\":200,\"data\":[{}], \"count\":111}")
    })
    @PostMapping("/findByCondition")
    public Result findByCondition(@RequestBody Map<String, String> pamams) {
        String animalVo = pamams.containsKey("animalVo") ? pamams.get("animalVo") : null;
        Integer pageSize = pamams.containsKey("pageSize") ? Integer.parseInt(pamams.get("pageSize")) : null;
        Integer pageNum = pamams.containsKey("pageNum") ? Integer.parseInt(pamams.get("pageNum")) : null;
        String orderByListStr = pamams.containsKey("orderByList") ? pamams.get("orderByList") : null;
        AnimalInfo vo = new AnimalInfo();
        if (!StringUtils.isBlank(animalVo)) {
            vo = JSONObject.parseObject(animalVo, AnimalInfo.class);
        }
        List<SortDto> sortList = new ArrayList<>();
        if (!StringUtils.isBlank(orderByListStr)) {
            sortList = JSONObject.parseArray(orderByListStr, SortDto.class);
        }
        List<AnimalInfo> list = animalInfoService.findByCondition(vo, pageSize, pageNum, sortList);
        return Result.success().put("data", list).put("count", animalInfoService.findCountByCondition(vo));
    }
    @ApiOperation("返回图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "图片路径")
    })
    @GetMapping(value = "/image")
    public void getImage(HttpServletRequest request, HttpServletResponse response, String path) {
        KeyValue keyValue = keyValueService.getById(FILE_PATH);
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            File file = new File(keyValue.getValue() + path);
            System.out.println(keyValue + path);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation("查找上一页或者下一页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "animalVo", value = "查询条件,对象字符串，字段查看AnimalInfo, 生日传月日，如0101.1201，startTime, endTime"),
            @ApiImplicitParam(name = "pageType", value = "上一页传1，下一页传2", paramType = "int"),
            @ApiImplicitParam(name = "orderByList", value = "排序对象{'sortName': '', 'descOrAsc':''}", paramType = "int"),
            @ApiImplicitParam(name = "id", value = "当前数据的ID", paramType = "int")
    }
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "animalVo")
    })
    @PostMapping("/findLastOrNextData")
    public Result findLastOrNextData(@RequestBody Map<String, String> pamams) {
        String animalVo = pamams.containsKey("animalVo") ? pamams.get("animalVo") : null;
        Integer pageType = pamams.containsKey("pageType") ? Integer.parseInt(pamams.get("pageType")) : null;
        Integer id = pamams.containsKey("id") ? Integer.parseInt(pamams.get("id")) : null;
        String orderByListStr = pamams.containsKey("orderByList") ? pamams.get("orderByList") : null;
        AnimalInfo vo = new AnimalInfo();
        if (!StringUtils.isBlank(animalVo)) {
            vo = JSONObject.parseObject(animalVo, AnimalInfo.class);
        }
        List<SortDto> sortList = new ArrayList<>();
        if (!StringUtils.isBlank(orderByListStr)) {
            sortList = JSONObject.parseArray(orderByListStr, SortDto.class);
        }

        AnimalInfo po = animalInfoService.findLastOrNextData(vo, pageType, id, sortList);
        return Result.success().put("data", po);
    }










}
