package com.pucp.chanot.util;

import com.pucp.chanot.entity.Corpus;
import com.pucp.chanot.entity.Sentence;
import com.pucp.chanot.entity.Word;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchivoUtil {

    // lee el archivo txt y devuelve array con las líneas leídas 
    public static List<String> leerArchivo(String nombre, String folder, String user) {
        BufferedReader br = null;
        List<String> arlOraciones = new ArrayList<String>();
        File archivo = new File(folder + "/files/input/" +user +nombre);
        try {
            String sCurrentLine;
            br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "UTF-8"));
            while ((sCurrentLine = br.readLine()) != null) {
                arlOraciones.add(sCurrentLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return arlOraciones;
    }

    //encuentra archivo en un directorio
    public static boolean findFile(String name, File directorio) {
        File[] list = directorio.listFiles();
        if (list != null) {
            for (File fil : list) {
                if (!fil.isDirectory()) {
                    if (name.equals(fil.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // obtener extension de un archivo
    public static String getExtension(String s) {
        int pos = s.indexOf(".");
        return s.substring(pos + 1);
    }
    
    public static Corpus createCorpus( List<String>sentShipibo, List<String>sentSpanish ){
        Corpus oCorpus = new Corpus();
        oCorpus.setLastSentence(0);
        for (int i=0; i<sentShipibo.size(); ++i) {
            Sentence oSentence = new Sentence();
            oSentence.setId(i);
            oSentence.setText(sentShipibo.get(i));
            oSentence.setTranslation(sentSpanish.get(i));
            oCorpus.getSentences().add(oSentence);
            List<String> wordShipibo = Arrays.asList(sentShipibo.get(i).split(" "));
            for( int j=0; j<wordShipibo.size(); ++j){
                Word oWord = new Word();
                oWord.setId(j);
                oWord.setToken(wordShipibo.get(j));
                if( FormatoUtil.isNumber( wordShipibo.get(j) ) ){
                    oWord.setLemma( wordShipibo.get(j) );
                    oWord.setPosTag("Numeral");
                    oWord.setSubPosTag("");
                }
                else if( FormatoUtil.isPunctuation( wordShipibo.get(j) )){
                    oWord.setLemma( wordShipibo.get(j) );
                    oWord.setPosTag("Puntuación");
                    oWord.setSubPosTag("");
                }
                else if ( FormatoUtil.isSymbol( wordShipibo.get(j) ) ){
                    oWord.setLemma( wordShipibo.get(j));
                    oWord.setLemma("Símbolo");
                    oWord.setSubPosTag("");
                }
                oCorpus.getSentences().get(i).getWords().add(oWord);
            }
        }
        return oCorpus;
    }

}
