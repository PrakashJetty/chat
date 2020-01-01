package com.compassitesinc.chat.operator.constructs;

import org.springframework.data.redis.core.RedisTemplate;

import java.nio.file.Path;

/**
 * Created by prakashjetty on 11/15/18.
 */
public class ProtocolProcessFlowConfiguration {

    private static ProtocolProcessNode startNode;

    public static void configure(Path rootLocation, RedisTemplate redisTemplate) {
        ProtocolProcessFlowConfigurer protocolProcessFlowConfigurer =
                new ProtocolProcessFlowConfigurer();
        ConvertAndCheckMessageIntegrityTask convertAndCheckMessageIntegrityTask =
                new ConvertAndCheckMessageIntegrityTask(rootLocation);
        ProtocolProcessNode convertAndCheckMessageIntegrityTaskNode = new ProtocolProcessNode();
        convertAndCheckMessageIntegrityTaskNode.setNodeValue(convertAndCheckMessageIntegrityTask);
        ProtocolProcessNode startNOde = new ProtocolProcessNode();
        ChannelMessageProcessorTask channelMessageProcessorTask = new ChannelMessageProcessorTask(rootLocation);
        startNOde.setNodeValue(channelMessageProcessorTask);
        PlaceInClientQueuetask placeInClientQueuetaskSucess = new PlaceInClientQueuetask(redisTemplate);
        ProtocolProcessNode placeInClientQueuetaskSucessNode = new ProtocolProcessNode();
        placeInClientQueuetaskSucessNode.setNodeValue(placeInClientQueuetaskSucess);
        placeInClientQueuetaskSucessNode.setNodeValue(placeInClientQueuetaskSucess);
        PlaceInClientQueuetask placeInClientQueuetaskFailure = new PlaceInClientQueuetask(redisTemplate);
        ProtocolProcessNode placeInClientQueuetaskFailureNode = new ProtocolProcessNode();
        placeInClientQueuetaskFailureNode.setNodeValue(placeInClientQueuetaskFailure);
        protocolProcessFlowConfigurer
                .addFlow(startNOde)
                .addBooleanNode(convertAndCheckMessageIntegrityTaskNode, placeInClientQueuetaskFailureNode)
                .addBooleanNode(placeInClientQueuetaskSucessNode, placeInClientQueuetaskFailureNode);
        startNode = startNOde;
    }

    public static ProtocolProcessNode getStartNode() {
        return startNode;
    }

    public static void setStartNode(ProtocolProcessNode startNode) {
        ProtocolProcessFlowConfiguration.startNode = startNode;
    }
}
