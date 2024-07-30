package com.example.websocketserver;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableScheduling
@Component
public class WebSocketServer implements WebSocketHandler {
    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Cliente conectado: " + session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        String jsonMessage1 = """
            {
                "event": "App\\\\Events\\\\DadosEnviadosParaTablets",
                "data": "{\\"dados\\":{\\"tipo\\":2,\\"comando\\":\\"ON\\",\\"mensagem\\":\\"Ativar modo kiosk\\",\\"serial_tablet\\":\\"EMULATOR34X1X20X0\\"}}",
                "channel": "dados-tablets-EMULATOR34X1X20X0"
            }
        """;

        String jsonMessage2 = """
            {
                "event": "App\\\\Events\\\\DadosEnviadosParaTablets",
                "data": "{\\"dados\\":{\\"tipo\\":1,\\"comando\\":\\"ON\\",\\"mensagem\\":\\"Ativar modo kiosk\\",\\"serial_tablet\\":\\"EMULATOR34X1X20X0\\"}}",
                "channel": "dados-tablets-EMULATOR34X1X20X0"
            }
        """;

        String jsonMessage3 = """
            {
                "event": "App\\\\Events\\\\DadosEnviadosParaTablets",
                "data": "{\\"dados\\":{\\"tipo\\":1,\\"comando\\":\\"OFF\\",\\"mensagem\\":\\"Ativar modo kiosk\\",\\"serial_tablet\\":\\"EMULATOR34X1X20X0\\"}}",
                "channel": "dados-tablets-EMULATOR34X1X20X0"
            }
        """;
        System.out.println("Mensagem recebida: " + message.getPayload());
        if (message.getPayload().equals("teste")){
            System.out.println("entro no if de dados");
            for (WebSocketSession wsSession : sessions.values()) {
                System.out.println(wsSession.getId());
                if (wsSession.isOpen()) {
                    try {
                        wsSession.sendMessage(new TextMessage(jsonMessage1));
                    } catch (IOException e) {
                        System.err.println("Erro ao enviar mensagem periódica: " + e.getMessage());
                    }
                }
            }

        } else if (message.getPayload().equals("teste1")) {
            System.out.println("entro no if de ativar o kiosk");
            for (WebSocketSession wsSession : sessions.values()) {
                System.out.println(wsSession.getId());
                if (wsSession.isOpen()) {
                    try {
                        wsSession.sendMessage(new TextMessage(jsonMessage2));
                    } catch (IOException e) {
                        System.err.println("Erro ao enviar mensagem periódica: " + e.getMessage());
                    }
                }
            }
        } else if (message.getPayload().equals("teste2")) {
            System.out.println("entro no if de desativar o kiosk");
            for (WebSocketSession wsSession : sessions.values()) {
                System.out.println(wsSession.getId());
                if (wsSession.isOpen()) {
                    try {
                        wsSession.sendMessage(new TextMessage(jsonMessage3));
                    } catch (IOException e) {
                        System.err.println("Erro ao enviar mensagem periódica: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("Erro de transporte: " + session.getId() + " " + exception.getMessage());
        sessions.remove(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("Cliente desconectado: " + session.getId());
        sessions.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

//    @Scheduled(fixedRate = 5000)  // Agendado para executar a cada 30 segundos
//    public void sendMessagePeriodically() {
//        String jsonMessage = """
//            {
//                "event": "App\\\\Events\\\\DadosEnviadosParaTablets",
//                "data": "{\\"dados\\":{\\"tipo\\":2,\\"comando\\":\\"ON\\",\\"mensagem\\":\\"Ativar modo kiosk\\",\\"serial_tablet\\":\\"EMULATOR34X1X20X0\\"}}",
//                "channel": "dados-tablets-EMULATOR34X1X20X0"
//            }
//        """;
//
//        for (WebSocketSession session : sessions.values()) {
//            if (session.isOpen()) {
//                try {
//                    session.sendMessage(new TextMessage(jsonMessage));
//                } catch (IOException e) {
//                    System.err.println("Erro ao enviar mensagem periódica: " + e.getMessage());
//                }
//            }
//        }
//    }
}