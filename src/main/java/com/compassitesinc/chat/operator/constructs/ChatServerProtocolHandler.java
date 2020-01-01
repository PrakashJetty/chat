package com.compassitesinc.chat.operator.constructs;

public class ChatServerProtocolHandler implements Runnable {

    //private IChatProtocolHelicProcessor hp;
    //private ChatCommunication comm;
    private ChannelMessageContext comm;

    public ChatServerProtocolHandler(ChannelMessageContext comm) {
        this.comm = comm;
    }

    public ChatServerProtocolHandler() {
    }
    //    public ChatServerProtocolHandler(IChatProtocolHelicProcessor hp) {
//        super();
//        this.hp = hp;
//    }
//
//
//    public ChatServerProtocolHandler(ChatProtocolHelicProcessor hp, ChannelMessageContext comm) {
//        super();
//        this.hp = hp;
//        this.comm = comm;
//
//    }
//
//
//    public void process(IChatProtocolLayerProcessor lp, ChannelMessage comm) {
//        // TODO Auto-generated method stub
//        //lp.processProtocolLayer(comm);
//    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
//		IChatProtocolDeviation d = hp.processLayers(comm);
//		//ChatCommunication comm = new ChatCommunication();
//		comm.setDev(d);
//		System.out.println(" sending message to out topic....");
//		System.out.println(d.getType());
//
//		ChatCommProducerFactory.getInstance().getInstance("kafka", "socket-out-topic").send(comm);
//		System.out.println(" sent message to out topic....");

        // handle Outer Layer
        // if success get inner layers
        //     handle inner layer
        // if Deviation
        //     handle deviations

        //latest poc
//		SocketChannel serverSocket = ChannelUserMap.getChannelUserMap().get(this.comm.getToUserId());
//		if (serverSocket != null) {
//			Queue<ChannelMessage> queue = ChannelMessageQueue.getChannelMap().get(serverSocket);
//			if (queue != null ) {
//				queue.add(this.comm);
//			}
//		}
        TaskFlowExecutor taskFlowExecutor = new TaskFlowExecutor();
        taskFlowExecutor.execute(ProtocolProcessFlowConfiguration.getStartNode(), this.comm);
    }

    public ChannelMessageContext getComm() {
        return comm;
    }

    public void setComm(ChannelMessageContext comm) {
        this.comm = comm;
    }
}
