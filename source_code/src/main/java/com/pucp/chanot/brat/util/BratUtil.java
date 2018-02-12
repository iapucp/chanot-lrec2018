package com.pucp.chanot.brat.util;

import com.pucp.chanot.brat.entity.Palabra;
import com.pucp.chanot.brat.entity.Oracion;
import com.pucp.chanot.brat.entity.Relacion;
import com.pucp.chanot.dao.DaoJaxb;
import com.pucp.chanot.entity.Corpus;
import com.pucp.chanot.entity.Sentence;
import com.pucp.chanot.entity.Word;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jose
 */
public class BratUtil {

    static int countRel;
    static int indexPal;

    public void guardarArchivos(String archivo, String rutaArch, String salida, String limpio) {
        File file = new File(rutaArch);
        File fileClean = new File(limpio + "/" + archivo + "_clean" + ".txt");
		countRel = 1;
		indexPal = 0;
        if (!fileClean.exists()) {
            Relacion brat = procesarXML(file);
            limpiarXML(file, limpio + "/" + archivo + "_clean" + ".xml", limpio + "/log" + ".txt");
            crearArchivos(brat, archivo, salida, limpio);
        }
    }

    public void rescribirArchivos(String archivo, String rutaArch, String salida, String limpio) {
        File file = new File(rutaArch);
        File fileClean = new File(limpio + "/" + archivo + "_clean" + ".txt");
		countRel = 1;
		indexPal = 0;
        Relacion brat = procesarXML(file);
        limpiarXML(file, limpio + "/" + archivo + "_clean" + ".xml", limpio + "/log" + ".txt");
        reescribirArchivos(brat, archivo, salida, limpio);
    }

    private void crearArchivos(Relacion brat, String name, String ruta, String limpio) {
        ArrayList<Oracion> oraciones = brat.getOraciones();
        ArrayList<String> relaciones = brat.getRelaciones();
        Iterator<Oracion> nombreIterator = oraciones.iterator();
        Iterator<String> iteratorRel = relaciones.iterator();

        File file = new File(ruta + "/" + name + ".txt");
        File fileClean = new File(limpio + "/" + name + "_clean" + ".txt");
        File fileAnn = new File(ruta + "/" + name + ".ann");

        int counterP = 1;
        int suma = 0;

        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                return;
            }
            if (!fileAnn.exists()) {
                fileAnn.createNewFile();
            } else {
                return;
            }
            if (!fileClean.exists()) {
                fileClean.createNewFile();
            } else {
                return;
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta + "/" + name + ".txt"), "UTF-8"));
            BufferedWriter bwC = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(limpio + "/" + name + "_clean" + ".txt"), "UTF-8"));
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta + "/" + name + ".ann"), "UTF-8"));

            while (nombreIterator.hasNext()) {
                Oracion elemento = nombreIterator.next();
                String texto = elemento.getTexto();
                bw.write(texto);
                bw.write("\n");
                bwC.write(texto);
                bwC.write("\n");

                ArrayList<Palabra> palabras = elemento.getPalabras();
                Iterator<Palabra> iterator = palabras.iterator();
                while (iterator.hasNext()) {
                    Palabra palabra = iterator.next();
                    int ini = texto.indexOf(palabra.getToken());
                    int fin = texto.indexOf(" ", ini);
                    ini = ini + suma;
                    if (fin == -1) {
                        fin = texto.length() - 1 + ini;
                        suma = fin + 1;
                    } else {
                        texto = texto.substring(fin);
                        fin = fin + suma;
                        suma = fin;
                    }
                    if (!palabra.getCategGram().equals("")) {
                        bw2.write("T" + counterP);
                        bw2.write("\t" + abreviatura(palabra.getCategGram()));
                        bw2.write(" " + ini + " " + fin);
                        bw2.write("\t" + palabra.getToken());
                        bw2.write("\n");
                    }
                    counterP++;
                }
            }
            counterP--;

            while (iteratorRel.hasNext()) {
                String elemento = iteratorRel.next();
                bw2.write(elemento);
            }

            bw.close();
            bwC.close();
            bw2.close();
        } catch (IOException e) {

        }

    }

    private void reescribirArchivos(Relacion brat, String name, String ruta, String limpio) {
        ArrayList<Oracion> oraciones = brat.getOraciones();
        ArrayList<String> relaciones = brat.getRelaciones();
        Iterator<Oracion> nombreIterator = oraciones.iterator();
        Iterator<String> iteratorRel = relaciones.iterator();

        File file = new File(ruta + "/" + name + ".txt");
        File fileClean = new File(limpio + "/" + name + "_clean" + ".txt");
        File fileAnn = new File(ruta + "/" + name + ".ann");

        int counterP = 1;
        int suma = 0;

        try {
            file.createNewFile();
            fileAnn.createNewFile();
            fileClean.createNewFile();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta + "/" + name + ".txt"), "UTF-8"));
            BufferedWriter bwC = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(limpio + "/" + name + "_clean" + ".txt"), "UTF-8"));
            BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ruta + "/" + name + ".ann"), "UTF-8"));

            while (nombreIterator.hasNext()) {
                Oracion elemento = nombreIterator.next();
                String texto = elemento.getTexto();
                bw.write(texto);
                bw.write("\n");
                bwC.write(texto);
                bwC.write("\n");

                ArrayList<Palabra> palabras = elemento.getPalabras();
                Iterator<Palabra> iterator = palabras.iterator();
                while (iterator.hasNext()) {
                    Palabra palabra = iterator.next();
                    int ini = texto.indexOf(palabra.getToken());
                    int fin = texto.indexOf(" ", ini);
                    ini = ini + suma;
                    if (fin == -1) {
                        fin = texto.length() - 1 + ini;
                        suma = fin + 1;
                    } else {
                        texto = texto.substring(fin);
                        fin = fin + suma;
                        suma = fin;
                    }
                    if (!palabra.getCategGram().equals("")) {
                        bw2.write("T" + counterP);
                        bw2.write("\t" + abreviatura(palabra.getCategGram()));
                        bw2.write(" " + ini + " " + fin);
                        bw2.write("\t" + palabra.getToken());
                        bw2.write("\n");
                    }
                    counterP++;
                }
            }
            counterP--;

            while (iteratorRel.hasNext()) {
                String elemento = iteratorRel.next();
                bw2.write(elemento);
            }

            bw.close();
            bwC.close();
            bw2.close();
        } catch (IOException e) {

        }
    }

    private Relacion procesarXML(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Boolean correcto = true;
        ArrayList<Oracion> oraciones = null;
        ArrayList<String> relaciones = new ArrayList<String>();
        Relacion brat = new Relacion();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("sentence");

            int cantOr = nList.getLength();
            oraciones = new ArrayList<>(cantOr);
            int indRule4 = 1;

            for (int temp = 0; temp < cantOr; temp++) {
                Oracion nOracion = new Oracion();
                Node nNode = nList.item(temp);
                String morphText = "COMPLETE ";
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    nOracion.setId(Integer.parseInt(eElement.getAttribute("id")));
                    nOracion.setTexto(eElement.getAttribute("text"));
                    nOracion.setTraduccion(eElement.getAttribute("translation"));

                    NodeList palabraList = eElement.getElementsByTagName("word");
                    int cant = palabraList.getLength();
                    ArrayList<Palabra> palabras = new ArrayList<>(cant);
                    nOracion.setPalabras(palabras);
                    int countPal = 0;
                    for (int j = 0; j < cant; j++) {
                        correcto = true;
                        Palabra nPalabra = new Palabra();
                        Node palabraNode = palabraList.item(j);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element palElement = (Element) palabraNode;
                            nPalabra.setId(Integer.parseInt(palElement.getAttribute("id")));
                            nPalabra.setCategGram(palElement.getAttribute("posTag"));
                            nPalabra.setLema(palElement.getAttribute("lemma"));
                            nPalabra.setSubCategGram(palElement.getAttribute("subPosTag"));
                            if (palElement.getAttribute("token").contains("ERROR")) {
                                correcto = false;
                                break;
                            }
                            nPalabra.setToken(palElement.getAttribute("token"));
                            palabras.add(countPal, nPalabra); //añadir palabra al arreglo

                            NodeList morfList = palElement.getElementsByTagName("affix");
                            int cantMorf = morfList.getLength();

                            if (cantMorf <= 0) {
                                if (countPal == 0) {
                                    morphText = morphText + palElement.getAttribute("token");
                                } else {
                                    morphText = morphText + " " + palElement.getAttribute("token");
                                }
                            } else {
                                nPalabra.setToken(palElement.getAttribute("lemma"));
                            }
                            int indLemma = indRule4;
                            indRule4++;

                            String lema = "";
                            ArrayList<String> pref = new ArrayList<>();
                            ArrayList<String> suf = new ArrayList<>();
                            for (int w = 0; w < cantMorf; w++) {
                                Palabra morfPalabra = new Palabra();
                                Node morfNode = morfList.item(w);
                                if (morfNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element palElement2 = (Element) morfNode;
                                    morfPalabra.setId(Integer.parseInt(palElement.getAttribute("id")));
                                    morfPalabra.setCategGram(palElement2.getAttribute("type"));
                                    morfPalabra.setLema("");
                                    morfPalabra.setSubCategGram("");
                                    morfPalabra.setToken("-" + palElement2.getAttribute("text"));
                                    morfPalabra.setOrder(palElement2.getAttribute("order"));
                                    morfPalabra.setType(palElement2.getAttribute("type"));

                                    int ord = Integer.valueOf(morfPalabra.getOrder());
                                    if (morfPalabra.getType().equals("Prefijo")) {
                                        palabras.add(countPal, morfPalabra);
                                    } else {
                                        palabras.add(countPal + 1, morfPalabra);
                                    }
                                    countPal++;
                                    if (morfPalabra.getType().equals("Prefijo")) {
                                        pref.add(ord - 1, morfPalabra.getToken());
                                    } else {
                                        suf.add(ord - 1, morfPalabra.getToken());
                                    }
                                }

                                //RULE4
                                String cat = abreviatura(morfPalabra.getCategGram());

                                if (cat != "CL2P") {
                                    String line = "R" + countRel + "\tx Arg1:T" + (indLemma);
                                    line = line + " Arg2:T" + (indRule4) + "\t\n";
                                    relaciones.add(line);
                                    countRel++;
                                }

                                indRule4++;
                            }

                            if (cantMorf > 0) {
                                if (pref.size() > 0) {
                                    for (int i = 0; i < pref.size(); i++) {
                                        if (i == 0) {
                                            lema = pref.get(i);
                                        } else {
                                            lema = lema + " " + pref.get(i);
                                        }
                                    }
                                    lema = lema + " " + palElement.getAttribute("lemma");
                                } else {
                                    lema = palElement.getAttribute("lemma");
                                }
                                if (suf.size() > 0) {
                                    for (int i = 0; i < suf.size(); i++) {
                                        lema = lema + " " + suf.get(i);
                                    }
                                }

                                if (countPal == 0) {
                                    morphText = morphText + lema;
                                } else {
                                    if (morphText == "COMPLETE ") {
                                        morphText = morphText + lema;
                                    } else {
                                        morphText = morphText + " " + lema;
                                    }
                                }
                            }
                            countPal++;
                        }
                    }
                    nOracion.setTexto(morphText);
                    relaciones = setPredefinedRules(nOracion, relaciones);
                }
                if (correcto) {
                    oraciones.add(nOracion);
                }
            }
        } catch (Exception e) {

        }
        brat.setOraciones(oraciones);
        brat.setRelaciones(relaciones);
        return brat;
    }

    private ArrayList<String> setPredefinedRules(Oracion oracion, ArrayList<String> relaciones) {
        ArrayList<Palabra> palabras = oracion.getPalabras();
        int count = 0, posVerb = 0, antVerb = 0, verbClcp = -1;
        Palabra verb = null;
		ArrayList<Palabra> verbAux = new ArrayList<Palabra>();
		ArrayList<Integer> indexVerbList = new ArrayList<Integer>();
		ArrayList<Integer> indexVerbAux = new ArrayList<Integer>();
        for (int i = 0; i < palabras.size(); i++) {
            Palabra palabra = palabras.get(i);
            String cat = abreviatura(palabra.getCategGram());
            if (cat == "VERB") {
                count++;
				verb = palabra;
				posVerb = i+1;
				indexVerbList.add(posVerb);
				antVerb = 1;
            } else if (cat == "VERB_AUX") {
                verbAux.add(palabra);
                indexVerbAux.add(i);
				antVerb = 0;
            } else if(palabra.getToken().startsWith("-") && antVerb != 0){ // Ver si es un afijo: REGLA6
				if(cat.equals("CLCP")){
					verbClcp = posVerb;
				}
			} else
				antVerb = 0;
        }
        if (count == 1) { //Si solo hay 1 Verbo
            for (int i = 0; i < palabras.size(); i++) {
                Palabra palabra = palabras.get(i);
                String cat = abreviatura(palabra.getCategGram());
                if (cat == "PUNCT" && (palabra.getSubCategGram() != "Copulativo") && ((palabra.getToken() == ".")
                        || (palabra.getToken() == "¡") || (palabra.getToken() == "!") || (palabra.getToken() == "¿") || (palabra.getToken() == "?"))) { //REGLA1
                    String line = "R" + countRel + "\tpunct Arg1:T" + (indexPal + posVerb);
                    line = line + " Arg2:T" + (indexPal + i + 1) + "\t\n";
                    relaciones.add(line);
                    countRel++;
                }
                if (cat == "CL2P") { //REGLA5
                    String line = "R" + countRel + "\taux_clit Arg1:T" + (indexPal + posVerb); 
                    line = line + " Arg2:T" + (indexPal + i + 1) + "\t\n";
                    relaciones.add(line);
                    countRel++;
                }

                if (indexVerbAux.size() == 1) {
                    int place = indexVerbAux.get(0);
                    if (i == place) { //REGLA2
                        String line = "R" + countRel + "\taux Arg1:T" + (indexPal + posVerb);
                        line = line + " Arg2:T" + (indexPal + i + 1) + "\t\n";
                        relaciones.add(line);
                        countRel++;
                    }
                }
            }
        }
		else if(count == 2 && verbClcp != -1){ //Si hay 2 Verbos REGLA6
			int indexVerbPrincipal = 0;
			for(int i = 0; i < 2; i++)
				if(indexVerbList.get(i) != verbClcp)
					indexVerbPrincipal = indexVerbList.get(i);
			String line = "R"+countRel+"\tadvcl Arg1:T"+(indexPal + indexVerbPrincipal); 
			line = line+" Arg2:T"+(indexPal + verbClcp)+"\t\n";
			relaciones.add(line);
			countRel++;
		}

        int[] exist = new int[2];
        if (indexVerbAux.size() > 1) {//RULE3
            for (int i = 0; i < indexVerbAux.size(); i++) {
                int index = indexVerbAux.get(i);
                Palabra palabra = palabras.get(index);
                if (palabra.getToken().toLowerCase() == "iki") {
                    exist[0] = index;
                } else if (palabra.getToken().toLowerCase() == "ika") {
                    exist[1] = index;
                }
            }
            if (exist[0] != 0 && exist[1] != 0) {
                String line = "R" + countRel + "\taux Arg1:T" + (indexPal + exist[1] + 1);
                line = line + " Arg2:T" + (indexPal + exist[0] + 1) + "\t\n"; //father
                relaciones.add(line);
                countRel++;
            }
        }

        indexPal = indexPal + palabras.size();

        return relaciones;
    }

    private String abreviatura(String categoria) {
        String abrev = categoria;

        if (categoria.equals("Adjetivo")) {
            abrev = "ADJ";
        } else if (categoria.equals("Adverbio")) {
            abrev = "ADV";
        } else if (categoria.startsWith("Conjunc")) {
            abrev = "CONJ";
        } else if (categoria.equals("Determinante")) {
            abrev = "DET";
        } else if (categoria.startsWith("Interjecc")) {
            abrev = "INTJ";
        } else if (categoria.equals("Nombre")) {
            abrev = "NOUN";
        } else if (categoria.equals("Nombre Propio")) {
            abrev = "NOUN_P";
        } else if (categoria.equals("Numeral")) {
            abrev = "NUM";
        } else if (categoria.startsWith("Postposic")) {
            abrev = "POSTP";
        } else if (categoria.equals("Pronombre")) {
            abrev = "PRON";
        } else if (categoria.startsWith("Puntuac")) {
            abrev = "PUNCT";
        } else if (categoria.equals("Verbo")) {
            abrev = "VERB";
        } else if (categoria.equals("Verbo Auxiliar")) {
            abrev = "VERB_AUX";
        } else if (categoria.equals("Símbolo")) {
            abrev = "SYM";
        } else if (categoria.equals("Palabra Interrogativa")) {
            abrev = "PINT";
        } else if (categoria.equals("Clít. concord. de particip.")) {
            abrev = "CLCP";
        } else if (categoria.equals("Clít. nominal")) {
            abrev = "CLNOM";
        } else if (categoria.equals("Clít. de segunda posición")) {
            abrev = "CL2P";
        } else if (categoria.equals("Clít. de posición libre")) {
            abrev = "CLPL";
        } else if (categoria.equals("Sufijo verbal")) {
            abrev = "SUFV";
        } else if (categoria.equals("Sufijo nominal")) {
            abrev = "SUFN";
        } else if (categoria.equals("Prefijo")) {
            abrev = "PREF";
        } else if (categoria.equals("Onomatopeya")) {
            abrev = "ONOM";
        }

        return abrev;
    }

    private void limpiarXML(File file, String path, String log) {
        File fileLog = new File(log);
        BufferedWriter bw = null;
        try {
            if (!fileLog.exists()) {
                fileLog.createNewFile();
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(log, true), "UTF-8"));
        } catch (IOException ex) {

        }

        Corpus corpus = DaoJaxb.readCorpusXmlFromFile(file);
        Iterator<Sentence> iterator = corpus.getSentences().iterator();
        while (iterator.hasNext()) {
            Sentence sent = iterator.next();
            for (Word w : sent.getWords()) {
                if (w.getToken().contains("ERROR")) {
                    try {
                        String text = sent.getText() + " | " + sent.getTranslation();
                        bw.append(text);
                        bw.newLine();
                    } catch (IOException ex) {

                    }
                    iterator.remove();
                }
            }
        }
        try {
            bw.close();
        } catch (IOException ex) {

        }
        Integer id = 0;
        for (Sentence sent : corpus.getSentences()) {
            sent.setId(id);
            id++;
        }
        corpus.setLastSentence(0);
        try {
            DaoJaxb.writeCleanCorpusXml(path, corpus);
        } catch (Exception ex) {

        }
    }
}
