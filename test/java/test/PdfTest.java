
package test;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import pdfmerge.main.objects.Pdf;


public class PdfTest {
    
    private Pdf pdf;
    
    @Before
    public void init(){
        pdf = new Pdf();
        uploadPaths(pdf);
    }
    
    private void uploadPaths(Pdf pdf){
        pdf.addPath("test.pdf");
        pdf.addPath("test.docx");
        pdf.addPath("test.pptx");
    }
    
    @Test
    public void MergeTest(){
        assertTrue(pdf.Merge());
    }
    
}
