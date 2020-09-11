/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.fincomun.tenderos.bean;

import static mx.com.fincomun.tenderos.util.Util.gson;

/**
 *
 * @author desarrollo5
 */
public class ResponseSMSBean {

    private int codigo;
    private String mensaje;
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    @Override
    public String toString(){
        return gson.toJson(this);
    }
}
