package com.lacuevita305.adPublisher.utils;

import java.io.IOException;

public class UtilsBash {

    public static void ejecutarBat(String bashName, String googleProfile, String  debuggingPort) {
        try {

            // Crear el proceso con argumentos
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", bashName, googleProfile, debuggingPort
            );

            // Redirigir errores al mismo flujo
            builder.redirectErrorStream(true);

            // Ejecutar
            Process proceso = builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

