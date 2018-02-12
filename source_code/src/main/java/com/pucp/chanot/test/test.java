/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pucp.chanot.test;

import com.pucp.chanot.dao.DaoJaxb;

/**
 *
 * @author Rodolfo
 */
public class test {
    public static void main(String[] args) {
        DaoJaxb oDao = new DaoJaxb();
        System.out.println( oDao.readCorpusXml("aligmnent_3.2_input", "C:/", "df") );
    }
}
