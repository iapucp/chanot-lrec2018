package com.pucp.chanot.controller;

import com.pucp.chanot.dao.DaoJaxb;
import com.pucp.chanot.dto.AnotacionDTO;
import com.pucp.chanot.dto.BaseDTO;
import com.pucp.chanot.dto.ListPalabraDTO;
import com.pucp.chanot.dto.PalabraDTO;
import com.pucp.chanot.entity.Corpus;
import com.pucp.chanot.entity.Sentence;
import com.pucp.chanot.entity.Word;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jose
 */
@Controller(value = "entidadController")
@RequestMapping(value = "/entidad")
public class EntidadController {

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicioUsuario() {
        return "chanot/entidad";
    }

    @RequestMapping(value = "/anotarPalabra", method = RequestMethod.POST)
    @ResponseBody
    public BaseDTO anotarPalabra(@RequestParam("palabra") String palabra,
            @RequestParam("categoria") String categoria, @RequestParam("subCategoria") String subCategoria,
            @RequestParam("posicion") Integer posicion, @RequestParam("subCat") String subCat, @RequestParam("prediccion") String entityPred, 
            HttpServletRequest request) {
        BaseDTO rpt = new BaseDTO();

        //proceso a guardar la palabra
        HttpSession session = request.getSession();
        Integer idCurSentence = (Integer) session.getAttribute("currentSentence");
        Integer posWord = posicion;
        Integer processed = 0;

        Corpus oCorpus = (Corpus) session.getAttribute("corpus");
        Word oWord = oCorpus.getSentences().get(idCurSentence).getWords().get(posWord);
        oWord.setId(posWord);
        oWord.setToken(palabra);
        String entidadTag = getTag(categoria, subCategoria);
        oWord.setEntityTag(entidadTag);
        oWord.setSubCatTag(subCat);
        oWord.setEntityTagPred(entityPred);
        oCorpus.getSentences().get(idCurSentence).getWords().set(posWord, oWord);
        oCorpus.setLastSentence(idCurSentence);
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        String user = (String) session.getAttribute("user") + "/";
        String file = (String) session.getAttribute("file");
        try {
            DaoJaxb.writeCorpusXml(file, oCorpus, folder, user);
        } catch (Exception ex) {
            rpt.setError(true);
            rpt.setMensaje("Error saving");
            return rpt;
        }

        return rpt;
    }

    public String getTag(String categoria, String subCategoria) {
        String tag = "";
        String tagL = "LOC", tagP = "PER", tagO = "ORG", tagN = "NUM", tagF = "FEC";
        if (categoria.equals("Otro")) {
            tag = "O";
        } else {
            if (!subCategoria.equals("Único")) {
                if (subCategoria.equals("Inicio")) {
                    tag += "B-";
                } else if (subCategoria.equals("Medio")) {
                    tag += "I-";
                } else {
                    tag += "E-";
                }
            }
            if (categoria.equals("Locación")) {
                tag += tagL;
            } else if (categoria.equals("Persona")){
                tag += tagP;
            } else if (categoria.equals("Número")){
                tag += tagN;
            } else if (categoria.equals("Fecha")){
                tag += tagF; 
            } else
                tag += tagO;
        }
        return tag;
    }

    @RequestMapping(value = "/getPalabra", method = RequestMethod.POST)
    @ResponseBody
    public AnotacionDTO getPalabra(@RequestParam("posicion") Integer posPalabra, HttpServletRequest request) {
        HttpSession session = request.getSession();
        AnotacionDTO rpt = new AnotacionDTO();
        List<PalabraDTO> lst = new ArrayList<>();
        Integer idCurSentence = (Integer) session.getAttribute("currentSentence");
        Corpus oCorpus = (Corpus) session.getAttribute("corpus");
        Sentence oSentence = oCorpus.getSentences().get(idCurSentence);
        Word oWord = oSentence.getWords().get(posPalabra);
        //paso los valores de la palabra
        String entityTag = oWord.getEntityTag();
        List<String> cats = reverseTag(entityTag);
        rpt.setCategoria(cats.get(0));
        rpt.setPalabra(oWord.getToken());
        rpt.setSubCategoria(cats.get(1));
        rpt.setSubCat(oWord.getSubCatTag());
        return rpt;
    }

    public List<String> reverseTag(String entityTag) {
        String cat = "";
        String subCat = "";
        if (entityTag.equals("O")) {
            cat = "Otro";
        } else {
            if (entityTag.length() == 3) {
                subCat = "Único";
            } else {
                String pos = entityTag.substring(0, 1);
                if (pos.equals("B")) {
                    subCat = "Inicio";
                } else if (pos.equals("I")) {
                    subCat = "Medio";
                } else {
                    subCat = "Fin";
                }
            }
            String tipo = entityTag.substring(entityTag.length() - 3);
            if (tipo.equals("LOC")) {
                cat = "Locación";
            } else if (tipo.equals("PER")) {
                cat = "Persona";
            } else if (tipo.equals("ORG")) {
                cat = "Organización";
            } else if (tipo.equals("NUM")) {
                cat = "Número";
            } else {
                cat = "Fecha";
            }

        }
        List<String> cats = new ArrayList<>();
        cats.add(cat);
        cats.add(subCat);
        return cats;
    }

    @RequestMapping(value = "/getOracion", method = RequestMethod.GET)
    @ResponseBody
    public ListPalabraDTO getOracion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ListPalabraDTO rpt = new ListPalabraDTO();
        List<PalabraDTO> lst = new ArrayList<>();
        Integer idCurSentence = (Integer) session.getAttribute("currentSentence");
        Corpus oCorpus = (Corpus) session.getAttribute("corpus");
        Sentence oSentence = oCorpus.getSentences().get(idCurSentence);
        for (Word w : oSentence.getWords()) {
            PalabraDTO oPalabra = new PalabraDTO();
            oPalabra.setPalabra(w.getToken());
            if (w.getEntityTag() == null) {
                oPalabra.setEstado(0);

            } else {
                oPalabra.setEstado(2);
            }
            lst.add(oPalabra);
        }
        rpt.setTraduccion(oSentence.getTranslation());
        rpt.setTotal(oSentence.getWords().size());
        rpt.setError(false);
        rpt.setPalabras(lst);
        rpt.setTotalOraciones(oCorpus.getSentences().size());
        rpt.setOracion(oSentence.getText());
        rpt.setOracionActual(idCurSentence + 1);
        return rpt;
    }

    @RequestMapping(value = "/cambiarOracion", method = RequestMethod.POST)
    @ResponseBody
    public ListPalabraDTO cambiarOracion(@RequestParam("direccion") Integer direccion, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ListPalabraDTO rpt = new ListPalabraDTO();
        List<PalabraDTO> lst = new ArrayList<>();
        Integer idCurSentence = (Integer) session.getAttribute("currentSentence");
        Corpus oCorpus = (Corpus) session.getAttribute("corpus");
        if ((idCurSentence + direccion) >= 0 && oCorpus.getSentences().size() > (idCurSentence + direccion)) {
            idCurSentence += direccion;
        } else {
            rpt.setError(true);
            rpt.setMensaje("Sentence index out of bounds");
            return rpt;
        }
        session.setAttribute("currentSentence", idCurSentence);
        Sentence oSentence = oCorpus.getSentences().get(idCurSentence);
        for (Word w : oSentence.getWords()) {
            PalabraDTO oPalabra = new PalabraDTO();
            oPalabra.setPalabra(w.getToken());
            if (w.getEntityTag() == null) {
                oPalabra.setEstado(0);

            } else {
                oPalabra.setEstado(2);
            }
            lst.add(oPalabra);
        }
        rpt.setOracion(oSentence.getText());
        rpt.setTraduccion(oSentence.getTranslation());
        rpt.setTotal(oSentence.getWords().size());
        rpt.setError(false);
        rpt.setPalabras(lst);
        return rpt;
    }
    
    @RequestMapping(value = "/nombreArchivo", method = RequestMethod.GET)
    @ResponseBody
    public BaseDTO nombreArchivo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        BaseDTO rpt = new BaseDTO();
        String file = (String) session.getAttribute("file");
        rpt.setMensaje(file.substring(0, file.length()-4));
        return rpt;
    }
}
