/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.upc.prop.domini.Alfabet;
import edu.upc.prop.domini.TST;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author arau
 */
public class General {

    public General() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void provaDeResultats() {
        
         Alfabet alfabet = Alfabet.getInstance();
        
        ArrayList llista = new ArrayList();
        
        llista.add('C');
        llista.add('G');
        llista.add('T');               
        alfabet.setLlistaCaracters(llista);
        
        List<String> seq = new ArrayList<String>();
        seq.add("ACCGT");
        seq.add("ATGGT");

        int numSequencies = seq.size();

        TST tst = new TST(numSequencies);

        //String sequencia = "ACCGT";

        int indexSeq = 0;
        String suffix;
        for (String str : seq) {
            int i = 0;
            while (i < str.length()) {
                suffix = str.substring(i);

                tst.inserta(suffix+alfabet.getFiSequencia(), indexSeq);
                i++;
            }
            indexSeq++;
        }
        ArrayList<String> expect = new ArrayList<String>();
        expect.add("ACCGT");
        expect.add("ATGGT");
        expect.add("CCGT");
        expect.add("CGT");
        expect.add("GT");
        expect.add("GGT");
        expect.add("T");


        ArrayList<String> consulta = new ArrayList();
        consulta.addAll(tst.consulta("*"));
        for (int i = 0; i < consulta.size(); i++) { 
            assertEquals("Prova general", consulta.get(i), expect.get(i));            
        }

        for (String s : tst.consulta("*")) {
            System.out.println(s);
        }

    }
    
}
