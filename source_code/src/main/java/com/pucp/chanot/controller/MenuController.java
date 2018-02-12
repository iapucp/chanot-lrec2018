package com.pucp.chanot.controller;

import com.pucp.chanot.dao.DaoJaxb;
import com.pucp.chanot.dto.ArchivoDTO;
import com.pucp.chanot.dto.BaseDTO;
import com.pucp.chanot.dto.EstadisticasDTO;
import com.pucp.chanot.dto.ListArchivoDTO;
import com.pucp.chanot.entity.InputFile;
import com.pucp.chanot.entity.User;
import com.pucp.chanot.entity.Users;
import com.pucp.chanot.brat.util.BratUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jose
 */
@Controller(value = "menuController")
public class MenuController {

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String inicioUsuario() {
        return "chanot/login";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu() {
        return "chanot/menu";
    }

    @RequestMapping(value = "/archivos", method = RequestMethod.GET)
    @ResponseBody
    public ListArchivoDTO listarArchivos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ListArchivoDTO archivos = new ListArchivoDTO();
        List<ArchivoDTO> inputs = new ArrayList<>();
        List<String> archs = new ArrayList<>();
        List<String> input = new ArrayList<>();
        String user = (String) session.getAttribute("user");
        String path = request.getSession().getServletContext().getInitParameter("upload.location");
        Integer id = (Integer) session.getAttribute("userId");

        File folder = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files/input/" + user);
        File[] listOfFiles = folder.listFiles();
        for (File listOfFile : listOfFiles) {
            input.add(listOfFile.getName());
        }
        User oUser = DaoJaxb.readUsersXml(path).getUsers().get(id);
        if (oUser.getInputFiles() != null && oUser.getInputFiles().size() > 0) {
            for (InputFile in : oUser.getInputFiles()) {
                archs.add(in.getName() + ".txt");
                ArchivoDTO arch = new ArchivoDTO();
                arch.setArchivo(in.getName() + ".txt");
                if (in.getWords().intValue() == in.getProcessedWords().intValue()) {
                    arch.setEstado(2);
                } else {
                    arch.setEstado(1);
                }
                inputs.add(arch);
            }
        }
        input.removeAll(archs);
        for (String x : input) {
            ArchivoDTO arch = new ArchivoDTO();
            arch.setArchivo(x);
            arch.setEstado(0);
            inputs.add(arch);
        }
        archivos.setArchivos(inputs);
        archivos.setTotal(inputs.size());
        return archivos;
    }

    @RequestMapping(value = "/subirArchivo", method = RequestMethod.POST)
    public @ResponseBody
    BaseDTO subirPlantilla(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        BaseDTO resultado = new BaseDTO();
        try {
            //subo el archivo
            String user = (String) session.getAttribute("user") + "/";
            String folder = request.getSession().getServletContext().getInitParameter("upload.location");
            String path = folder + "/files/input/" + user;
            File dest = new File(path + file.getOriginalFilename());
            file.transferTo(dest);
        } catch (Exception ex) {
            resultado.setError(true);
            resultado.setMensaje("No se pudo subir el archivo");
        }
        return resultado;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    BaseDTO login(@RequestParam("user") String usuario, @RequestParam("pass") String pass, HttpServletRequest request) {

        BaseDTO resultado = new BaseDTO();
        HttpSession session = request.getSession();
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        Users oUsers = DaoJaxb.readUsersXml(folder);
        if (oUsers != null) {
            for (User usr : oUsers.getUsers()) {
                if (usr.getUser().equals(usuario) && usr.getPassword().equals(pass)) {
                    session.setAttribute("user", usuario);
                    session.setAttribute("userId", usr.getId());
                    return resultado;
                }
            }
            resultado.setError(true);
            resultado.setMensaje("No se encontro el usuario");
        } else {
            resultado.setError(true);
            resultado.setMensaje("No se encontro el archivo de usuarios");
        }
        return resultado;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String cerrarSesion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("file");
        session.removeAttribute("corpus");
        session.removeAttribute("currentSentence");
        return "chanot/login";
    }

    @RequestMapping(value = "/getUsuario", method = RequestMethod.GET)
    @ResponseBody
    public BaseDTO getUser(HttpServletRequest request) {
        BaseDTO resultado = new BaseDTO();
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        if (user != null) {
            resultado.setMensaje(user);
        } else {
            resultado.setError(true);
            resultado.setMensaje("No se encontro el usuario");
        }
        return resultado;
    }

    @RequestMapping(value = "/getStatistics", method = RequestMethod.POST)
    @ResponseBody
    public EstadisticasDTO getStatistics(HttpServletRequest request, @RequestParam("archivo") String archivo) {
        EstadisticasDTO resultado = new EstadisticasDTO();
        HttpSession session = request.getSession();
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        Integer id = (Integer) session.getAttribute("userId");
        User oUser = DaoJaxb.readUsersXml(folder).getUsers().get(id);

        List<InputFile> archivos;
        if (oUser.getInputFiles() != null && oUser.getInputFiles().size() > 0) {
            archivos = oUser.getInputFiles();
            for (InputFile file : archivos) {
                if (file.getName().equals(archivo)) {
                    resultado.setOraciones(file.getSentences().toString());
                    resultado.setPalabras(file.getWords().toString() + " en total, " + file.getProcessedWords().toString() + " anotadas");
                    Integer time = file.getTime();
                    resultado.setTiempoTotal((time / 3600) + " h " + (time / 60) + " min " + (time % 60) + " s");
                    if (file.getProcessedWords() != 0) {
                        Integer prom = file.getTime() / file.getProcessedWords();
                        resultado.setTiempoProm((prom / 3600) + " h " + (prom / 60) + " min " + (prom % 60) + " s");
                    } else {
                        resultado.setTiempoProm("0");
                    }
                    break;
                }
            }
        } else {
            resultado.setError(true);
            resultado.setMensaje("No se encontro el archivo");
        }
        return resultado;
    }

    @RequestMapping(value = "/exportarBrat", method = RequestMethod.POST)
    @ResponseBody
    public BaseDTO exportarBrat(HttpServletRequest request, @RequestParam("archivo") String archivo) {
        BaseDTO resultado = new BaseDTO();
        HttpSession session = request.getSession();
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        String bratFolder = request.getSession().getServletContext().getInitParameter("brat.location");
        String user = (String) session.getAttribute("user");
        String salida = folder + "/files/clean/" + user;
        folder = folder + "/files/output/" + user + "/" + archivo + ".xml";
        BratUtil brat = new BratUtil();
        brat.guardarArchivos(archivo, folder, bratFolder + "/" + user, salida);
        resultado.setMensaje(user + "/");
        return resultado;
    }

    @RequestMapping(value = "/reescribirBrat", method = RequestMethod.POST)
    @ResponseBody
    public BaseDTO reescribirBrat(HttpServletRequest request, @RequestParam("archivo") String archivo) {
        BaseDTO resultado = new BaseDTO();
        HttpSession session = request.getSession();
        String folder = request.getSession().getServletContext().getInitParameter("upload.location");
        String bratFolder = request.getSession().getServletContext().getInitParameter("brat.location");
        String user = (String) session.getAttribute("user");
        String salida = folder + "/files/clean/" + user;
        folder = folder + "/files/output/" + user + "/" + archivo + ".xml";
        BratUtil brat = new BratUtil();
        brat.rescribirArchivos(archivo, folder, bratFolder + "/" + user, salida);
        resultado.setMensaje(user + "/");
        return resultado;
    }

}
