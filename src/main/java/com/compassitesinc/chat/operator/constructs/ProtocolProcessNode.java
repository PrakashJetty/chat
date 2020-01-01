package com.compassitesinc.chat.operator.constructs;


/**
 * Created by prakashjetty  on 2/5/18.
 */
public class ProtocolProcessNode {

    private ProtocolProcessTask nodeValue;

    private ProtocolProcessNode yesNode;

    private ProtocolProcessNode noNode;

    public ProtocolProcessNode() {
    }

    public ProtocolProcessNode(ProtocolProcessTask nodeValue, ProtocolProcessNode yesNode, ProtocolProcessNode noNode) {
        this.nodeValue = nodeValue;
        this.yesNode = yesNode;
        this.noNode = noNode;
    }


    public ProtocolProcessTask getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(ProtocolProcessTask nodeValue) {
        this.nodeValue = nodeValue;
    }

    public ProtocolProcessNode getNoNode() {
        return noNode;
    }

    public void setNoNode(ProtocolProcessNode noNode) {
        this.noNode = noNode;
    }

    public ProtocolProcessNode getYesNode() {
        return yesNode;
    }

    public void setYesNode(ProtocolProcessNode yesNode) {
        this.yesNode = yesNode;
    }
}
