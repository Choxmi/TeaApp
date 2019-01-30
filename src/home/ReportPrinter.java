package home;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportPrinter {

    public void print_month_report(String file){
        FileInputStream psStream = null;
        try {
            psStream = new FileInputStream(file);
        } catch (FileNotFoundException ffne) {
            ffne.printStackTrace();
        }
        if (psStream == null) {
            return;
        }
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);

        // this step is necessary because I have several printers configured
        PrintService myPrinter = null;
        for (int i = 0; i < services.length; i++) {
            String svcName = services[i].toString();
            System.out.println("service found: " + svcName);
            if (svcName.contains("printer closest to me")) {
                myPrinter = services[i];
                System.out.println("my printer found: " + svcName);
                break;
            }
        }
        if (myPrinter != null) {
            DocPrintJob job = myPrinter.createPrintJob();
            try {
                job.print(myDoc, aset);

            } catch (Exception pe) {
                pe.printStackTrace();
            }
        } else {
            System.out.println("no printer services found");
        }
    }

    private static final long serialVersionUID = 1L;

    public void printJasper() {

        String reportSrcFile = "E:\\EDU\\JavFX\\SampleProj\\RestaurantMgtSampleUI\\src\\data\\report.jrxml";

        // First, compile jrxml file.
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        }catch (Exception e){
            System.out.println("Error is: "+e.getCause());
        }
        // Fields for report
        HashMap<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("company", "MAROTHIA TECHS");
        parameters.put("receipt_no", "RE101".toString());
        parameters.put("name", "Khushboo");
        parameters.put("amount", "10000");
        parameters.put("receipt_for", "EMI Payment");
        parameters.put("date", "20-12-2016");
        parameters.put("contact", "98763178".toString());

        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        }catch (Exception e){
            System.out.println("Error 2 is : "+e.getCause());
        }
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);

        JFrame frame = new JFrame();
        frame.add(viewer);
        frame.setSize(700, 500);
        frame.setVisible(true);

        System.out.print("Done!");

    }

}
