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


/**
 * Created by Umesh on 2/13/2018.
 */
public class FinalRecordsPdf {

    Context context;
    String fileName;
    BaseColor baseColorSkyBlue, baseColorGrey, baseColorPeach, baseColorGreen, baseColorPurple;
    int total1, total2;

    public FinalRecordsPdf(Context _ctx, String _fileName, String address) {
        this.context = _ctx;
        this.fileName = _fileName;
        // generatePdf();
        try {
            Document document = new Document();

            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NursingMentor";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dir, fileName.trim() + ".pdf");
//            FileOutputStream fOut = new FileOutputStream(file);

            createPdf(file.getAbsolutePath(), address);

            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_share_dialog);
            dialog.setTitle("Mentor App");
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
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{JhpiegoHistory.userEmail});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, fileName);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                    //Add the attachment by specifying a reference to our custom ContentProvider
                    //and the specific file of interest
                    Log.d("file path", "file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/NursingMentor" + "/" + fileName.trim() + ".pdf");
                    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath() + "/NursingMentor" + "/" + fileName.trim() + ".pdf"));
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
        BorderEvent event = new BorderEvent();

        // Header Row Values
        PdfPTable tableHead1 = new PdfPTable(1);
        tableHead1.setTotalWidth(500);
        tableHead1.setLockedWidth(true);
        tableHead1.setTableEvent(event);
        tableHead1.setWidthPercentage(100);
        tableHead1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead1.setSplitLate(false);

        // 2 Column - Row Values
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(500);
        table.setLockedWidth(true);
        table.setTableEvent(event);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.setSplitLate(false);

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

        // Header Row Values
        PdfPTable tableHeadFirst = new PdfPTable(1);
        tableHeadFirst.setTotalWidth(500);
        tableHeadFirst.setLockedWidth(true);
        tableHeadFirst.setTableEvent(event);
        tableHeadFirst.setWidthPercentage(100);
        tableHeadFirst.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHeadFirst.setSplitLate(false);
        PdfPCell cellHeaderFirst = new PdfPCell(new Phrase(/*"  Mentor Name - " + JhpiegoHistory.mentorName + */"Submission Date - " + JhpiegoHistory.createdDate + "\n\nAddress (Provided by GPS) : " + address + "\n ", font));
        cellHeaderFirst.setBorder(Rectangle.NO_BORDER);
        cellHeaderFirst.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellHeaderFirst.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeaderFirst.setMinimumHeight(30);
        tableHeadFirst.addCell(cellHeaderFirst);
        event.setRowCount(tableHeadFirst.getRows().size());
        document.add(tableHeadFirst);

        // WorkPlan Status Row Values
        PdfPTable tableWorkPlan = new PdfPTable(1);
        tableWorkPlan.setTotalWidth(500);
        tableWorkPlan.setLockedWidth(true);
        tableWorkPlan.setTableEvent(event);
        tableWorkPlan.setWidthPercentage(100);
        tableWorkPlan.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableWorkPlan.setSplitLate(false);
        PdfPCell cellWorkPlan = new PdfPCell(new Phrase("                    WorkPlan Final Status" + "\n\n      Actual Visit Date - " + JhpiegoHistory.table1_col8 + "\n\n      Actual Planned Date - " + JhpiegoHistory.table1_colPlannedDate +
                "\n\n      Final Workplan Status - " + JhpiegoHistory.table1_colWorkPlanStatus + "\n\n", font));
        cellWorkPlan.setBorder(Rectangle.NO_BORDER);
        cellWorkPlan.setBackgroundColor(BaseColor.WHITE);
        cellWorkPlan.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellWorkPlan.setMinimumHeight(30);
        tableWorkPlan.addCell(cellWorkPlan);
        event.setRowCount(tableWorkPlan.getRows().size());
        document.add(tableWorkPlan);

        // Address Row Values
        /*PdfPTable tableAddress = new PdfPTable(1);
        tableAddress.setTotalWidth(500);
        tableAddress.setLockedWidth(true);
        tableAddress.setTableEvent(event);
        tableAddress.setWidthPercentage(100);
        tableAddress.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableAddress.setSplitLate(false);
        PdfPCell cellAddress = new PdfPCell(new Phrase("  Address : " + address, font));
        cellAddress.setBorder(Rectangle.NO_BORDER);
        cellAddress.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cellAddress.setVerticalAlignment(Element.ALIGN_LEFT);
        cellAddress.setMinimumHeight(30);
        tableAddress.addCell(cellAddress);
        event.setRowCount(tableAddress.getRows().size());
        document.add(tableAddress);*/

        // First page - 1st Table
        PdfPCell cellHeader = new PdfPCell(new Phrase("     I. Details of Mentoring Visit (Mandatory FORM )", font));
        cellHeader.setBorder(Rectangle.NO_BORDER);
        cellHeader.setBackgroundColor(baseColorSkyBlue);
        cellHeader.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader.setMinimumHeight(30);
        tableHead1.addCell(cellHeader);

        PdfPCell cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setMinimumHeight(60);

        // Row 1
        table.addCell("Name of Mentor:");
        table.addCell(JhpiegoHistory.table1_col1);
        // Row 2
        table.addCell("State:");
        table.addCell(JhpiegoHistory.table1_col2);
        // Row 3
        table.addCell("District:");
        table.addCell(JhpiegoHistory.table1_col3);
        // Row 4
        table.addCell("Block:");
        table.addCell(JhpiegoHistory.table1_col4);
        // Row 5
        table.addCell("Type of Facility:");
        table.addCell(JhpiegoHistory.table1_col5);
        // Row 6
        table.addCell("Name of Facility :");
        table.addCell(JhpiegoHistory.table1_col6);
        // Row 7
        //table.addCell("Level of Facility:");
        //table.addCell(JhpiegoHistory.table1_col7);
        // Row 8
        table.addCell("Date of Visit:");
        table.addCell(JhpiegoHistory.table1_col8);
        // Row 9
        table.addCell("Mentoring Visit Number:");
        table.addCell(JhpiegoHistory.table1_colVisit);

        event.setRowCount(tableHead1.getRows().size());
        event.setRowCount(table.getRows().size());
        document.add(tableHead1);
        document.add(table);

        // First page - 2nd Table
        // Header Row Values
        PdfPTable tableHead2 = new PdfPTable(13);
        tableHead2.setTotalWidth(500);
        tableHead2.setLockedWidth(true);
        tableHead2.setTableEvent(event);
        tableHead2.setWidthPercentage(100);
        tableHead2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead2.setSplitLate(false);

        // 2 Column - Row Values
        PdfPTable table2 = new PdfPTable(13);
        table2.setTotalWidth(500);
        table2.setLockedWidth(true);
        table2.setTableEvent(event);
        table2.setWidthPercentage(100);
        table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table2.setSplitLate(false);

        PdfPCell cellHeader2 = new PdfPCell(new Phrase("     II. Details of Labor Room Staff ", font));
        cellHeader2.setBorder(Rectangle.NO_BORDER);
        cellHeader2.setBackgroundColor(baseColorSkyBlue);
        cellHeader2.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setMinimumHeight(30);
        cellHeader2.setColspan(7);
        tableHead2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Staff working in LR", font, baseColorSkyBlue);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);
        cellHeader2 = getPdfCell("Dakshata/Skill lab trained", font, baseColorSkyBlue);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);
        cellHeader2 = getPdfCell("%", font, baseColorSkyBlue);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);

        PdfPCell cell2 = new PdfPCell(new Phrase(""));
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setMinimumHeight(60);

        // Row 1
        cellHeader2 = getPdfCellAndCheckValidation("1. OBG specialists", font, 3);
        cellHeader2.setColspan(7);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col1, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col2, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation2(JhpiegoHistory.table2_col1, JhpiegoHistory.table2_col2, font);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 2
        cellHeader2 = getPdfCellAndCheckValidation("2. Medical Officer", font, 3);
        cellHeader2.setColspan(7);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col3, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col4, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation2(JhpiegoHistory.table2_col3, JhpiegoHistory.table2_col4, font);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 3
        cellHeader2 = getPdfCellAndCheckValidation("3. Staff Nurses", font, 3);
        cellHeader2.setColspan(7);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col5, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col6, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation2(JhpiegoHistory.table2_col5, JhpiegoHistory.table2_col6, font);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 4
        cellHeader2 = getPdfCellAndCheckValidation("4. ANM", font, 3);
        cellHeader2.setColspan(7);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col7, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col8, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation2(JhpiegoHistory.table2_col7, JhpiegoHistory.table2_col8, font);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 5
        cellHeader2 = getPdfCellAndCheckValidation("5. LHV", font, 3);
        cellHeader2.setColspan(7);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col9, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col10, font, 5);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation2(JhpiegoHistory.table2_col9, JhpiegoHistory.table2_col10, font);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 6
        cellHeader2 = getPdfCellAndCheckValidation("Sub total", font, 8);
        cellHeader2.setColspan(7);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col9, font, 6);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation(JhpiegoHistory.table2_col10, font, 7);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);
        cellHeader2 = getPdfCellAndCheckValidation2(String.valueOf(total1), String.valueOf(total2), font);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        event.setRowCount(tableHead2.getRows().size());
        event.setRowCount(table2.getRows().size());
        document.add(tableHead2);
        document.add(table2);

        // First page - 3rd Table
        // Header Row Values
        PdfPTable tableHead3 = getPdfTable(event, 1);

        // 2 Column - Row Values
        PdfPTable table3 = getPdfTableForColumns(event, 2);
        PdfPCell cellHeader3 = new PdfPCell(new Phrase("     III. Details of Labor Room Infrastructure ", font));
        cellHeader3.setBorder(Rectangle.NO_BORDER);
        cellHeader3.setBackgroundColor(baseColorSkyBlue);
        cellHeader3.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader3.setMinimumHeight(30);
        tableHead3.addCell(cellHeader3);

        // Row Headers
        table3.addCell(getPdfCell("Infrastructure Details", font, baseColorGrey));
        table3.addCell(getPdfCell("Response", font, baseColorGrey));

        event.setRowCount(tableHead3.getRows().size());
        event.setRowCount(table3.getRows().size());
        document.add(tableHead3);
        document.add(table3);
        tableHead3 = getPdfTable(event, 1);
        table3 = getPdfTableForColumns(event, 2);

        tableHead3.addCell(getPdfCell("Respectful care", font, baseColorPurple));
        // Row 1
        table3.addCell("1. " + context.getResources().getString(R.string.section_c_label_1_new));
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col2, font, 1));
        // Row 2
        table3.addCell("2. Does the labor room windows have curtains of light color or frosted glass?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col3, font, 1));
        // Row 3
        table3.addCell("3. Is there a provision for cover by screens or curtains around each labor table?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col4, font, 1));
        // Row 16
        table3.addCell("4. Is there a facility for seating of birth companion at each labor table?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col16, font, 1));
        // Row 17
        table3.addCell("5. Does the labor room have an access to a functional washroom with running water supply?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col17, font, 1));

        event.setRowCount(tableHead3.getRows().size());
        event.setRowCount(table3.getRows().size());
        document.add(tableHead3);
        document.add(table3);
        tableHead3 = getPdfTable(event, 1);
        table3 = getPdfTableForColumns(event, 2);

        tableHead3.addCell(getPdfCell("LR Ambiance", font, baseColorPurple));
        // Row 5
        table3.addCell("6. Do the labor room walls have tiles extending upto the height of labor room door?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col6, font, 1));
        // Row 6
        table3.addCell("7. Does the labor room have an air-conditioner with adequate tonnage capacity?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col7, font, 1));
        // Row 7
        table3.addCell("8. Does the labor room have adequate exhaust for ventilation?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col8, font, 1));
        // Row 8
        table3.addCell("9. Is there a focus lamp/shadow-less light for each labor table?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col9, font, 1));
        // Row 12
        table3.addCell("10. Are all labor tables clean, non-rusted and not broken?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col13, font, 1));
        // Row 4
        table3.addCell("11. Is the labor room floor constructed with anti-skid tiles/natural stone or equivalent material?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col5, font, 1));

        event.setRowCount(tableHead3.getRows().size());
        event.setRowCount(table3.getRows().size());
        document.add(tableHead3);
        document.add(table3);
        tableHead3 = getPdfTable(event, 1);
        table3 = getPdfTableForColumns(event, 2);

        tableHead3.addCell(getPdfCell("Efficient Organisation of care", font, baseColorPurple));
        // Row 14
        table3.addCell("12. Is the radiant warmer in direct access from all labor tables?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col15, font, 1));
        // Row 15
        table3.addCell("13. Does the radiant warmer has free space on three sides?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col21, font, 1));
        // Row 11
        table3.addCell("14. Do the labor tables have adequate space between them (preferably 6 feet)?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col12, font, 1));
        // Row 13
        table3.addCell("15. Does each labor table have a clean McIntosh or disposable draw sheet?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col14, font, 1));
        // Row 18
        table3.addCell("16. Does the labor room have an access to a dirty utility area?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col18, font, 1));

        event.setRowCount(tableHead3.getRows().size());
        event.setRowCount(table3.getRows().size());
        document.add(tableHead3);
        document.add(table3);
        tableHead3 = getPdfTable(event, 1);
        table3 = getPdfTableForColumns(event, 2);

        tableHead3.addCell(getPdfCell("Availability of Important resource", font, baseColorPurple));
        // Row 19
        table3.addCell("17. Is there a functional hand washing station (with running water and soap) in the labor room?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col19, font, 1));
        // Row 20
        table3.addCell("18. Does the hand washing station have an elbow operated tap?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col20, font, 1));
        // Row 9
        table3.addCell("19. Does the labor room have adequate power back up?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col10, font, 1));
        // Row 10
        table3.addCell("20. Is the number of labor tables as per delivery load of facility?");
        table3.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table3_col11, font, 1));
        // Row 21
        table3.addCell(getPdfCell("No. of LR environment related VCs achieved (out of 20)", font, baseColorGrey));
        table3.addCell(getPdfCellAndCheckValidation("", font, 10));

        event.setRowCount(tableHead3.getRows().size());
        event.setRowCount(table3.getRows().size());
        document.add(tableHead3);
        document.add(table3);

        // First page - 4th Table
        // Header Row Values
        PdfPTable tableHead4 = new PdfPTable(1);
        tableHead4.setTotalWidth(500);
        tableHead4.setLockedWidth(true);
        tableHead4.setTableEvent(event);
        tableHead4.setWidthPercentage(100);
        tableHead4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead4.setSplitLate(false);

        // 2 Column - Row Values
        PdfPTable table4 = new PdfPTable(2);
        table4.setTotalWidth(500);
        table4.setLockedWidth(true);
        table4.setTableEvent(event);
        table4.setWidthPercentage(100);
        table4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table4.setSplitLate(false);
        PdfPCell cellHeader4 = new PdfPCell(new Phrase("     IV. Details of Essential Resources in Labor Room ", font));
        cellHeader4.setBorder(Rectangle.NO_BORDER);
        cellHeader4.setBackgroundColor(baseColorSkyBlue);
        cellHeader4.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader4.setMinimumHeight(30);
        tableHead4.addCell(cellHeader4);

        // Row Headers
        table4.addCell(getPdfCell("Resources", font, baseColorGrey));
        table4.addCell(getPdfCell("Response", font, baseColorGrey));
        // Row 1
        table4.addCell("1. Inj. Magnesium sulfate (at least 20 ampoules)");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col1, font, 2));
        // Row 2
        table4.addCell("2. Antibiotics for mother (As per GoI recommendation)");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col2, font, 2));
        // Row 3
        table4.addCell("3. Antibiotics for baby (As per GoI recommendation)");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col3, font, 2));
        // Row 4
        table4.addCell("4. Inj. Oxytocin");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col4, font, 2));
        // Row 5
        table4.addCell("5. Inj. Vitamin K1");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col5, font, 2));
        // Row 6
        table4.addCell("6. IV fluids");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col6, font, 2));
        // Row 7
        table4.addCell("7. Antiretroviral drugs for mother");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col7, font, 2));
        // Row 8
        table4.addCell("8. Soap and running water");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col8, font, 2));
        // Row 9
        table4.addCell("9. Gloves");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col9, font, 2));
        // Row 10
        table4.addCell("10. Uristix (for urine protein and sugar)");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col10, font, 2));
        // Row 11
        table4.addCell("11. Case Sheet");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col11, font, 2));
        // Row 12
        table4.addCell("12. Cord clamps");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col12, font, 2));
        // Row 13
        table4.addCell("13. Sterile scissors");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col13, font, 2));
        // Row 14
        table4.addCell("14. Sterile perineal pads");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col14, font, 2));
        // Row 15
        table4.addCell("15. Towels for receiving newborn");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col15, font, 2));
        // Row 16
        table4.addCell("16. Disposable syringes and needles");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col16, font, 2));
        // Row 17
        table4.addCell("17. IV sets");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col17, font, 2));
        // Row 18
        table4.addCell("18. Antenatal corticosteroids (Inj. Dexamethasone)");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col18, font, 2));
        // Row 19
        table4.addCell("19. Ambu bag for babies for both term and preterm masks (size 1 and 0)");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col19, font, 2));
        // Row 20
        table4.addCell("20. BP apparatus");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col20, font, 2));
        // Row 21
        table4.addCell("21. Stethoscope");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col21, font, 2));
        // Row 22
        table4.addCell("22. Thermometer");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col22, font, 2));
        // Row 23
        table4.addCell("23. Mucous extractors");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col23, font, 2));
        // Row 24
        table4.addCell("24. Suction device");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col24, font, 2));
        // Row 25
        table4.addCell("25. Functional radiant warmer");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col25, font, 2));
        // Row 26
        table4.addCell("26. Protocol posters displayed");
        table4.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table4_col26, font, 2));
        // Row 27
        table4.addCell(getPdfCell("No. of essential Items available in LR(Out of 26 items)", font, baseColorGrey));
        table4.addCell(getPdfCellAndCheckValidation("", font, 9));

        event.setRowCount(tableHead4.getRows().size());
        event.setRowCount(table4.getRows().size());
        document.add(tableHead4);
        document.add(table4);

        // First page - 5th Table
        // Header Row Values
        PdfPTable tableHead5 = new PdfPTable(1);
        tableHead5.setTotalWidth(500);
        tableHead5.setLockedWidth(true);
        tableHead5.setTableEvent(event);
        tableHead5.setWidthPercentage(100);
        tableHead5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead5.setSplitLate(false);

        // 2 Column - Row Values
        PdfPTable table5 = new PdfPTable(2);
        table5.setTotalWidth(500);
        table5.setLockedWidth(true);
        table5.setTableEvent(event);
        table5.setWidthPercentage(100);
        table5.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table5.setSplitLate(false);
        PdfPCell cellHeader5 = new PdfPCell(new Phrase("     V. Details of Adherence to 19 Clinical Practices ", font));
        cellHeader5.setBorder(Rectangle.NO_BORDER);
        cellHeader5.setBackgroundColor(baseColorSkyBlue);
        cellHeader5.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader5.setMinimumHeight(30);
        tableHead5.addCell(cellHeader5);

        // Row Headers
        table5.addCell(getPdfCell("Standards", font, baseColorGrey));
        table5.addCell(getPdfCell("Response", font, baseColorGrey));

        event.setRowCount(tableHead5.getRows().size());
        event.setRowCount(table5.getRows().size());
        document.add(tableHead5);
        document.add(table5);
        tableHead5 = getPdfTable(event, 1);
        table5 = getPdfTableForColumns(event, 2);

        tableHead5.addCell(getPdfCell("At the time of admission", font, baseColorPurple));
        // Row 1
        table5.addCell("1. Provider conducts an appropriate and adequate assessment of clinical condition of pregnant woman and fetus at the time of admission");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col1, font, 1));
        // Row 2
        table5.addCell("2. Provider conducts internal examination appropriately");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col2, font, 1));
        // Row 3
        table5.addCell("3. Provider identifies and manages HIV in pregnant woman and newborn");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col3, font, 1));
        // Row 4
        table5.addCell("4. Provider identifies and manages infection in pregnant woman");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col4, font, 1));
        // Row 5
        table5.addCell("5. Provider identifies conditions leading to preterm delivery and facilitates preventive care");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col5, font, 1));
        // Row 6
        table5.addCell("6. Provider identifies and manages severe Pre-eclampsia/Eclampsia (PE/E)");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col6, font, 1));
        // Row 7
        table5.addCell("7. Provider monitors progress of labour in every case and adjusts care accordingly correctly fills partograph to monitor progress of labour and timely interprets results for decision");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col7, font, 1));

        event.setRowCount(tableHead5.getRows().size());
        event.setRowCount(table5.getRows().size());
        document.add(tableHead5);
        document.add(table5);
        tableHead5 = getPdfTable(event, 1);
        table5 = getPdfTableForColumns(event, 2);

        tableHead5.addCell(getPdfCell("Immediate after delivery", font, baseColorPurple));
        // Row 8
        table5.addCell("8. Provider ensures respectful and supportive care for the woman coming for delivery");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col8, font, 1));
        // Row 9
        table5.addCell("9. Provider prepares for safe care during delivery");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col9, font, 1));
        // Row 10
        table5.addCell("10. Provider assists the woman to have a safe and clean birth");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col10, font, 1));
        // Row 11
        table5.addCell("11. Provider conducts a rapid initial assessment and performs immediate newborn care (if baby cried immediately)");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col11, font, 1));
        // Row 12
        table5.addCell("12. Provider performs newborn resuscitation if baby does not cry immediately after birth");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col12, font, 1));
        // Row 13
        table5.addCell("13. Provider performs Active Management of Third Stage of Labour (AMTSL)");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col13, font, 1));

        event.setRowCount(tableHead5.getRows().size());
        event.setRowCount(table5.getRows().size());
        document.add(tableHead5);
        document.add(table5);
        tableHead5 = getPdfTable(event, 1);
        table5 = getPdfTableForColumns(event, 2);

        tableHead5.addCell(getPdfCell("Standards Post partum period", font, baseColorPurple));
        // Row 14
        table5.addCell("14. Provider identifies and manages Postpartum Hemorrhage (PPH)");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col14, font, 1));
        // Row 15
        table5.addCell("15. Provider assesses condition of mother and baby before shifting them from labour room");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col15, font, 1));
        // Row 16
        table5.addCell("16. Provider ensures exclusive and on demand breastfeeding");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col16, font, 1));
        // Row 17
        table5.addCell("17. Provider ensures care of newborns with small size at birth");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col17, font, 1));
        // Row 18
        table5.addCell("18. Provider counsels mother and family at the time of discharge");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col18, font, 1));
        // Row 19
        table5.addCell("19. The facility adheres to universal infection prevention protocols");
        table5.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table5_col19, font, 1));
        // Row 20
        table5.addCell(getPdfCell("No. of Practice related Standards achieved(Out of 19)", font, baseColorGrey));
        table5.addCell(getPdfCellAndCheckValidation("", font, 11));

        event.setRowCount(tableHead5.getRows().size());
        event.setRowCount(table5.getRows().size());
        document.add(tableHead5);
        document.add(table5);

        // First page - 6th Table
        // Header Row Values
        PdfPTable tableHead6 = new PdfPTable(1);
        tableHead6.setTotalWidth(500);
        tableHead6.setLockedWidth(true);
        tableHead6.setTableEvent(event);
        tableHead6.setWidthPercentage(100);
        tableHead6.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead6.setSplitLate(false);

        // 2 Column - Row Values
        PdfPTable table6 = new PdfPTable(2);
        table6.setTotalWidth(500);
        table6.setLockedWidth(true);
        table6.setTableEvent(event);
        table6.setWidthPercentage(100);
        table6.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table6.setSplitLate(false);
        PdfPCell cellHeader6 = new PdfPCell(new Phrase("     VI. Details of Topics Covered During Mentoring Visit ", font));
        cellHeader6.setBorder(Rectangle.NO_BORDER);
        cellHeader6.setBackgroundColor(baseColorSkyBlue);
        cellHeader6.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader6.setMinimumHeight(30);
        tableHead6.addCell(cellHeader6);

        PdfPCell cell6 = new PdfPCell(new Phrase(""));
        cell6.setBorder(Rectangle.NO_BORDER);
        cell6.setMinimumHeight(60);

        // Row 1
        table6.addCell("1. Reorganization of labor room");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col1, font, 3));
        // Row 2
        table6.addCell("2. Infection prevention");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col2, font, 3));
        // Row 3
        table6.addCell("3. Correct estimation of gestational age");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col3, font, 3));
        // Row 4
        table6.addCell("4. Performing PV examination");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col4, font, 3));
        // Row 5
        table6.addCell("5. Appropriate use of antibiotics");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col5, font, 3));
        // Row 6
        table6.addCell("6. Use of antenatal corticosteroids for preterm labor");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col6, font, 3));
        // Row 7
        table6.addCell("7. Conducting normal delivery");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col7, font, 3));
        // Row 8
        table6.addCell("8. Active management of third stage of labor (AMTSL)");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col8, font, 3));
        // Row 9
        table6.addCell("9. Essential newborn care (ENBC)");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col9, font, 3));
        // Row 10
        table6.addCell("10. Newborn resuscitation");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col10, font, 3));
        // Row 11
        table6.addCell("11. Special care of newborns with small size at birth");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col11, font, 3));
        // Row 12
        table6.addCell("12. Identification and management of PPH");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col12, font, 3));
        // Row 13
        table6.addCell("13. Identification and management of severe pre-eclampsia and eclampsia");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col13, font, 3));
        // Row 14
        table6.addCell("14. Identification and management of prolonged and obstructed labor");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col14, font, 3));
        // Row 15
        table6.addCell("15. Plotting of partograph");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col15, font, 3));
        // Row 16
        table6.addCell("16. Drill on normal delivery, AMTSL and ENBC");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col16, font, 3));
        // Row 17
        table6.addCell("17. Drill on PPH");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col17, font, 3));
        // Row 18
        table6.addCell("18. Drill on PE/E");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col18, font, 3));
        // Row 19
        table6.addCell("19. Drill on newborn resuscitation");
        table6.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table6_col19, font, 3));

        event.setRowCount(tableHead6.getRows().size());
        event.setRowCount(table6.getRows().size());
        document.add(tableHead6);
        document.add(table6);

        // First page - 7th Table
        // Header Row Values
        PdfPTable tableHead7 = new PdfPTable(1);
        tableHead7.setTotalWidth(500);
        tableHead7.setLockedWidth(true);
        tableHead7.setTableEvent(event);
        tableHead7.setWidthPercentage(100);
        tableHead7.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead7.setSplitLate(false);

        // 2 Column - Row Values
        PdfPTable table7 = new PdfPTable(2);
        table7.setTotalWidth(500);
        table7.setLockedWidth(true);
        table7.setTableEvent(event);
        table7.setWidthPercentage(100);
        table7.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table7.setSplitLate(false);
        PdfPCell cellHeader7 = new PdfPCell(new Phrase("     VII. Details of Data Recording and Reporting ", font));
        cellHeader7.setBorder(Rectangle.NO_BORDER);
        cellHeader7.setBackgroundColor(baseColorSkyBlue);
        cellHeader7.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader7.setMinimumHeight(30);
        tableHead7.addCell(cellHeader7);

        PdfPCell cell7 = new PdfPCell(new Phrase(""));
        cell7.setBorder(Rectangle.NO_BORDER);
        cell7.setMinimumHeight(60);

        // Row 1
        table7.addCell("1. Are Case Sheets used for all cases in the labor room?");
        table7.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table7_col1, font, 3));
        // Row 2
        table7.addCell("2. Does the facility use standardised labor room register as per MNH Toolkit?");
        table7.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table7_col3, font, 3));
        // Row 3
        table7.addCell("3. Is the Labor Room MIS software functional?");
        table7.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table7_col2, font, 3));
        // Row 4
        table7.addCell("4. Does the facility have an exclusive computer Operator available for delivery records data entry?");
        table7.addCell(getPdfCellAndCheckValidation(JhpiegoHistory.table7_col4, font, 3));

        event.setRowCount(tableHead7.getRows().size());
        event.setRowCount(table7.getRows().size());
        document.add(tableHead7);
        document.add(table7);

        // First page - 8th Table
        // Header Row Values
        PdfPTable tableHead8 = new PdfPTable(2);
        tableHead8.setTotalWidth(500);
        tableHead8.setLockedWidth(true);
        tableHead8.setTableEvent(event);
        tableHead8.setWidthPercentage(100);
        tableHead8.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead8.setSplitLate(false);

        PdfPCell cellHeader81 = new PdfPCell(new Phrase("     VIII. Date of Next Visit Planned ", font));
        cellHeader81.setBorder(Rectangle.NO_BORDER);
        cellHeader81.setBackgroundColor(baseColorSkyBlue);
        cellHeader81.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader81.setMinimumHeight(30);
        tableHead8.addCell(cellHeader81);

        PdfPCell cellHeader82 = new PdfPCell(new Phrase(JhpiegoHistory.table8_col1, font));
        cellHeader82.setBorder(Rectangle.NO_BORDER);
        cellHeader82.setBackgroundColor(baseColorSkyBlue);
        cellHeader82.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader82.setMinimumHeight(30);
        tableHead8.addCell(cellHeader82);

        event.setRowCount(tableHead8.getRows().size());
        document.add(tableHead8);

        // First page - 9th Table
        // Header Row Values
        PdfPTable tableHead9 = new PdfPTable(2);
        tableHead9.setTotalWidth(500);
        tableHead9.setLockedWidth(true);
        tableHead9.setTableEvent(event);
        tableHead9.setWidthPercentage(100);
        tableHead9.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead9.setSplitLate(false);

        PdfPCell cellHeader91 = new PdfPCell(new Phrase("     IX. Any Additional Comments or Remarks ", font));
        cellHeader91.setBorder(Rectangle.NO_BORDER);
        cellHeader91.setBackgroundColor(baseColorSkyBlue);
        cellHeader91.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader91.setMinimumHeight(30);
        tableHead9.addCell(cellHeader91);

        PdfPCell cellHeader92 = new PdfPCell(new Phrase(JhpiegoHistory.table9_col1, font));
        cellHeader92.setBorder(Rectangle.NO_BORDER);
        cellHeader92.setBackgroundColor(baseColorSkyBlue);
        cellHeader92.setVerticalAlignment(Element.ALIGN_LEFT);
        cellHeader92.setMinimumHeight(30);
        tableHead9.addCell(cellHeader92);

        event.setRowCount(tableHead9.getRows().size());
        document.add(tableHead9);

        // Footer Acknowledgment Receipt Row Values
        PdfPTable tableAcknowledgmentReceipt = new PdfPTable(1);
        tableAcknowledgmentReceipt.setTotalWidth(500);
        tableAcknowledgmentReceipt.setLockedWidth(true);
        tableAcknowledgmentReceipt.setTableEvent(event);
        tableAcknowledgmentReceipt.setWidthPercentage(100);
        tableAcknowledgmentReceipt.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableAcknowledgmentReceipt.setSplitLate(false);
        PdfPCell cellAcknowledgmentReceipt = new PdfPCell(new Phrase("\nAcknowledgment Receipt\n\n\n\n\nI " + JhpiegoHistory.mentorName +
                " (Dakshata Mentor) has visited " + JhpiegoHistory.table1_col5 + ", " + JhpiegoHistory.table1_col6 +
                "\n\non dated " + JhpiegoHistory.createdDate + ", kindly submit my report." +
                " \n\n\n ", font));
        cellAcknowledgmentReceipt.setBorder(Rectangle.NO_BORDER);
        cellAcknowledgmentReceipt.setBackgroundColor(baseColorGrey);
        cellAcknowledgmentReceipt.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellAcknowledgmentReceipt.setMinimumHeight(50);
        tableAcknowledgmentReceipt.addCell(cellAcknowledgmentReceipt);
        event.setRowCount(tableAcknowledgmentReceipt.getRows().size());
        document.add(tableAcknowledgmentReceipt);

        // Row 1
        PdfPTable tableAcknowledgement = new PdfPTable(2);
//        tableAcknowledgement.addCell();
//        tableAcknowledgement.addCell();
        tableAcknowledgement.setTotalWidth(500);
        tableAcknowledgement.setLockedWidth(true);
        tableAcknowledgement.setTableEvent(event);
        tableAcknowledgement.setWidthPercentage(100);
        tableAcknowledgement.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableAcknowledgement.setSplitLate(false);
        PdfPCell cellAcknowledgment = new PdfPCell(new Phrase(context.getResources().getString(R.string.app_name) + "\n\n\n" +
                "Name:     " + JhpiegoHistory.table1_col1 + "\n\n" +
                "Mobile No.:      " + JhpiegoHistory.username + "\n\n" +
                "Date:     " + JhpiegoHistory.createdDate + "\n\n", font));
        cellAcknowledgment.setBorder(Rectangle.NO_BORDER);
        cellAcknowledgment.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAcknowledgment.setMinimumHeight(50);
        tableAcknowledgement.addCell(cellAcknowledgment);

        cellAcknowledgment = new PdfPCell(new Phrase("District RCH Officer" + "\n\n\n" +
                "RCHO Name:    " + "\n\n" +
                "District:    " /*+ JhpiegoHistory.table1_col3*/ + "\n\n" +
                "Date:     " + "\n\n", font));
        cellAcknowledgment.setBorder(Rectangle.NO_BORDER);
        cellAcknowledgment.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAcknowledgment.setMinimumHeight(50);
        tableAcknowledgement.addCell(cellAcknowledgment);
        event.setRowCount(tableAcknowledgement.getRows().size());
        document.add(tableAcknowledgement);

        // Row 1
        PdfPTable tableAcknowledgementSign = new PdfPTable(2);
//        tableAcknowledgement.addCell();
//        tableAcknowledgement.addCell();
        tableAcknowledgementSign.setTotalWidth(500);
        tableAcknowledgementSign.setLockedWidth(true);
        tableAcknowledgementSign.setTableEvent(event);
        tableAcknowledgementSign.setWidthPercentage(100);
        tableAcknowledgementSign.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableAcknowledgementSign.setSplitLate(false);
        PdfPCell cellAcknowledgmentSign = new PdfPCell(new Phrase("Mentor Signature:" + "\n\n\n" +
                " " + "\n\n" +
                " " + "\n\n\n", font));
        cellAcknowledgmentSign.setBorder(Rectangle.NO_BORDER);
        cellAcknowledgmentSign.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAcknowledgmentSign.setMinimumHeight(40);
        tableAcknowledgementSign.addCell(cellAcknowledgmentSign);

        cellAcknowledgmentSign = new PdfPCell(new Phrase("RCHO Signature & Stamp:" + "\n\n\n" +
                " " + "\n\n" +
                " " + "\n\n\n", font));
        cellAcknowledgmentSign.setBorder(Rectangle.NO_BORDER);
        cellAcknowledgmentSign.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAcknowledgmentSign.setMinimumHeight(40);
        tableAcknowledgementSign.addCell(cellAcknowledgmentSign);
        event.setRowCount(tableAcknowledgementSign.getRows().size());
        document.add(tableAcknowledgementSign);

        // Header Row Values
        PdfPTable tableFooter = new PdfPTable(1);
        tableFooter.setTotalWidth(500);
        tableFooter.setLockedWidth(true);
        tableFooter.setTableEvent(event);
        tableFooter.setWidthPercentage(100);
        tableFooter.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableFooter.setSplitLate(false);
        PdfPCell cellFooter = new PdfPCell(new Phrase("\n\nRecord Status : Synced (Synced with Server)\n\n\n* Data has been entered by Dakshata Mentor Android application\n\n(App Version : " + BuildConfig.VERSION_NAME + ")\n\n\n ", font));
        cellFooter.setBorder(Rectangle.NO_BORDER);
        cellFooter.setBackgroundColor(baseColorGreen);
        cellFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellFooter.setMinimumHeight(30);
        tableFooter.addCell(cellFooter);
        event.setRowCount(tableFooter.getRows().size());
        document.add(tableFooter);

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
                if (JhpiegoHistory.table2_col1.equalsIgnoreCase("") || JhpiegoHistory.table2_col1.equalsIgnoreCase("null"))
                    table2_col1 = "0";
                else table2_col1 = JhpiegoHistory.table2_col1;
                if (JhpiegoHistory.table2_col3.equalsIgnoreCase("") || JhpiegoHistory.table2_col3.equalsIgnoreCase("null"))
                    table2_col3 = "0";
                else table2_col3 = JhpiegoHistory.table2_col3;
                if (JhpiegoHistory.table2_col5.equalsIgnoreCase("") || JhpiegoHistory.table2_col5.equalsIgnoreCase("null"))
                    table2_col5 = "0";
                else table2_col5 = JhpiegoHistory.table2_col5;
                if (JhpiegoHistory.table2_col7.equalsIgnoreCase("") || JhpiegoHistory.table2_col7.equalsIgnoreCase("null"))
                    table2_col7 = "0";
                else table2_col7 = JhpiegoHistory.table2_col7;
                if (JhpiegoHistory.table2_col9.equalsIgnoreCase("") || JhpiegoHistory.table2_col9.equalsIgnoreCase("null"))
                    table2_col9 = "0";
                else table2_col9 = JhpiegoHistory.table2_col9;
                total1 = Integer.parseInt(table2_col1) + Integer.parseInt(table2_col3) + Integer.parseInt(table2_col5) +
                        Integer.parseInt(table2_col7) + Integer.parseInt(table2_col9);
                title1 = String.valueOf(total1);
                break;
            case 7:
                String table2_col2, table2_col4, table2_col6, table2_col8, table2_col10;
                if (JhpiegoHistory.table2_col2.equalsIgnoreCase("") || JhpiegoHistory.table2_col2.equalsIgnoreCase("null"))
                    table2_col2 = "0";
                else table2_col2 = JhpiegoHistory.table2_col2;
                if (JhpiegoHistory.table2_col4.equalsIgnoreCase("") || JhpiegoHistory.table2_col4.equalsIgnoreCase("null"))
                    table2_col4 = "0";
                else table2_col4 = JhpiegoHistory.table2_col4;
                if (JhpiegoHistory.table2_col6.equalsIgnoreCase("") || JhpiegoHistory.table2_col6.equalsIgnoreCase("null"))
                    table2_col6 = "0";
                else table2_col6 = JhpiegoHistory.table2_col6;
                if (JhpiegoHistory.table2_col8.equalsIgnoreCase("") || JhpiegoHistory.table2_col8.equalsIgnoreCase("null"))
                    table2_col8 = "0";
                else table2_col8 = JhpiegoHistory.table2_col8;
                if (JhpiegoHistory.table2_col10.equalsIgnoreCase("") || JhpiegoHistory.table2_col10.equalsIgnoreCase("null"))
                    table2_col10 = "0";
                else table2_col10 = JhpiegoHistory.table2_col10;
                total2 = Integer.parseInt(table2_col2) + Integer.parseInt(table2_col4) + Integer.parseInt(table2_col6) +
                        Integer.parseInt(table2_col8) + Integer.parseInt(table2_col10);
                title1 = String.valueOf(total2);
                break;
            case 9:
                int total26 = 0;
                try {
                    if (JhpiegoHistory.table4_col1.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col2.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col3.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col4.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col5.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col6.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col7.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col8.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col9.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col10.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col11.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col12.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col13.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col14.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col15.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col16.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col17.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col18.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col19.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col20.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col21.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col22.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col23.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col24.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col25.equalsIgnoreCase("Available")) total26++;
                    if (JhpiegoHistory.table4_col26.equalsIgnoreCase("Available")) total26++;
                    value26 = (total26 * 100) / 26;
                    title1 = total26 + "(" + String.format("%.2f", value26) + "%)";
                } catch (Exception e) {
                    title1 = "-";
                }
                break;
            case 10:
                int total20 = 0;
                try {
                    if (JhpiegoHistory.table3_col2.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col3.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col4.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col5.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col6.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col7.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col8.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col9.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col10.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col11.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col12.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col13.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col14.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col15.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col16.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col17.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col18.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col19.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col20.equalsIgnoreCase("Yes")) total20++;
                    if (JhpiegoHistory.table3_col21.equalsIgnoreCase("Yes")) total20++;
                    value20 = (total20 * 100) / 20;
                    title1 = total20 + "(" + String.format("%.2f", value20) + "%)";
                } catch (Exception e) {
                    title1 = "-";
                }
                break;
            case 11:
                int total19 = 0;
                try {
                    if (JhpiegoHistory.table5_col1.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col2.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col3.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col4.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col5.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col6.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col7.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col8.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col9.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col10.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col11.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col12.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col13.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col14.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col15.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col16.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col17.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col18.equalsIgnoreCase("Yes")) total19++;
                    if (JhpiegoHistory.table5_col19.equalsIgnoreCase("Yes")) total19++;
                    value19 = (total19 * 100) / 19;
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
