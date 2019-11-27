package com.dakshata.mentor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.ClientFeedbackDto;
import com.dakshata.mentor.mydatecalendar.AppConstants;
import com.dakshata.mentor.mydatecalendar.GlobalMethods;
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
import java.util.Calendar;
import java.util.Date;

import static com.dakshata.mentor.JhpiegoDatabase.COL_EMAIL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MOBILE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;


/**
 * Created by Umesh on 2/13/2018.
 */
public class ClientFeedbackPdf {

    Context context;
    String fileName;
    BaseColor baseColorSkyBlue, baseColorGrey, baseColorPeach, baseColorGreen, baseColorPurple,baseColorWhite;
    int total1, total2;
    ArrayList<ClientFeedbackDto> clientFeedbackDtoArrayList;
    JhpiegoDatabase mydb;


    public ClientFeedbackPdf(Context _ctx, String _fileName, String address, ArrayList<ClientFeedbackDto> clientFeedbackDtoArrayList) {
        this.context = _ctx;
        this.fileName = _fileName;
        this.clientFeedbackDtoArrayList=clientFeedbackDtoArrayList;
        mydb = new JhpiegoDatabase(context);
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
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ClientFeedBackForm.userEmail});
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
        PdfPTable table = new PdfPTable(8);
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
        baseColorWhite = new BaseColor(255, 255, 255);

        Font fontNormal = new Font();
        fontNormal.setStyle(Font.NORMAL);

        // First page - 1st Table

        PdfPCell cell = new PdfPCell(new Phrase(""));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setMinimumHeight(40);

        PdfPTable tableHead0 = new PdfPTable(8);
        tableHead0.setTotalWidth(500);
        tableHead0.setLockedWidth(true);
        tableHead0.setTableEvent(event);
        tableHead0.setWidthPercentage(100);
        tableHead0.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead0.setSplitLate(false);

        // Row 1
        PdfPCell cellHeader1 = new PdfPCell(new Phrase("District", font));
        cellHeader1.setBorder(Rectangle.NO_BORDER);
        cellHeader1.setBackgroundColor(baseColorPeach);
        cellHeader1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader1.setVerticalAlignment(Element.ALIGN_CENTER);
        cellHeader1.setMinimumHeight(40);
        cellHeader1.setColspan(2);
        tableHead0.addCell(cellHeader1);


String facilityName="";
String facilityType="";
String dateOfFeedback="";
String[] data=mydb.getDataOfVisit();
if(data!=null && data.length>0){
    facilityType=data[1];
    facilityName=data[2];
}

        cellHeader1 = getPdfCell(mydb.getDistrictNameForMentor(), fontNormal, baseColorPeach,40);
        cellHeader1.setColspan(1);
        tableHead0.addCell(cellHeader1);

        cellHeader1 = getPdfCell("Name of\nFacility", font, baseColorPeach,40);
        cellHeader1.setColspan(1);
        tableHead0.addCell(cellHeader1);

        cellHeader1 = getPdfCell(facilityName, fontNormal, baseColorPeach,40);
        cellHeader1.setColspan(1);
        tableHead0.addCell(cellHeader1);

        cellHeader1 = getPdfCell("Type of \nFacility", font, baseColorPeach,40);
        cellHeader1.setColspan(1);
        tableHead0.addCell(cellHeader1);

        cellHeader1 = getPdfCell(facilityType, fontNormal, baseColorPeach,40);
        cellHeader1.setColspan(2);
        tableHead0.addCell(cellHeader1);

        //Row 2
//        PdfPCell cell = new PdfPCell(new Phrase(""));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setMinimumHeight(60);

        PdfPTable tableHead01 = new PdfPTable(8);
        tableHead01.setTotalWidth(500);
        tableHead01.setLockedWidth(true);
        tableHead01.setTableEvent(event);
        tableHead01.setWidthPercentage(100);
        tableHead01.setSpacingAfter(30f);
        tableHead01.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableHead01.setSplitLate(false);


        // Row 1
        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
             dateOfFeedback= clientFeedbackDtoArrayList.get(0).getDateOfFeedback();
            }

        PdfPCell cellHeader01 = new PdfPCell(new Phrase("Date Of\nFeedback", font));
        cellHeader01.setBorder(Rectangle.NO_BORDER);
        cellHeader01.setBackgroundColor(baseColorPeach);
        cellHeader01.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader01.setVerticalAlignment(Element.ALIGN_CENTER);
        cellHeader01.setMinimumHeight(40);
        cellHeader01.setColspan(2);
        tableHead01.addCell(cellHeader01);

        cellHeader01 = getPdfCell(dateOfFeedback, fontNormal, baseColorPeach,40);
        cellHeader01.setColspan(2);
        tableHead01.addCell(cellHeader01);

        cellHeader01 = getPdfCell("Name of\nMentor", font, baseColorPeach,40);
        cellHeader01.setColspan(2);
        tableHead01.addCell(cellHeader01);
        String mentorName="";
        SharedPreferences sh_Pref = context.getSharedPreferences("Login Credentials", context.MODE_PRIVATE);
        String username = sh_Pref.getString("Username", "Unknown");
        SQLiteDatabase sqLiteDatabase = mydb.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_EMAIL, COL_MOBILE, COL_STATE, COL_NAME}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mentorName = cursor.getString(cursor.getColumnIndex(COL_NAME));
//            userEmail = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
        }
        cursor.close();

        cellHeader01 = getPdfCell(mentorName, fontNormal, baseColorPeach,40);
        cellHeader01.setColspan(2);
        tableHead01.addCell(cellHeader01);

        event.setRowCount(tableHead0.getRows().size());
        event.setRowCount(tableHead01.getRows().size());
        document.add(tableHead0);
        document.add(tableHead01);

        // First page - 2nd Table
        // Header Row Values
        PdfPCell cell2 = new PdfPCell(new Phrase(""));
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setMinimumHeight(40);

        PdfPTable tableHead2 = new PdfPTable(13);
        tableHead2.setTotalWidth(500);
        tableHead2.setLockedWidth(true);
        tableHead2.setTableEvent(event);
        tableHead2.setWidthPercentage(100);
        tableHead0.setSpacingBefore(20f);
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

        PdfPCell cellHeader2 = new PdfPCell(new Phrase(" Sl.\n No.", font));
        cellHeader2.setBorder(Rectangle.NO_BORDER);
        cellHeader2.setBackgroundColor(baseColorSkyBlue);
        cellHeader2.setVerticalAlignment(Element.ALIGN_CENTER);
        cellHeader2.setMinimumHeight(40);
        cellHeader2.setColspan(1);
        tableHead2.addCell(cellHeader2);

        String C1_pcts="PCTS ID";
        String C2_pcts="PCTS ID";
        String C3_pcts="PCTS ID";
        String C1_ipd="\nIPD";
        String C2_ipd="\nIPD";
        String C3_ipd="\nIPD";

        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    C1_pcts=clientFeedbackDtoArrayList.get(0).getClient_pcts_id();
                    C1_ipd=clientFeedbackDtoArrayList.get(0).getClient_ipd_no();
                    break;
                case 2:
                    C1_pcts=clientFeedbackDtoArrayList.get(0).getClient_pcts_id();
                    C1_ipd=clientFeedbackDtoArrayList.get(0).getClient_ipd_no();
                    C2_pcts=clientFeedbackDtoArrayList.get(1).getClient_pcts_id();
                    C2_ipd=clientFeedbackDtoArrayList.get(1).getClient_ipd_no();
                    break;
                case 3:
                    C1_pcts=clientFeedbackDtoArrayList.get(0).getClient_pcts_id();
                    C1_ipd=clientFeedbackDtoArrayList.get(0).getClient_ipd_no();
                    C2_pcts=clientFeedbackDtoArrayList.get(1).getClient_pcts_id();
                    C2_ipd=clientFeedbackDtoArrayList.get(1).getClient_ipd_no();
                    C3_pcts=clientFeedbackDtoArrayList.get(2).getClient_pcts_id();
                    C3_ipd=clientFeedbackDtoArrayList.get(2).getClient_ipd_no();

                    break;
            }
        }

        cellHeader2 = getPdfCell("Feedback Questions", font, baseColorSkyBlue,40);
        cellHeader2.setColspan(4);
        tableHead2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Client 1\n"+C1_pcts+"/"+C1_ipd, font, baseColorSkyBlue,40);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Client 2\n"+C2_pcts+"/"+C2_ipd, font, baseColorSkyBlue,40);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Client 3\n"+C3_pcts+"/"+C3_ipd, font, baseColorSkyBlue,40);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Overall\n%", font, baseColorSkyBlue,40);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        tableHead2.addCell(cellHeader2);


        // Row 1
        cellHeader2 = getPdfCell("1", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("How did you reach the hospital for delivery?", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);

        String responseC1="",responseC2="",responseC3="";
        String percentage="0%";
        int filledFor=0;
        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(0).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(0).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(0).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(0).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(0).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(0).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
            percentage=""+(100/3)*filledFor+"%";
        }

        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 2
        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(1).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(1).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(1).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(1).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(1).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(1).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
                percentage=""+(100/3)*filledFor+"%";
        }

        cellHeader2 = getPdfCell("2", fontNormal, baseColorGrey,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("How is the cleanliness at the hospital?", fontNormal, baseColorGrey,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);


        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

// Row 3

        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(2).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(2).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(2).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(2).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(2).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(2).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
                percentage=""+(100/3)*filledFor+"%";
        }
        cellHeader2 = getPdfCell("3", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Did a relative/companion accompany you during delivery in the labor room?", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 4

        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(3).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(3).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(3).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(3).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(3).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(3).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
                percentage=""+(100/3)*filledFor+"%";
        }
        cellHeader2 = getPdfCell("4", fontNormal, baseColorGrey,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Was your BP measured at the time of admission?", fontNormal, baseColorGrey,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);


        // Row 5

        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(4).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(4).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(4).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(4).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(4).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(4).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
                percentage=""+(100/3)*filledFor+"%";
        }
        cellHeader2 = getPdfCell("5", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("Was the baby breastfeed within 1 hour of delivery?", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 6
        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(5).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(5).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(5).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(5).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(5).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(5).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
                percentage=""+(100/3)*filledFor+"%";
        }
        cellHeader2 = getPdfCell("6", fontNormal, baseColorGrey,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("After delivery, did someone inform you about danger signs in mother and baby?", fontNormal, baseColorGrey,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorGrey,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        // Row 7
        if(clientFeedbackDtoArrayList!=null && clientFeedbackDtoArrayList.size()>0){
            switch (clientFeedbackDtoArrayList.size()){
                case 1:
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(6).getqAnswer();
                    filledFor=1;
                    break;
                case 2:
                    filledFor=2;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(6).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(6).getqAnswer();
                    break;
                case 3:
                    filledFor=3;
                    responseC1=clientFeedbackDtoArrayList.get(0).getQuestionList().get(6).getqAnswer();
                    responseC2=clientFeedbackDtoArrayList.get(1).getQuestionList().get(6).getqAnswer();
                    responseC3=clientFeedbackDtoArrayList.get(2).getQuestionList().get(6).getqAnswer();
                    break;
            }
        }
        if(filledFor!=0){
            if(filledFor==3){
                percentage="100%";
            }else
                percentage=""+(100/3)*filledFor+"%";
        }
        cellHeader2 = getPdfCell("7", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(1);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell("After delivery, did a health worker measure your and baby’s temperature?", fontNormal, baseColorWhite,50);
        cellHeader2.setColspan(4);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC1, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC2, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(responseC3, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        cellHeader2 = getPdfCell(percentage, fontNormal, baseColorWhite,50);
        cellHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader2.setColspan(2);
        table2.addCell(cellHeader2);

        event.setRowCount(tableHead2.getRows().size());
        event.setRowCount(table2.getRows().size());
        document.add(tableHead2);
        document.add(table2);


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

    private PdfPCell getPdfCell(String title, Font font, BaseColor baseColorSkyBlue,int minHeight) {
        PdfPCell cellHeader3 = new PdfPCell(new Phrase(title, font));
        cellHeader3.setBorder(Rectangle.NO_BORDER);
        cellHeader3.setBackgroundColor(baseColorSkyBlue);
        cellHeader3.setVerticalAlignment(Element.ALIGN_CENTER);
        cellHeader3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellHeader3.setMinimumHeight(minHeight);
        return cellHeader3;
    }

}
