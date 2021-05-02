package cn.zkz.animal.controller;

import cn.zkz.animal.model.dto.CollectModel;
import cn.zkz.animal.model.dto.SortDto;
import cn.zkz.animal.model.po.AnimalInfo;
import cn.zkz.animal.model.po.KeyValue;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.service.IKeyValueService;
import cn.zkz.animal.util.Result;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.Inet4Address;
import java.util.*;
import java.util.List;

import static com.itextpdf.text.BaseColor.*;

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

    private static final int SIZE_5 = 7;
    private static final int SIZE_7 = 8;
    private static final int SIZE_8 = 10;
    private static final int SIZE_10 = 12;

    // 一页纸放21
    private static final int MAX_SIZE = 14;
    private static final int MAX_SIZE_ONE = 16;

    private static final int HEIGHT = 40;


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
        return Result.success().put("data", list);
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

    @ApiOperation("获取查询条件")
    @PostMapping("/getQueryData")
    public Result getQueryData() {
        return animalInfoService.getQueryData();
    }


    @ApiOperation("打印小动物")
    @GetMapping("/toPdf")
    public void toPdf() throws Exception {
          /*
        要求:
        1、种族内排序：性格 - 爱好 - 生日
        2、性别用符号表示： 男 - ♂ - 蓝色 女 - ♀ - 粉红色
        3、性格爱好一栏：用换行分隔性格和爱好
        4、打钩的框框再小一点
        5、去掉种族标题的边框
        6、图片按原图比例缩放
        7、清理垃圾数据
     */

        List<SortDto> sortList = new ArrayList<>();
        sortList.add(new SortDto("ASC", "animalXingge"));
        sortList.add(new SortDto("ASC", "animalLove"));
        sortList.add(new SortDto("ASC", "animalBridthMon"));
        sortList.add(new SortDto("ASC", "animal_bridth_day"));

        List<AnimalInfo> byCondition = animalInfoService.findByCondition(new AnimalInfo(), 10000, 1, sortList);

        String[] titles = new String[]{"序号","图片","姓名","生日","性格/爱好","是否喜欢","是否永久","野狸岛","荷包岛","评价"};

        // 按种族分类，每个种族一页纸
        Map<String, List<AnimalInfo>> zhongzuMap = new LinkedHashMap<>();
        for (AnimalInfo info : byCondition) {
            if (!zhongzuMap.keySet().contains(info.getAnimalZhongzu())) {
                zhongzuMap.put(info.getAnimalZhongzu(), new ArrayList<>());
            }
            info.setXuhao(zhongzuMap.get(info.getAnimalZhongzu()).size()+1);
            zhongzuMap.get(info.getAnimalZhongzu()).add(info);
        }

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("E:/table.pdf"));
        document.open();//使用iTextAsian.jar中的字体

        // 先找出一页纸排不了的种族。按一页纸切分，最后再汇总
        Map<String, List<AnimalInfo>> bigAniMap = new HashMap<>();
        for (String zhongzu : zhongzuMap.keySet()) {
            if (zhongzuMap.get(zhongzu).size() > MAX_SIZE_ONE) {
                for (int i=0; i<zhongzuMap.get(zhongzu).size()/MAX_SIZE_ONE; i++) {
                    bigAniMap.put(zhongzu ,zhongzuMap.get(zhongzu).subList(MAX_SIZE_ONE*i, MAX_SIZE_ONE * (i + 1)));
                }
                zhongzuMap.put(zhongzu, zhongzuMap.get(zhongzu).subList(MAX_SIZE_ONE*(MAX_SIZE_ONE/zhongzuMap.get(zhongzu).size()+1), zhongzuMap.get(zhongzu).size()));
            }
        }

        // 找出两个种族一页纸最佳匹配，先排序
        List<CollectModel> list = new ArrayList<>();
        for (String zhongzu : zhongzuMap.keySet()) {
            CollectModel map = new CollectModel();
            map.setZhongzu(zhongzu);
            map.setSize(zhongzuMap.get(zhongzu).size());
            list.add(map);
        }
        Collections.sort(list, (o1, o2) -> o1.getSize() - o2.getSize());


        List<Map<String, List<AnimalInfo>>> pageList = new ArrayList<>();
        Set<String> zhongzuSet = new HashSet<>();
        for (int i=0; i<list.size()-1; i++) {
            for (int j=list.size()-1; j>i;j--) {
                 if (StringUtils.isBlank(list.get(i).getPipei()) && StringUtils.isBlank(list.get(j).getPipei()) &&
                         (list.get(i).getSize() + list.get(j).getSize() <= MAX_SIZE)) {
                     list.get(i).setPipei(list.get(j).getZhongzu());
                     list.get(j).setPipei(list.get(i).getZhongzu());
                    break;
                 }
            }
        }
        for (int i=0; i<list.size(); i++) {
            String zz = list.get(i).getZhongzu();
            if (StringUtils.isBlank(list.get(i).getPipei())) {
                Map<String, List<AnimalInfo>> map = new LinkedHashMap<>();
                map.put(list.get(i).getZhongzu(), zhongzuMap.get(list.get(i).getZhongzu()));
                pageList.add(map);
            }else {
                if (!zhongzuSet.contains(zz)) {
                    zhongzuSet.add(zz);
                    zhongzuSet.add(list.get(i).getPipei());
                    Map<String, List<AnimalInfo>> map = new LinkedHashMap<>();
                    map.put(zz, zhongzuMap.get(zz));
                    map.put(list.get(i).getPipei(), zhongzuMap.get(list.get(i).getPipei()));
                    pageList.add(map);
                }
            }
        }

        List<Map<String, List<AnimalInfo>>> pageListTemp = new ArrayList<>();


            for (Map<String, List<AnimalInfo>> map : pageList) {
                HashSet<String> s = JSONObject.parseObject(JSONObject.toJSONString(map.keySet()), new TypeReference<HashSet<String>>(){});
                s.retainAll(bigAniMap.keySet());
                if (s.size() >0) {
                    Map<String, List<AnimalInfo>> temp = new HashMap<>();

                    // 存在交集
                    int position = 0;
                    int count = 0;
                    for (String key : map.keySet()) {
                        if (bigAniMap.keySet().contains(key) && count != 0) {
                            position = 2;
                            temp.put(key, bigAniMap.get(key));
                        }
                        if (bigAniMap.keySet().contains(key) && count == 0) {
                            position = 1;
                            temp.put(key, bigAniMap.get(key));
                        }
                        count++;
                    }
                    if (position == 1) {
                        pageListTemp.add(temp);
                        pageListTemp.add(map);

                    }else if (position == 2) {
                        pageListTemp.add(map);
                        pageListTemp.add(temp);
                    }
                }else {
                    pageListTemp.add(map);
                }
            }



        for (Map<String, List<AnimalInfo>> map : pageListTemp) {
            for (String key : map.keySet()) {
                System.out.println(map.get(key).size() + "############" + Arrays.toString(map.keySet().toArray()));
            }

        }

        for (Map<String, List<AnimalInfo>> map : pageListTemp) {
            document.newPage();
            PdfPTable table = new PdfPTable(titles.length);
            float[] columnWidths = { 1f, 1.5f, 1.2f, 1f, 1.8f, 1.2f, 1.2f, 1f, 1f, 6f };
            table.setWidths(columnWidths);
            List<PdfPRow> listRow = table.getRows();
            table.setWidthPercentage(100);
            int zzCount = 1;
            for (String zhongzu : map.keySet()) {

                if (zzCount > 1) {
                    PdfPCell columnSpan = new PdfPCell(new Phrase(" ",setFont(SIZE_8, Font.BOLD, BLACK)));
                    columnSpan.disableBorderSide(2);
                    columnSpan.disableBorderSide(4);
                    columnSpan.disableBorderSide(8);
                    columnSpan.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    columnSpan.setColspan(titles.length);
                    columnSpan.setPaddingBottom(3f);
                    columnSpan.setPaddingTop(3f);
                    table.addCell(columnSpan);
                }
                zzCount++;
                PdfPCell columnSpan = new PdfPCell(new Phrase(zhongzu,setFont(SIZE_10, Font.BOLD, BLACK)));
                columnSpan.disableBorderSide(1);
                columnSpan.disableBorderSide(4);
                columnSpan.disableBorderSide(8);
                columnSpan.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                columnSpan.setColspan(titles.length);
                columnSpan.setPaddingBottom(6f);
                table.addCell(columnSpan);

                PdfPCell titleCell[] = new PdfPCell[titles.length];
                PdfPRow titlerow1 = new PdfPRow(titleCell);
                for(int i=0;i<titles.length;i++) {
                    titleCell[i] = new PdfPCell(new Paragraph(titles[i], setFont(SIZE_7, Font.BOLD, BLACK)));
                    titleCell[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    titleCell[i].setPaddingBottom(3f);
                    titleCell[i].setPaddingTop(3f);
                }
                listRow.add(titlerow1);
                int i = 1;
                for (AnimalInfo info : map.get(zhongzu)) {

                    PdfPCell titleCel[] = new PdfPCell[titles.length];
                    PdfPRow titlerow = new PdfPRow(titleCel);
                    titleCel[0] = new PdfPCell(new Paragraph(info.getXuhao() + "", setFont(SIZE_5, Font.BOLD, BLACK)));
                    Image image= Image.getInstance("E:/"+info.getAnimalImg());
//                    image.scaleAbsolute(image.getWidth()/20, image.getHeight()/20);
                    image.scaleAbsolute(HEIGHT*image.getWidth()/image.getHeight(), HEIGHT);
                    titleCel[1] = new PdfPCell(image);
                    Chunk chunkBlue = info.getAnimalSex().equals("男") ? new Chunk("♂", setFont(SIZE_5, Font.NORMAL, BLUE)) :
                            new Chunk("♀", setFont(SIZE_5, Font.NORMAL, new BaseColor(255,20,147)));
                    Chunk chunkblack = new Chunk(info.getAnimalChinaName() + " ", setFont(SIZE_5, Font.NORMAL, BLACK));
                    Paragraph elements = new Paragraph();
                    elements.add(chunkblack);
                    elements.add(chunkBlue);
                    titleCel[2] = new PdfPCell(elements);

                    titleCel[3] = new PdfPCell(new Paragraph(info.getAnimalBirthday(), setFont(SIZE_5, Font.NORMAL, BLACK)));
                    titleCel[4] = new PdfPCell(new Paragraph(info.getAnimalXingge()+" / " + info.getAnimalLove(), setFont(SIZE_5, Font.NORMAL, BLACK)));
                    titleCel[5] = new PdfPCell(new Paragraph("□", setFont(SIZE_8, Font.NORMAL, BLACK)));
                    titleCel[6] = new PdfPCell(new Paragraph("□", setFont(SIZE_8, Font.NORMAL, BLACK)));
                    titleCel[7] = new PdfPCell(new Paragraph("□", setFont(SIZE_8, Font.NORMAL, BLACK)));
                    titleCel[8] = new PdfPCell(new Paragraph("□", setFont(SIZE_8, Font.NORMAL, BLACK)));
                    titleCel[9] = new PdfPCell(new Paragraph(""));

                    for (int j=0; j<titles.length; j++) {
                        titleCel[j].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        titleCel[j].setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                        titleCel[j].setPaddingBottom(3f);
                        titleCel[j].setPaddingTop(3f);
                    }

                    listRow.add(titlerow);
                }

            }
            document.add(table);
        }
        document.close();
    }


    public static Font setFont(int size, int style, BaseColor color){
        BaseFont baseFont= null;try{
            baseFont= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

        }catch(DocumentException e) {
            e.printStackTrace();

        }catch(IOException e) {
            e.printStackTrace();

        }

        Font font= new Font(baseFont, size, style, color);
//        Font font= new Font(baseFont, size, Font.NORMAL, BLACK);
        return font;

    }

    public static void main(String[] args) throws Exception {


        int withLen = 15;
        int heightLen = 25;

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("E:/test.pdf"));
        document.open();//使用iTextAsian.jar中的字体
        document.newPage();
        PdfPTable table = new PdfPTable(withLen);
//        float[] columnWidths = { 1f, 1.5f, 1.2f, 1f, 1.8f, 1.2f, 1.2f, 1f, 1f, 6f };
//        table.setWidths(columnWidths);
        List<PdfPRow> listRow = table.getRows();
        table.setWidthPercentage(100);

        PdfDocument doc = new PdfDocument();





        int count = 1;
        for (int i=0; i<heightLen; i++) {
            for (int j=0; j<withLen; j++) {

                String temp = count >365 ? "" : count + "";
                PdfPCell columnSpan = new PdfPCell(new Phrase(temp, setFont(SIZE_10, Font.NORMAL, BLACK)));
                columnSpan.disableBorderSide(1);
                columnSpan.disableBorderSide(4);
                columnSpan.disableBorderSide(8);
                columnSpan.disableBorderSide(PdfPCell.BOTTOM);
                columnSpan.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//                columnSpan.setColspan(titles.length);
                columnSpan.setPaddingBottom(15f);

                int r = count >365 ? 0 : 13;
                if (count <= 365) {
                    PdfContentByte cb = writer.getDirectContent();
                    cb.saveState();
                    cb.circle(53+(j*34.92857f),100+((heightLen-i-1)*29),r);
                    cb.stroke();
                    cb.restoreState();
                    System.out.println(count);
                }

                table.addCell(columnSpan);

                count++;



            }
        }
        document.add(table);
        document.close();
    }




}
