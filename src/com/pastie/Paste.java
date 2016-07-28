/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pastie;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 *
 * @author Turgay
 */
public class Paste {

    /**
     * Bir plain texti pastie.org'a post eder ve redirect linkini döner
     * @param data Plain Text
     * @return URL
     */
    public String postText(String data) {
        try {
            Connection.Response res = Jsoup.connect("http://pastie.org/pastes")
                    .userAgent("Mozilla")
                    .timeout(30000)
                    .data("utf8", "✓")
                    .data("paste[authorization]", "burger")
                    .data("paste[parser_id]", "6")
                    .data("paste[body]", data)
                    .data("paste[restricted]", "0")
                    .data("commit", "Create Paste")
                    .followRedirects(true)
                    .method(Connection.Method.POST).execute();

            System.out.println("****************");
            System.out.println("Pastie adresi: " + res.url());
            System.out.println("****************");

            return res.url().toString();
        } catch (IOException ex) {
            Logger.getLogger(Paste.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error";
    }
    /**
     * Desktopun default İnternet Browserında verilen adresi açar
     * @param adr Adres
     */
    public void showOnBrowser(String adr) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(adr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Menu çıktısı verir.
     */
    public static void showMenu() {
        System.out.println("1 - Pastie yazı gonder");
        System.out.println("2 - CIKIS");
        System.out.println();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Paste paste=new Paste();

        while (true) {
            showMenu();
            int secim = scanner.nextInt();
            if (secim == 1) {
                StringBuilder sb=new StringBuilder();
                while (true) {
                    
                    System.out.println("Satır gir:");
                    Scanner s = new Scanner(System.in);
                    String dump = s.nextLine()+"\n";
                    sb.append(dump);

                    char c;
                    System.out.println("Satır Eklesın mı? (E-Evet,H-Hayır)");
                    c = scanner.next().toLowerCase().charAt(0);
                    if (c == 'e') {
                        continue;
                    } else {
                        break;
                    }
                }
                String data = sb.toString();

                String url = paste.postText(data);
                
                

                char c;
                System.out.println("Browserda acılsın mı? (E-Evet,H-Hayır)");
                c = scanner.next().toLowerCase().charAt(0);
                
                if(c=='e'){
                    paste.showOnBrowser(url);
                }
                
                

            } else if (secim == 2) {
                System.exit(0);
            } else {
                System.out.println("Hatali giris");
            }
        }

    }

}
