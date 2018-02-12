package com.pucp.chanot.controller;

import com.pucp.chanot.dao.DaoJaxb;
import com.pucp.chanot.dto.AfijoDTO;
import com.pucp.chanot.dto.AnotacionDTO;
import com.pucp.chanot.dto.BaseDTO;
import com.pucp.chanot.dto.ListPalabraDTO;
import com.pucp.chanot.dto.ListAfijoDTO;
import com.pucp.chanot.dto.PalabraDTO;
import com.pucp.chanot.entity.Corpus;
import com.pucp.chanot.entity.Sentence;
import com.pucp.chanot.entity.Word;
import com.pucp.chanot.entity.Affix;
import com.pucp.chanot.entity.InputFile;
import com.pucp.chanot.entity.User;
import com.pucp.chanot.entity.Users;
import com.pucp.chanot.util.ArchivoUtil;
import com.pucp.chanot.util.FormatoUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jose
 */
@Controller(value = "gramaticalController")
@RequestMapping(value = "/gramatical")
public class GramaticalController {

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicioUsuario() {
        return "chanot/gramatical";
    }

    @RequestMapping(value = "/setArchivo", method = RequestMethod.POST)
    @ResponseBody
    public BaseDTO setArchivo(@RequestParam("archivo") String archivo, HttpServletRequest request) {
        BaseDTO rpt = new BaseDTO();
        HttpSession session = request.getSession();
        Integer idcurSentence = 0;
        //leo las oraciones
        List<String> sentShipibo = new ArrayList<>();
        List<String> sentSpanish = new ArrayList<>();
        List<String> arrAux, arrLines;
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        String user = (String) session.getAttribute("user") + "/";
        Corpus oCorpus = DaoJaxb.readCorpusXml(archivo, folder, user);
        if (oCorpus == null) {
            arrLines = ArchivoUtil.leerArchivo(archivo, folder, user);
            for (String line : arrLines) {
                arrAux = Arrays.asList(line.split("\\|"));
                if (arrAux.size() < 2) {
                    rpt.setError(true);
                    rpt.setMensaje("Format file error");
                    return rpt;
                }
                sentShipibo.add(arrAux.get(0).trim());
                sentSpanish.add(arrAux.get(1).trim());
            }
            oCorpus = ArchivoUtil.createCorpus(sentShipibo, sentSpanish);
            try {
                DaoJaxb.writeCorpusXml(archivo, oCorpus, folder, user);
            } catch (Exception ex) {
                rpt.setError(true);
                rpt.setMensaje("Error creating xml for first time");
                return rpt;
            }
            //ingreso el registro en el xml
            Integer id = (Integer) session.getAttribute("userId");
            Users oUsers = DaoJaxb.readUsersXml(folder);
            User oUser = oUsers.getUsers().get(id);
            InputFile inputFile = new InputFile();
            inputFile.setName(archivo.substring(0, archivo.length() - 4));
            inputFile.setSentences(oCorpus.getSentences().size());
            Integer count = 0;
            Integer anot = 0;
            for (Sentence snt : oCorpus.getSentences()) {
                count += snt.getWords().size();
                for (Word w : snt.getWords()) {
                    if (w.getPosTag() != null) {
                        anot++;
                    }
                }
            }
            if (oUser.getInputFiles().size() > 0) {
                inputFile.setId(oUser.getInputFiles().size());
            } else {
                inputFile.setId(0);
            }
            inputFile.setWords(count);
            inputFile.setProcessedWords(anot);
            oUser.getInputFiles().add(inputFile);
            try {
                DaoJaxb.writeUsersXml(oUsers, folder);
            } catch (Exception ex) {
                rpt.setError(true);
                rpt.setMensaje("Error saving the file in users.xml");
                return rpt;
            }

        } else {
            idcurSentence = oCorpus.getLastSentence();
        }
        session.setAttribute("file", archivo.substring(0, archivo.length() - 3) + "xml");
        session.setAttribute("corpus", oCorpus);
        session.setAttribute("currentSentence", idcurSentence);
        createRepository(request);
        return rpt;
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
            if (w.getPosTag() == null) {
                if (getWordInRepository(request, w.getToken()) != null) {
                    oPalabra.setEstado(1);
                } else {
                    oPalabra.setEstado(0);
                }
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
        rpt.setOracionActual(idCurSentence + 1);
        rpt.setOracion(oSentence.getText());
        return rpt;
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
        if (oWord.getPosTag() == null) {
            if (getWordInRepository(request, oWord.getToken()) != null) {
                oWord = getWordInRepository(request, oWord.getToken());
            } else {
                rpt.setError(true);
                rpt.setMensaje("Word not annotated");
            }
        }
        rpt.setCategoria(oWord.getPosTag());
        rpt.setLema(oWord.getLemma());
        rpt.setPalabra(oWord.getToken());
        rpt.setSubCategoria(oWord.getSubPosTag());
        List<AfijoDTO> affixs = new ArrayList<>();
        ListAfijoDTO lista = new ListAfijoDTO();
        for (Affix af : oWord.getAffixs()) {
            AfijoDTO afij = new AfijoDTO();
            afij.setAfijo(af.getText());
            afij.setTipo(af.getType());
            afij.setPosicion(af.getOrder());
            affixs.add(afij);
        }
        lista.setAfijos(affixs);
        lista.setTotal(affixs.size());
        rpt.setAfijos(lista);
        return rpt;
    }

    @RequestMapping(value = "/anotarPalabra", method = RequestMethod.POST)
    @ResponseBody
    public BaseDTO anotarPalabra(@RequestParam("palabra") String palabra, @RequestParam("lema") String lema,
            @RequestParam("categoria") String categoria, @RequestParam("subCategoria") String subCategoria,
            @RequestParam("afijos") String afijos, @RequestParam("posicion") Integer posicion,
            @RequestParam("tiempo") Integer tiempo, @RequestParam("lemaPred") String lemaPred, @RequestParam("posTagPred") String postPred,
            HttpServletRequest request) {
        BaseDTO rpt = new BaseDTO();
        List<Affix> afjs = new ArrayList<>();
        if (!afijos.equals("]")) {
            JSONArray jsonArray = new JSONArray(afijos);
            //ordenar el arreglo
            JSONArray sortedJsonArray = new JSONArray();
            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonList.add(jsonArray.getJSONObject(i));
            }
            Collections.sort(jsonList, new Comparator<JSONObject>() {
                public int compare(JSONObject a, JSONObject b) {
                    Integer valA = 0;
                    Integer valB = 0;
                    try {
                        valA = (Integer) a.getInt("posicion");
                        valB = (Integer) b.getInt("posicion");
                    } catch (JSONException e) {
                        //do something
                    }
                    return valA.compareTo(valB);
                }
            });
            for (int i = 0; i < jsonArray.length(); i++) {
                sortedJsonArray.put(jsonList.get(i));
            }

            for (int i = 0; i < sortedJsonArray.length(); i++) {
                Affix afijo = new Affix();
                afijo.setText(sortedJsonArray.getJSONObject(i).getString("afijo"));
                afijo.setOrder(Integer.parseInt(sortedJsonArray.getJSONObject(i).getString("posicion")));
                afijo.setType(sortedJsonArray.getJSONObject(i).getString("tipo"));
                afjs.add(afijo);
            }
        }
        //proceso a guardar la palabra
        HttpSession session = request.getSession();
        Integer idCurSentence = (Integer) session.getAttribute("currentSentence");
        Integer posWord = posicion;
        Integer processed = 0;

        Corpus oCorpus = (Corpus) session.getAttribute("corpus");
        if (oCorpus.getSentences().get(idCurSentence).getWords().get(posWord).getPosTag() == null) {
            processed = 1;
        }
        Word oWord = oCorpus.getSentences().get(idCurSentence).getWords().get(posWord);
        oWord.setId(posWord);
        oWord.setLemma(lema);
        oWord.setToken(palabra);
        oWord.setPosTag(categoria);
        oWord.setSubPosTag(subCategoria);
        oWord.setAffix(afjs);
        oWord.setLemmaPred(lemaPred);
        oWord.setPosTagPred(postPred);
        addToRepository(request, oWord);
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
        //guardo el tiempo
        Integer id = (Integer) session.getAttribute("userId");
        Users oUsers = DaoJaxb.readUsersXml(folder);
        Integer idFile = 0;
        file = file.substring(0, file.length() - 4);
        for (InputFile c : oUsers.getUsers().get(id).getInputFiles()) {
            if (file.equals(c.getName())) {
                idFile = c.getId();
                break;
            }
        }
        InputFile inputFile = oUsers.getUsers().get(id).getInputFiles().get(idFile);
        inputFile.setTime(inputFile.getTime() + tiempo);
        inputFile.setProcessedWords(inputFile.getProcessedWords() + processed);
        oUsers.getUsers().get(id).getInputFiles().set(idFile, inputFile);
        try {
            DaoJaxb.writeUsersXml(oUsers, folder);
        } catch (Exception ex) {
            rpt.setError(true);
            rpt.setMensaje("Error saving the data in users.xml");
            return rpt;
        }
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
            if (w.getPosTag() == null) {
                if (getWordInRepository(request, w.getToken()) != null) {
                    oPalabra.setEstado(1);
                } else {
                    oPalabra.setEstado(0);
                }
            } else {
                oPalabra.setEstado(2);
            }
            lst.add(oPalabra);
        }
        rpt.setTraduccion(oSentence.getTranslation());
        rpt.setTotal(oSentence.getWords().size());
        rpt.setError(false);
        rpt.setPalabras(lst);
        rpt.setOracion(oSentence.getText());
        return rpt;
    }

    @RequestMapping(value = "/editarTraduccion", method = RequestMethod.POST)
    @ResponseBody
    public BaseDTO editarTraduccion(@RequestParam("traduccion") String traduccion, HttpServletRequest request) {
        BaseDTO rpt = new BaseDTO();
        HttpSession session = request.getSession();
        Integer idCurSentence = (Integer) session.getAttribute("currentSentence");
        Corpus oCorpus = (Corpus) session.getAttribute("corpus");
        oCorpus.getSentences().get(idCurSentence).setTranslation(traduccion);
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        String user = (String) session.getAttribute("user") + "/";
        try {
            String file = (String) session.getAttribute("file");
            DaoJaxb.writeCorpusXml(file, oCorpus, folder, user);
        } catch (Exception ex) {
            rpt.setError(true);
            rpt.setMensaje("Error saving translation");
            return rpt;
        }
        return rpt;
    }

    public void createRepository(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Integer> mapRepository = new HashMap<String, Integer>();
        Sentence oRepository = new Sentence();
        session.setAttribute("mapRepository", mapRepository);
        session.setAttribute("repository", oRepository);
        // antes solo se recorria la carpeta del usuario
        //File folderOut = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files/output/" + session.getAttribute("user"));
        File folderOut = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files/output");
        File[] listOfUsersOut = folderOut.listFiles();
        String[] validUsers = {"a20105822", "a20111606", "a20153884", "a20162836", "a20163785"};
        for (File userOut : listOfUsersOut) {
            if (userOut.isFile()) {
                continue;
            }
            boolean isValidUser = false;
            for (String user : validUsers) {
                if (userOut.getName().equals(user)) {
                    isValidUser = true;
                    break;
                }
            }
            if (!isValidUser) {
                continue;
            }
            File[] listOfFilesOut = userOut.listFiles();
            for (File listOfFile : listOfFilesOut) {
                if (listOfFile.isFile()) {
                    Corpus oCorpus = DaoJaxb.readCorpusXml(listOfFile.getName(), request.getSession().getServletContext().getInitParameter("upload.location"), userOut.getName() + "/");
                    if (oCorpus == null) {
                        continue;
                    }
                    for (Sentence oSentence : oCorpus.getSentences()) {
                        for (Word oWord : oSentence.getWords()) {
                            if (oWord.getLemma() != null) {
                                addToRepository(request, oWord);
                            }
                        }
                    }
                }
            }
        }
    }

    public Word getWordInRepository(HttpServletRequest request, String word) {
        HttpSession session = request.getSession();
        Map<String, Integer> mapRepository = (Map<String, Integer>) session.getAttribute("mapRepository");
        Sentence oRepository = (Sentence) session.getAttribute("repository");
        if (mapRepository.containsKey(word) == false) {
            return null;
        } else {
            Integer id = mapRepository.get(word);
            return oRepository.getWords().get(id);
        }
    }

    public void addToRepository(HttpServletRequest request, Word oWord) {
        HttpSession session = request.getSession();
        String token = (oWord.getToken());
        if (FormatoUtil.isNumber(token) || FormatoUtil.isPunctuation(token) || FormatoUtil.isSymbol(token)) {
            return;
        }
        Map<String, Integer> mapRepository = (Map<String, Integer>) session.getAttribute("mapRepository");
        Sentence oRepository = (Sentence) session.getAttribute("repository");
        //agrego la palabra en forma igual a como llego con primera mayuscula
        token = token.substring(0, 1).toUpperCase() + token.substring(1);
        if (mapRepository.containsKey(token) == false) {
            mapRepository.put(token, (Integer) oRepository.getWords().size());
            oRepository.getWords().add(oWord);
            session.setAttribute("mapRepository", mapRepository);
        } else {
            Integer id = mapRepository.get(token);
            oRepository.getWords().set(id, oWord);
        }
        //la añado igual a que llego con primera minuscula
        token = token.substring(0, 1).toLowerCase() + token.substring(1);
        if (mapRepository.containsKey(token) == false) {
            mapRepository.put(token, (Integer) oRepository.getWords().size());
            oRepository.getWords().add(oWord);
            session.setAttribute("mapRepository", mapRepository);
        } else {
            Integer id = mapRepository.get(token);
            oRepository.getWords().set(id, oWord);
        }

        //la añado normalizada (y la primera mayusucla)
        token = FormatoUtil.normalize(oWord.getToken());
        token = token.substring(0, 1).toUpperCase() + token.substring(1);
        if (mapRepository.containsKey(token) == false) {
            mapRepository.put(token, (Integer) oRepository.getWords().size());
            oRepository.getWords().add(oWord);
            session.setAttribute("mapRepository", mapRepository);
        } else {
            Integer id = mapRepository.get(token);
            oRepository.getWords().set(id, oWord);
        }

        //la añado normalizada (sin tildes y minusculas)
        token = FormatoUtil.normalize(oWord.getToken());
        if (mapRepository.containsKey(token) == false) {
            mapRepository.put(token, (Integer) oRepository.getWords().size());
            oRepository.getWords().add(oWord);
            session.setAttribute("mapRepository", mapRepository);
        } else {
            Integer id = mapRepository.get(token);
            oRepository.getWords().set(id, oWord);
        }

        session.setAttribute("repository", oRepository);
    }

    @RequestMapping(value = "/nombreArchivo", method = RequestMethod.GET)
    @ResponseBody
    public BaseDTO nombreArchivo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        BaseDTO rpt = new BaseDTO();
        String file = (String) session.getAttribute("file");
        rpt.setMensaje(file.substring(0, file.length() - 4));
        return rpt;
    }

}
