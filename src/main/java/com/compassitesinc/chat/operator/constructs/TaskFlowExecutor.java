package com.compassitesinc.chat.operator.constructs;


/**
 * Created by prakashjetty on 2/1/18.
 */
public class TaskFlowExecutor {


    public void execute(ProtocolProcessNode taskNode, ChannelMessageContext taskContext) {
        taskNode.getNodeValue().processStep(taskContext);
        while ((taskContext.isSuccess() && taskNode.getYesNode() != null) || (!taskContext.isSuccess() && taskNode.getNoNode() != null)) {

            if (taskContext.isSuccess() && taskNode.getYesNode() != null) {
                taskNode = taskNode.getYesNode();
//                TaskFlowStep taskFlowStep = taskNode.getTaskFlowStep();
//                taskContext.setTaskFlowStep(taskFlowStep != null ? taskFlowStep : taskContext.getTaskFlowStep());
                taskNode.getNodeValue().processStep(taskContext);
            } else if (!taskContext.isSuccess() && taskNode.getNoNode() != null) {
                taskNode = taskNode.getNoNode();
//                TaskFlowStep taskFlowStep = taskNode.getTaskFlowStep();
//                taskContext.setTaskFlowStep(taskFlowStep != null ? taskFlowStep : taskContext.getTaskFlowStep());
                taskNode.getNodeValue().processStep(taskContext);
            }
        }
    }


}
