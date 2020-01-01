package com.compassitesinc.chat.operator.constructs;


/**
 * Created by prakashjetty on 11/15/18.
 */
public class ProtocolProcessFlowConfigurer {


    private ProtocolProcessNode currentNode;

    private ProtocolProcessNode booleanNode;


    public ProtocolProcessFlowConfigurer addFlow( ProtocolProcessNode startNode) {

        this.currentNode = startNode;
        return this;
    }

    public ProtocolProcessFlowConfigurer addYesNode(ProtocolProcessNode yesNode) {
        this.currentNode.setYesNode(yesNode);
        this.currentNode = yesNode;
        return this;
    }

    public ProtocolProcessFlowConfigurer addNoNode(ProtocolProcessNode noNode) {
        this.currentNode.setNoNode(noNode);
        this.currentNode = noNode;
        return this;
    }

    public ProtocolProcessFlowConfigurer addBooleanNode(ProtocolProcessNode yesNode, ProtocolProcessNode noNode) {
        this.currentNode.setYesNode(yesNode);
        this.currentNode.setNoNode(noNode);

        this.booleanNode = this.currentNode;
        this.currentNode = yesNode;
        return this;
    }

    public ProtocolProcessFlowConfigurer or() {
        this.currentNode = this.booleanNode.getNoNode();
        this.booleanNode = null;
        return this;
    }

    public ProtocolProcessNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(ProtocolProcessNode currentNode) {
        this.currentNode = currentNode;
    }

    public ProtocolProcessNode getBooleanNode() {
        return booleanNode;
    }

    public void setBooleanNode(ProtocolProcessNode booleanNode) {
        this.booleanNode = booleanNode;
    }
}
