package com.patrones.u2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * NotifierFactory: Factory Method con registro dinamico.
 * Aplica OCP: nuevos tipos se registran sin modificar logica existente.
 */
public class NotifierFactory {

    private static final Map<String, Supplier<Notifier>> REGISTRY = new HashMap<>();

    static {
        // Registro inicial
        REGISTRY.put("email", EmailNotifier::new);
        REGISTRY.put("sms", SmsNotifier::new);
        REGISTRY.put("push", PushNotifier::new);
    }

    /**
     * Permite registrar nuevos notificadores en tiempo de ejecución
     */
    public static void register(String type, Supplier<Notifier> factory) {
        REGISTRY.put(type.toLowerCase(), factory);
    }

    /**
     * Crea un notificador según el tipo
     */
    public static Notifier create(String type) {
        Supplier<Notifier> factory = REGISTRY.get(type.toLowerCase());

        if (factory == null) {
            throw new IllegalArgumentException(
                "Tipo de notificador no registrado: " + type +
                ". Tipos disponibles: " + REGISTRY.keySet()
            );
        }

        return factory.get();
    }
}