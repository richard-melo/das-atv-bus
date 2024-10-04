package br.univille.eventos.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

@RestController
@RequestMapping("/api")
public class HomeControllerAPI {
    @Autowired
    @Qualifier("topicSenderClient")
    private ServiceBusSenderClient topicSenderClient;
    @Autowired
    @Qualifier("queueSenderClient")
    private ServiceBusSenderClient queueSenderClient;
    @Autowired
    @Qualifier("topicProcessorClient")
    private ServiceBusProcessorClient topicProcessorClient;
    @Autowired
    @Qualifier("queueProcessorClient")
    private ServiceBusProcessorClient queueProcessorClient;

    @PostMapping("/topic/enviar")
    public ResponseEntity<?> topicSend(@RequestBody String msg) {
        System.out.println(msg);
        topicSenderClient.sendMessage(new ServiceBusMessage(msg));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/queue/enviar")
    public ResponseEntity<?> queueSend(@RequestBody String msg) {
        System.out.println(msg);
        queueSenderClient.sendMessage(new ServiceBusMessage(msg));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/topic/receber")
    public ResponseEntity<?> topicReceive() {
        topicProcessorClient.start();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/queue/receber")
    public ResponseEntity<?> queueReceive() {
        queueProcessorClient.start();
        return ResponseEntity.ok().build();
    }
}