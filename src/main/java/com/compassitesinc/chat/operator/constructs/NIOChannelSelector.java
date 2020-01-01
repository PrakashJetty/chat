package com.compassitesinc.chat.operator.constructs;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by prakashjetty on 11/12/18.
 */
public class NIOChannelSelector implements Runnable {


    private Selector selector;
    private boolean selecting = true;
    private String name;
    private String topicName;
    private ExecutorService consumerExecutors;
    private RedisTemplate<String, ChannelMessage> redisTemplate;

    private Queue<ChannelMessage> messageQueue = new LinkedBlockingQueue<>();


    public NIOChannelSelector(Selector selector) {
        this.selector = selector;
    }

    public NIOChannelSelector(Selector selector, String name, String topicName) {
        this.selector = selector;
        this.name = name;
        this.topicName = topicName;
        consumerExecutors = Executors.newFixedThreadPool(6);

    }

    public NIOChannelSelector(Selector selector, String name, String topicName, RedisTemplate redisTemplate) {
        this.selector = selector;
        this.name = name;
        this.topicName = topicName;
        this.redisTemplate = redisTemplate;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public void run() {


        while (selecting) {
            try {
                selector.selectNow();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();

                while (iter.hasNext()) {

                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isAcceptable()) {
                        register(selector, (ServerSocketChannel) key.channel());
                    }

                    if (key.isReadable()) {
                        read(key);
                    }

                    if (key.isValid() && key.isWritable()) {
                        write(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {
        SocketChannel client = serverSocket.accept();
        if (client != null) {
            client.configureBlocking(false);
            SelectionKey selectionKey = client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            SelectionKeyUserIdMap.getSelectionKeyConcurrentHashMap().put(client, selectionKey);
            System.out.println(" Registering.....");
            ChannelMessageQueue.getChannelMap().put(client, new PriorityBlockingQueue<ChannelMessage>());
        }
    }

    private void read(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();

        NIOChannelReader nioChannelReader = new NIOChannelReader(client, key, selector, topicName);
        try {
            this.consumerExecutors.submit(nioChannelReader).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//        ByteBuffer rbyteBuffer = ByteBuffer.allocate(10000024);
//        ByteBuffer wbyteBuffer = ByteBuffer.allocate(1024);
//
//
//        List<Byte> resbytes = new ArrayList<>();
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            ChannelMessage channelMessage = new ChannelMessage();
//            String currentFile = ChannelFileMap.getChannelFileMap().get(client);
//            String directory = null;
//            if (currentFile == null) {
//                currentFile = UUID.randomUUID().toString() + ".json";
//
//            }
//            File file = new File(currentFile);
//            FileOutputStream wChannel = new FileOutputStream(file, true);
//
////            System.out.print("Read: " + client.getLocalAddress().toString());
//            int read = client.read(rbyteBuffer);
//            int count = 0;
//            while (read > 0) {
////                int count = rbyteBuffer.position();
//                count += read;
////                Byte[] bytes = new Byte[read];
//                //wChannel.write(rbyteBuffer);
////                resbytes.addAll(Arrays.asList(bytes));
//                read = client.read(rbyteBuffer);
//            }
//            if (count > 0) {
//                try {
//                    String startString = new String(Arrays.copyOfRange(rbyteBuffer.array(), 0, 11), "UTF-8");
//                    if (startString.equalsIgnoreCase("$$$START$$$") || ChannelFileMap.getChannelFileMap().get(client) != null) {
//                        if (startString.equalsIgnoreCase("$$$START$$$")) {
//                            ChannelFileMap.getChannelFileMap().put(client, currentFile);
//                        }
//                        IOUtils.copyLarge(new ByteArrayInputStream(rbyteBuffer.array(), 0, count), wChannel);
//                        byte[] latestFileBytes = Arrays.copyOfRange(rbyteBuffer.array(), 0, count);
//                        String endString = new String(Arrays.copyOfRange(latestFileBytes, latestFileBytes.length - 9, latestFileBytes.length), "UTF-8");
//                        if (!endString.equalsIgnoreCase("$$$END$$$")) {
//                            wChannel.close();
//                            return;
//                        }
//                        ChannelFileMap.getChannelFileMap().remove(client);
//                        //, 0, rbyteBuffer.position());
//                        channelMessage.setRequestType("SENDFILE");
//                    } else {
//                        stringBuilder.append(new String(rbyteBuffer.array(), "UTF-8"));
//                        channelMessage = ChatCommMessageEncoder.deserialize(stringBuilder.toString().trim());
//                        if (channelMessage.getRequestType() == null) {
//                            channelMessage.setRequestType("MSGTEXT");
//                        }
//
//                    }
//                    wChannel.close();
////            if (stringBuilder.length() > 0) {
//
//
////                    byte[] sbytes = new byte[count];
////                    int index = 0;
////                    for (Byte aByte : resbytes) {
////                        sbytes[index] = aByte.byteValue();
////                        ++index;
////                    }
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                    client.write(wbyteBuffer.wrap("Message Corrupted".getBytes("UTF-8")));
//                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                }
//                if (channelMessage != null) {
//                    if (channelMessage.getMessage() != null && channelMessage.getMessage().equalsIgnoreCase("INITREADPING")) {
//                        System.out.print("INIT PING came for read: " + client.getLocalAddress().toString());
//                        //ChannelUserMap.getChannelUserMap().put(channelMessage.getFromUserId(), client);
////                        SocketInternalPublisher socketInternalPublisher = new SocketInternalPublisher(this.getTopicName());
////                        ChannelMessageContext channelMessageContext = new ChannelMessageContext();
////                        channelMessageContext.setChannelMessage(channelMessage);
////                        channelMessageContext.getChannelMessage().setRequestType("INIT");
////                        channelMessageContext.setTopic(this.getTopicName());
////                        channelMessageContext.setClient(client);
////                        socketInternalPublisher.publishSocket(ChannelContextSerializerDeserializer.serialize(channelMessageContext));
//                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                    } else if (channelMessage.getMessage() != null && channelMessage.getMessage().equalsIgnoreCase("INITWRITEPING")) {
//                        System.out.print("INIT PING came for write: " + client.getLocalAddress().toString());
//                        ChannelUserMap.getChannelUserMap().put(channelMessage.getFromUserId(), client);
//                        SocketInternalPublisher socketInternalPublisher = new SocketInternalPublisher(this.getTopicName());
//                        ChannelMessageContext channelMessageContext = new ChannelMessageContext();
//                        channelMessageContext.setChannelMessage(channelMessage);
//                        channelMessageContext.getChannelMessage().setRequestType("INIT");
//                        channelMessageContext.setTopic(this.getTopicName());
//                        channelMessageContext.setClient(client);
//                        socketInternalPublisher.publishSocket(ChannelContextSerializerDeserializer.serialize(channelMessageContext));
//                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                    } else {
//                        SocketInternalPublisher socketInternalPublisher = new SocketInternalPublisher(this.getTopicName());
//                        ChannelMessageContext channelMessageContext = new ChannelMessageContext();
//                        channelMessageContext.setChannelMessage(channelMessage);
//                        channelMessageContext.setTopic(this.getTopicName());
//                        channelMessageContext.setClient(client);
//                        channelMessageContext.setFileDirectory(currentFile);
//                        socketInternalPublisher.publishSocket(ChannelContextSerializerDeserializer.serialize(channelMessageContext));
//                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                    }
//                }
//                //}
////                        }
//                // }
//
//
//            } else {
//                key.cancel();
//                client.close();
//            }
//        } catch (
//                IOException e
//                )
//
//        {
//            e.printStackTrace();
//        }

    }

    private void write(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        NIOChannelWriter nioChannelWriter = new NIOChannelWriter(client, key, selector, redisTemplate);
        this.consumerExecutors.submit(nioChannelWriter);
//        try {
//            Queue<ChannelMessage> queue = ChannelMessageQueue.getChannelMap().get(client);
//            if (!queue.isEmpty()) {
//                ChannelMessage channelMessage = ChannelMessageQueue.getChannelMap().get(client).remove();
//                if (channelMessage != null) {
//                    byte[] bytes = ChatCommMessageEncoder.serialize(channelMessage);
//                    ByteBuffer byteBuffer = ByteBuffer.allocate((bytes.length + 8));
//                    byteBuffer.putLong(bytes.length);
//                    byteBuffer.put(bytes);
//                    byteBuffer.flip();
//                    int write = 0;
//                    System.out.println(" Before Written to client:" + write + " bytes remaining:" + byteBuffer.remaining());
//                    int count = 0;
//
////                    write = client.write(byteBuffer);
////                    System.out.println(" First Written to client:" + write + " bytes remaining:" + byteBuffer.remaining());
//                    while (byteBuffer.position() != byteBuffer.capacity()) {
//                        count += write;
//                        write = client.write(byteBuffer);
//                        System.out.println(" Written to client:" + write + " bytes remaining:" + byteBuffer.remaining());
//
//                    }
//                    System.out.println("bytes:" + bytes.length);
//                    System.out.println("writecount:" + count);
//                    System.out.println(write);
//                    byteBuffer.clear();
//                }
//            }
//            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
