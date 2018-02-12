package com.pucp.chanot.controller;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author alulab14
 */
@Controller(value = "descargaController")
public class DescargaController {

    @RequestMapping(value = "/descargar/input", method = RequestMethod.GET)
    @ResponseBody
    public void descargarInput(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"input.zip\"");
        response.setContentType("application/zip");

        HttpSession session = request.getSession();

        List<String> input = new ArrayList<>();
        File folder = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files/input");
        try {
            File zipfile = File.createTempFile("tempfile", ".tmp");
            zip(folder, zipfile);
            FileUtils.copyFile(zipfile, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {

        }
    }

    @RequestMapping(value = "/descargar/output", method = RequestMethod.GET)
    @ResponseBody
    public void descargarOutput(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"output.zip\"");
        response.setContentType("application/zip");

        HttpSession session = request.getSession();

        List<String> input = new ArrayList<>();
        File folder = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files/output");
        try {
            File zipfile = File.createTempFile("tempfile", ".tmp");
            zip(folder, zipfile);
            FileUtils.copyFile(zipfile, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {

        }
    }

    @RequestMapping(value = "/descargar/clean", method = RequestMethod.GET)
    @ResponseBody
    public void descargarClean(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"clean.zip\"");
        response.setContentType("application/zip");

        HttpSession session = request.getSession();

        List<String> input = new ArrayList<>();
        File folder = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files/clean");
        try {
            File zipfile = File.createTempFile("tempfile", ".tmp");
            zip(folder, zipfile);
            FileUtils.copyFile(zipfile, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {

        }
    }
    
    @RequestMapping(value = "/descargar/all", method = RequestMethod.GET)
    @ResponseBody
    public void descargarAll(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"all.zip\"");
        response.setContentType("application/zip");

        HttpSession session = request.getSession();

        List<String> input = new ArrayList<>();
        File folder = new File(request.getSession().getServletContext().getInitParameter("upload.location") + "/files");
        try {
            File zipfile = File.createTempFile("tempfile", ".tmp");
            zip(folder, zipfile);
            FileUtils.copyFile(zipfile, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {

        }
    }
    
    @RequestMapping(value = "/descargar/brat", method = RequestMethod.GET)
    @ResponseBody
    public void descargarBrat(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"brat.zip\"");
        response.setContentType("application/zip");

        HttpSession session = request.getSession();

        List<String> input = new ArrayList<>();
        File folder = new File(request.getSession().getServletContext().getInitParameter("brat.location"));
        try {
            File zipfile = File.createTempFile("tempfile", ".tmp");
            zip(folder, zipfile);
            FileUtils.copyFile(zipfile, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {

        }
    }

    private void zip(File directory, File zipfile) throws IOException {
        URI base = directory.toURI();
        Deque<File> queue = new LinkedList<File>();
        queue.push(directory);
        OutputStream out = new FileOutputStream(zipfile);
        Closeable res = out;
        try {
            ZipOutputStream zout = new ZipOutputStream(out);
            res = zout;
            while (!queue.isEmpty()) {
                directory = queue.pop();
                for (File kid : directory.listFiles()) {
                    String name = base.relativize(kid.toURI()).getPath();
                    if (kid.isDirectory()) {
                        queue.push(kid);
                        name = name.endsWith("/") ? name : name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    } else {
                        zout.putNextEntry(new ZipEntry(name));
                        copy(kid, zout);
                        zout.closeEntry();
                    }
                }
            }
        } finally {
            res.close();
        }
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = in.read(buffer);
            if (readCount < 0) {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }

    private void copy(File file, OutputStream out) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            copy(in, out);
        } finally {
            in.close();
        }
    }

}
