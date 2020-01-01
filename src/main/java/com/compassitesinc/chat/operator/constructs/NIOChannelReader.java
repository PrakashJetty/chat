package com.compassitesinc.chat.operator.constructs;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by prakashjetty on 12/6/18.
 */
public class NIOChannelReader implements Runnable {

    private SocketChannel client;
    private SelectionKey key;
    private Selector selector;
    private String topicName;

    public NIOChannelReader() {
    }

    public NIOChannelReader(SocketChannel client, SelectionKey key) {
        this.client = client;
        this.key = key;
    }


    public NIOChannelReader(SocketChannel client, SelectionKey key, Selector selector) {
        this.client = client;
        this.key = key;
        this.selector = selector;
    }

    public NIOChannelReader(SocketChannel client, SelectionKey key, Selector selector, String topicName) {
        this.client = client;
        this.key = key;
        this.selector = selector;
        this.topicName = topicName;
    }

    @Override
    public void run() {
        ByteBuffer rbyteBuffer = ByteBuffer.allocate(10000024);
        ByteBuffer wbyteBuffer = ByteBuffer.allocate(1024);


        List<Byte> resbytes = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ChannelMessage channelMessage = new ChannelMessage();
            String currentFile = ChannelFileMap.getChannelFileMap().get(client);
            String directory = null;
            if (currentFile == null) {
                currentFile = UUID.randomUUID().toString() + ".json";

            }
            File file = new File(currentFile);
            FileOutputStream wChannel = new FileOutputStream(file, true);

//            System.out.print("Read: " + client.getLocalAddress().toString());
            int read = client.read(rbyteBuffer);
            int count = 0;
            while (read > 0) {
//                int count = rbyteBuffer.position();
                count += read;
//                Byte[] bytes = new Byte[read];
                //wChannel.write(rbyteBuffer);
//                resbytes.addAll(Arrays.asList(bytes));
                read = client.read(rbyteBuffer);
            }
            if (count > 0) {
                try {
                    String startString = new String(Arrays.copyOfRange(rbyteBuffer.array(), 0, 11), "UTF-8");
                    if (startString.equalsIgnoreCase("$$$START$$$") || ChannelFileMap.getChannelFileMap().get(client) != null) {
                        if (startString.equalsIgnoreCase("$$$START$$$")) {
                            ChannelFileMap.getChannelFileMap().put(client, currentFile);
                        }
                        IOUtils.copyLarge(new ByteArrayInputStream(rbyteBuffer.array(), 0, count), wChannel);
                        byte[] latestFileBytes = Arrays.copyOfRange(rbyteBuffer.array(), 0, count);
                        String endString = new String(Arrays.copyOfRange(latestFileBytes, latestFileBytes.length - 9, latestFileBytes.length), "UTF-8");
                        if (!endString.equalsIgnoreCase("$$$END$$$")) {
                            wChannel.close();
                            return;
                        }
                        ChannelFileMap.getChannelFileMap().remove(client);
                        //, 0, rbyteBuffer.position());
                        channelMessage.setRequestType("SENDFILE");
                    } else {
                        stringBuilder.append(new String(rbyteBuffer.array(), "UTF-8"));
                        channelMessage = ChatCommMessageEncoder.deserialize(stringBuilder.toString().trim());
                        if (channelMessage.getRequestType() == null) {
                            channelMessage.setRequestType("MSGTEXT");
                        }

                    }
                    wChannel.close();
//            if (stringBuilder.length() > 0) {


//                    byte[] sbytes = new byte[count];
//                    int index = 0;
//                    for (Byte aByte : resbytes) {
//                        sbytes[index] = aByte.byteValue();
//                        ++index;
//                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    client.write(wbyteBuffer.wrap("Message Corrupted".getBytes("UTF-8")));
                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                if (channelMessage != null) {
                    if (channelMessage.getMessage() != null && channelMessage.getMessage().equalsIgnoreCase("INITREADPING")) {
                        System.out.print("INIT PING came for read: " + client.getLocalAddress().toString());
                        //ChannelUserMap.getChannelUserMap().put(channelMessage.getFromUserId(), client);
//                        SocketInternalPublisher socketInternalPublisher = new SocketInternalPublisher(this.getTopicName());
//                        ChannelMessageContext channelMessageContext = new ChannelMessageContext();
//                        channelMessageContext.setChannelMessage(channelMessage);
//                        channelMessageContext.getChannelMessage().setRequestType("INIT");
//                        channelMessageContext.setTopic(this.getTopicName());
//                        channelMessageContext.setClient(client);
//                        socketInternalPublisher.publishSocket(ChannelContextSerializerDeserializer.serialize(channelMessageContext));
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    } else if (channelMessage.getMessage() != null && channelMessage.getMessage().equalsIgnoreCase("INITWRITEPING")) {
                        System.out.print("INIT PING came for write: " + client.getLocalAddress().toString());
                        ChannelUserMap.getChannelUserMap().put(channelMessage.getFromUserId(), client);
                        UserChannelMap.getChannelUserMap().put(client, channelMessage.getFromUserId());
                        SocketInternalPublisher socketInternalPublisher = new SocketInternalPublisher(this.topicName);
                        ChannelMessageContext channelMessageContext = new ChannelMessageContext();
                        channelMessageContext.setChannelMessage(channelMessage);
                        channelMessageContext.getChannelMessage().setRequestType("INIT");
                        channelMessageContext.setTopic(this.topicName);
                        channelMessageContext.setClient(client);
                        socketInternalPublisher.publishSocket(ChannelContextSerializerDeserializer.serialize(channelMessageContext));
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    } else {
                        SocketInternalPublisher socketInternalPublisher = new SocketInternalPublisher(this.topicName);
                        ChannelMessageContext channelMessageContext = new ChannelMessageContext();
                        channelMessageContext.setChannelMessage(channelMessage);
                        channelMessageContext.setTopic(this.topicName);
                        channelMessageContext.setClient(client);
                        channelMessageContext.setFileDirectory(currentFile);
                        socketInternalPublisher.publishSocket(ChannelContextSerializerDeserializer.serialize(channelMessageContext));
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                }
            } else {
                key.cancel();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
