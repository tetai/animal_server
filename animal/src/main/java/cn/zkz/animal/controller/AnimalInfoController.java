package cn.zkz.animal.controller;

import cn.zkz.animal.po.AnimalInfo;
import cn.zkz.animal.po.KeyValue;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.service.IKeyValueService;
import cn.zkz.animal.util.Result;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("animalInfo")
@Api(tags = "动物集合相关接口")
public class AnimalInfoController {

    private Logger log = LoggerFactory.getLogger(AnimalInfoController.class);

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
            @ApiImplicitParam(name = "animalVo", value = "查询条件,对象字符串，字段查看AnimalInfo"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "pageNum", value = "第几页")
    }
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "{\"code\":200,\"data\":[{}]}, \"count\":111")
    })
    @PostMapping("/findByCondition")
    public Result findByCondition(@RequestBody Map<String, String> pamams) {
        String animalVo = pamams.containsKey("animalVo") ? pamams.get("animalVo") : null;
        Integer pageSize = pamams.containsKey("pageSize") ? Integer.parseInt(pamams.get("pageSize")) : null;
        Integer pageNum = pamams.containsKey("pageNum") ? Integer.parseInt(pamams.get("pageNum")) : null;
        AnimalInfo vo = new AnimalInfo();
        if (!StringUtils.isBlank(animalVo)) {
            vo = JSONObject.parseObject(animalVo, AnimalInfo.class);
        }

        List<AnimalInfo> list = animalInfoService.findByCondition(vo, pageSize, pageNum);
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










}
