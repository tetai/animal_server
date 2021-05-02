package cn.zkz.animal.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.util.List;
@RestController
public class Test {

    @RequestMapping("getIp")
    public String getIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public static void main(String[] args) throws Exception {
        // 创建文件
        Document document = new Document();
// 建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("E:/table.pdf"));
// 打开文件
        document.open();
// 添加内容
        document.add(new Paragraph("-----------------table----------------"));

// 3列的表
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距

        List<PdfPRow> listRow = table.getRows();
// 设置列宽
        float[] columnWidths = { 2f, 2f, 2f };
        table.setWidths(columnWidths);

// 行1
        PdfPCell cells1[] = new PdfPCell[3];
        PdfPRow row1 = new PdfPRow(cells1);

// 单元格
// 单元格内容
        cells1[0] = new PdfPCell(new Paragraph("name"));
// 边框验证
// cells1[0].setBorderColor(BaseColor.BLUE);
// 左填充20
        cells1[0].setPaddingLeft(20);
// 水平居中
        cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 单元格内容
        cells1[1] = new PdfPCell(new Paragraph("pwd"));
// 左填充20
        cells1[1].setPaddingLeft(20);
// 水平居中
        cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 单元格内容
        cells1[2] = new PdfPCell(new Paragraph("birthday"));
// 左填充20
        cells1[2].setPaddingLeft(20);
// 水平居中
        cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);

// 行2
        // 图片
        Image image= Image.getInstance("E:\\animal\\img\\tin80dfsjanwhjgpg3d19a56rd83dr8.png");
// 设置图片位置的x轴和y周
//        image.setAbsolutePosition(100f, 550f);
// 设置图片的宽度和高度
        image.scaleAbsolute(50, 50);

        PdfPCell cells2[] = new PdfPCell[3];
        PdfPRow row2 = new PdfPRow(cells2);
// 单元格内容
        cells2[0] = new PdfPCell(image);
// 左填充20
        cells2[0].setPaddingLeft(20);
// 水平居中
        cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 单元格内容
        cells2[1] = new PdfPCell(new Paragraph("666"));
// 左填充20
        cells2[1].setPaddingLeft(20);
// 水平居中
        cells2[1].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells2[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 单元格内容
        cells2[2] = new PdfPCell(new Paragraph("1993-02-05"));
// 左填充20
        cells2[2].setPaddingLeft(20);
// 水平居中
        cells2[2].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 行3
        PdfPCell cells3[] = new PdfPCell[3];
        PdfPRow row3 = new PdfPRow(cells3);
// 单元格内容
        cells3[0] = new PdfPCell(new Paragraph("lisi"));
// 左填充20
        cells3[0].setPaddingLeft(20);
// 水平居中
        cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells3[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 单元格内容
        cells3[1] = new PdfPCell(new Paragraph("222"));
// 左填充20
        cells3[1].setPaddingLeft(20);
// 水平居中
        cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells3[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
// 单元格内容
        cells3[2] = new PdfPCell(new Paragraph("1993-02-05"));
// 左填充20
        cells3[2].setPaddingLeft(20);
// 水平居中
        cells3[2].setHorizontalAlignment(Element.ALIGN_CENTER);
// 垂直居中
        cells3[2].setVerticalAlignment(Element.ALIGN_MIDDLE);

        listRow.add(row1);
        listRow.add(row2);
        listRow.add(row3);
        document.add(table);

        document.close();
        writer.close();

    }

}
