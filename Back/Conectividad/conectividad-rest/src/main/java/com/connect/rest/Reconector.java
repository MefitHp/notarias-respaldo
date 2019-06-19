package com.connect.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.palestra.notaria.util.JonasBotCon;

@SuppressWarnings("serial")
public class Reconector extends HttpServlet
{
 
    public void init() throws ServletException
    {
         new JonasBotCon();
    }
}
