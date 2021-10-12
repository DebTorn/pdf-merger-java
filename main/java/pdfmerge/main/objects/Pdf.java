
package pdfmerge.main.objects;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.options.convert.PdfConvertOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class Pdf {
    
    private ArrayList<String> paths;
    
    public Pdf(){
        paths = new ArrayList<>();
    }
    
    public void addPath(String path){
        paths.add(path);
    }
    
    public boolean deletePath(String path){
        return paths.remove(path);
    }
    
    public ArrayList<String> getPaths(){
        return paths;
    }
    
    public boolean Merge(){
        try{
            SimpleDateFormat sf = new SimpleDateFormat("hms");
            Date date = new Date();
            
            PDFMergerUtility PdfMerge = new PDFMergerUtility();
            ArrayList<File> atalakitottFileok = null;
            int i = 0;
            for(String path : paths){
                if(!path.contains(".pdf")){
                    
                    if(atalakitottFileok == null){
                        atalakitottFileok = new ArrayList<>();
                    }
                    
                    try {
                        i++;
                        String fajlNeve = "atalakitott"+sf.format(date)+""+i+".pdf";
                        File atalakitott = new File(fajlNeve);
                        InputStream input = new FileInputStream(new File(path));
                        OutputStream output = new FileOutputStream(atalakitott);
                        IConverter msconverter = LocalConverter.builder().build();
                        if(path.contains(".doc")){
                            msconverter.convert(input).as(DocumentType.DOCX).to(output).as(DocumentType.PDF).execute(); 
                        }else if(path.contains(".ppt")){
                            Converter pptconverter = new Converter(path);
                            PdfConvertOptions options = new PdfConvertOptions();
                            pptconverter.convert(fajlNeve, options);
                        }
                        output.close();

                        atalakitottFileok.add(atalakitott);
                        path = fajlNeve;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        return false;
                    }   
                }
                
                PdfMerge.addSource(path);
            }
            String mFName = "osszefuzott"+sf.format(date)+".pdf";
            PdfMerge.setDestinationFileName(mFName);
            
            PdfMerge.mergeDocuments(null);
            
            if(atalakitottFileok != null && atalakitottFileok.size() > 0){
                for(File file : atalakitottFileok){
                    file.delete();
                }
            }
            
            int db = 0;
            for(File file : atalakitottFileok){
                if(!file.exists()){
                    db++;
                }
            }
            File file = new File(mFName);
            if(db == atalakitottFileok.size() && file.exists()){
                return true;
            }
            
            return false;
            
        }catch(IOException e){
            System.err.println(e);
            return false;
        }
    }
    
    public boolean deleteAllPath(){
        return paths.removeAll(paths);
    }
      
}
