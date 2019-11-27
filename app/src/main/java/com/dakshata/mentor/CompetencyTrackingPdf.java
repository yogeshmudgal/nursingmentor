package com.dakshata.mentor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dakshata.mentor.models.CompetencyTrackingDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEventAfterSplit;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Umesh on 2/13/2018.
 */
public class CompetencyTrackingPdf {

    Context context;
    String fileName;
    BaseColor baseColorSkyBlue, baseColorGrey, baseColorPeach, baseColorGreen, baseColorPurple,baseColorWhite;
    int total1, total2;
    ArrayList<CompetencyTrackingDto> competencyTrackingDtoArrayList;

    public CompetencyTrackingPdf(Context _ctx, String _fileName, String address, ArrayList<CompetencyTrackingDto> competencyTrackingDtos) {
        this.context = _ctx;
        this.fileName = _fileName;
        this.competencyTrackingDtoArrayList=competencyTrackingDtos;
        // generatePdf();
        try {
            Document document = new Document();

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NursingMentor";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dir, fileName.trim() + ".pdf");
            if(file.exists())
                file.delete();

//            FileOutputStream fOut = new FileOutputStream(file);

            createPdf(file.getAbsolutePath(), address);

            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_share_dialog);
            dialog.setTitle("Dakshata Mentor");
            Button btn_view = dialog.findViewById(R.id.btn_view);
            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NursingMentor" + "/" + fileName.trim() + ".pdf");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                    intent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".GenericFileProvider.provider", file), "application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(intent);

                    dialog.cancel();
                }
            });

            Button btn_share = dialog.findViewById(R.id.btn_share);
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{DraftActivity.userEmail});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, fileName);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                    //Add the attachment by specifying a reference to our custom ContentProvider
                    //and the specific file of interest
                    String outFilePath=Environment.getExternalStorageDirectory().getAbsolutePath() + "/NursingMentor" + "/" + fileName.trim() + ".pdf";
                    Uri path = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".GenericFileProvider.provider", new File(outFilePath));

                    Log.d("file path", outFilePath );
                    emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                    context.startActivity(emailIntent);

                    dialog.cancel();
                }
            });
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main PDF Creation
    class BorderEvent implements PdfPTableEventAfterSplit {

        protected int rowCount;
        protected boolean bottom = true;
        protected boolean top = true;

        public void setRowCount(int rowCount) {
            this.rowCount = rowCount;
        }

        public void splitTable(PdfPTable table) {
            if (table.getRows().size() != rowCount) {
                bottom = false;
            }
        }

        public void afterSplitTable(PdfPTable table, PdfPRow startRow, int startIdx) {
            if (table.getRows().size() != rowCount) {
                rowCount = table.getRows().size();
                top = false;
            }
        }

        public void tableLayout(PdfPTable table, float[][] width, float[] height, int headerRows, int rowStart, PdfContentByte[] canvas) {
            float widths[] = width[0];
            float y1 = height[0];
            float y2 = height[height.length - 1];
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
            for (int i = 0; i < widths.length; i++) {
                cb.moveTo(widths[i], y1);
                cb.lineTo(widths[i], y2);
            }
            float x1 = widths[0];
            float x2 = widths[widths.length - 1];
            for (int i = top ? 0 : 1; i < (bottom ? height.length : height.length - 1); i++) {
                cb.moveTo(x1, height[i]);
                cb.lineTo(x2, height[i]);
            }
            cb.stroke();
            cb.resetRGBColorStroke();
            bottom = true;
            top = true;
        }
    }

    public void createPdf(String dest, String address) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        Font font = new Font();
        font.setStyle(Font.BOLD);
        font.setSize(8f);
        BorderEvent event = new BorderEvent();

        // Header Row Values
        PdfPTable tableHead1 = new PdfPTable(1);
        tableHead1.setTotalWidth(500);
        tableHead1.setLockedWidth(true);
        tableHead1.setTableEvent(event);
        tableHead1.setWidthPercentage(100);
        tableHead1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead1.setSplitLate(true);

        // 2 Column - Row Values
        PdfPTable table = new PdfPTable(14);
        table.setTotalWidth(500);
        table.setLockedWidth(true);
        table.setTableEvent(event);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setSplitLate(true);

        createFirstTable(document, font, tableHead1, event, table, address);

        document.close();
    }

    private void createFirstTable(Document document, Font font, PdfPTable tableHead1, BorderEvent event, PdfPTable table,
                                  String address) throws IOException, DocumentException {

        baseColorSkyBlue = new BaseColor(146, 205, 220);
        baseColorGrey = new BaseColor(217, 217, 217);
        baseColorPeach = new BaseColor(229, 184, 183);
        baseColorGreen = new BaseColor(146, 208, 80);
        baseColorPurple = new BaseColor(178, 161, 199);
        baseColorWhite = new BaseColor(255, 255, 255);


        // Header Row Values
        PdfPTable tableHeadFirst = new PdfPTable(1);
        tableHeadFirst.setTotalWidth(500);
        tableHeadFirst.setLockedWidth(true);
        tableHeadFirst.setTableEvent(event);
        tableHeadFirst.setWidthPercentage(100);
        tableHeadFirst.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHeadFirst.setSplitLate(false);
        PdfPCell cellHeaderFirst = new PdfPCell(new Phrase(/*"  Mentor Name - " + DraftActivity.mentorName + */"PACK (Provider Assessment for Competency & Knowledge)", font));
        cellHeaderFirst.setBorder(Rectangle.NO_BORDER);
        cellHeaderFirst.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellHeaderFirst.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeaderFirst.setVerticalAlignment(Element.ALIGN_CENTER);
        cellHeaderFirst.setMinimumHeight(30);
        tableHeadFirst.addCell(cellHeaderFirst);
        event.setRowCount(tableHeadFirst.getRows().size());
        document.add(tableHeadFirst);



        // First page - 1st Table
        PdfPCell cellHeader = new PdfPCell(new Phrase("Name of\nProvider", font));
        cellHeader.setBorder(Rectangle.NO_BORDER);
        cellHeader.setBackgroundColor(baseColorSkyBlue);
        cellHeader.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader.setMinimumHeight(30);
        cellHeader.setColspan(2);
        table.addCell(cellHeader);

        // Col 1
        cellHeader = getPdfCell("Cadre", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 2
        cellHeader = getPdfCell("PA Exam", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 3
        cellHeader = getPdfCell("PV Exam", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 4
        cellHeader = getPdfCell("AMTSL", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 5
        cellHeader = getPdfCell("NBR", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 6
        cellHeader = getPdfCell("Hand\nHygiene", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 7
        cellHeader = getPdfCell("Antenatal\nComp.", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 8
        cellHeader = getPdfCell("Partograph", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 9
        cellHeader = getPdfCell("Postnatal\nComp.", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 10
        cellHeader = getPdfCell("Manage\nPreterm\nBirth", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 11
        cellHeader = getPdfCell("PNC\nCounseling", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);
        // Col 12
        cellHeader = getPdfCell("Overall", font, baseColorSkyBlue);
        cellHeader.setColspan(1);
        table.addCell(cellHeader);

        event.setRowCount(tableHead1.getRows().size());
        event.setRowCount(table.getRows().size());
        document.add(tableHead1);
        document.add(table);

        for(CompetencyTrackingDto competencyTrackingDto:competencyTrackingDtoArrayList){
            // First page - 1st Table
            PdfPTable tableData = new PdfPTable(14);
            tableData.setTotalWidth(500);
            tableData.setLockedWidth(true);
            tableData.setTableEvent(event);
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            tableData.setSplitLate(false);


            PdfPCell cellHeader1 = new PdfPCell(new Phrase(competencyTrackingDto.getPName(), font));
            cellHeader1.setBorder(Rectangle.NO_BORDER);
            cellHeader1.setBackgroundColor(baseColorWhite);
            cellHeader1.setVerticalAlignment(Element.ALIGN_LEFT);
            cellHeader1.setMinimumHeight(30);
            cellHeader1.setColspan(2);
            tableData.addCell(cellHeader1);

            // Col 1
            cellHeader1 = getPdfCell(competencyTrackingDto.getCadre(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 2
            cellHeader1 = getPdfCell(competencyTrackingDto.getPaExam(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 3
            cellHeader1 = getPdfCell(competencyTrackingDto.getPvExam(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 4
            cellHeader1 = getPdfCell(competencyTrackingDto.getAMTSL(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 5
            cellHeader1 = getPdfCell(competencyTrackingDto.getNBR(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 6
            cellHeader1 = getPdfCell(competencyTrackingDto.getHandHygiene(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 7
            cellHeader1 = getPdfCell(competencyTrackingDto.getAntenatalComp(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 8
            cellHeader1 = getPdfCell(competencyTrackingDto.getPartograph(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 9
            cellHeader1 = getPdfCell(competencyTrackingDto.getPostnatalComp(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 10
            cellHeader1 = getPdfCell(competencyTrackingDto.getManagePretermBirth(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 11
            cellHeader1 = getPdfCell(competencyTrackingDto.getPNCCounseling(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);
            // Col 12
            cellHeader1 = getPdfCell(competencyTrackingDto.getOverall(), font, baseColorWhite);
            cellHeader1.setColspan(1);
            tableData.addCell(cellHeader1);

            event.setRowCount(tableData.getRows().size());
            document.add(tableData);

        }
        document.close();


        Toast.makeText(context, "Pdf file created", Toast.LENGTH_LONG).show();
    }

    private PdfPTable getPdfTableForColumns(BorderEvent event, int columns) {
        PdfPTable table3 = new PdfPTable(columns);
        table3.setTotalWidth(500);
        table3.setLockedWidth(true);
        table3.setTableEvent(event);
        table3.setWidthPercentage(100);
        table3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table3.setSplitLate(false);
        return table3;
    }

    private PdfPTable getPdfTable(BorderEvent event, int columns) {
        PdfPTable tableHead3 = new PdfPTable(columns);
        tableHead3.setTotalWidth(500);
        tableHead3.setLockedWidth(true);
        tableHead3.setTableEvent(event);
        tableHead3.setWidthPercentage(100);
        tableHead3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead3.setSplitLate(false);
        return tableHead3;
    }

    private PdfPCell getPdfCell(String title, Font font, BaseColor baseColorSkyBlue) {
        PdfPCell cellHeader3 = new PdfPCell(new Phrase(title, font));
        cellHeader3.setBorder(Rectangle.NO_BORDER);
        cellHeader3.setBackgroundColor(baseColorSkyBlue);
        cellHeader3.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader3.setMinimumHeight(30);
        return cellHeader3;
    }

    private PdfPCell getPdfCellAndCheckValidation(String title1, Font font, int position) {
        float value26 = 0, value20 = 0, value19 = 0;
        switch (position) {
            case 5:
                if (title1.equalsIgnoreCase("") || title1.equalsIgnoreCase("0")) {
                    title1 = "-";
                }
                break;
            case 6:
                String table2_col1, table2_col3, table2_col5, table2_col7, table2_col9;
                if (DraftActivity.table2_col1.equalsIgnoreCase("") || DraftActivity.table2_col1.equalsIgnoreCase("null"))
                    table2_col1 = "0";
                else table2_col1 = DraftActivity.table2_col1;
                if (DraftActivity.table2_col3.equalsIgnoreCase("") || DraftActivity.table2_col3.equalsIgnoreCase("null"))
                    table2_col3 = "0";
                else table2_col3 = DraftActivity.table2_col3;
                if (DraftActivity.table2_col5.equalsIgnoreCase("") || DraftActivity.table2_col5.equalsIgnoreCase("null"))
                    table2_col5 = "0";
                else table2_col5 = DraftActivity.table2_col5;
                if (DraftActivity.table2_col7.equalsIgnoreCase("") || DraftActivity.table2_col7.equalsIgnoreCase("null"))
                    table2_col7 = "0";
                else table2_col7 = DraftActivity.table2_col7;
                if (DraftActivity.table2_col9.equalsIgnoreCase("") || DraftActivity.table2_col9.equalsIgnoreCase("null"))
                    table2_col9 = "0";
                else table2_col9 = DraftActivity.table2_col9;
                total1 = Integer.parseInt(table2_col1) + Integer.parseInt(table2_col3) + Integer.parseInt(table2_col5) +
                        Integer.parseInt(table2_col7) + Integer.parseInt(table2_col9);
                title1 = String.valueOf(total1);
                break;
            case 7:
                String table2_col2, table2_col4, table2_col6, table2_col8, table2_col10;
                if (DraftActivity.table2_col2.equalsIgnoreCase("") || DraftActivity.table2_col2.equalsIgnoreCase("null"))
                    table2_col2 = "0";
                else table2_col2 = DraftActivity.table2_col2;
                if (DraftActivity.table2_col4.equalsIgnoreCase("") || DraftActivity.table2_col4.equalsIgnoreCase("null"))
                    table2_col4 = "0";
                else table2_col4 = DraftActivity.table2_col4;
                if (DraftActivity.table2_col6.equalsIgnoreCase("") || DraftActivity.table2_col6.equalsIgnoreCase("null"))
                    table2_col6 = "0";
                else table2_col6 = DraftActivity.table2_col6;
                if (DraftActivity.table2_col8.equalsIgnoreCase("") || DraftActivity.table2_col8.equalsIgnoreCase("null"))
                    table2_col8 = "0";
                else table2_col8 = DraftActivity.table2_col8;
                if (DraftActivity.table2_col10.equalsIgnoreCase("") || DraftActivity.table2_col10.equalsIgnoreCase("null"))
                    table2_col10 = "0";
                else table2_col10 = DraftActivity.table2_col10;
                total2 = Integer.parseInt(table2_col2) + Integer.parseInt(table2_col4) + Integer.parseInt(table2_col6) +
                        Integer.parseInt(table2_col8) + Integer.parseInt(table2_col10);
                title1 = String.valueOf(total2);
                break;
            case 9:
                int total26 = 0;
                try {
                    if (DraftActivity.table4_col1.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col2.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col3.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col4.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col5.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col6.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col7.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col8.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col9.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col10.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col11.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col12.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col13.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col14.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col15.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col16.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col17.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col18.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col19.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col20.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col21.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col22.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col23.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col24.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col25.equalsIgnoreCase("Available")) total26++;
                    if (DraftActivity.table4_col26.equalsIgnoreCase("Available")) total26++;
                    value26 = (total26 * 100) / 26;
                    title1 = total26 + "(" + String.format("%.2f", value26) + "%)";
                } catch (Exception e) {
                    title1 = "-";
                }
                break;
            case 10:
                int total20 = 0;
                try {
                    if (DraftActivity.table3_col2.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col3.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col4.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col5.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col6.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col7.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col8.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col9.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col10.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col11.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col12.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col13.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col14.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col15.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col16.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col17.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col18.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col19.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col20.equalsIgnoreCase("Yes")) total20++;
                    if (DraftActivity.table3_col21.equalsIgnoreCase("Yes")) total20++;
                    value20 = (total20 * 100) / 20;
                    title1 = total20 + "(" + String.format("%.2f", value20) + "%)";
                } catch (Exception e) {
                    title1 = "-";
                }
                break;
            case 11:
                int total19 = 0;
                try {
                    if (DraftActivity.table5_col1.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col2.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col3.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col4.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col5.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col6.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col7.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col8.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col9.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col10.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col11.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col12.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col13.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col14.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col15.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col16.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col17.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col18.equalsIgnoreCase("Yes")) total19++;
                    if (DraftActivity.table5_col19.equalsIgnoreCase("Yes")) total19++;
                    if (total19 != 0) {
                        value19 = (total19 * 100) / 19;
                    }
                    title1 = total19 + "(" + String.format("%.2f", value19) + "%)";
                } catch (Exception e) {
                    title1 = "-";
                }
                break;
        }

        PdfPCell cellHeader3 = new PdfPCell(new Phrase(title1, font));
        cellHeader3.setBorder(Rectangle.NO_BORDER);
        cellHeader3.setVerticalAlignment(Element.ALIGN_LEFT);
        switch (position) {
            case 1:
                if (title1 != null && !title1.equalsIgnoreCase("Yes")) {
                    cellHeader3.setBackgroundColor(baseColorPeach);
                }
                break;
            case 2:
                if (title1 != null && !title1.equalsIgnoreCase("Available")) {
                    cellHeader3.setBackgroundColor(baseColorPeach);
                }
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:
                cellHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            case 6:
            case 7:
                cellHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellHeader3.setBackgroundColor(baseColorGrey);
                break;
            case 8:
                cellHeader3.setBackgroundColor(baseColorGrey);
                break;
            case 9:
            case 10:
            case 11:
                if (value19 >= 85.00 || value20 >= 85.00 || value26 >= 85.00) {
                    cellHeader3.setBackgroundColor(baseColorGreen);
                } else {
                    cellHeader3.setBackgroundColor(baseColorPeach);
                }
                cellHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            default:
                break;
        }
        cellHeader3.setMinimumHeight(30);
        return cellHeader3;
    }

    private PdfPCell getPdfCellAndCheckValidation2(String title1, String title2, Font font) {
        float value = 0;
        if (!title1.equalsIgnoreCase("")) {
            if (!title1.equalsIgnoreCase("0")) {
                try {
                    value = (Float.parseFloat(title2) * 100) / Float.parseFloat(title1);
                    title1 = String.format("%.2f", value) + "%";
                } catch (Exception e) {
                    title1 = "-";
                }
            } else {
                title1 = "-";
            }
        } else {
            title1 = "-";
        }
        PdfPCell cellHeader3 = new PdfPCell(new Phrase(title1, font));
        cellHeader3.setBorder(Rectangle.NO_BORDER);
        if (value >= 85.00) {
            cellHeader3.setBackgroundColor(baseColorGreen);
        } else {
            cellHeader3.setBackgroundColor(baseColorPeach);
        }
        cellHeader3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeader3.setMinimumHeight(30);
        return cellHeader3;
    }
}
