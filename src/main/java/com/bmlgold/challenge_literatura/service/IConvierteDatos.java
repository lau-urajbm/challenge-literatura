package com.bmlgold.challenge_literatura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T>clase);

}
